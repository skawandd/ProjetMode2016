package plugins.modeles;

import java.util.HashMap;
import java.util.List;
import modeles.Serie;
import modeles.Traitement;

public class Divide extends Traitement{
	
	public Serie traiter(List<Object> l) {
		// On recoit le parametre utilisateur, c-a-d le nombre par lequel il faut diviser
		int diviseur = (Integer)l.get(0);
		if(diviseur == 0) return null;
		// On creer une nouvelle HashMap qui va nous servir a ranger les nouvelles donnees pour la nouvelle serie
		HashMap<Integer, Double> h = new HashMap<>();
		// On transforme la serie en lisant les donnes de la serie a transformer
		for(Integer j : entrees.keySet())
			h.put(j, entrees.get(j)/diviseur);
		// On creer la nouvelle serie
		Serie serie = new Serie(s.getNom()+"/"+diviseur, s, h);
		// facile non ?
		return serie;
	}

	public String getNom() {
		// le nom qui decrit la transformation
		return "Division";
	}
	
}
