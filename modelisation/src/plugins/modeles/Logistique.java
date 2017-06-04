package plugins.modeles;

import java.util.HashMap;
import java.util.List;

import modeles.Serie;
import modeles.Traitement;

public class Logistique extends Traitement{

	/**
	 * Verifie si une transformation logistique est possible
	 * @return true si elle est possible, false sinon
	 */	
	public static boolean checkLogistique(Serie serie){
		HashMap<Integer, Double> entrees = serie.getSerie();
		for(Integer j: entrees.keySet())
			if(entrees.get(j) <= 0 || entrees.get(j) >= 1)
				return false;
		return true;
	}
	
	/**
	 * Effectue la transformation logistique
	 * @return La serie resutlat ou null en cas d'erreur
	 */
	public Serie traiter(List<Object> l) {
		HashMap<Integer, Double> h = new HashMap<>();
		Double t;
		if(!checkLogistique(s)) return null;
		for(Integer j: entrees.keySet()){
			t = Math.log(entrees.get(j)/(1 - entrees.get(j)));
			h.put(j, t);
		}
		Serie serie = new Serie(s.getNom()+"_logistique", s, h);
		return serie;
	}

	public String getNom() {
		return "Transformation Logistique";
	}

}
