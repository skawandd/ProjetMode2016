import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MainClass {
	public static void main(String[] args){
		
		String path = Compatibilite.getPath("bin/valeurs.csv");
		CSVDecoder csv = new CSVDecoder(new File(path));
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		try{
			if((data = csv.decodeCsv()) == null){
				System.out.println("erreur en decodant le csv");
				return;
			}
		}catch(IOException e){
			System.out.println("IO exception");
			return;
		}
		for(String i : data.keySet()){
			System.out.println("cle :" + i + " valeur :"+data.get(i));
		}
	}
}
