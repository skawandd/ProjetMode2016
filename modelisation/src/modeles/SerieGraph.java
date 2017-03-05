package modeles;

public class SerieGraph{

	private int rgb[] = {0, 0, 0};
	private boolean visible = true;
	private Serie serie;
	
	public SerieGraph(Serie s){
		serie = s;
	}
	
	public void setVisible(boolean visible){ this.visible = visible; }
	public void setRgb(int[] rgb){ this.rgb = rgb; }
	public Serie getSerie(){ return serie; }
	public boolean isVisible(){ return visible; }
	public int[] getRgb(){ return rgb; }
	
}
