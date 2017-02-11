import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MainClass {
	public static void main(String[] args){
		// TODO compatibilite linux.
		CSVDecoder csv = new CSVDecoder(new File("bin\\\\valeurs.csv"));
		HashMap<Integer, Integer> data = new HashMap<Integer, Integer>();
		try{
			if((data = csv.decodeCsv()) == null){
				System.out.println("error decoding csv");
				return;
			}
		}catch(IOException e){
			System.out.println("IO exception");
			return;
		}
		for(Integer i : data.keySet()){
			System.out.println("clé :" + i + " valeur :"+data.get(i));
		}
	}
}
