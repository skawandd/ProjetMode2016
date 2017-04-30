package modeles;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import javafx.collections.ObservableList;
import utils.CSVDecoder;

/**
 * Represente une serie et permet d'effectuer different calcul dessus (transformation/analyse/prediction)
 */
public class Serie extends Observable{
	
	private String nomSerie;
	private CSVDecoder csv;
	private HashMap<Integer, Double> entrees;
	private Serie parent;
	private ArrayList<Serie> childrens;
	
	
	public Serie(){
		this.entrees = new HashMap<Integer, Double>();
		childrens = new ArrayList<>();
	}
	
	/**
	 * Charge une serie a partir d'un fichier csv
	 * @param file
	 */
	
	public Serie(File file,int choixAbs, int choixOrd){
		csv = new CSVDecoder(file);
		csv.decodeCsv3();
		ObservableList<String> items = csv.getItems();
		nomSerie = nameWithOutExtension(file.getName())+"("+items.get(choixAbs)+"-"+items.get(choixOrd)+")";
		entrees = csv.decodeCsv2(choixAbs, choixOrd);
		childrens = new ArrayList<>();
	}
	
	/**
	 * Charge une serie a partir de donnees
	 * @param nomSerie Le nom de la serie
	 * @param parent La serie parent, ou null si elle n'en a pas
	 * @param h Une HashMap avec le temps en abscisse et la valeur en ordonnee
	 */
	public Serie(String nomSerie, Serie parent, HashMap<Integer, Double> h){
		this.nomSerie = nomSerie;
		this.parent = parent;
		this.entrees = h;
		childrens = new ArrayList<>();
	}
	
	public String nameWithOutExtension(String nomSerie){
		return nomSerie.substring(0, nomSerie.indexOf(".csv"));
	}
	
	public Serie getOldestParent(){
		Serie s = this;
		if(s.parent == null) return null;
		while(s.parent != null){
			s = s.parent;
		}
		return s;
	}
	
	public boolean isBrother(Serie serie){
		if(serie.getParent() == null) return false;
		return serie.getParent().getChildrens().contains(this);
	}
	
	public boolean hasParent(Serie serie){
		Serie s = this;
		while( (s = s.parent) != null ){
			if(serie == s) return true;
		}
		return false;
	}
	
	public int getNbParent(){
		int compteur = 0;
		Serie s = this;
		while( (s=s.parent) != null ){
			compteur++;
		}
		return compteur;
	}
	
	public boolean isSameFamily(Serie serie){
		Serie s = getOldestParent();
		if(s == null) return false;
		LinkedList<Serie> fifo = new LinkedList<>();
		fifo.add(s);
		while(!fifo.isEmpty()){
			s = fifo.getFirst();
			if(serie == s) return true;
			fifo.removeFirst();
			for(Serie child : s.childrens)
				fifo.add(child);
		}
		return false;
	}
	
	/**
	 * Verifie que la transformation logarithmique est realisable
	 * @return true si elle est possible, false sinon
	 */
	public boolean checkLogarithmique(){
		for(Integer j : entrees.keySet())
			if(entrees.get(j) <= 0) return false;
		return true;
	}
	
	/**
	 * Effectue la transformation logarithmique
	 * @return La serie resultat ou null en cas d'erreur
	 */
	public Serie transformationLogarithmique(){
		HashMap<Integer, Double> h = new HashMap<>();
		if(!checkLogarithmique()) return null;
		for(Integer j : entrees.keySet())
			h.put(j, Math.log(entrees.get(j)));
		Serie serie = new Serie(this.nomSerie+"_log", this, h);
		childrens.add(serie);
		this.setChanged();
		this.notifyObservers(serie);
		return serie;
	}
	
	/**
	 * Effectue la transformation de BoxCox
	 * @param lambda 
	 * @return La serie resultat ou null en cas d'erreur
	 */
	public Serie transformationBoxCox(Double lambda){
		Serie serie;
		if(lambda == 0){
			serie = this.transformationLogarithmique();
			if(serie == null) return null;
			serie.nomSerie = serie.nomSerie+"_BoxCox";
		}else{
			HashMap<Integer, Double> h = new HashMap<>();
			for(Integer j : entrees.keySet())
				h.put(j,((Math.pow(entrees.get(j), lambda))-1)/lambda);
			serie = new Serie(this.nomSerie+"_BoxCox", this, h);
		}
		childrens.add(serie);
		this.setChanged();
		this.notifyObservers(serie);
		return serie;
	}
	
