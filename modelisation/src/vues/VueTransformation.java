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
import modeles.Serie;

public class VueTransformation {

	Stage st;
	Serie s;
	VBox modulaire;
	Button valider;
	
	VueTransformation(Stage st, Serie s){
		this.st = st;
		this.s = s;
		VBox total = new VBox();
		modulaire = new VBox();
		ComboBox<String> cb = new ComboBox<>();
		cb.getItems().addAll("Transformation logarithmique",
							 "Transformation de BoxCox",
							 "Transformation logistique",
							 "Transformation moyenne mobile",
							 "Transformation moyenne mobile pondérée");
		cb.valueProperty().addListener((e) -> {
			int item = cb.getSelectionModel().getSelectedIndex();
			valider.setDisable(false);
			switch(item){
				case 0:
					afficherLogarithmique();
					break;
				case 1:
					afficherBoxCox();
					break;
				case 2:
					afficherLogistique();
					break;
				case 3:
					afficherMoyMobile();
					break;
				case 4:
					afficherMoyMobilePonderee();
					break;
			}
		});
		cb.setMaxWidth(Double.MAX_VALUE);
		VBox.setMargin(cb, new Insets(10,10,10,10));
		valider = new Button("Valider");
		valider.setPadding(new Insets(5, 50, 5, 50));
		HBox hb = new HBox();
		hb.getChildren().add(valider);
		hb.setAlignment(Pos.CENTER);
		total.getChildren().addAll(cb, new Separator(), modulaire, hb);
		cb.getSelectionModel().select(0);
		st.setWidth(400);
		Scene sc = new Scene(total);
		st.setResizable(false);
		st.setScene(sc);
		st.show();
	}
	
	public void afficherLogarithmique(){
		Label resume = new Label("La transformation logarithmique stabilise la variance des données selon la formule Y(t) = log(Xt)");
		resume.setFont(new Font(12.0));
		resume.setWrapText(true);
		VBox.setMargin(resume, new Insets(10,10,10,10));
		Label isPossible = new Label("La transformation est possible");
		if(!s.checkLogarithmique()){
			isPossible.setText("La transformation est impossible, certaines entrées sont inférieures ou égales a 0");
			valider.setDisable(true);
		}
		VBox.setMargin(isPossible, new Insets(10, 10, 10, 10));
		isPossible.setWrapText(true);
		modulaire.getChildren().setAll(resume, isPossible);
		st.setHeight(200);
		valider.setOnAction( (e)->{
			s.transformationLogarithmique();
			st.close();
		});
	}
	
	public void afficherBoxCox(){
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
		modulaire.getChildren().setAll(resume, hb);
		st.setHeight(220);
		valider.setDisable(true);
		ntf.textProperty().addListener((observable, oldValue, newValue) ->{
			if(ntf.getText().length() > 0 && newValue.charAt(newValue.length()-1) != '.')
				valider.setDisable(false);
			else
				valider.setDisable(true);
		});
		valider.setOnAction( (e)->{
			s.transformationBoxCox(Double.parseDouble(ntf.getText()));
			st.close();
		});
	}
	
	public void afficherLogistique(){
		Label resume = new Label("La transformation logistique stabilise la variance des données dans l'intervalle ]0,1[ selon la formule Yt = log(Xt/(1-Xt))");
		resume.setFont(new Font(12.0));
		resume.setWrapText(true);
		Label isPossible = new Label("La transformation logistique est possible");
		if(!s.checkLogistique()){
			isPossible.setText("La transformation est impossible car certaines valeurs ne sont pas compris dans l'intervalle ]0,1[");
			valider.setDisable(true);
		}
		isPossible.setWrapText(true);
		modulaire.getChildren().setAll(resume, isPossible);
		VBox.setMargin(resume, new Insets(10,10,10,10));
		VBox.setMargin(isPossible, new Insets(10, 10, 10, 10));
		st.setHeight(230);
		valider.setOnAction((e) ->{
			s.transformationLogistique();
			st.close();
		});
	}
	
	public void afficherMoyMobile(){
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
		modulaire.getChildren().setAll(resume, hb);
		st.setHeight(180);
		valider.setDisable(true);
		ntf.textProperty().addListener((observable, oldValue, newValue) ->{
			if(ntf.getText().length() > 0)
				valider.setDisable(false);
			else
				valider.setDisable(true);
		});
		valider.setOnAction((e)->{
			s.transformationMoyMobile(Integer.parseInt(ntf.getText()));
			st.close();
		});
	}
	
	public void afficherMoyMobilePonderee(){
		
	}
	
	
}
