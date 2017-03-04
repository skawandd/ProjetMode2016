import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

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
