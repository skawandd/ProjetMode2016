package plugins.modeles;

import java.util.List;

import modeles.Serie;
import modeles.Traitement;

public class MoyMobilePond extends Traitement{

	/**
	 * Methode presente dans le corps de Serie car utile aux analyse
	 */
	public Serie traiter(List<Object> l) {
		return s.transformationMoyMobilePonderee((Integer)l.get(0), (double[])l.get(1));
	}

	public String getNom() {
		return "Transformation Moyenne mobile ponderee";
	}

}
