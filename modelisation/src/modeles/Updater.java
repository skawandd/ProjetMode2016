package modeles;

public class Updater {
	
	private String descriptif;
	private Object arg;
	
	public Updater(String descriptif, Object arg){
		this.descriptif = descriptif;
		this.arg = arg;
	}
	
	public String getDescriptif(){ return descriptif; }
	public Object getArg(){ return arg; }
	
}
