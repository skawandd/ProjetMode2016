package plugins.modeles;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import modeles.Serie;
import modeles.Traitement;

public class MoyMobile extends Traitement{

	/**
	 * Methode presente dans le corp de Serie car utile aux anaylse
	 */
	public Serie traiter(List<Object> l) {
		return s.transformationMoyMobile((Integer)l.get(0));
	}

	@Override
	public String getNom() {
		return "Transformation Moyenne mobile";
	}

}
