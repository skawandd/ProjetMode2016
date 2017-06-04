package plugins.modeles;

import java.util.HashMap;
import java.util.List;

import modeles.Serie;
import modeles.Traitement;

public class Logarithmique extends Traitement{
	
	/**
	 * Verifie que la transformation logarithmique est realisable
	 * @return true si elle est possible, false sinon
	 */
	public static boolean checkLogarithmique(Serie serie){
		HashMap<Integer, Double> entrees = serie.getSerie();
		for(Integer j : entrees.keySet())
			if(entrees.get(j) <= 0) return false;
		return true;
	}
	
	/**
	 * Effectue la transformation logarithmique
	 * @return La serie resultat ou null en cas d'erreur
	 */
	public Serie traiter(List<Object> l) {
		HashMap<Integer, Double> h = new HashMap<>();
		if(!checkLogarithmique(s)) return null;
		for(Integer j : entrees.keySet())
			h.put(j, Math.log(entrees.get(j)));
		Serie serie = new Serie(s.getNom()+"_log", s, h);
		return serie;
	}

	public String getNom() {
		return "Transformation logarithmique";
	}

}
