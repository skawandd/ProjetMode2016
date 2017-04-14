package vues;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.stage.Stage;
import modeles.GraphModel;
import modeles.SerieModel;

public class MainVue extends Application implements Observer {
	private SerieModel sm= new SerieModel();
	@Override
	public void start(Stage stage) {
		GraphModel gm = new GraphModel();
		VueIhm vi = new VueIhm(stage, sm);
		vi.lancer();
		//vi.creerNouveauGraph(gm);
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
