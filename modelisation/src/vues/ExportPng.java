package vues;

import java.io.File;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ExportPng {
	
	public void export(Stage st_snap, LineChart<Number, Number>lc_snap, String nom){


		
		WritableImage snapImage = new WritableImage(800, 400);
		snapImage = lc_snap.snapshot(new SnapshotParameters(), snapImage);
		
		FileChooser fc_export = new FileChooser();
		fc_export.setTitle("Export - "+nom);
		fc_export.getExtensionFilters().addAll(new ExtensionFilter("PNG Images", "*.png"));
		fc_export.setInitialFileName(nom);
		
		File f_export = fc_export.showSaveDialog(new Stage());
		
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(snapImage, null), "png", f_export);
		} catch (Exception ex) {
			System.out.println("Ereur inconnu");
		}
	}
}
