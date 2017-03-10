package vues;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import modeles.Serie;
import modeles.SerieGraph;
import modeles.Updater;

public class DrawCourbe implements Observer{
 
	Scene scene;
	LineChart<String, Number> lineChart;
	HashMap<SerieGraph, XYChart.Series<String, Number>> chart;
	
	public DrawCourbe(Scene scene){
		this.scene = scene;
		chart = new HashMap<SerieGraph, XYChart.Series<String, Number>>();
	}
	
    public void afficherCourbes(ArrayList<SerieGraph> ALseries) {
    	int i;
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Temps");
        lineChart = new LineChart<String,Number>(xAxis,yAxis);
        lineChart.setTitle("Series");
        for(SerieGraph sg : ALseries){
        	this.addCourbe(sg);
        }
        lineChart.setCreateSymbols(false);
        scene.setRoot(lineChart);
    }
    
    public void addCourbe(SerieGraph sg){
    	XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
    	series.setName(sg.getSerie().getNom());
    	Serie s = sg.getSerie();
        HashMap<String, Double> data = s.getSerie();
		for(String j : data.keySet()){
			series.getData().add(new XYChart.Data<String, Number>(j, data.get(j)));
		}
        lineChart.getData().add(series);
        chart.put(sg, series);
        editStyle(sg);
    	this.updateLegend();
    }
    
    public void removeCourbe(SerieGraph sg){
    	XYChart.Series<String, Number> series = chart.get(sg);
    	lineChart.getData().remove(series);
    	chart.remove(sg);
    }
    
    public void editVisibilite(SerieGraph sg){
    	
    }
    
	public void editStyle(SerieGraph sg){
        Node node = chart.get(sg).getNode();
        int[] rgb = sg.getRgb();
        node.setStyle("-fx-stroke: rgb("+rgb[0]+", "+rgb[1]+", "+rgb[2]+");");
        this.updateLegend();
	}

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