package stic3.sn.ui;

import java.math.BigDecimal;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import cours.java.stic3.model.Actor;
import cours.java.stic3.model.Category;
import cours.java.stic3.model.Film;
import cours.java.stic3.model.FilmActor;
import cours.java.stic3.model.FilmActorPK;
import cours.java.stic3.model.FilmCategory;
import cours.java.stic3.model.FilmCategoryPK;
import cours.java.stic3.model.Language;
import cours.java.stic3.service.IActor;
import cours.java.stic3.service.ICategory;
import cours.java.stic3.service.IFilm;
import cours.java.stic3.service.IFilmActor;
import cours.java.stic3.service.IFilmCategory;
import cours.java.stic3.service.ILanguage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import stic3.sn.Utilitaires;

public class FilmController implements Initializable 
{

    @FXML
    private AnchorPane filmPage;

    @FXML
    private TextField txtTitre;
    
    @FXML
    private TextField txtId;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TableView<Film> tableau;

    @FXML
    private TableColumn<Film, String> colTitre;

    @FXML
    private TableColumn<Film, String> colDescription;

    @FXML
    private TableColumn<Film, Date> colDateSortie;

    @FXML
    private TableColumn<Film, Language> ColLangue;

    @FXML
    private TableColumn<Film, Language> colLangueOrigine;

    @FXML
    private TableColumn<Film, Short> colDureeLocation;

    @FXML
    private TableColumn<Film, Short> colDureeFilm;

    @FXML
    private TableColumn<Film, String> colCaracSpecial;

    @FXML
    private DatePicker txtDateSortie;

    @FXML
    private TextField txtCaracteristique;

    @FXML
    private ComboBox<Language> cbxLangue;

    @FXML
    private TextField txtEvaluation;

    @FXML
    private ComboBox<Language> cbxLangueOrigine;

    @FXML
    private Button btnAjouter;

    @FXML
    private TextField txtDureeLocation;

    @FXML
    private Button btnActualiser;

    @FXML
    private TextField txtRechercherLangue;

    @FXML
    private TextField txtRechercherTitre;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private TextField txtPrixLocation;

    @FXML
    private TextField txtDureeFilm;

    @FXML
    private TextField txtPrixRemplacement;

    @FXML
    private TableView<Actor> tableauActor;

    @FXML
    private TableColumn<Actor, String> colActorNom;

    @FXML
    private TableColumn<Actor, String> colActorPrenom;

    @FXML
    private Button btnRechercher;

    @FXML
    private TableView<Category> tableauCategory;

    @FXML
    private TableColumn<Category, String> colCategoNom;

    @FXML
    private TableColumn<Category, Date> colCategoryUpdate;

    @FXML
    private Button btnAjouterActor;

    @FXML
    private Button btnAjouterCategorie;
    
    @FXML
    private TextField txtIdCatego;
    
    private Film filmId;
    
    private Actor acteur;
    
    private Category category;

    @FXML
    void actionActualiser(ActionEvent event) {
    	rafraichir();
    }

