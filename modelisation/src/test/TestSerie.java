package test;
import java.io.File;
import java.io.IOException;
import org.junit.Test;
import junit.framework.TestCase;
import modeles.Serie;
import utils.CSVDecoder;
import utils.Compatibilite;

public class TestSerie extends TestCase{
	
	
	/*public File loadFile(String path){
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
	
	@Test
	public void testTransformationMoyMobile() throws IOException{
		Serie valeurs = new Serie(loadFile("ressources/valeurs.csv"));
		//Paire:
		assertEquals("Erreur TransformationMoyMobilePaire", 24.375, (valeurs.transformationMoyMobile(4)).getListSerie().get(0));
		assertEquals("Erreur TransformationMoyMobilePaire", 23.875, (valeurs.transformationMoyMobile(4)).getListSerie().get(1));
		assertEquals("Erreur TransformationMoyMobilePaire", 20.75, (valeurs.transformationMoyMobile(4)).getListSerie().get(2));
		//Impaire:
		assertEquals("Erreur TransformationMoyMobileImpaire", 22.8, (valeurs.transformationMoyMobile(5)).getListSerie().get(0));
		assertEquals("Erreur TransformationMoyMobileImpaire", 18.4, (valeurs.transformationMoyMobile(5)).getListSerie().get(1));
		assertEquals("Erreur TransformationMoyMobileImpaire", 17.4, (valeurs.transformationMoyMobile(5)).getListSerie().get(2));
	}
	
	@Test
	public void testTransformationMoyMobilePonderee() throws IOException{
		Serie valeurs = new Serie(loadFile("ressources/valeurs.csv"));
		double[] pond = new double[3];
		pond[0] = 1/14;
		pond[1] = 4/14;
		pond[2] = 9/14;
		//Impaire:
		assertEquals("Erreur TransformationMoyMobilePondereeImpaire", (10*pond[0] + 5*pond[1] + 8*pond[2] + 68*pond[1] + 23*pond[0])/(2*pond[0] + 2*pond[1] +pond[2]), (valeurs.transformationMoyMobilePonderee(5, pond)).getListSerie().get(0), 0.0001);
		assertEquals("Erreur TransformationMoyMobilePondereeImpaire", (5*pond[0] + 8*pond[1] + 68*pond[2] + 23*pond[1] - 12*pond[0])/(2*pond[0] + 2*pond[1] +pond[2]), (valeurs.transformationMoyMobilePonderee(5, pond)).getListSerie().get(1), 0.0001);
		assertEquals("Erreur TransformationMoyMobilePondereeImpaire", (8*pond[0] + 68*pond[1] + 23*pond[2] - 12*pond[1] + 0*pond[0])/(2*pond[0] + 2*pond[1] +pond[2]), (valeurs.transformationMoyMobilePonderee(5, pond)).getListSerie().get(2), 0.0001);
		//Paire:
		assertEquals("Erreur TransformationMoyMobilePondereePaire", ((10/2)*pond[0] + 5*pond[1] + 8*pond[2] + 68*pond[1] + (23/2)*pond[0])/(pond[0] + 2*pond[1] +pond[2]), (valeurs.transformationMoyMobilePonderee(4, pond)).getListSerie().get(0), 0.0001);
		assertEquals("Erreur TransformationMoyMobilePondereePaire", ((5/2)*pond[0] + 8*pond[1] + 68*pond[2] + 23*pond[1] + (-12/2)*pond[0])/(pond[0] + 2*pond[1] +pond[2]), (valeurs.transformationMoyMobilePonderee(4, pond)).getListSerie().get(1), 0.0001);
		assertEquals("Erreur TransformationMoyMobilePondereePaire", ((8/2)*pond[0] + 68*pond[1] + 23*pond[2] - 12*pond[1] + (0/2)*pond[0])/(pond[0] + 2*pond[1] +pond[2]), (valeurs.transformationMoyMobilePonderee(4, pond)).getListSerie().get(2), 0.0001);
	}
	@Test
	public void testTransformationLogarithmique() throws IOException{
		Serie val = new Serie(loadFile("ressources/valeurs.csv"));
		Serie test= new Serie();
		double[] rep= new double[3];
		rep[0] = 1;
		rep[1] = 0.6989700043;
		rep[2] = 0.903089987;
		assertEquals("Erreur dans le premier csv", rep[0], val.transformationLogarithmique().getListSerie().get(0), 0.0001);
		assertEquals("Erreur dans le premier csv", rep[1], val.transformationLogarithmique().getListSerie().get(1), 0.0001);
		assertEquals("Erreur dans le premier csv", rep[2], val.transformationLogarithmique().getListSerie().get(2), 0.0001);
		
	}
	public void runTest() throws IOException{
		testCsv();
		testTransformationMoyMobile();
		testTransformationMoyMobilePonderee();
		testTransformationMoyMobilePonderee();
	}*/

}
