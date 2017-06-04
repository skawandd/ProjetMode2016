package plugins.modeles;

import java.util.HashMap;
import java.util.List;

import modeles.Serie;
import modeles.Transformation;
import plugins.modeles.Logarithmique;

public class BoxCox extends Transformation{

	/**
	 * Effectue la transformation de BoxCox
	 * @param lambda 
	 * @return La serie resultat ou null en cas d'erreur
	 */
	public Serie transformer(List<Object> l) {
		Serie serie;
		double lambda = (Double)l.get(0);
		if(lambda == 0){
			Transformation t = new Logarithmique();
			t.setParams(s);
			serie = t.transformer(null);
			if(serie == null) return null;
			serie.setNom(s.getNom()+"_BoxCox");
		}else{
			HashMap<Integer, Double> h = new HashMap<>();
			for(Integer j : entrees.keySet())
				h.put(j,((Math.pow(entrees.get(j), lambda))-1)/lambda);
			serie = new Serie(s.getNom()+"_BoxCox", s, h);
		}
		return serie;
	}

	public String getNom() {
		return "Transformation de BoxCox";
	}

	
	
}