    @FXML
    void actionAjouter(ActionEvent event) {
    	if(isValide()) {
    		Film film = new Film();
    		initFilm(film);
    		
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", 1002);
				IFilm ifilm = (IFilm) registry.lookup("filmDistant");
				List<Film> films = ifilm.getFilms();
				int verif = 0;
				for (Film a : films) {
					if (a.getTitle().equals(film.getTitle()) && a.getLength().toString().equals(film.getLength().toString()) 
							&& a.getReleaseYear().toString().equals(film.getReleaseYear().toString()))
						verif++;
				}
				if (verif != 0) {
					Utilitaires.showMessage("Error", "Erreur", "Le film existe deja", 3);
				} 
				else {
					System.out.println("Rating est "+film.getRating());
					Film f = ifilm.addFilm(film);
					if (f != null) {
						Utilitaires.showMessage("Success", "Information", "Film ajouter avec success", 1);
						vider();
						rafraichirFilm();
					} 
					else
						Utilitaires.showMessage("Error", "Erreur", "Echec d'enregistrement", 3);
				}
			} 
			catch (Exception e) {
				System.out.println("Message d'eereur est :"+ e.getMessage());
				e.printStackTrace();
			}
    	}
    	else
    		Utilitaires.showMessage("", "Erreur", "Un des champs est vide", 3);
    }

    @FXML
    void actionAjouterActeur(ActionEvent event) {
    	if(this.filmId != null && this.acteur != null) {
			FilmActor filmActor = new FilmActor();
			filmActor.setActor(this.acteur);
			filmActor.setFilm(this.filmId);
			filmActor.setLastUpdate(new Date());
			filmActor.setFilmActorPK(new FilmActorPK(this.acteur.getActorId(), this.filmId.getFilmId()));
			//this.filmId.getFilmActorList().add(filmActor);
			Registry registry;
			Registry registry2;
			try 
			{
				registry2 = LocateRegistry.getRegistry("localhost", 1002);
				registry = LocateRegistry.getRegistry("localhost", 1002);
				IFilm ifilm = (IFilm) registry.lookup("filmDistant");
				IFilmActor ifilmActor = (IFilmActor) registry2.lookup("filmActorDistant");
				//Film f = ifilm.modifierFilm(filmId);
				FilmActor fa = ifilmActor.addFilmActor(filmActor);
				if (fa != null) {
					Utilitaires.showMessage("Success", "Information", "Acteur ajouter avec success", 1);
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
			vider();
			Utilitaires.showMessage("Error", "Erreur", "Choisissez un acteur et un film", 3);
		}
    }

    @FXML
    void actionAjouterCategorie(ActionEvent event) {
    	if(this.filmId != null && !this.txtIdCatego.getText().isEmpty()) {
    		FilmCategory filmCatego = new FilmCategory();
    		filmCatego.setCategory(this.category);
    		filmCatego.setFilm(this.filmId);
    		filmCatego.setLastUpdate(new Date());
    		filmCatego.setFilmCategoryPK(new FilmCategoryPK(this.filmId.getFilmId(), this.category.getCategoryId()));
			Registry registry;
			try 
			{
				registry = LocateRegistry.getRegistry("localhost", 1002);
				IFilmCategory ifilmCatego = (IFilmCategory) registry.lookup("filmCategoDistant");
				FilmCategory fc = ifilmCatego.addFilmCategory(filmCatego);
				if (fc != null) {
					Utilitaires.showMessage("Success", "Information", "Categorie ajouter avec success", 1);
					rafraichir();
					vider();
				} else
					Utilitaires.showMessage("Error", "Erreur", "Echec d'enregistrement", 3);

			} 
			catch (Exception e) 
			{
				vider();
				e.printStackTrace();
			}

		} 
		else 
		{
			vider();
			Utilitaires.showMessage("Error", "Erreur", "Choisissez un acteur et un film", 3);
		}
    }

    @FXML
    void actionModifier(ActionEvent event) {
    	if(isValide()) {
    		Film film = new Film();
    		initFilm(film);
    		film.setFilmId(Short.parseShort(txtId.getText()));
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", 1002);
				IFilm ifilm = (IFilm) registry.lookup("filmDistant");
				List<Film> films = ifilm.getFilms();
				int verif = 0;
				for (Film a : films) {
					System.out.println("Dans boucle");
					if (a.getTitle().equals(film.getTitle()) && a.getLength().equals(film.getLength()) 
							&& a.getReleaseYear().equals(film.getReleaseYear()))
						verif++;
				}
				if (verif != 0) {
					Utilitaires.showMessage("Error", "Erreur", "Le film existe deja", 3);
				} 
				else {
					Film n = ifilm.modifierFilm(film);
					if (n != null) {
						Utilitaires.showMessage("Success", "Information", "Film modifier avec success", 1);
						rafraichirFilm();
						vider();
					} 
					else
						Utilitaires.showMessage("Error", "Erreur", "Echec d'enregistrement", 3);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	else
    		Utilitaires.showMessage("Error", "Erreur", "Veuillez selectionner un film", 3);
    }

    @FXML
    void actionRechercher(ActionEvent event) {
    	if (txtRechercherTitre.getText().isEmpty() && txtRechercherLangue.getText().isEmpty()) {
			Utilitaires.showMessage("Error", "Erreur", "Un champ doit etre renseigner", 2);
		}
    	else if (!txtRechercherTitre.getText().isEmpty() && !txtRechercherLangue.getText().isEmpty()) {
			Utilitaires.showMessage("Error", "Erreur", "Un seul champ doit etre renseigner", 2);
			vider();
		}
    	else 
    	{
			if (!txtRechercherTitre.getText().isEmpty()) 
			{
				Registry registry;
				try 
				{
					registry = LocateRegistry.getRegistry("localhost", 1002);
					IFilm ifilm = (IFilm) registry.lookup("filmDistant");
					List<Film> films = ifilm.getFilmsByLangue(txtRechercherLangue.getText());
					ObservableList<Film> data = FXCollections.observableArrayList();
					for (Film ent : films) {
						data.add(ent);
					}
					tableau.getItems().clear();
					tableau.setItems(data);

				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			} 
			else 
			{
				Registry registry;
				try 
				{
					registry = LocateRegistry.getRegistry("localhost", 1002);
					IFilm ifilm = (IFilm) registry.lookup("filmDistant");
					List<Film> films = ifilm.getFilmsByLangue(txtRechercherLangue.getText());
					ObservableList<Film> data = FXCollections.observableArrayList();
					for (Film ent : films) {
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//REMPLISSAGE
		remplirTableauActeur();
		remplirTableauCategory();
		remplirTableauFilm();
		
		//LES SELECTED ITEM
		tableauActeurSelectedItem();
		tableauCategorySelectedItem();
		tableauFilmSelectedItem();
		
		//FILL UP COMBO BOX
		remplirComboBox();
		
	}
	
	//REMPLISSAGE DES TABLEAUX
	void remplirTableauFilm() {
		colTitre.setCellValueFactory(new PropertyValueFactory<Film, String>("title"));
		colDescription.setCellValueFactory(new PropertyValueFactory<Film, String>("description"));
		colDateSortie.setCellValueFactory(new PropertyValueFactory<Film, Date>("releaseYear"));
		ColLangue.setCellValueFactory(new PropertyValueFactory<Film, Language>("languageId"));
		colLangueOrigine.setCellValueFactory(new PropertyValueFactory<Film, Language>("originalLanguageId"));
		colDureeLocation.setCellValueFactory(new PropertyValueFactory<Film, Short>("rentalDuration"));
		colDureeFilm.setCellValueFactory(new PropertyValueFactory<Film, Short>("length"));
		colCaracSpecial.setCellValueFactory(new PropertyValueFactory<Film, String>("specialFeatures"));
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IFilm ifilm = (IFilm) registry.lookup("filmDistant");
			List<Film> films = ifilm.getFilms();
			ObservableList<Film> data = FXCollections.observableArrayList();
			for (Film ent : films) 
			{
				data.add(ent);
			}
			
			tableau.setItems(data);
			vider();

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	void remplirTableauCategory() {
		colCategoNom.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));
		colCategoryUpdate.setCellValueFactory(new PropertyValueFactory<Category, Date>("lastUpdate"));
		
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			ICategory icategory = (ICategory) registry.lookup("categoryDistant");
			List<Category> categories = icategory.getCategories();
			ObservableList<Category> data = FXCollections.observableArrayList();
			for (Category ent : categories) 
			{
				data.add(ent);
			}
			tableauCategory.setItems(data);
			vider();

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	void remplirTableauActeur() {
		colActorNom.setCellValueFactory(new PropertyValueFactory<Actor, String>("lastName"));
		colActorPrenom.setCellValueFactory(new PropertyValueFactory<Actor, String>("firstName"));
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IActor iactor = (IActor) registry.lookup("actorDistant");
			List<Actor> actors = iactor.getActors();
			ObservableList<Actor> data = FXCollections.observableArrayList();
			for (Actor ent : actors) 
			{
				data.add(ent);
			}
			tableauActor.setItems(data);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	//ACTION POUR LA SELECTION DU TABLEAU
	void tableauFilmSelectedItem() {
		tableau.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Film>() {

			@Override
			public void changed(ObservableValue<? extends Film> arg0, Film ancien, Film nouveau) {
				txtTitre.setText(nouveau.getTitle());
				txtDescription.setText(nouveau.getDescription());
				txtPrixLocation.setText(String.valueOf(nouveau.getRentalRate()));
				txtCaracteristique.setText(nouveau.getSpecialFeatures());
				txtDureeFilm.setText(String.valueOf(nouveau.getLength()));
				txtEvaluation.setText(nouveau.getRating());
				txtDureeLocation.setText(String.valueOf(nouveau.getRentalDuration()));
				txtPrixRemplacement.setText(String.valueOf(nouveau.getReplacementCost()));
				cbxLangue.setValue(nouveau.getLanguageId());
				cbxLangueOrigine.setValue(nouveau.getOriginalLanguageId());
				txtDateSortie.setValue(LocalDate.from(nouveau.getReleaseYear().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
				txtId.setText(String.valueOf(nouveau.getFilmId()));
				FilmController.this.filmId = nouveau;
			}
			

		});
	}
	
	void tableauCategorySelectedItem() {
		tableauCategory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Category>() {

			@Override
			public void changed(ObservableValue<? extends Category> arg0, Category ancien, Category nouveau) {
				txtIdCatego.setText(String.valueOf(nouveau.getCategoryId()));
				FilmController.this.category = nouveau;
			}
			

		});
	}
	
	void tableauActeurSelectedItem() {
		tableauActor.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Actor>() {

			@Override
			public void changed(ObservableValue<? extends Actor> arg0, Actor ancienActor, Actor nouveauActor) {
				FilmController.this.acteur = nouveauActor;
			}

		});
	}
	
	//RAFRAICHIR ET VIDER LES CONTENUS
	void vider() {
		txtTitre.setText("");
		txtDescription.setText("");
		txtPrixLocation.setText("");
		txtCaracteristique.setText("");
		txtDureeFilm.setText("");
		txtEvaluation.setText("");
		txtDureeLocation.setText("");
		txtPrixRemplacement.setText("");
		txtRechercherLangue.setText("");
		txtRechercherTitre.setText("");
		txtIdCatego.setText("");
		txtId.setText("");
		cbxLangue.getSelectionModel().clearSelection();
		cbxLangueOrigine.getSelectionModel().clearSelection();
		this.filmId = null;
		this.acteur = null;
		this.category = null;
	}
	
	void rafraichir() {
		rafraichirActor();
		rafraichirCategory();
		rafraichirFilm();
		vider();
	}
	
	//RAFRAICHIR ONE BY ONE
	void rafraichirActor() {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IActor iactor = (IActor) registry.lookup("actorDistant");
			List<Actor> actors = iactor.getActors();
			ObservableList<Actor> data = FXCollections.observableArrayList();
			for (Actor ent : actors) {
				data.add(ent);
			}
			tableauActor.getItems().clear();
			tableauActor.setItems(data);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void rafraichirCategory() {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("localhost", 1002);
			ICategory icategory = (ICategory) registry.lookup("categoryDistant");
			List<Category> categories = icategory.getCategories();
			ObservableList<Category> data = FXCollections.observableArrayList();
			for (Category ent : categories) {
				data.add(ent);
			}
			tableauCategory.getItems().clear();
			tableauCategory.setItems(data);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	void rafraichirFilm() {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IFilm ifilm = (IFilm) registry.lookup("filmDistant");
			List<Film> films = ifilm.getFilms();
			ObservableList<Film> data = FXCollections.observableArrayList();
			for (Film ent : films) {
				
				data.add(ent);
			}
			
			StringConverter<Language> convertir = new StringConverter<Language>() {
				
				@Override
				public String toString(Language lang) {
					return lang.getName();
				}
				
				@Override
				public Language fromString(String arg0) {
					return null;
				}
			};
			tableau.getItems().clear();
			
			tableau.setItems(data);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//VALIDATION DES TEXTES
	boolean isValide() {
		if(txtTitre.getText().isEmpty() || txtDescription.getText().isEmpty() || txtPrixLocation.getText().isEmpty() 
				|| txtCaracteristique.getText().isEmpty() || txtDureeFilm.getText().isEmpty() || txtEvaluation.getText().isEmpty()
				|| txtDureeLocation.getText().isEmpty() || txtPrixRemplacement.getText().isEmpty() || cbxLangue.getValue().getName().isEmpty()
				|| cbxLangueOrigine.getValue().getName().isEmpty())
			return false;
		return true;
	}
	
	void initFilm(Film film) {
		film.setTitle(txtTitre.getText());
		film.setDescription(txtDescription.getText());
		film.setReleaseYear(Date.from(txtDateSortie.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		film.setRentalDuration(Short.parseShort(txtDureeLocation.getText()));
		film.setRentalRate(new BigDecimal(txtPrixLocation.getText()));
		film.setLength(Short.parseShort(txtDureeFilm.getText()));
		film.setReplacementCost(new BigDecimal(txtPrixRemplacement.getText()));
		film.setRating(txtEvaluation.getText());
		film.setSpecialFeatures(txtCaracteristique.getText());
		film.setLastUpdate(new Date());
		film.setLanguageId(cbxLangue.getValue());
		film.setOriginalLanguageId(cbxLangueOrigine.getValue());
	}
	
	void remplirComboBox() {
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			ILanguage ilanguage = (ILanguage) registry.lookup("languageDistant");
			List<Language> languages = ilanguage.getLanguages();
			ObservableList<Language> data = FXCollections.observableArrayList();
			StringConverter<Language> convertir = new StringConverter<Language>() {
				
				@Override
				public String toString(Language object) {
					return object.getName();
				}
				
				@Override
				public Language fromString(String string) {
					return null;
				}
			};
			for (Language ent : languages) 
			{
				data.add(ent);
			}
			cbxLangue.setConverter(convertir);
			cbxLangueOrigine.setConverter(convertir);
			cbxLangue.setItems(data);
			cbxLangueOrigine.setItems(data);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	
	
	
	
}
