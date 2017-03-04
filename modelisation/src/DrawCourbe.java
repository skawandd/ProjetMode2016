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

public class DrawCourbe implements Observer{
 
	Scene scene;
	ArrayList<Serie> series;
	
	public DrawCourbe(Scene scene){
		this.scene = scene;
	}
	
	// TODO afficher toutes les courbes qui sont dans ALseries sur le graph et de differentes couleurs.
    public void afficherCourbe(ArrayList<SerieGraph> ALseries) {
       /* final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        final LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);
        lineChart.setTitle("Bourse");
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName("Serie 1");
		for(String i : data.keySet()){
			series.getData().add(new XYChart.Data<String, Number>(i, data.get(i)));
		}
        scene.setRoot(lineChart);
        lineChart.getData().add(series);*/
    }

	@Override @SuppressWarnings("unchecked")
	public void update(Observable arg0, Object arg1) {
		this.afficherCourbe((ArrayList<SerieGraph>)arg1);
	}
 
}