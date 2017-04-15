package utils;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modeles.SerieModel;
public class graphCSV{
	
	ObservableList<String> items = FXCollections.observableArrayList ();
	CSVDecoder csv;
	
	public graphCSV(ObservableList<String> items, CSVDecoder csv){
		this.csv = csv;
		this.items = items;
	}
	
	public void CSV(Stage stage, SerieModel sm){
		
		
		VBox tot = new VBox();
		VBox abs = new VBox();
		VBox ord = new VBox();
		HBox hbox = new HBox();
		
		Button btn = new Button();

		btn.setText("valider");
		ListView<String> listAbs = new ListView <String>();
		listAbs.setItems(items);
		ListView<String> listOrd = new ListView<String>();
		listOrd.setItems(items);
		
		abs.getChildren().addAll(new Label("Abscisse"), listAbs);
		ord.getChildren().addAll(new Label("Ordonnee"), listOrd);
		hbox.getChildren().addAll(abs, ord);
		tot.getChildren().addAll(hbox, btn);
		
		btn.setOnAction(f-> {
			int choixOrd, choixAbs;
			choixAbs = listAbs.getSelectionModel().getSelectedIndex();
			choixOrd = listOrd.getSelectionModel().getSelectedIndex();
			sm.addSerieFromFile(csv.getFile(), choixAbs, choixOrd);
			stage.close();
		});
		
		Scene scene = new Scene(tot, 800, 600);
		stage.setScene(scene);
		stage.show();
	
}
	
	
	
	
	
	
	
	

}
