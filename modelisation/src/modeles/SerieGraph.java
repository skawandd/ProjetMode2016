package modeles;

import java.util.Observable;

/**
 * Represente une Serie avec en plus des attributs graphiques
 * Une Serie peut donc avoir plusieurs SerieGraph avec des attributs differents (nom, couleur, visibilite ...)
 * Ainsi, cela permet un systeme d'onglet avec une Serie represente differement sur chaque onglet
 */
public class SerieGraph extends Observable{

	private int[] rgb = {0, 0, 0};
	private boolean visible = true;
	private Serie serie;
	private String name; 		// le nom de la SerieGraph peut etre different de la Serie qu'elle reference
	
	/**
	 * Cree une nouvelle SerieGraph a partir d'une Serie
	 * @param s
	 */
	public SerieGraph(Serie s){
		serie = s;
		name = s.getNom();
	}
	
	public void release(Updater update){
		this.setChanged();
		this.notifyObservers(update);
	}
	
	public void setVisible(boolean visible){ 
		this.visible = visible; 
		this.release(new Updater("visibilite", this));
	}
	
	public void setRgb(int[] rgb){
		this.rgb = rgb;
		this.release(new Updater("style", this));
	}
	
	public void setName(String name){
		this.name = name;
		this.release(new Updater("style", this));
	}
	
	public Serie getSerie(){ return serie; }
	public boolean isVisible(){ return visible; }
	public int[] getRgb(){ return rgb; }
	public String getNom(){ return name; }
	
}
