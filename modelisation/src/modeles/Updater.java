package modeles;

/**
 * Updater encapsule les donnees dans un objet pour faciliter l'utilisation de update()
 */
public class Updater {
	
	private String descriptif;
	private Object arg;
	
	public Updater(String descriptif, Object arg){
		this.descriptif = descriptif;
		this.arg = arg;
	}
	
	/**
	 * 
	 * @return Un descriptif resumant l'action a effectue par update()
	 */
	public String getDescriptif(){ return descriptif; }
	
	/**
	 * 
	 * @return Un objet utile a la mise a jour.
	 */
	public Object getArg(){ return arg; }
	
}
