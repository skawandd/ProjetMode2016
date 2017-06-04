package vues;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modeles.Serie;
import modeles.Transformation;
import modeles.Updater;

public abstract class VueTransfo extends Observable{
	
	protected VBox vb;
	protected Button valider;
	protected Serie s;
	protected HashMap<Integer, Double> entrees;
	protected Stage st;
	private Transformation t;

	public void setParams(VBox vb, Button valider, Transformation t, Serie s, Stage st){
		this.vb = vb;
		this.valider = valider;
		this.t = t;
		this.s = s;
		this.entrees = s.getSerie();
		this.st = st;
	}
	
	public abstract void presenterTransformation();
	
	public void transformer(List<Object> l){
		Serie serie = t.transformer(l);
		if(serie != null){
			s.callChanged();
			s.notifyObservers(serie);
		}
		this.setChanged();
		this.notifyObservers(new Updater("exit", null));
	}
	
}
