import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;

import org.junit.Test;
import junit.framework.TestCase;
import utils.CSVDecoder;
import utils.Compatibilite;

public class Tests extends TestCase{
	
	
	public File loadFile(String path){
		File f = new File(Compatibilite.getPath(path));
		assertNotSame("Erreur en chargeant le fichier", null, f);
		return f;
	}
	
	@Test
	public void testCsv() throws IOException {
		CSVDecoder testCsv = new CSVDecoder(loadFile("ressources/valeurs.csv"));
		Integer[] x = {0,1,2,3,4,5,6};
		assertArrayEquals("Erreur dans le premier csv", x, testCsv.getColonne(1));
	}
	
	public void runTest() throws IOException{
		testCsv();
	}
	@Test
	public void testTransformationLogarithmique() throws IOException {
			
		
	}

}
