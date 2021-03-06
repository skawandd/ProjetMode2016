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

	public void addSerieFromFile(File f, int choixAbs, int choixOrd, String name){
		Serie s = new Serie(f,choixAbs, choixOrd, name);
		for(Serie s2 : series){
			if(s2.getNom() == s.getNom()) return;
		}
		series.add(s);
		s.addObserver(this);
		this.setChanged();
		this.notifyObservers(new Updater("ajouter", s));
	}
	
	public void addSerie(Serie s){
		series.add(s);
	}
	
	public void removeSerie(Serie s){
		series.remove(s);
	}
	
	/**
	 * Charge une serie a partir d'un serveur web
	 * @param url
	 */
	public void addSerieFromUrl(String url){
		
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Serie){					// evenement declenche par une Serie lorsqu'elle cree une nouvelle Serie.
			series.add((Serie)arg1);
			((Serie)arg1).addObserver(this);
			this.setChanged();
			this.notifyObservers(new Updater("ajouter", (Serie)arg1));
		}
	}
	
	
	@Override
	public String toString() {
		return "SerieModel [series=" + series + "]";
	}

	public ArrayList<Serie> getSeries(){ return series; }
	
}
