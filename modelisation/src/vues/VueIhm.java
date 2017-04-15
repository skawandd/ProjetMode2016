package vues;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
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
import modeles.Updater;

public class VueIhm implements Observer{

	Stage stage;
	TabPane tabPane;
	SerieModel sm;
	ArrayList<GraphModel> gms;
	ObservableList<Serie> ol;
	
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
		ol = FXCollections.observableArrayList();
		Tab tabP = new Tab("+");
		tabP.setOnSelectionChanged(new EventHandler<Event>() {
		      @Override
		      public void handle(Event event) {
		    	if(tabP.isSelected()){
		    		creerNouveauGraph(new GraphModel());
		    	}
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
		Tab tab = new Tab(gm.getNom());
		tabPane.getTabs().add(tabPane.getTabs().size()-1,tab);
		tabPane.getSelectionModel().select(tab);
		VueGraphique vg = new VueGraphique(tabPane.getSelectionModel().getSelectedItem(), gm, ol);
		vg.init();
		gm.addObserver(vg);
		gm.release();
		gms.add(gm);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		Updater u = (Updater)arg1;
		switch(u.getDescriptif()){
			case "ajouter":
				ol.add((Serie)u.getArg());
		}
		
	}

}
