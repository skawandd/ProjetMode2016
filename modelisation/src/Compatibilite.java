
public class Compatibilite {
	
	
	private static String getOs(){
		String os = System.getProperty("os.name").toLowerCase();
		if(os.indexOf("nix") >=0 ) return "linux";
		else if(os.indexOf("win") >= 0) return "windows";
		else if(os.indexOf("mac") >= 0) return "mac";
		return "unknown";
	}
	
	public static String getPath(String path){
		if(getOs().equals("windows")) return path.replace("/", "\\"); 
		else return path;
	}
	
}
