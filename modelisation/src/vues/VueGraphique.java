package vues;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Callback;
import modeles.GraphModel;
import modeles.Serie;
import modeles.SerieGraph;
import modeles.Updater;
import utils.BothWayHashMap;

@SuppressWarnings("unchecked")
public class VueGraphique implements Observer{
 
	Tab tab;
	LineChart<Number, Number> lineChart;
	TreeView<String> treeView;
	BothWayHashMap<SerieGraph, TreeItem<String>> sgToTi;
	BothWayHashMap<SerieGraph, XYChart.Series<Number, Number>> chart;
	GraphModel gm;
	ObservableList<Serie> ol;
	
	/**
	 * Creer une nouvelle vue graphique
	 * @param scene La scene dans laquelle le graphique apparaitra
	 * @param gm Le GraphModel correspondant au graphique
	 */
	public VueGraphique(Tab tab, GraphModel gm, ObservableList<Serie> ol){
		this.ol = ol;
		this.tab = tab;
		this.gm = gm;
		chart = new BothWayHashMap<>();
		sgToTi = new BothWayHashMap<>();
	}
	
	/**
	 * Initialise le graphique
	 */
	public void init(){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Temps");
        lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        lineChart.setTitle("Series");
        lineChart.setCreateSymbols(false);
        lineChart.setLegendVisible(false);
        ComboBox<String> cb = new ComboBox<String>();
        cb.setEditable(true);
        cb.setPromptText("serie ...");
        updateCombo(cb);
        ol.addListener((ListChangeListener<Serie>)e -> {
        	updateCombo(cb);
        });
        TreeItem<String> root = new TreeItem<>("Hided");
        treeView = new TreeView<>(root);
        treeView.setShowRoot(false);
	    ContextMenu cm = new ContextMenu();
	    MenuItem transformation = new MenuItem("Transformation");
	    transformation.setOnAction( (e) ->{
	    		TreeItem<String> ti = treeView.getSelectionModel().getSelectedItem();
	    		if(ti == null) return;
	    });
	    MenuItem analyse = new MenuItem("Analyse");
	    MenuItem exporter = new MenuItem("Exporter");
	    MenuItem propriete = new MenuItem("Propriete");
	    MenuItem masquer = new MenuItem("Masquer");
	    masquer.setOnAction((e) -> {
				TreeItem<String> ti = treeView.getSelectionModel().getSelectedItem();
				SerieGraph sg = (SerieGraph)(sgToTi.getByValue(ti));
				sg.setVisible(sg.isVisible()^true);
	    });
	    treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
	    	TreeItem<String> ti = treeView.getSelectionModel().getSelectedItem();
	    	if(e.getButton() == MouseButton.SECONDARY && ti != null){
	    		if(((SerieGraph)(sgToTi.getByValue(ti))).isVisible()){
	    			masquer.setText("Masquer");
	    		}else{
	    			masquer.setText("Afficher");
	    		}
	    	}
	    });
	    MenuItem supprimer = new MenuItem("Supprimer");
	    supprimer.setOnAction((e) -> {
				TreeItem<String> ti = treeView.getSelectionModel().getSelectedItem();
				gm.removeSerie((SerieGraph)sgToTi.getByValue(ti));
	    });
	    cm.getItems().addAll(transformation, analyse, exporter, propriete, masquer, supprimer);
	    treeView.setContextMenu(cm);
	    treeView.setEditable(true);
	    treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>(){
	    	@Override
	    	public TreeCell<String> call(TreeView<String> p){
	    		TreeItemWithNode tiwn = new TreeItemWithNode();
	    		tiwn.setEventCallback(new Callback(){
					public Object call(Object param){
						if(tiwn.getTreeItem() == null) return null;
						SerieGraph sg = (SerieGraph)sgToTi.getByValue(tiwn.getTreeItem());
						XYChart.Series<Number, Number> series = (Series<Number, Number>) chart.get(sg);
				    	Node node = series.getNode();
				    	if( ((MouseEvent)param).getEventType() == MouseEvent.MOUSE_ENTERED)
				    		node.setStyle(node.getStyle()+" -fx-stroke-width: 6px;");
				    	else if( ((MouseEvent)param).getEventType() == MouseEvent.MOUSE_EXITED)
					    	node.setStyle(node.getStyle().substring(0, node.getStyle().length()- 22)+"-fx-stroke-width: 3px;");
						return null;
					}
	    		});
	    		return tiwn;
	    		
	    	}
	    });
        HBox hbox = new HBox();
        VBox vbox = new VBox();
        vbox.getChildren().addAll(cb, treeView);
		hbox.getChildren().addAll(vbox, lineChart);
		tab.setContent(hbox);
	}
	
	/**
	 * Affiche la ou les courbes donnees
	 * @param ALseries
	 */
	public void afficherCourbes(ArrayList<SerieGraph> ALseries) {
		tab.setText(gm.getNom());
		for(SerieGraph sg : ALseries){
			this.addCourbe(sg);
		}
    }
    
	/**
	 * Ajoute une courbe au graphique
	 * @param sg
	 */
	public void addCourbe(SerieGraph sg){
		Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName(sg.getSerie().getNom());
		Serie s = sg.getSerie();
	    HashMap<Integer, Double> data = s.getSerie();
		for(Integer j : data.keySet()){
			series.getData().add(new XYChart.Data<Number, Number>(j, data.get(j)));
		}
	    lineChart.getData().add(series);
	    chart.put(sg, series);
	    setMouseEvents(series);
	    editStyle(sg);
	    updateSerieListe();
	}
	
	public void setMouseEvents(Series<Number, Number> series){
	    series.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, e->{
	    	Node node = series.getNode();
	    	node.setStyle(node.getStyle()+" -fx-stroke-width: 6px;");
	    });
	    series.getNode().addEventHandler(MouseEvent.MOUSE_EXITED_TARGET,  e->{
	    	Node node = series.getNode();
	    	node.setStyle(node.getStyle().substring(0, node.getStyle().length()- 22)+"-fx-stroke-width: 3px;");
	    });
	    series.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
	    	SerieGraph sg = (SerieGraph) chart.getByValue(series);
	    	treeView.getSelectionModel().select((TreeItem<String>)sgToTi.get(sg));
	    });
	}
	
	public void treatMouseEvent(){
		
	}
	
	private void updateCombo(ComboBox<String> cb){
		cb.getItems().clear();
		for(Serie s : ol){
			cb.getItems().add(s.getNom());
		}
	}
	
	/**
	 * Met a jour la liste des Series disponibles dans le graph
	 */
	private void updateSerieListe(){
		int j;
		ArrayList<SerieGraph> series = gm.getSeries();
		TreeItem<String> root = treeView.getRoot();
		ArrayList<TreeItem<String>> l = new ArrayList<>();
		root.getChildren().clear();
		for(int i=0; i<series.size(); i++){
			Serie s = series.get(i).getSerie();
			TreeItem<String> ti = new TreeItem<>(series.get(i).getNom());
			Line line = new Line(0,12,12,0);
			int rgb[] = series.get(i).getRgb();
			line.setStyle("-fx-stroke: rgb("+rgb[0]+","+rgb[1]+","+rgb[2]+"); -fx-stroke-width: 2px;");
			ti.setGraphic(line);
			sgToTi.put(series.get(i), ti);
			if(i > 0 && s.isSameFamily(series.get(i-1).getSerie()) ){
				if(s.isBrother(series.get(i-1).getSerie())){
					for(j = i; s.isBrother(series.get(j).getSerie()); j--){}
					l.get(j).getChildren().add(ti);
				}else{
					for(j = i; j >= 0; j--){
						if(s.hasParent(series.get(j).getSerie())){
							l.get(j).getChildren().add(ti);
							break;
						}
					}
				}
			}else{
				root.getChildren().add(ti);
			}
			l.add(ti);
		}
	}
	
	/**
	 * Supprime une courbe du graphique
	 * @param sg
	 */
	public void removeCourbe(SerieGraph sg){
		Series<Number, Number> series = (Series<Number, Number>) chart.get(sg);
		lineChart.getData().remove(series);
		chart.remove(sg);
		updateSerieListe();
	}
	
	/**
	 * Modifie la visibilite d'une courbe
	 * @param sg
	 */
	public void editVisibilite(SerieGraph sg){
		Series<Number, Number> series = (Series<Number, Number>) chart.get(sg);
		if(sg.isVisible()){
			lineChart.getData().add(series);
			setMouseEvents(series);
			editStyle(sg);
		}else{
			lineChart.getData().remove(series);
			TreeItem<String> ti = (TreeItem<String>)sgToTi.get(sg);
		}
	}
	
	public void editStyle(SerieGraph sg){
		Node node = ((XYChart.Series<Number, Number>)chart.get(sg)).getNode();
		int[] rgb = sg.getRgb();
		node.setStyle("-fx-stroke: rgb("+rgb[0]+", "+rgb[1]+", "+rgb[2]+");");
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		Updater u = (Updater)arg1;
		switch(u.getDescriptif()){
			case "ajouter":
				this.addCourbe((SerieGraph)u.getArg());
				break;
			case "creer":
				this.afficherCourbes((ArrayList<SerieGraph>)u.getArg());
				break;
			case "supprimer":
				this.removeCourbe((SerieGraph)u.getArg());
				break;
			case "visibilite":
				this.editVisibilite((SerieGraph)u.getArg());
				break;
			case "style":
				this.editStyle((SerieGraph)u.getArg());
				break;
		}
	}
 
}