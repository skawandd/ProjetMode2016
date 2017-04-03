package utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
	int choix;
	ObservableList<String> items =FXCollections.observableArrayList ();
	
	public CSVDecoder(File file){
		this.file = file;
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
	
	public HashMap<Integer, Double> decodeCsv2() throws IOException{
		VBox vbox = new VBox();
		ListView<String> list= new ListView <String>();
		String elems[];
		String elems1[];
		String line;
		FileInputStream fis;
		Scanner sc = new Scanner(System.in);
		HashMap<Integer, Double> CSV = new HashMap<Integer, Double>();
		if(!file.canRead()) return null;
		fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		line=br.readLine();
		elems1 = line.split(",");
		
		for(int i=0;i<elems1.length;i++){
			items.add(elems1[i]);
		}
		

		test();
		System.out.println(choix);
		
		while((line = br.readLine()) != null){
			elems = line.split(",");
			if(elems.length == 7){
			System.out.println(elems[0]);
			System.out.println(elems[choix]);
				CSV.put(Integer.parseInt(elems[0]), Double.parseDouble(elems[choix]));
			}
		}
		br.close();
		return CSV;
}
	
	public void test(){
		Stage stage= new Stage();
		VBox vbox = new VBox();
		ListView<String> list= new ListView <String>();
		list.setItems(items);
		list.getSelectionModel().getSelectedItem();
		Button btn = new Button();
		btn.setText("valider");
		vbox.getChildren().addAll(btn);
	
		
		
		
		//vbox.getChildren().addAll(list);
		Scene scene = new Scene(vbox, 800,800);
		
		stage.setScene(scene);
		 
	    stage.show();
	    
	    /*list.setOnMouseClicked(f-> {
            if(list.getSelectionModel().getSelectedIndex()==0){
            	choix=0;
            	stage.close();
            }
            if(list.getSelectionModel().getSelectedIndex()==1){
            	choix=1;
            	stage.close();
            }
            if(list.getSelectionModel().getSelectedIndex()==2){
            	choix=2;
            	stage.close();
            }
            if(list.getSelectionModel().getSelectedIndex()==3){
            	choix=3;
            	stage.close();
            }
            if(list.getSelectionModel().getSelectedIndex()==4){
            	choix=4;
            	stage.close();
            }
            if(list.getSelectionModel().getSelectedIndex()==5){
            	choix=5;
            	stage.close();
            }
            if(list.getSelectionModel().getSelectedIndex()==6){
            	choix=6;
            	stage.close();
            }
		
			});
		
		*/
		
		
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

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
}