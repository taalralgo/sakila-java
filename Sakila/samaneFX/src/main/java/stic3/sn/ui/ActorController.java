package stic3.sn.ui;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import cours.java.stic3.model.Actor;
import cours.java.stic3.model.Film;
import cours.java.stic3.model.FilmActor;
import cours.java.stic3.model.FilmActorPK;
import cours.java.stic3.service.IActor;
import cours.java.stic3.service.IFilm;
import cours.java.stic3.service.IFilmActor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import stic3.sn.Utilitaires;

public class ActorController implements Initializable {

	@FXML
	private AnchorPane staffPage;

	@FXML
	private TextField txtNom;

	@FXML
	private TextField txtPrenom;

	@FXML
	private TableView<Actor> tableau;

	@FXML
	private TableColumn<Actor, String> colNom;

	@FXML
	private TableColumn<Actor, String> colPrenom;

	@FXML
	private TableColumn<Actor, Date> colLastUpdate;

	@FXML
	private Button btnAjouter;

	@FXML
	private Button btnActualiser;

	@FXML
	private TextField txtRechercherNom;

	@FXML
	private TextField txtRechercherPrenom;

	@FXML
	private Button btnAlterActor;

	@FXML
	private Button btnSupprimer;

	@FXML
	private Button btnSearch;

	@FXML
	private TextField txtId;
	
	//LES VARIABLES DE FILM
	@FXML
    private TableView<Film> tableauFilm;

    @FXML
    private TableColumn<Film, String> txtFilmColTitre;

    @FXML
    private TableColumn<Film, Date> txtFilmColDate;

	@FXML
	private Button txtAjouterSesFilms;
	
	private Actor acteur;
	private Film film;

	@FXML
	void actionActorAjouterSesFilms(ActionEvent event) {
		if(this.acteur != null && this.film != null) {
			FilmActor filmActor = new FilmActor();
			filmActor.setActor(this.acteur);
			filmActor.setFilm(this.film);
			filmActor.setFilmActorPK(new FilmActorPK(this.acteur.getActorId(), this.film.getFilmId()));
			filmActor.setLastUpdate(new Date());
			//this.acteur.getFilmActorList().add(filmActor);
			Registry registry;
			try 
			{
				registry = LocateRegistry.getRegistry("localhost", 1002);
				IFilmActor ifilmActor = (IFilmActor) registry.lookup("filmActorDistant");
				FilmActor n = ifilmActor.addFilmActor(filmActor);
				if (n != null) {
					Utilitaires.showMessage("Success", "Information", "Film ajouter avec success", 1);
					rafraichir();
					vider();
				} else
					Utilitaires.showMessage("Error", "Erreur", "Echec d'enregistrement", 3);

			} 
			catch (Exception e) 
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

		} 
		else 
		{
			Utilitaires.showMessage("Error", "Erreur", "Choisissez un acteur et un film", 3);
		}
	}

