package modeles;

import java.util.Observable;

public class SerieGraph extends Observable{

	private int rgb[] = {0, 0, 0};
	private boolean visible = true;
	private Serie serie;
	
	public SerieGraph(Serie s){
		serie = s;
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
	
	public Serie getSerie(){ return serie; }
	public boolean isVisible(){ return visible; }
	public int[] getRgb(){ return rgb; }
	
}
