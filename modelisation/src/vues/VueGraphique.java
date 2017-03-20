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
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import modeles.GraphModel;
import modeles.Serie;
import modeles.SerieGraph;
import modeles.Updater;

public class VueGraphique implements Observer{
 
	Tab tab;
	LineChart<Number, Number> lineChart;
	ListView<String> list;
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
        list = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        list.setItems(items);
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        HBox hbox = new HBox();
		hbox.getChildren().addAll(list, lineChart);
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
	    	list.getSelectionModel().select(sg.getNom());
	    });
	    
	    addSerieListe(sg);
	    chart.put(sg, series);
	    editStyle(sg);
	    this.updateLegend();
	}
	
	/**
	 * Ajoute une SerieGraph dans la liste des series
	 * @param sg
	 */
	void addSerieListe(SerieGraph sg){
		
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