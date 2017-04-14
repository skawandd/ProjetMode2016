package vues;
import java.io.File;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modeles.GraphModel;
import modeles.Serie;
import utils.FileExplorer;
import modeles.SerieModel;

/**
 * Vue et controleurs pour une utilisation de l'application via le terminal
 */
public class VueTerminal implements Observer {

	private SerieModel sm;
	private Stage stage;
	private Scene scene;
	
	public VueTerminal(Stage stage, SerieModel s) {
		this.stage = stage;
		sm = s;
	}

	/**
	 * Intialise et demarre la vue terminal
	 */
	public void lancer() {
		this.afficherMenu();
		while (traiterGlobal(getChoix(1, 7)) != 0) {
			this.afficherMenu();
		}
		System.out.println("A bientot");
	}

	/**
	 * Recupere le choix d'un utilisateur
	 * @return le choix de l'utilisateur
	 */
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

	/**
	 * Affiche le menu principal
	 */
	private void afficherMenu() {
		System.out.println("\n1) Charger une serie d'un fichier csv");
		System.out.println("2) Lister les series charges");
		System.out.println("3) Afficher une ou des serie(s)");
		System.out.println("4) Effectuer une transformation");
		System.out.println("5) Calculer les residus entre une serie de depart et une serie lissee");
		System.out.println("6) Quitter");
	}
	
	/**
	 * Affiche les differentes transformations possible
	 */
	private void afficherTransformations(){
		System.out.println("\n1) Transformation logarithme");
		System.out.println("2) Transformation de Box-Cox");
		System.out.println("3) Transformation logistique ]0,1[");
		System.out.println("4) Lissage a l'aide d'une moyenne mobile simple d'ordre h");
		System.out.println("5) Lissage a l'aide d'une moyenne mobile ponderee d'ordre h");
		System.out.println("6) Calcul de la serie desaisonnalise");
		System.out.println("7) Quitter");
	}


	/**
	 * Affiche les series chargees
	 */
	void afficherSeries() {
		ArrayList<Serie> s = sm.getSeries();
		for (int i = 1; i <= s.size(); i++) {
			System.out.println(i + ") " + s.get(i - 1).getNom());
		}
	}
	
	/**
	 * Recupere la selection serie de l'utilisateur
	 * @return le choix de l'utilisateur
	 */
	private int afficherChoixSerie(){
		System.out.println("Choisissez une serie parmis la liste ci dessous:");
		afficherSeries();
		return getChoix(1, sm.getSeries().size());
	}

	/**
	 * Gere le choix des series par l'utilisateur a afficher sur le graphique
	 * @return
	 */
	public GraphModel traiterChoixSerie() {
		GraphModel gm = new GraphModel();
		int choi, ajouter = 1;
		while(ajouter == 1){
			choi = afficherChoixSerie();
			gm.addSerie(sm.getSeries().get(choi -1));
			System.out.println("Ajouter une serie ?\n1) Oui\n2) Non, afficher\n3) Annuler");
			ajouter = getChoix(1, 3);
		}
		if(ajouter == 3) return null;
		else return gm;
	}

	/**
	 * Controlleur global en fonction du choix de l'utilisateur sur le menu principal
	 * @param choix
	 * @return 0 si le programme doit s'arreter, 1 sinon
	 */
	private int traiterGlobal(int choix) {
		switch (choix) {
		case 1:
			File file = FileExplorer.getFile();
			// TODO replacer avec une exception si fichier impossible a lire
			if (file != null) {
				sm.addSerieFromFile(file, choix);
			}
			break;
		case 2:
			afficherSeries();
			break;
		case 3:
			if(sm.getSeries().size() > 0){
				GraphModel gm = traiterChoixSerie();
				if(gm != null){
					stage.setTitle("Resultat annalyse");
					VueIhm vi = new VueIhm(stage, sm);
					vi.lancer();
					vi.creerNouveauGraph(gm);
					return 0;
				}
			}else
				System.out.println("Aucun serie charge");
			break;
		case 4:
			if(sm.getSeries().size() > 0){
				int choixSerie = afficherChoixSerie();
				int choixTransformation = afficherChoixTransformation();
				traiterChoixTransformation(choixTransformation, sm.getSeries().get(choixSerie -1));
			}
			break;
		case 6:
			return 0;
		}
		return 1;
	}
	
	/**
	 * Recupere la transformation choisi par l'utilisateur
	 * @return Le choix de l'utilisateur
	 */
	private int afficherChoixTransformation(){
		System.out.println("Choisissez une transformation parmis la liste ci dessous:");
		afficherTransformations();
		return getChoix(1, 7);
	}
	
	/**
	 * Traite la transformation choisi par l'utilisateur
	 * @param choix La transformation choisi par l'utilisateur
	 * @param parent La serie choisi par l'utilisateur
	 * @return
	 */
	private Serie traiterChoixTransformation(int choix, Serie parent){
		switch(choix){
			case 1:
				System.out.println("Transformation logarithmique de la serie realise");
				return parent.transformationLogarithmique();
			case 2:
				System.out.println("Valeur lambda:");
				Scanner sc = new Scanner(System.in);
				Double lambda = sc.nextDouble();
	
				if(lambda == 0){
					System.out.println("Transformation BoxCox (lambda == 0) de la serie realise");
					return parent.transformationBoxCox(lambda);
				}
				if(lambda > 0){
					System.out.println("Transformation BoxCox (lambda > 0) de la serie realise");
					return parent.transformationBoxCox(lambda);
				}
				System.out.println("Transformation BoxCox de la serie realise");
				return parent.transformationBoxCox(lambda);
			case 3:
				Serie r = parent.transformationLogistique();
				if(r == null){
					System.out.println("Valeurs hors de l'intervalle ]0,1[ !");
					return null;
	
				}
				System.out.println("Transformation logistique ]0,1[ de la serie realise");
				return r;
			case 4:
				System.out.println("Valeur ordre j :");
				Scanner sc2 = new Scanner(System.in);
				int ordre = sc2.nextInt();
				return parent.transformationMoyMobile(ordre);
			case 5:
				System.out.println("Valeur ordre h :");
				Scanner sc1 = new Scanner(System.in);
				int h = sc1.nextInt();
				return parent.transformationMoyMobilePonderee(h);
			default:
					System.out.println("Oups");
					return null;
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
