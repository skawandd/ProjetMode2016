package plugins.vues;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import modeles.Serie;
import vues.VueTraitement;

public class Logistique extends VueTraitement{

	public void presenterTransformation() {
		Label resume = new Label("La transformation logistique stabilise la variance des données dans l'intervalle ]0,1[ selon la formule Yt = log(Xt/(1-Xt))");
		resume.setFont(new Font(12.0));
		resume.setWrapText(true);
		Label isPossible = new Label("La transformation logistique est possible");
		if(!plugins.modeles.Logistique.checkLogistique(s)){
			isPossible.setText("La transformation est impossible car certaines valeurs ne sont pas compris dans l'intervalle ]0,1[");
			valider.setDisable(true);
		}
		isPossible.setWrapText(true);
		vb.getChildren().setAll(resume, isPossible);
		VBox.setMargin(resume, new Insets(10,10,10,10));
		VBox.setMargin(isPossible, new Insets(10, 10, 10, 10));
		st.setHeight(230);
		valider.setOnAction((e) ->{
			traiter(null);
		});
	}

}
