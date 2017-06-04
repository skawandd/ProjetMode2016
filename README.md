Résumé des fonctionnalités implémentées/non implémentées :

	Fonctionnalités liés à l'ihm :

		- Charger des données/Importer une série : Oui depuis un csv, non depuis un serveur web
		- Exporter une série
		- Exporter un graphique en png
		- Afficher des courbes
		- Système parent/enfant des séries
		- Renommer/Ajouter/Supprimer un graphique
		- Zoomer sur le graphique
		- Afficher/Masquer/Supprimer une série
		- Sélectionner une série à partir du graphique
		- Mise en valeur de la série sur le graphique au survole de son nom
		Non implémentés : Analyses et prévisions MAIS modèles présent dans le code de Série /!\
	
	Fonctionnalités liés à la modélisation :

		- Toutes les transformations/analyses/prévisions ont été faite !
		En revanche, seulement environ 1/3 ont été inclu dans l'ihm.
		Les calculs sont présent à deux endroits :	- Dans src > plugins > modeles
													- Dans src > modeles > Serie.java (à partir de la ligne 110 environ)


	COO :

		- UML dans ressources > uml.png
		Seul les classes les plus importantes ont été représenté pour garder un UML assez clair et compréhensif.
		- Quelques tests présent dans src > test > TestSerie.java ...
		- MVC largement utilisé.


Le système de plugins :

Les plugins vous permettent de créer vos propres traitements.
Nous allons créer ensemble un traitement simple permettant de diviser par un nombre donné par l'utilisateur chaque point d'une serie.

Pour créer un plugin, il faut créer deux classes, la premiere, se situe dans src>plugins>modeles et hérite de Traitement.
La seconde se situe dans src>plugins>vues et hérite de TraitementVue.

Les deux classes doivent imperativement avoir le même nom.

Le modele comporte deux methodes à implémenter:

- Serie traiter(List<Object> l)

Cette méthode prend en paramètre la liste des entrées fournient par l'utilisateur (cf TraitementVue).
La serie est accessible grace a l'attribut s (comme Serie) pour la serie et entrees pour les données (une HashMap<Integer,Double>)
Cette transformation doit renvoyer une nouvelle serie, ou null en cas d'erreur. Pour creer une nouvelle serie : new Serie(String nom_de_la_serie, Serie serie_parent, HashMap<Integer, Double> donnees);
!!!! Un exemple complet et commenté est fournit (chercher la classe divide) !!!!

- String getNom()

Cette méthodes doit renvoyer une string correspondant au nom du traitement. Celui-ci s'afficher dans la ComboBox des traitement proposés.

La vue comporte 1 seul méthode à implémenter :

- void presenterTransformation()

Celle-ci vous permet de réaliser l'ihm présentant votre traitement. Lorsque l'utilisateur appuie sur valider, il vous faut appeler la méthode this.traiter (et non pas la méthode traiter du modèle !!)
avec comme paramètre une liste des paramètres à transmettre a la méthode traiter du modèle.