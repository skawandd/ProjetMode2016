package modeles;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class SerieModel extends Observable implements Observer{

	private ArrayList<Serie> series;
	
	public SerieModel(){
		series = new ArrayList<Serie>();
	}
	
	public void addSerieFromFile(File f){
		Serie s = new Serie(f);
		series.add(s);
		s.addObserver(this);
		this.setChanged();
		this.notifyObservers(s);
	}
	
	public void addSerieFromUrl(String url){
		
	}
	
	private void organise(){
		
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Serie){
			series.add((Serie)arg1);
			((Serie)arg1).addObserver(this);
			this.setChanged();
			this.notifyObservers((Serie)arg1);
		}
	}
	
	public ArrayList<Serie> getSeries(){ return series; }
	
}
