package vues;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import modeles.Serie;
import modeles.SerieGraph;

public class DrawCourbe implements Observer{
 
	Scene scene;
	
	public DrawCourbe(Scene scene){
		this.scene = scene;
	}
	
	// TODO afficher toutes les courbes qui sont dans ALseries sur le graph et de differentes couleurs.
    public void afficherCourbe(ArrayList<SerieGraph> ALseries) {
    	
    	
    	
    	System.out.println("appeler");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        final LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);
        lineChart.setTitle("Bourse");
        
        for(int i=0;i<ALseries.size();i++){
        String name="Series"+i;
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName("Serie "+i);
        
        SerieGraph test1=ALseries.get(i);
        Serie test2=test1.getSerie();
        HashMap<String, Double> test3=test2.getSerie();
		for(String j : test3.keySet()){
			series.getData().add(new XYChart.Data<String, Number>(j, test3.get(j)));
		}
        scene.setRoot(lineChart);
        lineChart.getData().add(series);
    }
    }
    public void updateCourbe(SerieGraph sg){
    	
    }

	@Override @SuppressWarnings("unchecked")
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof ArrayList<?>) this.afficherCourbe((ArrayList<SerieGraph>)arg1);
		else if(arg1 instanceof SerieGraph) this.updateCourbe((SerieGraph)arg1);
	}
 
}