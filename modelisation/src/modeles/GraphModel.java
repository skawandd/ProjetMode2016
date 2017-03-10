package modeles;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class GraphModel extends Observable implements Observer{
	
	ArrayList<SerieGraph> series;
	
	public GraphModel(){
		series = new ArrayList<SerieGraph>();
	}
	
	public GraphModel(Serie serie){
		this();
		series.add(new SerieGraph(serie));
	}
	
	public GraphModel(ArrayList<Serie> series){
		this();
		for(Serie serie : series){
			this.series.add(new SerieGraph(serie));
		}
	}
	
	public void addSerie(Serie s){
		SerieGraph sg = new SerieGraph(s);
		double goldenRatioConj = 1.618033988749895;
		float hue = new Random().nextFloat();
		hue = (float) ((goldenRatioConj*(series.size()/(5*Math.random())))%1);
		Color c = Color.getHSBColor(hue, 0.5f, 0.95f);
		int colors[] = {c.getRed(), c.getGreen(), c.getBlue()};
		sg.setRgb(colors);
		series.add(sg);
		sg.addObserver(this);
		this.release(new Updater("ajouter", s));
	}
	
	public void release(){
		this.release(new Updater("creer", series));
	}
	
	public void release(Updater update){
		this.setChanged();
		this.notifyObservers(update);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.release((Updater)arg);
		
	}
	
}
