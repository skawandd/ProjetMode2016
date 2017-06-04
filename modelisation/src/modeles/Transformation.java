package modeles;

import java.util.HashMap;
import java.util.List;

import javafx.scene.layout.VBox;

public abstract class Transformation {

	protected HashMap<Integer, Double> entrees;
	protected Serie s;
	
	public void setParams(Serie serie){
		entrees = serie.getSerie();
		s = serie;
	}
	public abstract Serie transformer(List<Object> l);
	public abstract String getNom();
	
}
