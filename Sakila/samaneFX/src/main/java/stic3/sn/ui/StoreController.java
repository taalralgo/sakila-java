package stic3.sn.ui;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import cours.java.stic3.model.Address;
import cours.java.stic3.model.Category;
import cours.java.stic3.model.Language;
import cours.java.stic3.model.Staff;
import cours.java.stic3.model.Store;
import cours.java.stic3.service.IAddress;
import cours.java.stic3.service.ICategory;
import cours.java.stic3.service.ILanguage;
import cours.java.stic3.service.IStaff;
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

public class StoreController implements Initializable 
{

    @FXML
    private AnchorPane staffPage;

    @FXML
    private ComboBox<Staff> cbxManager;

    @FXML
    private ComboBox<Address> cbxAdresse;

    @FXML
    private TableView<Store> tableau;

    @FXML
    private TableColumn<Store, Staff> colManager;

    @FXML
    private TableColumn<Store, Address> colAdresse;

    @FXML
    private TableColumn<Store, Date> colLastUpdate;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnActualiser;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;
    
    @FXML
    private TextField txtId;

    @FXML
    void actionActualiser(ActionEvent event) {

    }

    @FXML
    void actionAjouter(ActionEvent event) {
    	if (cbxAdresse.getValue().toString().isEmpty() ) {
			Utilitaires.showMessage("Error", "Erreur", "L'adresse n'est pas renseigé", 3);
		} 
		else {
			Store store = new Store();
			initStore(store);
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", 1002);
				IStore istore = (IStore) registry.lookup("storeDistant");
				List<Store> stores = istore.getStores();
				int verif = 0;
				for (Store a : stores) {
					if (a.getAddressId().getAddress().equals(store.getAddressId().getAddress()))
						verif++;
				}
				if (verif != 0) {
					Utilitaires.showMessage("Error", "Erreur", "Store existe deja", 3);
				} else {
					Store n = istore.addStore(store);
					if (n != null) {
						Utilitaires.showMessage("Success", "Information", "Store ajouter avec success", 1);
						vider();
						remplirTableau();
					} else
						Utilitaires.showMessage("Error", "Erreur", "Echec d'enregistrement", 3);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }

    private void remplirTableau() {
    	colManager.setCellValueFactory(new PropertyValueFactory<Store, Staff>("managerStaffId"));
		colLastUpdate.setCellValueFactory(new PropertyValueFactory<Store, Date>("lastUpdate"));
		colAdresse.setCellValueFactory(new PropertyValueFactory<Store, Address>("addressId"));
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IStore istore = (IStore) registry.lookup("storeDistant");
			List<Store> stores = istore.getStores();
			ObservableList<Store> data = FXCollections.observableArrayList();
			for (Store ent : stores) 
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

	private void vider() {
		cbxAdresse.getSelectionModel().clearSelection();
		cbxManager.getSelectionModel().clearSelection();
		txtId.setText("");
		
	}

	private void initStore(Store store) {
    	store.setLastUpdate(new Date());
    	store.setAddressId(cbxAdresse.getValue());
    	store.setManagerStaffId(cbxManager.getValue());
		
	}

	@FXML
    void actionModifier(ActionEvent event) {

    }

    @FXML
    void actionSupprimer(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		remplirTableau();
		remplirAdresse();
		remplirManager();
		tableauSelectedItem();
	}

	private void remplirManager() {
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
			cbxManager.setConverter(convertir);
			cbxManager.setItems(data);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private void remplirAdresse() {
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

	private void tableauSelectedItem() {
		tableau.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Store>() {

			@Override
			public void changed(ObservableValue<? extends Store> arg0, Store ancien, Store nouveau) {
				txtId.setText(String.valueOf(nouveau.getStoreId()));
			}

		});
	}

}
