package modeles;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import javafx.scene.chart.XYChart;
import utils.CSVDecoder;

/**
 * Represente une serie et permet d'effectuer different calcul dessus (transformation/analyse/prediction)
 */
public class Serie extends Observable{
	
	private String nomSerie;
	private CSVDecoder csv;
	private HashMap<String, Double> entrees;
	private Serie parent;
	private ArrayList<Serie> childrens;
	
	
	public Serie(){
		this.entrees = new HashMap<String, Double>();
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
	
	Serie(String nomSerie, Serie parent, HashMap<String, Double> h){
		this.nomSerie = nomSerie;
		this.parent = parent;
		this.entrees = h;
	}
	
	public Serie transformationLogarithmique(){
		HashMap<String, Double> h = new HashMap<>();
		for(String j : entrees.keySet())
			if(entrees.get(j) > 0) h.put(j, Math.log(entrees.get(j)));
		Serie serie = new Serie(this.nomSerie+"_log",this,h);
		this.setChanged();
		this.notifyObservers(serie);
		return serie;
	}
	public String getNom(){ return nomSerie; }
	public HashMap<String, Double> getSerie(){ return entrees; }
	public Serie getParent(){ return parent; }
	public ArrayList<Serie> getChildrens(){ return childrens; }
}
