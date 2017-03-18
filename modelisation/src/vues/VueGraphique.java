package vues;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.io.File;




import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modeles.GraphModel;
import modeles.Serie;
import modeles.SerieGraph;
import modeles.Updater;

public class VueGraphique implements Observer{
 
	Tab tab;
	LineChart<Number, Number> lineChart;
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
	
	public void init(){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Temps");
        lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        lineChart.setTitle("Series");
        lineChart.setCreateSymbols(false);
        HBox hbox = new HBox();
		hbox.getChildren().add(lineChart);
		tab.setContent(hbox);
	}
	
	/**
	 * Initialise le graphique et affiche la ou les courbes donnees
	 * @param ALseries
	 */
	public void afficherCourbes(ArrayList<SerieGraph> ALseries) {
		tab.setText(gm.getNom());
		for(SerieGraph sg : ALseries){
			this.addCourbe(sg);
		}
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
			System.out.println(sg.getSerie().getNom()+" : "+j+" : "+data.get(j));
			series.getData().add(new XYChart.Data<Number, Number>(j, data.get(j)));
		}
	    lineChart.getData().add(series);
	    chart.put(sg, series);
	    editStyle(sg);
	    this.updateLegend();
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
					Set<Node> nodes = lineChart.lookupAll(".default-color"+(i++)+".chart-line-symbol");
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