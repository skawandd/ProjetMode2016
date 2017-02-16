import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MainClass {
	public static void main(String[] args){
		
		String path = Compatibilite.getPath("bin/valeurs.csv");
		CSVDecoder csv = new CSVDecoder(new File(path));
		HashMap<Integer, Integer> data = new HashMap<Integer, Integer>();
		try{
			if((data = csv.decodeCsv()) == null){
				System.out.println("erreur en decodant le csv");
				return;
			}
		}catch(IOException e){
			System.out.println("IO exception");
			return;
		}
		for(Integer i : data.keySet()){
			System.out.println("cle :" + i + " valeur :"+data.get(i));
		}
	}
}