	/**
	 * Verifie si une transformation logistique est possible
	 * @return true si elle est possible, false sinon
	 */
	public boolean checkLogistique(){
		for(Integer j: entrees.keySet())
			if(entrees.get(j) <= 0 || entrees.get(j) >= 1)
				return false;
		return true;
	}
	
	/**
	 * Effectue la transformation logistique
	 * @return La serie resutlat ou null en cas d'erreur
	 */
	public Serie transformationLogistique(){
		HashMap<Integer, Double> h = new HashMap<>();
		Double t;
		if(!checkLogistique()) return null;
		for(Integer j: entrees.keySet()){
			t = Math.log(entrees.get(j)/(1 - entrees.get(j)));
			h.put(j, t);
		}
		Serie serie = new Serie(this.nomSerie+"_logistique", this, h);
		childrens.add(serie);
		this.setChanged();
		this.notifyObservers(serie);
		return serie;
	}			
			
	/**
	 * La transformation moyenne mobile appel transformationMoyMobilePonderee avec des ponderations egales
	 * En effet, ce n'est rien d'autre qu'une transformation moyenne mobile ponderee particuliere
	 * @param ordre
	 * @return La serie resultat ou null en cas d'erreur
	 */
	public Serie transformationMoyMobile(int ordre){
		double[] ponderation = new double[ordre/2 + 1];
		for(int i = 0; i < ordre/2 + 1; i++){
			ponderation[i] = 1;
		}
		return transformationMoyMobilePonderee(ordre, ponderation);
	}
	
	/**
	 * Effectue la transformation moyenne mobile ponderee d'une serie
	 * @param ordre
	 * @param ponderation Un tableau des ponderations a appliquer, du point le plus loin au point le plus proche
	 * ponderation[0] = ponderation Xt-ordre/2 = ponderation Xt+ordre/2 et ponderation[ponderation.length-1] = ponderation Xt
	 * @return La serie resultat ou null en cas d'erreur
	 */
	public Serie transformationMoyMobilePonderee(int ordre, double[] ponderation){
		double total, coef = 0;
		if(ponderation.length != ordre/2 + 1) return null;
		if(ordre > entrees.size()) return null;
		HashMap<Integer, Double> hm = new HashMap<>();
		Integer[] e = entrees.keySet().toArray(new Integer[entrees.size()]);
		for(int i = 0; i < ponderation.length-1; i++)
			coef += ponderation[i]*2;
		coef += ponderation[ponderation.length-1];
		if(ordre%2 == 0) coef -= ponderation[0];
		for(int i = ordre/2; i < entrees.size() - ordre/2; i++){
			total = entrees.get(e[(i-ordre/2)])*ponderation[0] + entrees.get(e[(i+ordre/2)])*ponderation[0];
			if(ordre%2==0) total /= 2;
			for(int j = 1; j < ordre/2; j++){
				double ponde = ponderation[ponderation.length-j-1];
				total += entrees.get(e[i-j])*ponde + entrees.get(e[i+j])*ponde;
			}
			total += entrees.get(e[i])*ponderation[ponderation.length-1];
			total /= coef;
			hm.put(e[i], total);
		}
		Serie serie;
		if(Arrays.stream(ponderation).distinct().count() == 1) serie = new Serie(this.nomSerie+"_LissMoyMob"+ordre, this, hm);
		else serie = new Serie(this.nomSerie+"_LissMoyMobPond"+ordre, this, hm);
		childrens.add(serie);
		this.setChanged();
		this.notifyObservers(serie);
		return serie;
	}
	
