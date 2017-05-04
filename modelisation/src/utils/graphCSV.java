package utils;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modeles.SerieModel;
public class graphCSV{
	
ObservableList<String> items = FXCollections.observableArrayList ();
	CSVDecoder csv;
	
	public graphCSV(ObservableList<String > items, CSVDecoder csv){
		this.csv = csv;
		this.items = items;
	}
	
	public void CSV(Stage stage, SerieModel sm){
		
		
	VBox main = new VBox();	
	Label titre_label = new Label("Nom de la série:");
	TextField titre_textfield = new TextField(""+csv.getName());
	HBox titre_hbox = new HBox(20);
	titre_hbox.getChildren().addAll(titre_label, titre_textfield);
	titre_hbox.setPadding(new Insets(30, 40, 0, 40));
	
	HBox abscisse_hbox = new HBox(20);
	Label abscisse_label = new Label("Unité:");
	ComboBox<String> abscisse_combo = new ComboBox<>();
	abscisse_combo.setItems(items);
	abscisse_combo.getSelectionModel().select(0);
	abscisse_hbox.getChildren().addAll(abscisse_label, abscisse_combo);
	TitledPane abscisse = new TitledPane("Choix de l'abscisse", abscisse_hbox);
	abscisse.setCollapsible(false);
    abscisse.setAnimated(false);
    abscisse.setMaxSize(400, 100);
    abscisse.setPadding(new Insets(30, 20, 30, 20));
    
    HBox ordonnee_hbox = new HBox(20);
	Label ordonnee_label = new Label("Unité:");
	ComboBox<String> ordonnee_combo = new ComboBox<>();
	ordonnee_combo.setItems(items);
	ordonnee_combo.getSelectionModel().select(5);
	ordonnee_hbox.getChildren().addAll(ordonnee_label, ordonnee_combo);
	TitledPane ordonnee = new TitledPane("Choix de l'ordonnée", ordonnee_hbox);
	ordonnee.setCollapsible(false);
    ordonnee.setAnimated(false);
    ordonnee.setMaxSize(400, 100);
    ordonnee.setPadding(new Insets(30, 20, 0, 20));
	
	Button btn = new Button("Valider");
	HBox btn_hbox = new HBox();
	btn_hbox.getChildren().add(btn);
	btn_hbox.setPadding(new Insets(0, 0, 0, 175));
	//btn.setLayoutX(100);
		btn.setOnAction(f-> {
			int choixOrd, choixAbs;
			String name = titre_textfield.getText();
			choixAbs = abscisse_combo.getSelectionModel().getSelectedIndex();
			choixOrd = ordonnee_combo.getSelectionModel().getSelectedIndex();
			sm.addSerieFromFile(csv.getFile(), choixAbs, choixOrd, name);
			stage.close();
		});
		
		main.getChildren().addAll(titre_hbox, ordonnee, abscisse, btn_hbox);		;
		Scene scene = new Scene(main, 400, 400);
		stage.setScene(scene);
		stage.show();
	
}
	
	
	
	
	
	
	
	

}
