import java.io.File;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;

public class VueTerminal implements Observer {

	private ArrayList<Serie> series;
	private Stage stage;

	public VueTerminal(Stage stage) {
		this.stage = stage;
		series = new ArrayList<Serie>();
	}

	public void lancer() {
		this.afficherMenu();
		while (traiter(getChoix(1, 7)) != 0) {
			this.afficherMenu();
		}
		System.out.println("A bientot");
	}

	private int getChoix(int min, int max) {
		int choix = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Choix:");
		while (choix == 0) {
			try {
				choix = sc.nextInt();
				if (choix < min || choix > max) {
					System.out.println("Veuillez choisir un nombre entre " + min + " et " + max);
					choix = 0;
				}
			} catch (NoSuchElementException e) {
				System.out.println("Ceci n'est pas un entier.\nChoix:");
			}
		}
		return choix;
	}

	private void afficherMenu() {
		System.out.println("1) Charger une serie d'un fichier csv");
		System.out.println("2) Lister les series charges");
		System.out.println("3) Afficher une serie");
		System.out.println("4) Effectuer une transformation");
		System.out.println("5) Effectuer une moyenne mobile d'ordre h");
		System.out.println("6) Calculer les residus entre une serie de depart et une serie lissee");
		System.out.println("7) Quitter");
	}

	private void afficherSeries() {
		for (int i = 1; i <= series.size(); i++) {
			System.out.println(i + ") " + series.get(i - 1).getNom());
		}
	}

	private void afficherChoixSerie() {
		System.out.println("Choisissez une serie parmis la liste ci dessous:");
		afficherSeries();
	}

	private int traiter(int choix) {
		switch (choix) {
		case 1:
			File file = FileExplorer.getFile();
			// TODO replacer avec une exception si fichier impossible a lire
			if (file != null) {
				Serie serie = new Serie(file);
				series.add(serie);
			}
			break;
		case 2:
			afficherSeries();
			break;
		case 3:
			afficherChoixSerie();
			if(series.size() > 0){
				DrawCourbe.afficherCourbe(stage, series.get(getChoix(1, series.size()) - 1).getSerie());
				return 0;
			}else
				System.out.println("Aucun serie charge");
			break;
		case 7:
			return 0;
		}
		return 1;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
