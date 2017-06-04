package plugins.vues;

import java.util.Arrays;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import modeles.Serie;
import vues.NumberTextField;
import vues.VueTransfo;

public class MoyMobile extends VueTransfo{

	public void presenterTransformation() {
		Label resume = new Label("La moyenne mobile permet d'estimer la tendance et la saisonnalité");
		resume.setFont(new Font(12.0));
		resume.setWrapText(true);
		VBox.setMargin(resume, new Insets(10,10,10,10));
		Label ordre = new Label("Ordre : ");
		ordre.setFont(new Font(25.0));
		NumberTextField ntf = new NumberTextField();
		ntf.setMaxWidth(100.0);
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(ordre, ntf);
		vb.getChildren().setAll(resume, hb);
		st.setHeight(200);
		valider.setDisable(true);
		ntf.textProperty().addListener((observable, oldValue, newValue) ->{
			if(ntf.getText().length() > 0)
				valider.setDisable(false);
			else
				valider.setDisable(true);
		});
		valider.setOnAction((e)->{
			transformer(Arrays.asList(Integer.parseInt(ntf.getText())));
		});
	}

}
