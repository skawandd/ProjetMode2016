package vues;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFileChooser;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import modeles.Serie;

public class VueExport {

	Stage st;
	Serie s;
	VBox modulaire;
	Button valider;
	String col1="";
	String col2="";
	
	/*
	public VueExport(Stage st, Serie s){
		this.st = st;
		this.s = s;
		VBox total = new VBox();
		TextField t=new TextField();
		TextField c1=new TextField();
		TextField c2=new TextField();
		modulaire = new VBox();
		Label l1=new Label("Colone 1 ?");
		Label l2=new Label("Colone 2 ?");
		valider = new Button("Valider");
		valider.setPadding(new Insets(5, 50, 5, 50));
		HBox hb = new HBox();
		HBox hb1 = new HBox();
		HBox hb2 = new HBox();
		hb1.getChildren().add(l1);
		hb1.getChildren().add(c1);
		hb2.getChildren().add(l2);
		hb2.getChildren().add(c2);
		hb.getChildren().add(t);
		hb.getChildren().add(valider);
		hb.setAlignment(Pos.CENTER);
		total.getChildren().addAll(hb1,hb2,hb);
		st.setWidth(400);
		Scene sc = new Scene(total);
		st.setResizable(false);
		st.setScene(sc);
		st.show();
		JFileChooser chooser = new JFileChooser();
		String someString = chooser.getSelectedFile().toString();
		valider.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	String name="";
				name=t.getText();
				col1=c1.getText();
				col2=c2.getText();
				exportCSV(name);
		    }
		});
	}
	*/
	
	
	public VueExport(Stage st, Serie s){
		this.st = st;
		this.s = s;
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as CSV");
        Stage stage=new Stage();
		File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            exportCSV(file);
        }
		
		

	}
	public void exportCSV(File f){
		
		//File f= new File(name+".csv");
		try {
			f.createNewFile();
			System.out.println("je creer un ficher qui sapelle "+ f);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("je creer un ficher qui sapelle"+ f);
		HashMap<Integer, Double> serie= new HashMap<Integer, Double>();
		serie=s.getSerie();
			
		try {
			
			FileWriter fw = new FileWriter(f);
			Iterator iterator = serie.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				int key = (int) entry.getKey();
				Double value = (Double) entry.getValue();
				fw.write(key+","+value+"\n");
				fw.flush();
			}

			
			/*
			for(int i=0;i<serie.size();i++){
				fw.write(i+","+serie.get(i)+"\n");
				fw.flush();
			}
			*/
			fw.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Probleme ecriture fichier");
			e.printStackTrace();
		}
			
			
		
		
		
		
	}

	
}
