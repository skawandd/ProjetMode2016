package modeles;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represente un graphique avec une ou plusieurs series
 */
public class GraphModel extends Observable implements Observer{
	
	ArrayList<SerieGraph> series;
	String nom;
	static int cpt=1;

	/**
	 * Construit un nouveau modele graphique vide
	 */
	public GraphModel(){
		series = new ArrayList<SerieGraph>();
		nom="Graphique "+cpt;
		cpt++;
	}
	
	/**
	 * Construit un nouveau modele graphique
	 * @param serie Une serie qui sera contenue dans le graphique
	 */
	public GraphModel(Serie serie){
		this();
		series.add(new SerieGraph(serie));
	}
	
	/**
	 * Construit un nouveau modele graphique
	 * @param series Une ArrayList de Serie qui seront contenues dans le graphique
	 */
	public GraphModel(ArrayList<Serie> series){
		this();
		for(Serie serie : series){
			this.series.add(new SerieGraph(serie));
		}
	}
	
	/**
	 * Ajoute une Serie au graphique et la renomme si il en existe une avec un nom identique
	 * @param s
	 */
	public void addSerie(Serie s){
		SerieGraph sg = new SerieGraph(s);
		for(SerieGraph test : series)
			if(sg.getSerie() == test.getSerie()) return;
		checkNomSerie(sg);
		double goldenRatioConj = 1.618033988749895;
		float hue = new Random().nextFloat();
		hue = (float) ((goldenRatioConj*(series.size()/(5*Math.random())))%1);
		Color c = Color.getHSBColor(hue, 0.5f, 0.95f);
		int colors[] = {c.getRed(), c.getGreen(), c.getBlue()};
		sg.setRgb(colors);
		series.add(sg);
		organise();
		sg.addObserver(this);
		this.release(new Updater("ajouter", s));
	}
	
	/**
	 * Supprime une Serie du graphique
	 * @param sg
	 */
	public void removeSerie(SerieGraph sg){
		sg.deleteObserver(this);
		series.remove(sg);
		this.release(new Updater("supprimer", sg));
	}
	
	/**
	 * Verifie que la serie ne porte pas le meme nom qu'une autre deja presente sur le graph
	 * @param sg
	 */
	public void checkNomSerie(SerieGraph sg){
		String nom = sg.getNom();
		for(SerieGraph test : series){
			if(nom.equals(test.getNom()) && test != sg){
				if(sg.hasSameName()){
					Matcher m = Pattern.compile("\\((\\d+)\\)$").matcher(nom);
					m.find();
					int number = Integer.parseInt(m.group(1))+1;
					sg.setName(nom.substring(0, nom.length()-2)+number+")");
				}else{
					sg.setName(nom+"(1)");
					sg.setSameName(true);
				}
				checkNomSerie(sg);
			}
		}
	}
	
	/**
	 * Organise les series en fonctions des parents <-> enfants
	 */
	public void organise(){
		int i, j = 0, k, index= 0;
		ArrayList<SerieGraph> futureSeries = new ArrayList<>();
		for(i=0 ; i<series.size(); i++){
			k = -1;
			for(j=i; j<series.size(); j++){
				if(k == -1 || k > series.get(j).getSerie().getNbParent()){
					index = j;
					k = series.get(j).getSerie().getNbParent();
				}
			}
			if(k != -1 && series.get(index) != series.get(i)){
				series.add(i, series.get(index));
				series.remove(index+1);
				series.add(series.get(i+1));
				series.remove(i+1);
			}else break;
		}
		
		for(SerieGraph s : series){
			j = 0;
			for(i=futureSeries.size()-1; i>=0; i--){
				Serie s2 = futureSeries.get(i).getSerie();
				if(s.getSerie().hasParent(s2)){
					i++;
					break;
				}
			}
			futureSeries.add(i >= 0 ? i : 0, s);
		}
		
		series = futureSeries;
		
		for(SerieGraph s : series){
			System.out.println(s.getNom());
		}
	}

	
	public void release(){
		this.release(new Updater("creer", series));
	}
	
	public void release(Updater update){
		this.setChanged();
		this.notifyObservers(update);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.release((Updater)arg);
	}
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public ArrayList<SerieGraph> getSeries(){
		return series;
	}
}
