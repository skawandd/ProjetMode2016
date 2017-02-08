import java.io.File;
import java.util.Map;

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
	 * @return une Map associant une valeur a une date.
	 */
	public Map<Integer, Integer> decodeCsv(){
		return null;
	}
	
	/**
	 * 
	 * @param colonne
	 * @return la colonne pour laquelle on veut les donnees.
	 */
	public int[] getColonne(int colonne){
		return null;
	}
	
}