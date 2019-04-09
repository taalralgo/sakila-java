package stic3.sn.ui;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import cours.java.stic3.model.Customer;
import cours.java.stic3.model.Inventory;
import cours.java.stic3.model.Rental;
import cours.java.stic3.model.Staff;
import cours.java.stic3.service.ICustomer;
import cours.java.stic3.service.IIventory;
import cours.java.stic3.service.IRental;
import cours.java.stic3.service.IStaff;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import stic3.sn.Utilitaires;

public class LocationController implements Initializable
{

    @FXML
    private AnchorPane staffPage;

    @FXML
    private ComboBox<Inventory> cbxInventaire;

    @FXML
    private ComboBox<Customer> cbxClient;

    @FXML
    private ComboBox<Staff> cbxStaff;

    @FXML
    private DatePicker txtDateRetour;

    @FXML
    private TableView<Rental> tableau;

    @FXML
    private TableColumn<Rental, Inventory> colInventaire;

    @FXML
    private TableColumn<Rental, Customer> colClient;

    @FXML
    private TableColumn<Rental, Staff> colStaff;

    @FXML
    private TableColumn<Rental, Date> colDateLocation;

    @FXML
    private TableColumn<Rental, Date> colDateRetour;

    @FXML
    private TableColumn<Rental, Date> colLastUpdate;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnActualiser;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;
    
    private Integer rentalId;

    @FXML
    void actionActualiser(ActionEvent event) {
    	remplirTableau();
    }

