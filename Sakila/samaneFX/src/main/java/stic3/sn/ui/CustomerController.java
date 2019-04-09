package stic3.sn.ui;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import cours.java.stic3.model.Actor;
import cours.java.stic3.model.Address;
import cours.java.stic3.model.Customer;
import cours.java.stic3.model.Film;
import cours.java.stic3.model.Language;
import cours.java.stic3.model.Store;
import cours.java.stic3.service.IAddress;
import cours.java.stic3.service.ICustomer;
import cours.java.stic3.service.IFilm;
import cours.java.stic3.service.ILanguage;
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

public class CustomerController implements Initializable
{

    @FXML
    private AnchorPane staffPage;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TableView<Customer> tableau;

    @FXML
    private TableColumn<Customer, String> colNom;

    @FXML
    private TableColumn<Customer, String> colPrenom;

    @FXML
    private TableColumn<Customer, Address> colAdresse;

    @FXML
    private TableColumn<Customer, String> colEmail;

    @FXML
    private TableColumn<Customer, Store> colStore;

    @FXML
    private TableColumn<Customer, String> colStatus;

    @FXML
    private TableColumn<Customer, Date> colCreatedAt;

    @FXML
    private TableColumn<Customer, Date> colLastUpdate;

    @FXML
    private TextField txtEmail;

    @FXML
    private ComboBox<Address> cbxAdresse;

    @FXML
    private ComboBox<String> cbxStatut;

    @FXML
    private ComboBox<Store> cbxStore;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnActualiser;

    @FXML
    private TextField txtRechercherNom;

    @FXML
    private TextField txtRechercherPrenom;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    void actionActualiser(ActionEvent event) {
    	vider();
    }

    @FXML
    void actionAjouter(ActionEvent event) {
    	if(isValide()) {
    		Customer client = new Customer();
    		initClient(client);
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", 1002);
				ICustomer iclient = (ICustomer) registry.lookup("customerDistant");
				List<Customer> clients = iclient.getCustomers();
				int verif = 0;
				for (Customer a : clients) {
					if (a.getFirstName().equals(client.getFirstName()) && a.getLastName().equals(client.getLastName()) 
							&& a.getAddressId().equals(client.getAddressId()))
						verif++;
				}
				if (verif != 0) {
					Utilitaires.showMessage("Error", "Erreur", "Le client existe deja", 3);
				} 
				else {
					Customer c = iclient.addCustomer(client);
					if (c != null) {
						Utilitaires.showMessage("Success", "Information", "Client ajouter avec success", 1);
						rafraichir();
					} 
					else
						Utilitaires.showMessage("Error", "Erreur", "Echec d'enregistrement", 3);
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	else
    		Utilitaires.showMessage("", "Erreur", "Un des champs est vide", 3);
    }

	@FXML
    void actionModifier(ActionEvent event) {

    }

    @FXML
    void actionSupprimer(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Remplissage
		remplirTableau();
		remplirComboAdresse();
		remplirComboStore();
		remplirComboStatus();
		
		//Initialisation
		tableauSelectedItem();
	}
	
	//Selection d'un element du tableau
	private void tableauSelectedItem() {
		tableau.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {

			@Override
			public void changed(ObservableValue<? extends Customer> arg0, Customer ancien, Customer nouveau) {
				txtNom.setText(nouveau.getLastName());
				txtPrenom.setText(nouveau.getFirstName());
				txtEmail.setText(nouveau.getEmail());
				cbxAdresse.setValue(nouveau.getAddressId());
				cbxStore.setValue(nouveau.getStoreId());
				if(nouveau.getActive())
					cbxStatut.setValue("Active");
				else
					cbxStatut.setValue("Desactive");
				
			}

		});
	}

	//REMPLISSAGE
	private void remplirComboStatus() {
		ObservableList<String> data = FXCollections.observableArrayList();
		List<String> listStatus = new ArrayList<String>();
		listStatus.add("Active");
		listStatus.add("Desactive");
		for (String ent : listStatus) 
		{
			data.add(ent);
		}
		cbxStatut.setItems(data);
	}
	
	
	private void remplirComboStore() {
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

	private void remplirComboAdresse() {
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IAddress iadresse = (IAddress) registry.lookup("adresseDistant");
			List<Address> adresses = iadresse.getAddress();
			ObservableList<Address> data = FXCollections.observableArrayList();
			StringConverter<Address> convertir = new StringConverter<Address>() {
				
				@Override
				public String toString(Address object) {
					return object.getAddress();
				}
				
				@Override
				public Address fromString(String string) {
					return null;
				}
			};
			for (Address ent : adresses) 
			{
				data.add(ent);
			}
			cbxAdresse.setConverter(convertir);
			cbxAdresse.setItems(data);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private void remplirTableau() {
		colNom.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
		colPrenom.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
		colAdresse.setCellValueFactory(new PropertyValueFactory<Customer, Address>("addressId"));
		colStore.setCellValueFactory(new PropertyValueFactory<Customer, Store>("storeId"));
		colEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
		colStatus.setCellValueFactory(new PropertyValueFactory<Customer, String>("active"));
		colCreatedAt.setCellValueFactory(new PropertyValueFactory<Customer, Date>("createDate"));
		colLastUpdate.setCellValueFactory(new PropertyValueFactory<Customer, Date>("lastUpdate"));
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			ICustomer ifilm = (ICustomer) registry.lookup("customerDistant");
			List<Customer> clients = ifilm.getCustomers();
			ObservableList<Customer> data = FXCollections.observableArrayList();
			for (Customer ent : clients) 
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

	private void vider() {
		txtNom.setText("");
		txtPrenom.setText("");
		txtEmail.setText("");
		cbxAdresse.getSelectionModel().clearSelection();
		cbxStore.getSelectionModel().clearSelection();
		cbxStatut.getSelectionModel().clearSelection();
		cbxStatut.valueProperty().set(null);
		txtRechercherNom.setText("");
		txtRechercherPrenom.setText("");
	}
	
	private boolean isValide() {
		if(txtNom.getText().isEmpty() || txtPrenom.getText().isEmpty() || txtEmail.getText().isEmpty() || cbxAdresse.getValue() == null
				|| cbxStatut.getValue().isEmpty() || cbxStore.getValue() == null)
			return false;
		return true;
	}
	
	private void initClient(Customer client) {
		client.setFirstName(txtPrenom.getText());
		client.setLastName(txtNom.getText());
		client.setEmail(txtEmail.getText());
		client.setAddressId(cbxAdresse.getValue());
		client.setStoreId(cbxStore.getValue());
		client.setCreateDate(new Date());
		client.setLastUpdate(new Date());
		if(cbxStatut.getValue().equals("Active"))
			client.setActive(true);
		else
			client.setActive(false);
	}
	
	private void rafraichir() {
		remplirTableau();
		vider();
	}

}
