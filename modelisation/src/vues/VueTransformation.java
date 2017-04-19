package vues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class VueTransformation {

	Stage st;
	VBox total;
	VBox modulaire;
	
	VueTransformation(Stage st){
		this.st = st;
		total = new VBox();
		modulaire = new VBox();
		ComboBox<String> cb = new ComboBox<>();
		cb.getItems().addAll("Transformation logarithmique",
							 "Transformation de BoxCox",
							 "Transformation logistique",
							 "Transformation moyenne mobile",
							 "Transformation moyenne mobile pondérée");
		cb.valueProperty().addListener((e) -> {
			int item = cb.getSelectionModel().getSelectedIndex();
			switch(item){
				case 0:
					afficherLogarithmique();
					break;
				case 1:
					afficherBoxCox();
			}
		});
		cb.setMaxWidth(Double.MAX_VALUE);
		cb.getSelectionModel().select(0);
		VBox.setMargin(cb, new Insets(10,10,10,10));
		Button valider = new Button("Valider");
		valider.setPadding(new Insets(5, 50, 5, 50));
		HBox hb = new HBox();
		hb.getChildren().add(valider);
		hb.setAlignment(Pos.CENTER);
		total.getChildren().addAll(cb, new Separator(), modulaire, hb);
		this.afficherLogarithmique();
		st.setWidth(400);
		Scene sc = new Scene(total);
		st.setResizable(false);
		st.setScene(sc);
		st.show();
	}
	
	public void afficherLogarithmique(){
		// TODO afficher si transformation possible
		Label resume = new Label("La transformation logarithmique stabilise la variance des données selon la formule Y(t) = log(Xt)");
		resume.setFont(new Font(12.0));
		resume.setWrapText(true);
		VBox.setMargin(resume, new Insets(10,10,10,10));
		Label isPossible = new Label("La transformation est possible");
		VBox.setMargin(isPossible, new Insets(10, 10, 10, 10));
		isPossible.setWrapText(true);
		modulaire.getChildren().setAll(resume, isPossible);
		st.setHeight(200);
	}
	
	public void afficherBoxCox(){
		Label resume = new Label("La transformation de BoxCox stabilise la variance des données selon la formule Yt = (Xt^λ-1)/λ si λ > 0 ou log(Xt) si λ = 0");
		Label lambda = new Label("λ : ");
		resume.setFont(new Font(12.0));
		resume.setWrapText(true);
		VBox.setMargin(resume, new Insets(10,10,10,10));
		lambda.setFont(new Font(30.0));
		NumberTextField ntf = new NumberTextField();
		ntf.setMaxWidth(100.0);
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(lambda, ntf);
		hb.setMaxHeight(Double.MAX_VALUE);
		modulaire.getChildren().setAll(resume, hb);
		st.setHeight(230);
	}
	
	public void afficherLogistique(){
		
	}
	
	public void afficherMoyMobile(){
		
	}
	
	public void afficherMoyMobilePonderee(){
		
	}
	
	
}
