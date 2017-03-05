package modeles;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import utils.CSVDecoder;

public class Serie extends Observable{
	
	private String nomSerie;
	private CSVDecoder csv;
	private HashMap<String, Integer> entrees;
	private Serie parent;
	private ArrayList<Serie> childrens;
	
	Serie(File file){
		nomSerie = file.getName();
		csv = new CSVDecoder(file);
		try{
			entrees = csv.decodeCsv();
		}catch(IOException e){
			
		}
	}
	
	public String getNom(){ return nomSerie; }
	public HashMap<String, Integer> getSerie(){ return entrees; }
	public Serie getParent(){ return parent; }
	public ArrayList<Serie> getChildrens(){ return childrens; }
}
