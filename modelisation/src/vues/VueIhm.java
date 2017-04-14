package vues;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

<<<<<<< HEAD
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
=======
import javafx.collections.ObservableList;
>>>>>>> 8fa03f0420f9aad87b07d0a2098d59d96b5e133e
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modeles.GraphModel;
import modeles.Serie;
import modeles.SerieModel;
import utils.CSVDecoder;
import utils.FileExplorer;
import utils.graphCSV;

public class VueIhm implements Observer{

	Stage stage;
	Stage stage1;
	TabPane tabPane;
	SerieModel sm;
	ArrayList<GraphModel> gms;
	int choix;
	ObservableList<String> items =FXCollections.observableArrayList ();
	File file;
	/**
	 * Creer une IHM permettant de visualiser des courbes dans des graphiques, d'en charger, de les sauvegarder ...
	 * @param s Le Stage dans lequel sera affiche l'ihm
	 * @param sm Le modele serie de reference (contenant toutes les series charges)
	 */
	public VueIhm(Stage s, SerieModel sm){
		this.sm = sm;
		tabPane = new TabPane();
		gms = new ArrayList<>();
		stage = s; 
	}
	
	/**
	 * Lance l'ihm : creer les widgets et affiche la fenetre
	 */
	public void lancer(){
		MenuBar menuBar = new MenuBar();
		VBox vbox = new VBox();
		menuBar.setUseSystemMenuBar(true);
		Menu fileMenu = new Menu("Fichier"); 
		MenuItem importcsv = new MenuItem("Importer une serie depuis un csv");
		MenuItem importweb = new MenuItem("Importer depuis un serveur web");
		fileMenu.getItems().addAll(importcsv, importweb);
		Menu editMenu = new Menu("Edition"); 
		Menu helpMenu = new Menu("Aide");
		menuBar.getMenus().setAll(fileMenu, editMenu, helpMenu);
		
		Tab tabP = new Tab("+");
		tabP.setOnSelectionChanged(new EventHandler<Event>() {
		      @Override
		      public void handle(Event event) {
		    	if(tabP.isSelected()){
		    		creerNouveauGraph(new GraphModel());
		    	}
		      }
		 });
		
		importcsv.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		    	
				test();
				
		    }
		});
		
		
		tabPane.getTabs().add(tabP);
		vbox.getChildren().addAll(menuBar, tabPane);
		Scene scene = new Scene(vbox, 800, 600);
		sm.addObserver(this);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Creer un nouveau graphique dans un nouvel onglet
	 * @param gm Le modele graphique de reference
	 */
	public void creerNouveauGraph(GraphModel gm){
		HBox hbox = new HBox();
		Tab tab = new Tab(gm.getNom());
		tabPane.getTabs().add(tabPane.getTabs().size()-1,tab);
		tabPane.getSelectionModel().select(tab);
		VueGraphique vg = new VueGraphique(tabPane.getSelectionModel().getSelectedItem(), gm);
		vg.init();
		gm.addObserver(vg);
		gm.release();
		gms.add(gm);
	}
	public void test(){
		file = FileExplorer.getFile();
		// TODO replacer avec une exception si fichier impossible a lire
		
		CSVDecoder csv= new CSVDecoder(file);
		
				
					csv.decodeCsv3(file);
				
    	items=csv.getItems();
    	System.out.println(items.toString());
    	System.out.println("");
    	graphCSV graph = new graphCSV(items);
    	graph.CSV(stage,sm);
    	//test2(choix);
		}
  public void test2(int choix){
		
		
		System.out.println("Teste2");
		System.out.println(choix);
		System.out.println("");
		File files = FileExplorer.getFile();
		System.out.println(files.getName());
		sm.addSerieFromFile(files, choix);
		
		VueTerminal vt = new VueTerminal(stage,sm);
		System.out.println("");
		vt.afficherSeries();
		System.out.println("");
		GraphModel gm = vt.traiterChoixSerie();
		stage.setTitle("Resultat annalyse");
		VueIhm vi = new VueIhm(stage, sm);
		vi.lancer();
		vi.creerNouveauGraph(gm);
	}
	
<<<<<<< HEAD
	@Override
	public String toString() {
		return "VueIhm [items=" + items + "]";
	}

=======
>>>>>>> 8fa03f0420f9aad87b07d0a2098d59d96b5e133e
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
