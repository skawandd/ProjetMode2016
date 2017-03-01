import java.util.HashMap;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
 
public class DrawCourbe {
 
    public static void afficherCourbe(Stage stage, HashMap<String,Integer> data) {
        //defining the axes
    	stage.setTitle("Resultat annalyse");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");       
        
        final LineChart<String,Number> lineChart = 
                new LineChart<String,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Bourse");
                                
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName("Serie 1");
		for(String i : data.keySet()){
			series.getData().add(new XYChart.Data<String, Number>(i, data.get(i)));
		}
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
        stage.setScene(scene);
        stage.show();
    }
 
}