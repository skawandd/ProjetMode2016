package plugins.vues;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import vues.VueTraitement;

public class Logarithmique extends VueTraitement{
	
	public void presenterTransformation() {
		Label resume = new Label("La transformation logarithmique stabilise la variance des données selon la formule Y(t) = log(Xt)");
		resume.setFont(new Font(12.0));
		resume.setWrapText(true);
		VBox.setMargin(resume, new Insets(10,10,10,10));
		Label isPossible = new Label("La transformation est possible");
		if(!plugins.modeles.Logarithmique.checkLogarithmique(s)){
			isPossible.setText("La transformation est impossible, certaines entrées sont inférieures ou égales a 0");
			valider.setDisable(true);
		}
		VBox.setMargin(isPossible, new Insets(10, 10, 10, 10));
		isPossible.setWrapText(true);
		vb.getChildren().setAll(resume, isPossible);
		st.setHeight(200);
		valider.setOnAction( (e)->{
			traiter(null);
		});
	}
	
}
