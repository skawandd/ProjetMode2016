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
	private boolean sameName = false;
	
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
	
	public String getNameWithOutTypes(){
		if(name.contains("(") && name.contains(")")){
			return name.substring(0, name.indexOf("("));
		}
		return name;
	}
	
	public void setSameName(boolean value){ sameName = value; }
	public Serie getSerie(){ return serie; }
	public boolean isVisible(){ return visible; }
	public boolean hasSameName(){ return sameName; }
	public int[] getRgb(){ return rgb; }
	public String getNom(){ return name; }
	
}
