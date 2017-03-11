package modeles;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Charge et gere les differentes series
 */
public class SerieModel extends Observable implements Observer{

	private ArrayList<Serie> series;
	
	public SerieModel(){
		series = new ArrayList<Serie>();
	}
	
	/**
	 * Charge une serie a partir d'un fichier csv
	 * @param f
	 */
	public void addSerieFromFile(File f){
		Serie s = new Serie(f);
		series.add(s);
		s.addObserver(this);
		this.setChanged();
		this.notifyObservers(s);
	}
	
	/**
	 * Charge une serie a partir d'un serveur web
	 * @param url
	 */
	public void addSerieFromUrl(String url){
		
	}
	
	/**
	 * Trie les series pour que les enfants suivent les parents
	 */
	private void organise(){
		
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Serie){					// evenement declenche par une Serie lorsqu'elle cree une nouvelle Serie.
			series.add((Serie)arg1);
			((Serie)arg1).addObserver(this);
			this.setChanged();
			this.notifyObservers((Serie)arg1);
		}
	}
	
	public ArrayList<Serie> getSeries(){ return series; }
	
}
