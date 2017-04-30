package utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vues.VueIhm;
/**
 * 
 * Decode un fichier csv a 2 colonnes et le renvoie dans une liste.
 */

public class CSVDecoder implements Observer{
	
	private File file;
	private String file_name;
	int choix;
	ObservableList<String> items = FXCollections.observableArrayList ();
	
	public CSVDecoder(File file){
		this.file = file;
		this.file_name = file.getName();
	}
	
	/**
	 * 
	 * @return une Map associant une valeur a une date. null si le fichier ne peut etre lu.
	 */
	public HashMap<Integer, Double> decodeCsv() throws IOException{
		String elems[];
		String line;
		FileInputStream fis;
		HashMap<Integer, Double> CSV = new HashMap<Integer, Double>();
		if(!file.canRead()) return null;
		fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		while((line = br.readLine()) != null){
			elems = line.split(",");
			if(elems.length == 2){
				CSV.put(Integer.parseInt(elems[0]), Double.parseDouble(elems[1]));
			}
		}
		br.close();
		return CSV;
	}
	
	@SuppressWarnings("resource")
	public HashMap<Integer, Double> decodeCsv2(int choixAbs, int choixOrd){

		String elems[];
		String line;
		FileInputStream fis = null;
		Scanner sc = new Scanner(System.in);
		HashMap<Integer, Double> CSV = new HashMap<Integer, Double>();
		if(!file.canRead()) return null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		try {
			line=br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			while((line = br.readLine()) != null){
				elems = line.split(",");
				System.out.println(elems[choixAbs]+" "+elems[choixOrd]);
				CSV.put(Integer.parseInt(elems[choixAbs]), Double.parseDouble(elems[choixOrd]));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CSV;
}
	
	/**
	 * find column's title
	 */
	public void decodeCsv3() {

		String elems[];
		String elems1[];
		String line = null;
		//FileInputStream fis;
		HashMap<Integer, Double> CSV = new HashMap<Integer, Double>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		try {
			line=br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		elems1 = line.split(",");
		
		for(int i=0;i<elems1.length;i++){
			items.add(elems1[i]);
		}
}

	/**
	 * 
	 * @param colonne
	 * @return la colonne pour laquelle on veut les donnees.
	 */
	public Object[] getColonne(int colonne) throws IOException{
		if(colonne == 1)
			return decodeCsv().keySet().toArray(new Integer[decodeCsv().keySet().size()]);
		else if(colonne == 2) 
			return decodeCsv().values().toArray(new Double[decodeCsv().values().size()]);
		return null;
	}


	public ObservableList<String> getItems() {
		return items;
	}

	public void setItems(ObservableList<String> items) {
		this.items = items;
	}
	
	public File getFile(){
		return file;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	public String getName(){
		return this.file_name;
	}
	
}