	/**
	 * Effectue une transformation moyenne mobile ponderee avec des ponderations calculees selon la formule \
	 * ponderation[i-1] = (i^2)/sum(0, ordre/2+1, i^2)
	 * @param ordre
	 * @return La serie resultat ou null en cas d'erreur
	 */
	public Serie transformationMoyMobilePonderee(int ordre){
		int ordrediv2 = ordre/2 + 1;
		double[] ponderation = new double[ordrediv2];
		int sumCarre = (ordrediv2*(ordrediv2+1)*(2*ordrediv2+1))/6;
		for(double i = 1; i <= ordrediv2; i++){
			ponderation[(int)i-1] = (i*i)/sumCarre;
		}
		//System.out.println(ponderation);
		return transformationMoyMobilePonderee(ordre, ponderation);
	}

	
	// En entrée série originale (sans transformations)
	public Serie saisonnalite(int ordre){
		Serie serie;
		Serie serieMM = this.transformationMoyMobilePonderee(ordre);
		Integer[] e = this.entrees.keySet().toArray(new Integer[entrees.size()]);
		Integer[] eM = serieMM.entrees.keySet().toArray(new Integer[entrees.size()]);
		HashMap<Integer, Double> hm = new HashMap<>();
		for(int i = ordre/2  ; i < this.entrees.size() - ordre/2; i++){
			hm.put(i,(double)( e[i]-eM[i-ordre/2]));
		}
		serie = new Serie(this.nomSerie + "_Saison" + ordre,this,hm);		
		return serie;
	}
	
	//En entrée série saisonnalite
	public Serie coeffSais(int ordre){
		if(entrees.size() % ordre != 0) return null;
		int nbIterations = entrees.size()/ordre;
		Serie serie;
		double total;
		Integer[] e = this.entrees.keySet().toArray(new Integer[entrees.size()]);
		HashMap<Integer, Double> hm = new HashMap<>();
		for(int i = 0; i < ordre ; ++i){
			total = 0;
			for(int j = 0; j < nbIterations; j++){
				total += e[i + j*ordre];
			}
			total /= nbIterations;
			hm.put(i,total);
		}
		serie = new Serie(this.nomSerie + "_coeffSais" + ordre,this,hm);
		return serie;
	}
	
	// En entrée série coefficientsSais
	public Serie coeffSaisNorm(int ordre){
		if (entrees.size() != ordre) return null;
		Serie serie;
		Integer[] e = this.entrees.keySet().toArray(new Integer[entrees.size()]);
		HashMap<Integer, Double> hm = new HashMap<>();
		double somme = 0;
		double coeff = 0;
		for(int i = 0; i < ordre; i++){
			somme += e[i];
		}
		for(int i = 0; i < ordre; i++){
			coeff = entrees.get(e[i]) - ((1/ordre)*somme);
			hm.put(e[i], coeff);
		}
		serie = new Serie(this.nomSerie + "_coeffSaisNorm" + ordre,this,hm);
		return serie;
	}
	
	//En entrée série originale (sans transformations)
	public Serie desaisonnalise(int ordre){
		Serie serie;
		Serie serieSais = this.saisonnalite(ordre);
		Serie serieCoeff = serieSais.coeffSais(ordre);		
		Serie serieCoeffNorm = serieCoeff.coeffSaisNorm(ordre);
		Integer[] e = this.entrees.keySet().toArray(new Integer[entrees.size()]);
		int nbIterations = entrees.size()/ordre;
		double total;
		Integer[] eC = serieCoeffNorm.entrees.keySet().toArray(new Integer[entrees.size()]);
		HashMap<Integer, Double> hm = new HashMap<>();
		for(int i = 0; i < ordre ; ++i){
			total = 0;
			for(int j = 0; j < nbIterations; j++){
				total = e[i + j*ordre] - eC[i];
				hm.put(i+j*ordre,total);
			}
		}
		serie = new Serie(this.nomSerie + "_desais" + ordre,this,hm);
		return serie;
	}
	
	// Calcul la variance de temps d'un échantillon
	public double variance(){
		Integer[] e = this.entrees.keySet().toArray(new Integer[entrees.size()]);
		double var =0;
		double somme = 0;
		double moyenne = 0;
		for(int i = 0; i < e.length; i++){
			somme += i+1;
		}
		moyenne = somme / e.length;
		for (int i = 0; i < e.length; i++){
			var += ((i+1-moyenne)*(i+1-moyenne));
		}
		return var / e.length;
	}
	
	
	
	public String getNom(){ return nomSerie; }
	public void setNom(String nom){ nomSerie = nom; }
	public HashMap<Integer, Double> getSerie(){ return entrees; }
	public Serie getParent(){ return parent; }
	public ArrayList<Serie> getChildrens(){ return childrens; }
	public ArrayList<Double> getListSerie(){ return new ArrayList<Double>(entrees.values());}
}
