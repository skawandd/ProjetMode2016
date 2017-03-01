import java.io.File;

import javafx.scene.control.Label;
import javafx.stage.FileChooser;

public class FileExplorer {

	public File getFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Selection fichier");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*", "*");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(null);
		return file;
	}
}
