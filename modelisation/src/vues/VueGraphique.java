package vues;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import modeles.GraphModel;
import modeles.Serie;
import modeles.SerieGraph;
import modeles.Updater;

public class VueGraphique implements Observer{
 
	Tab tab;
	LineChart<Number, Number> lineChart;
	TreeView<String> treeView;
	LinkedHashMap<SerieGraph, XYChart.Series<Number, Number>> chart;
	GraphModel gm;
	
	/**
	 * Creer une nouvelle vue graphique
	 * @param scene La scene dans laquelle le graphique apparaitra
	 * @param gm Le GraphModel correspondant au graphique
	 */
	public VueGraphique(Tab tab, GraphModel gm){
		this.tab = tab;
		this.gm = gm;
		chart = new LinkedHashMap<>();
	}
	
	/**
	 * Initialise le graphique
	 */
	public void init(){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Temps");
        lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        lineChart.setTitle("Series");
        lineChart.setCreateSymbols(false);
        TreeItem<String> root = new TreeItem<>("Hided");
        treeView = new TreeView<>(root);
        treeView.setShowRoot(false);
        HBox hbox = new HBox();
		hbox.getChildren().addAll(treeView, lineChart);
		tab.setContent(hbox);
	}
	
	/**
	 * Affiche la ou les courbes donnees
	 * @param ALseries
	 */
	public void afficherCourbes(ArrayList<SerieGraph> ALseries) {
		tab.setText(gm.getNom());
		for(SerieGraph sg : ALseries){
			this.addCourbe(sg);
		}
		this.updateLegend();
    }
    
	/**
	 * Ajoute une courbe au graphique
	 * @param sg
	 */
	public void addCourbe(SerieGraph sg){
		Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName(sg.getSerie().getNom());
		Serie s = sg.getSerie();
	    HashMap<Integer, Double> data = s.getSerie();
		for(Integer j : data.keySet()){
			series.getData().add(new XYChart.Data<Number, Number>(j, data.get(j)));
		}
	    lineChart.getData().add(series);
	    
	    series.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, e->{
	    	Node node = chart.get(sg).getNode();
	    	node.setStyle("-fx-stroke-width: 5px;");
	    });
	    series.getNode().addEventHandler(MouseEvent.MOUSE_EXITED_TARGET,  e->{
	    	Node node = chart.get(sg).getNode();
	    	node.setStyle("-fx-stroke-width: 3px;");
	    });
	    series.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
	    	//list.getSelectionModel().select(sg.getNom());
	    });
	    
	    ContextMenu cm = new ContextMenu();
	    MenuItem transformation = new MenuItem("Transformation");
	    transformation.setOnAction(new EventHandler<ActionEvent>(){
	    	public void handle(ActionEvent e){
	    		TreeItem<String> ti = treeView.getSelectionModel().getSelectedItem();
	    		if(ti == null) return;
	    	}
	    });
	    MenuItem analyse = new MenuItem("Analyse");
	    MenuItem exporter = new MenuItem("Exporter");
	    MenuItem propriete = new MenuItem("Propriete");
	    MenuItem supprimer = new MenuItem("Supprimer");
	    cm.getItems().addAll(transformation, analyse, exporter, propriete, supprimer);
	    treeView.setContextMenu(cm);
	    
	    updateSerieListe();
	    chart.put(sg, series);
	    editStyle(sg);
	    this.updateLegend();
	}
	
	/**
	 * Met a jour la liste des Series disponibles dans le graph
	 */
	void updateSerieListe(){
		int j;
		ArrayList<SerieGraph> series = gm.getSeries();
		TreeItem<String> root = treeView.getRoot();
		ArrayList<TreeItem<String>> l = new ArrayList<>();
		root.getChildren().clear();
		for(int i=0; i<series.size(); i++){
			Serie s = series.get(i).getSerie();
			TreeItem<String> ti = new TreeItem<>(series.get(i).getNom());
			if(i > 0 && s.isSameFamily(series.get(i-1).getSerie()) ){
				if(s.isBrother(series.get(i-1).getSerie())){
					for(j = i; s.isBrother(series.get(j).getSerie()); j--){}
					l.get(j).getChildren().add(ti);
				}else{
					for(j = i; j >= 0; j--){
						if(s.hasParent(series.get(j).getSerie())){
							l.get(j).getChildren().add(ti);
							break;
						}
					}
				}
			}else{
				root.getChildren().add(ti);
			}
			l.add(ti);
		}
	}
	
	/**
	 * Supprime une courbe du graphique
	 * @param sg
	 */
	public void removeCourbe(SerieGraph sg){
		Series<Number, Number> series = chart.get(sg);
		lineChart.getData().remove(series);
		chart.remove(sg);
	}
	
	/**
	 * Modifie la visibilite d'une courbe
	 * @param sg
	 */
	public void editVisibilite(SerieGraph sg){
		Series<Number, Number> series = chart.get(sg);
		if(sg.isVisible()){
			lineChart.getData().add(series);
		}else{
			lineChart.getData().remove(series);
		}
	}
    
	/**
	 * Modifie le style d'une courbe
	 * @param sg
	 */
	public void editStyle(SerieGraph sg){
	    Node node = chart.get(sg).getNode();
	    int[] rgb = sg.getRgb();
	    node.setStyle("-fx-stroke: rgb("+rgb[0]+", "+rgb[1]+", "+rgb[2]+");");
	    this.updateLegend();
	}

	/**
	 * Met a jour la legende
	 */
    public void updateLegend(){
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
				int i = 0;
				for(SerieGraph sg : chart.keySet()){
					Set<Node> nodes = lineChart.lookupAll(".default-color"+(i++));
					int rgb[] = sg.getRgb();
					for(Node node : nodes){
						node.setStyle("-fx-background-color: rgb("+rgb[0]+","+rgb[1]+","+rgb[2]+")");
					}
				}
		    }
		});
	}
	
	@Override @SuppressWarnings("unchecked")
	public void update(Observable arg0, Object arg1) {
		Updater u = (Updater)arg1;
		switch(u.getDescriptif()){
			case "ajouter":
				this.addCourbe((SerieGraph)u.getArg());
				break;
			case "creer":
				this.afficherCourbes((ArrayList<SerieGraph>)u.getArg());
				break;
			case "supprimer":
				this.removeCourbe((SerieGraph)u.getArg());
				break;
			case "visibilite":
				this.editVisibilite((SerieGraph)u.getArg());
				break;
			case "style":
				this.editStyle((SerieGraph)u.getArg());
				break;
		}
	}
 
}