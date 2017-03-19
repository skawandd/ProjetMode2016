package modeles;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Represente un graphique avec une ou plusieurs series
 */
public class GraphModel extends Observable implements Observer{
	
	ArrayList<SerieGraph> series;
	String nom;
	static int cpt=1;

	/**
	 * Construit un nouveau modele graphique vide
	 */
	public GraphModel(){
		series = new ArrayList<SerieGraph>();
		nom="Graphique "+cpt;
		cpt++;
	}
	
	/**
	 * Construit un nouveau modele graphique
	 * @param serie Une serie qui sera contenue dans le graphique
	 */
	public GraphModel(Serie serie){
		this();
		series.add(new SerieGraph(serie));
	}
	
	/**
	 * Construit un nouveau modele graphique
	 * @param series Une ArrayList de Serie qui seront contenues dans le graphique
	 */
	public GraphModel(ArrayList<Serie> series){
		this();
		for(Serie serie : series){
			this.series.add(new SerieGraph(serie));
		}
	}
	
	/**
	 * Ajoute une Serie au graphique
	 * @param s
	 */
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
	
	/**
	 * Supprime une Serie du graphique
	 * @param sg
	 */
	public void removeSerie(SerieGraph sg){
		sg.deleteObserver(this);
		series.remove(sg);
		this.release(new Updater("supprimer", sg));
	}
	
	/**
	 * Organise les series en fonctions des parents <-> enfants
	 */
	public void organise(){
		
	}
	
	/**
	 * Retourne la position dans d'une SerieGraph (ex 1.1.2 est le deuxieme enfant du premiere enfant d'une serie)
	 * @param sg La SerieGraph dont on veut la position
	 * @return
	 */
	public String getPos(SerieGraph sg){
		organise();
		return "1";
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
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
}
