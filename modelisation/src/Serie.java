import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;

public class Serie extends Observable{
	
	String nomSerie;
	CSVDecoder csv;
	HashMap<String, Integer> entrees;
	
	Serie(File file){
		nomSerie = file.getName();
		csv = new CSVDecoder(file);
		try{
			entrees = csv.decodeCsv();
		}catch(IOException e){
			this.setChanged();
			this.notifyObservers();
		}
	}
	
	String getNom(){
		return nomSerie;
	}
	
	HashMap<String, Integer> getSerie(){
		return entrees;
	}
}
