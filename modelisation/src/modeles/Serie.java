package modeles;
import java.io.File;
import java.io.IOException;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

import javafx.scene.chart.XYChart;
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
	}
	
	/**
	 * Charge une serie a partir d'un fichier csv
	 * @param file
	 */
	
	Serie(File file){
		nomSerie = file.getName();
		csv = new CSVDecoder(file);
		try{
			entrees = csv.decodeCsv();
		}catch(IOException e){
			
		}
	}
	
	Serie(String nomSerie, Serie parent, HashMap<Integer, Double> h){
		this.nomSerie = nomSerie;
		this.parent = parent;
		this.entrees = h;
	}
	
	public String nameWithOutExtension(String nomSerie){
		return this.nomSerie.substring(0, this.nomSerie.indexOf(".csv"));
	}
	
	public Serie transformationLogarithmique(){
		HashMap<Integer, Double> h = new HashMap<>();
		for(Integer j : entrees.keySet())
			if(entrees.get(j) > 0) h.put(j, Math.log(entrees.get(j)));
			else return null;
		Serie serie = new Serie(nameWithOutExtension(this.nomSerie)+"_log", this, h);
		this.setChanged();
		this.notifyObservers(serie);
		return serie;
	}
	
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
			serie = new Serie(nameWithOutExtension(this.nomSerie)+"_BoxCox", this, h);
		}
		this.setChanged();
		this.notifyObservers(serie);
		return serie;
	}
	
	public Serie transformationLogistique(){
		HashMap<Integer, Double> h = new HashMap<>();
		Double t;
		for(Integer j: entrees.keySet()){
			if(0 >= entrees.get(j) || entrees.get(j) >= 1)
				return null;
			t = Math.log(entrees.get(j)/(1 - entrees.get(j)));
			h.put(j, t);
		}
		Serie serie = new Serie(nameWithOutExtension(this.nomSerie)+"_logistique", this, h);
		this.setChanged();
		this.notifyObservers(serie);
		return serie;
	}

	
	public Serie transformationMoyMobile(int ordre){
		double[] ponderation = new double[ordre/2 + 1];
		for(int i = 0; i < ordre/2 + 1; i++){
			ponderation[i] = 1;
		}
		return transformationMoyMobilePonderee(ordre, ponderation);
	}
	
	public Serie transformationMoyMobilePonderee(int ordre, double[] ponderation){
		double total, coef = 0;
		if(ponderation.length != ordre/2 + 1) return null;
		if(ordre > entrees.size()) return null;
		HashMap<Integer, Double> hm = new HashMap<>();
		Integer[] e = entrees.keySet().toArray(new Integer[entrees.size()]);
		for(int i = 0; i < ponderation.length; i++)
			coef += ponderation[i]*2;
		if(ordre%2 == 0) coef -= ponderation[0];
		else coef -= ponderation[ponderation.length-1];
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
		this.setChanged();
		this.notifyObservers(serie);
		return serie;
	}
	
	public Serie transformationMoyMobilePonderee(int ordre){
		int ordrediv2 = ordre/2 + 1;
		double[] ponderation = new double[ordrediv2];
		int sumCarre = (ordrediv2*(ordrediv2+1)*(2*ordrediv2+1))/6;
		for(double i = 1; i <= ordrediv2; i++){
			ponderation[(int)i-1] = (i*i)/sumCarre;
		}
		return transformationMoyMobilePonderee(ordre, ponderation);
	}

	
	public String getNom(){ return nomSerie; }
	public void setNom(String nom){ nomSerie = nom; }
	public HashMap<Integer, Double> getSerie(){ return entrees; }
	public Serie getParent(){ return parent; }
	public ArrayList<Serie> getChildrens(){ return childrens; }
}
