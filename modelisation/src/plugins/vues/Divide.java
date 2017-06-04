package plugins.vues;

import java.util.Arrays;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import vues.NumberTextField;
import vues.VueTransfo;

public class Divide extends VueTransfo{

	public void presenterTransformation() {
		
		// De base, on ne peut pas valider car il faut que l'utilisateur rentre un chiffre d'abord
		valider.setDisable(true);
		
		// On creer les Nodes pour decrire la transformation et accepter un diviseur
		Label resume = new Label("Divise toute la serie");
		resume.setFont(new Font(12.0));
		resume.setWrapText(true);
		Label ordre = new Label("Diviseur : ");
		ordre.setFont(new Font(25.0));
		NumberTextField ntf = new NumberTextField();
		ntf.setMaxWidth(100.0);
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(ordre, ntf);
		VBox.setMargin(resume, new Insets(10,10,10,10));
		vb.getChildren().setAll(resume, hb);
		
		// On fixe la hauteur du stage, c'est degeulasse je sais mais je n'ai pas reussi a l'adapter automatiquement
		st.setHeight(200);
		
		// On ne permet de valider que si l'utilisateur a ecrit un chiffre et qu'il est different de 0
		ntf.textProperty().addListener((observable, oldValue, newValue) ->{
			if(ntf.getText().length() > 0 && Integer.parseInt(ntf.getText()) != 0)
				valider.setDisable(false);
			else
				valider.setDisable(true);
		});
		
		// On transforme en mettant le diviseur dans la liste.
		valider.setOnAction( (e)->{
			transformer(Arrays.asList(Integer.parseInt(ntf.getText())));
		});
	}

}
