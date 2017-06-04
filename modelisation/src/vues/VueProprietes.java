package vues;


import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modeles.GraphModel;
import modeles.SerieGraph;

public class VueProprietes {

	// #TODO capacite a renommer, changer la couleur ou supprimer definitivement une serie (supprimer du graph ET de la liste)
	public VueProprietes(Stage st, GraphModel gm, SerieGraph sg){
		VBox main = new VBox(20);
		Label label_nom_serie = new Label("Nom de la série:");
		Label label_couleur_serie = new Label("Couleur de la série:");
		TextField nom_serie = new TextField(""+sg.getNameWithOutTypes());
		ColorPicker couleur_serie = new ColorPicker(Color.rgb(sg.getRgb()[0], sg.getRgb()[1], sg.getRgb()[2]));
		Button valider = new Button("Valider");
		Button supprimer = new Button("Supprimer");
		
		HBox hbox_nom = new HBox(20);
		hbox_nom.getChildren().addAll(label_nom_serie, nom_serie);
		hbox_nom.setPadding(new Insets(30, 40, 0, 40));
		
		HBox hbox_couleur = new HBox(20);
		hbox_couleur.getChildren().addAll(label_couleur_serie, couleur_serie);
		TitledPane titlepane = new TitledPane("Choix de la couleur de la série", hbox_couleur);
		titlepane.setCollapsible(false);
	    titlepane.setAnimated(false);
		titlepane.setMaxSize(400, 120);
	    titlepane.setPadding(new Insets(30, 20, 0, 20));
		
		HBox hbox_button = new HBox(20);
		hbox_button.getChildren().addAll(valider,supprimer);
		hbox_button.setPadding(new Insets(20, 0, 0, 130));
		
		
		
		valider.setOnAction(f-> {
			Color c = couleur_serie.getValue();
			int t[] = {(int)c.getRed(), (int)c.getGreen(), (int)c.getBlue()};
			sg.setName(nom_serie.getText());
			sg.setRgb(t);
			st.close();
		});
		
		supprimer.setOnAction(f-> {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Attention");
			alert.setHeaderText("Etes vous sur de vouloir supprimer " + sg.getNom() + " ?");
			alert.setContentText("Toute suppression est definitive !");

			Optional<ButtonType> resultat = alert.showAndWait();
			if (resultat.get() == ButtonType.OK){
				gm.removeSerie(sg);
			}

		});
		
		main.getChildren().addAll(hbox_nom, titlepane, hbox_button);
		Scene scene = new Scene(main, 400, 350);
		st.setScene(scene);
		st.setTitle("Propriete");
		st.show();
		

	}
	
}
