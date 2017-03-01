import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * 
 * Decode un fichier csv a 2 colonnes et le renvoie dans une liste.
 */

public class CSVDecoder {

	private File file;
	
	public CSVDecoder(File file){
		this.file = file;
	}
	
	/**
	 * 
	 * @return une Map associant une valeur a une date. null si le fichier ne peut etre lu.
	 */
	public HashMap<String, Integer> decodeCsv() throws IOException{
		String elems[];
		String line;
		FileInputStream fis;
		HashMap<String, Integer> CSV = new HashMap<String, Integer>();
		if(!file.canRead()) return null;
		fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		while((line = br.readLine()) != null){
			elems = line.split(",");
			if(elems.length == 2){
				CSV.put(elems[0], Integer.parseInt(elems[1]));
			}
		}
		br.close();
		return CSV;
	}
	
	/**
	 * 
	 * @param colonne
	 * @return la colonne pour laquelle on veut les donnees.
	 */
	public Object[] getColonne(int colonne) throws IOException{
		if(colonne == 1)return decodeCsv().keySet().toArray(new String[0]);
		else if(colonne == 2) return decodeCsv().values().toArray(new Integer[0]);
		return null;
	}
	
}