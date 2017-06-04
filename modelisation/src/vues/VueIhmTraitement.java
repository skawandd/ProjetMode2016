package vues;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import modeles.Serie;
import modeles.Traitement;
import modeles.Updater;

public class VueIhmTraitement implements Observer{

	Stage st;
	Serie s;
	VBox modulaire;
	Button valider;
	
	VueIhmTraitement(Stage st, Serie s) throws Exception{
		ArrayList<Traitement> transf = new ArrayList<>();
		ArrayList<VueTraitement> vueTransf = new ArrayList<>();
		this.st = st;
		this.s = s;
		VBox total = new VBox();
		modulaire = new VBox();
		valider = new Button("Valider");
		ComboBox<String> cb = new ComboBox<>();
		File folder = new File("bin/plugins/modeles");
		File[] listOfFile = folder.listFiles();
		URL url = new File("bin/plugins").toURI().toURL();
		URL[] urls = new URL[]{url};
		ClassLoader cl = new URLClassLoader(urls);
		for(File f : listOfFile){
			String className = "plugins.modeles."+f.getName().replaceAll(".class", "");
			String className2 = "plugins.vues."+f.getName().replaceAll(".class", "");
			Class<?> cls = cl.loadClass(className);
			Class<?> cls2 = cl.loadClass(className2);
			Traitement t = (Traitement) cls.newInstance();
			t.setParams(s);
			VueTraitement vt = (VueTraitement) cls2.newInstance();
			vt.setParams(modulaire, valider, t, s, st);
			vt.addObserver(this);
			cb.getItems().add(t.getNom());
			transf.add(t);
			vueTransf.add(vt);
		}
		
		cb.valueProperty().addListener((e) -> {
			int item = cb.getSelectionModel().getSelectedIndex();
			valider.setDisable(false);
			vueTransf.get(item).presenterTransformation();
		});
		
		cb.setMaxWidth(Double.MAX_VALUE);
		VBox.setMargin(cb, new Insets(10,10,10,10));
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

	@Override
	public void update(Observable arg0, Object arg1) {
		
		Updater up = (Updater)arg1;
		if(up.getDescriptif().equals("exit")){
			st.close();
		}
		
	}
	
	
}
