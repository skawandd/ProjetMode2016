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

public class BoxCox extends VueTransfo{

	public void presenterTransformation() {
		Label resume = new Label("La transformation de BoxCox stabilise la variance des données selon la formule Yt = (Xt^λ-1)/λ si λ > 0 ou log(Xt) si λ = 0");
		Label lambda = new Label("λ : ");
		resume.setFont(new Font(12.0));
		resume.setWrapText(true);
		VBox.setMargin(resume, new Insets(10,10,10,10));
		lambda.setFont(new Font(30.0));
		NumberTextField ntf = new NumberTextField();
		ntf.setDot(true);
		ntf.setMaxWidth(100.0);
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(lambda, ntf);
		vb.getChildren().setAll(resume, hb);
		st.setHeight(220);
		valider.setDisable(true);
		ntf.textProperty().addListener((observable, oldValue, newValue) ->{
			if(ntf.getText().length() > 0 && newValue.charAt(newValue.length()-1) != '.')
				valider.setDisable(false);
			else
				valider.setDisable(true);
		});
		valider.setOnAction( (e)->{
			transformer(Arrays.asList(Double.parseDouble(ntf.getText())));
		});
		
	}

}
