import javafx.application.Application;
import javafx.stage.Stage;
import vues.VueTerminal;

/**
 * Lance l'application
 */
public class MainClass extends Application{
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		VueTerminal term = new VueTerminal(primaryStage);
		term.lancer();
	}
}