    @FXML
    void actionAjouter(ActionEvent event) {
    	if (cbxClient.getValue().toString().isEmpty() || cbxInventaire.getValue().toString().isEmpty() || cbxStaff.getValue().toString().isEmpty()) {
			Utilitaires.showMessage("Error", "Erreur", "Une liste de selection est vide", 3);
		} 
		else {
			Rental category = new Rental();
			category.setCustomerId(cbxClient.getValue());
			category.setInventoryId(cbxInventaire.getValue());
			category.setStaffId(cbxStaff.getValue());
			category.setRentalDate(new Date());
			category.setReturnDate(Date.from(txtDateRetour.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			category.setLastUpdate(new Date());
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", 1002);
				IRental icatego = (IRental) registry.lookup("rentalDistant");
				List<Rental> categos = icatego.getRentals();
				int verif = 0;
				for (Rental a : categos) {
					if (a.getInventoryId().equals(category.getInventoryId()) && a.getCustomerId().equals(category.getCustomerId()))
						verif++;
				}
				if (verif != 0) {
					Utilitaires.showMessage("Error", "Erreur", "Le client ne peut pas louer deux exemplaires ", 3);
				} else {
					Rental n = icatego.addRental(category);
					if (n != null) {
						Utilitaires.showMessage("Success", "Information", "Film louer avec success", 1);
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
    	if (cbxClient.getValue().toString().isEmpty() || cbxInventaire.getValue().toString().isEmpty() || cbxStaff.getValue().toString().isEmpty()) {
			Utilitaires.showMessage("Error", "Erreur", "Un champ est vide", 3);
		} 
    	else {
    		Rental category = new Rental();
			category.setCustomerId(cbxClient.getValue());
			category.setInventoryId(cbxInventaire.getValue());
			category.setStaffId(cbxStaff.getValue());
			category.setRentalDate(new Date());
			category.setReturnDate(Date.from(txtDateRetour.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			category.setLastUpdate(new Date());
			category.setRentalId(rentalId);
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", 1002);
				IRental icatego = (IRental) registry.lookup("rentalDistant");
				List<Rental> categos = icatego.getRentals();
				int verif = 0;
				for (Rental a : categos) {
					if (a.getStaffId().equals(category.getStaffId()) && a.getCustomerId().equals(category.getCustomerId()))
						verif++;
				}
				if (verif == 0) {
					Utilitaires.showMessage("Error", "Erreur", "Le client ne peut pas louer deux exemplaires ou la location n'existe pas ", 3);
				} else {
					Rental n = icatego.modifierRental(category);
					if (n != null) {
						Utilitaires.showMessage("Success", "Information", "Location modifier avec success", 1);
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
		remplirStaff();
		remplirIventaire();
		remplirClient();
		
		tableauSelectedItem();
	}

	private void remplirTableau() {
		colInventaire.setCellValueFactory(new PropertyValueFactory<Rental, Inventory>("inventoryId"));
		colClient.setCellValueFactory(new PropertyValueFactory<Rental, Customer>("customerId"));
		colStaff.setCellValueFactory(new PropertyValueFactory<Rental, Staff>("staffId"));
		colDateLocation.setCellValueFactory(new PropertyValueFactory<Rental, Date>("rentalDate"));
		colDateRetour.setCellValueFactory(new PropertyValueFactory<Rental, Date>("returnDate"));
		colLastUpdate.setCellValueFactory(new PropertyValueFactory<Rental, Date>("lastUpdate"));
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IRental iinventory = (IRental) registry.lookup("rentalDistant");
			List<Rental> inventories = iinventory.getRentals();
			ObservableList<Rental> data = FXCollections.observableArrayList();
			for (Rental ent : inventories) 
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

	private void remplirStaff() {
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IStaff istaff = (IStaff) registry.lookup("staffDistant");
			List<Staff> staffs = istaff.getStaffs();
			ObservableList<Staff> data = FXCollections.observableArrayList();
			StringConverter<Staff> convertir = new StringConverter<Staff>() {
				
				@Override
				public String toString(Staff object) {
					return object.getFirstName()+" "+object.getLastName();
				}
				
				@Override
				public Staff fromString(String string) {
					return null;
				}
			};
			for (Staff ent : staffs) 
			{
				data.add(ent);
			}
			cbxStaff.setConverter(convertir);
			cbxStaff.setItems(data);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private void remplirIventaire() {
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IIventory istaff = (IIventory) registry.lookup("inventoryDistant");
			List<Inventory> staffs = istaff.getInventories();
			ObservableList<Inventory> data = FXCollections.observableArrayList();
			StringConverter<Inventory> convertir = new StringConverter<Inventory>() {
				
				@Override
				public String toString(Inventory object) {
					return object.getFilmId()+" "+object.getStoreId();
				}
				
				@Override
				public Inventory fromString(String string) {
					return null;
				}
			};
			for (Inventory ent : staffs) 
			{
				data.add(ent);
			}
			cbxInventaire.setConverter(convertir);
			cbxInventaire.setItems(data);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private void remplirClient() {
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			ICustomer istaff = (ICustomer) registry.lookup("customerDistant");
			List<Customer> staffs = istaff.getCustomers();
			ObservableList<Customer> data = FXCollections.observableArrayList();
			StringConverter<Customer> convertir = new StringConverter<Customer>() {
				
				@Override
				public String toString(Customer object) {
					return object.getFirstName()+" "+object.getLastName();
				}
				
				@Override
				public Customer fromString(String string) {
					return null;
				}
			};
			for (Customer ent : staffs) 
			{
				data.add(ent);
			}
			cbxClient.setConverter(convertir);
			cbxClient.setItems(data);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private void tableauSelectedItem() {
		tableau.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Rental>() {

			@Override
			public void changed(ObservableValue<? extends Rental> arg0, Rental ancien, Rental nouveau) {
				cbxClient.setValue(nouveau.getCustomerId());
				cbxInventaire.setValue(nouveau.getInventoryId());
				cbxStaff.setValue(nouveau.getStaffId());
				txtDateRetour.setValue(LocalDate.from(nouveau.getReturnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
				LocationController.this.rentalId = nouveau.getRentalId();
			}

		});
	}

	void vider() {
		cbxClient.getSelectionModel().clearSelection();
		cbxStaff.getSelectionModel().clearSelection();
		cbxInventaire.getSelectionModel().clearSelection();
		this.rentalId = null;
	}
}
