package plugins.vues;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import vues.NumberTextField;
import vues.VueTraitement;

public class MoyMobilePond extends VueTraitement{

	@Override
	public void presenterTransformation() {
		Label resume = new Label("La moyenne mobile ponderee permet d'estimer la tendance et la saisonnalite en specifiant les ponderations");
		resume.setFont(new Font(12.0));
		resume.setWrapText(true);
		VBox.setMargin(resume, new Insets(10,10,10,10));
		Label ordre = new Label("Ordre :");
		ordre.setFont(new Font(20.0));
		NumberTextField ntf = new NumberTextField();
		ntf.setMaxWidth(100.0);
		VBox vordre = new VBox();
		VBox ponderation = new VBox();
		vordre.getChildren().addAll(ordre, ntf);
		VBox.setMargin(vordre, new Insets(20,20,20,20));
		HBox hb = new HBox();
		Separator separator = new Separator(Orientation.VERTICAL);
		/*HBox.setHgrow(vordre, Priority.ALWAYS);
		HBox.setHgrow(ponderation, Priority.ALWAYS);
		HBox.setHgrow(separator, Priority.NEVER);
		separator.setHalignment(HPos.CENTER);*/
		hb.getChildren().addAll(vordre, separator, ponderation);
		vb.getChildren().setAll(resume, hb);
		ntf.textProperty().addListener((observable, oldValue, newValue) -> {
			if(ntf.getText().length() > 0){
				
			}
		});
		st.setHeight(250);
		
	}

}
