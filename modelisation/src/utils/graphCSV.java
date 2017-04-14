package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modeles.GraphModel;
import modeles.Serie;
import modeles.SerieModel;
import vues.VueIhm;

public class graphCSV implements Observer {
	
	
	int choix;
	ObservableList<String> items =FXCollections.observableArrayList ();
	
	
	
	public graphCSV(ObservableList<String> items) {
		super();
		this.items = items;
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	public void CSV(Stage stage,SerieModel sm){
		VueIhm vi = new VueIhm(stage, sm);
		VBox vbox = new VBox();
		Button btn = new Button();
		btn.setText("valider");
		vbox.getChildren().addAll(btn);
		ListView<String> list= new ListView <String>();
		list.setItems(items);
		list.getSelectionModel().getSelectedItem();
		list.setOnMouseClicked(f-> {
            if(list.getSelectionModel().getSelectedIndex()==0){
            	choix=0;
            	vi.test2(choix);
            }
            if(list.getSelectionModel().getSelectedIndex()==1){
            	choix=1;
            	vi.test2(choix);
            }
            if(list.getSelectionModel().getSelectedIndex()==2){
            	choix=2;
            	vi.test2(choix);
            }
            if(list.getSelectionModel().getSelectedIndex()==3){
            	choix=3;
            	vi.test2(choix);
            }
            if(list.getSelectionModel().getSelectedIndex()==4){
            	choix=4;
            	vi.test2(choix);
            }
            if(list.getSelectionModel().getSelectedIndex()==5){
            	choix=5;
            	vi.test2(choix);
            }
            if(list.getSelectionModel().getSelectedIndex()==6){
            	choix=6;
            	vi.test2(choix);
            }
		
			});
		
		vbox.getChildren().addAll(list);
		Scene scene = new Scene(vbox, 800, 600);
		stage.setScene(scene);
		stage.show();
		
		
		
	}


	

	public int getChoix() {
		return choix;
	}

	public void setChoix(int choix) {
		this.choix = choix;
	}
	
	
	
	
	
	
	
	

}