	@FXML
	void actionAjouter(ActionEvent event) {
		if (txtNom.getText().toString().isEmpty() || txtPrenom.getText().toString().isEmpty()) {
			Utilitaires.showMessage("Error", "Erreur", "Un champ est vide", 3);
		} 
		else {
			Actor acteur = new Actor();
			acteur.setFirstName(txtPrenom.getText().toString());
			acteur.setLastName((txtNom.getText().toString().toUpperCase()));
			acteur.setLastUpdate(new Date());
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", 1002);
				IActor iactor = (IActor) registry.lookup("actorDistant");
				List<Actor> actors = iactor.getActors();
				int verif = 0;
				for (Actor a : actors) {
					if (a.getFirstName().equals(acteur.getFirstName()) && a.getLastName().equals(acteur.getLastName()))
						verif++;
				}
				if (verif != 0) {
					Utilitaires.showMessage("Error", "Erreur", "L'acteur existe deja", 3);
				} else {
					Actor n = iactor.addActor(acteur);
					if (n != null) {
						Utilitaires.showMessage("Success", "Information", "Acteur ajouter avec success", 1);
						txtNom.setText("");
						txtId.setText("");
						txtPrenom.setText("");
						txtRechercherNom.setText("");
						txtRechercherPrenom.setText("");
					} else
						Utilitaires.showMessage("Error", "Erreur", "Echec d'enregistrement", 3);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	void actionAlterActor(ActionEvent event) {
		if (txtNom.getText().toString().isEmpty() || txtPrenom.getText().toString().isEmpty()) {
			Utilitaires.showMessage("Error", "Erreur", "Un champ est vide", 3);
		} else {
			Actor acteur = new Actor();
			acteur.setFirstName(txtPrenom.getText().toString());
			acteur.setLastName((txtNom.getText().toString().toUpperCase()));
			acteur.setLastUpdate(new Date());
			acteur.setActorId(Short.parseShort(txtId.getText()));
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", 1002);
				IActor iactor = (IActor) registry.lookup("actorDistant");
				List<Actor> actors = iactor.getActors();
				int verif = 0;
				for (Actor a : actors) {
					if (a.getFirstName().equals(acteur.getFirstName()) && a.getLastName().equals(acteur.getLastName()))
						verif++;
				}
				if (verif != 0) {
					Utilitaires.showMessage("Error", "Erreur", "L'acteur existe deja", 3);
				} else {
					Actor n = iactor.modifierActor(acteur);
					if (n != null) {
						Utilitaires.showMessage("Success", "Information", "Acteur modifier avec success", 1);
						rafraichir();
					} else
						Utilitaires.showMessage("Error", "Erreur", "Echec d'enregistrement", 3);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	void actionRefresh(ActionEvent event) {
		rafraichir();
	}

	@FXML
	void actionSearch(ActionEvent event) {
		if (txtRechercherNom.getText().isEmpty() && txtRechercherPrenom.getText().isEmpty()) {
			Utilitaires.showMessage("Error", "Erreur", "Un champ doit etre renseigner", 2);
		} else if (!txtRechercherNom.getText().isEmpty() && !txtRechercherPrenom.getText().isEmpty()) {
			Utilitaires.showMessage("Error", "Erreur", "Un seul champ doit etre renseigner", 2);
			txtNom.setText("");
			txtPrenom.setText("");
			txtRechercherNom.setText("");
			txtRechercherPrenom.setText("");
		} else {
			if (!txtRechercherNom.getText().isEmpty()) {
				Registry registry;
				try {
					registry = LocateRegistry.getRegistry("localhost", 1002);
					IActor iactor = (IActor) registry.lookup("actorDistant");
					List<Actor> actors = iactor.getActorByLastName(txtRechercherNom.getText());
					ObservableList<Actor> data = FXCollections.observableArrayList();
					for (Actor ent : actors) {
						data.add(ent);
					}
					tableau.getItems().clear();
					tableau.setItems(data);

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Registry registry;
				try {
					registry = LocateRegistry.getRegistry("localhost", 1002);
					IActor iactor = (IActor) registry.lookup("actorDistant");
					List<Actor> actors = iactor.getActorByFirstName(txtRechercherPrenom.getText());
					ObservableList<Actor> data = FXCollections.observableArrayList();
					for (Actor ent : actors) {
						data.add(ent);
					}
					tableau.getItems().clear();
					tableau.setItems(data);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		vider();
	}

	@FXML
	void actionSupprimer(ActionEvent event) {

	}

	void rafraichir() {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IActor iactor = (IActor) registry.lookup("actorDistant");
			List<Actor> actors = iactor.getActors();
			// actors.forEach((l)->System.out.println(l.getLastName()));
			ObservableList<Actor> data = FXCollections.observableArrayList();
			for (Actor ent : actors) {
				data.add(ent);
			}
			tableau.getItems().clear();
			tableau.setItems(data);
			vider();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void vider() {
		txtNom.setText("");
		txtId.setText("");
		txtPrenom.setText("");
		txtRechercherNom.setText("");
		txtRechercherPrenom.setText("");
		this.acteur = null;
		this.film = null;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		remplirTableauActeur();
		tableauSelectedItem();
		tableauFilmSelectedItem();
		remplirTableauFilm();
	}
	
	void tableauFilmSelectedItem() {
		tableauFilm.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Film>() {

			@Override
			public void changed(ObservableValue<? extends Film> arg0, Film ancienFilm, Film nouveauFilm) {
				ActorController.this.film = nouveauFilm;
				
			}
		});
	}
	
	void tableauSelectedItem() {
		tableau.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Actor>() {

			@Override
			public void changed(ObservableValue<? extends Actor> arg0, Actor ancienActor, Actor nouveauActor) {
				txtId.setText(Short.toString(nouveauActor.getActorId()));
				txtNom.setText(nouveauActor.getLastName());
				txtPrenom.setText(nouveauActor.getFirstName());
				ActorController.this.acteur = nouveauActor;
			}

		});
	}

	void remplirTableauFilm() {
		txtFilmColTitre.setCellValueFactory(new PropertyValueFactory<Film, String>("title"));
		txtFilmColDate.setCellValueFactory(new PropertyValueFactory<Film, Date>("releaseYear"));
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IFilm ifilm = (IFilm) registry.lookup("filmDistant");
			List<Film> films = ifilm.getFilms();
			// actors.forEach((l)->System.out.println(l.getLastName()));
			ObservableList<Film> data = FXCollections.observableArrayList();
			for (Film ent : films) 
			{
				data.add(ent);
			}
			tableauFilm.setItems(data);
			vider();

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	void remplirTableauActeur() 
	{
		colNom.setCellValueFactory(new PropertyValueFactory<Actor, String>("lastName"));
		colPrenom.setCellValueFactory(new PropertyValueFactory<Actor, String>("firstName"));
		colLastUpdate.setCellValueFactory(new PropertyValueFactory<Actor, Date>("lastUpdate"));
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IActor iactor = (IActor) registry.lookup("actorDistant");
			List<Actor> actors = iactor.getActors();
			// actors.forEach((l)->System.out.println(l.getLastName()));
			ObservableList<Actor> data = FXCollections.observableArrayList();
			for (Actor ent : actors) 
			{
				data.add(ent);
			}
			tableau.setItems(data);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
