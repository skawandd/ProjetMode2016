package modeles;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

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
	
	public String getNom(){ return nomSerie; }
	public HashMap<String, Double> getSerie(){ return entrees; }
	public Serie getParent(){ return parent; }
	public ArrayList<Serie> getChildrens(){ return childrens; }
}
