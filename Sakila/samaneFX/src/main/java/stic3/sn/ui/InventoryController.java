package stic3.sn.ui;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import cours.java.stic3.model.Film;
import cours.java.stic3.model.Inventory;
import cours.java.stic3.model.Store;
import cours.java.stic3.service.IFilm;
import cours.java.stic3.service.IIventory;
import cours.java.stic3.service.IStore;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import stic3.sn.Utilitaires;

public class InventoryController implements Initializable
{

    @FXML
    private AnchorPane staffPage;

    @FXML
    private ComboBox<Film> cbxFilm;

    @FXML
    private ComboBox<Store> cbxStore;

    @FXML
    private TableView<Inventory> tableau;

    @FXML
    private TableColumn<Inventory, Film> colFilm;

    @FXML
    private TableColumn<Inventory, Store> colStore;

    @FXML
    private TableColumn<Inventory, Date> colLastUpdate;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnActualiser;

    @FXML
    private TextField txtRechercherStore;

    @FXML
    private TextField txtRechercherFilm;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;
    
    private Integer inventoryId;

    @FXML
    void actionActualiser(ActionEvent event) {
    	remplirTableau();
    }

    @FXML
    void actionAjouter(ActionEvent event) {
    	if (cbxFilm.getValue().toString().isEmpty() || cbxStore.getValue().toString().isEmpty()) {
			Utilitaires.showMessage("Error", "Erreur", "Une liste de selection est vide", 3);
		} 
		else {
			Inventory category = new Inventory();
			category.setFilmId(cbxFilm.getValue());
			category.setStoreId(cbxStore.getValue());
			category.setLastUpdate(new Date());
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", 1002);
				IIventory icatego = (IIventory) registry.lookup("inventoryDistant");
				List<Inventory> categos = icatego.getInventories();
				int verif = 0;
				for (Inventory a : categos) {
					if (a.getFilmId().equals(category.getFilmId()) && a.getStoreId().equals(category.getStoreId()))
						verif++;
				}
				if (verif != 0) {
					Utilitaires.showMessage("Error", "Erreur", "L'inventaire existe deja", 3);
				} else {
					Inventory n = icatego.addInventory(category);
					if (n != null) {
						Utilitaires.showMessage("Success", "Information", "Inventaire ajouter avec success", 1);
						remplirTableau();
					} else
						Utilitaires.showMessage("Error", "Erreur", "Echec d'enregistrement", 3);
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
    }

    @FXML
    void actionModifier(ActionEvent event) {
    	if (cbxFilm.getValue().toString().isEmpty() || cbxStore.getValue().toString().isEmpty()) {
			Utilitaires.showMessage("Error", "Erreur", "Un champ est vide", 3);
		} 
    	else {
			Inventory category = new Inventory();
			category.setFilmId(cbxFilm.getValue());
			category.setStoreId(cbxStore.getValue());
			category.setLastUpdate(new Date());
			category.setInventoryId(inventoryId);
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", 1002);
				IIventory icatego = (IIventory) registry.lookup("inventoryDistant");
				List<Inventory> categos = icatego.getInventories();
				int verif = 0;
				for (Inventory a : categos) {
					if (a.getFilmId().equals(category.getFilmId()) && a.getStoreId().equals(category.getStoreId()))
						verif++;
				}
				if (verif != 0) {
					Utilitaires.showMessage("Error", "Erreur", "L'inventaire existe deja", 3);
				} else {
					Inventory n = icatego.modifierInventory(category);
					if (n != null) {
						Utilitaires.showMessage("Success", "Information", "Inventaire modifier avec success", 1);
						remplirTableau();
					} else
						Utilitaires.showMessage("Error", "Erreur", "Echec d'enregistrement", 3);
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
    }

    @FXML
    void actionSupprimer(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		remplirTableau();
		remplirFilm();
		remplirStore();
		tableauSelectedItem();
	}

	private void remplirStore() {
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IStore istore = (IStore) registry.lookup("storeDistant");
			List<Store> stores = istore.getStores();
			ObservableList<Store> data = FXCollections.observableArrayList();
			StringConverter<Store> convertir = new StringConverter<Store>() {
				
				@Override
				public String toString(Store object) {
					return object.getAddressId().getAddress();
				}
				
				@Override
				public Store fromString(String string) {
					return null;
				}
			};
			for (Store ent : stores) 
			{
				data.add(ent);
			}
			cbxStore.setConverter(convertir);
			cbxStore.setItems(data);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private void remplirFilm() {
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IFilm ifilm = (IFilm) registry.lookup("filmDistant");
			List<Film> films = ifilm.getFilms();
			ObservableList<Film> data = FXCollections.observableArrayList();
			StringConverter<Film> convertir = new StringConverter<Film>() {
				
				@Override
				public String toString(Film object) {
					return object.getTitle().toString();
				}
				
				@Override
				public Film fromString(String string) {
					return null;
				}
			};
			for (Film ent : films) 
			{
				data.add(ent);
			}
			cbxFilm.setConverter(convertir);
			cbxFilm.setItems(data);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private void remplirTableau() {
		colFilm.setCellValueFactory(new PropertyValueFactory<Inventory, Film>("filmId"));
		colStore.setCellValueFactory(new PropertyValueFactory<Inventory, Store>("storeId"));
		colLastUpdate.setCellValueFactory(new PropertyValueFactory<Inventory, Date>("lastUpdate"));
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IIventory iinventory = (IIventory) registry.lookup("inventoryDistant");
			List<Inventory> inventories = iinventory.getInventories();
			ObservableList<Inventory> data = FXCollections.observableArrayList();
			for (Inventory ent : inventories) 
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
	
	private void tableauSelectedItem() {
		tableau.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Inventory>() {

			@Override
			public void changed(ObservableValue<? extends Inventory> arg0, Inventory ancien, Inventory nouveau) {
				cbxFilm.setValue(nouveau.getFilmId());
				cbxStore.setValue(nouveau.getStoreId());
				InventoryController.this.inventoryId = nouveau.getInventoryId();
			}

		});
		
	}
	
	void vider() {
		cbxStore.getSelectionModel().clearSelection();
		cbxFilm.getSelectionModel().clearSelection();
		txtRechercherFilm.setText("");
		txtRechercherStore.setText("");
	}
}
