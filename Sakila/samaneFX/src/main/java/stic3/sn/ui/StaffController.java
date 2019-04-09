package stic3.sn.ui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import cours.java.stic3.model.Address;
import cours.java.stic3.model.Staff;
import cours.java.stic3.model.Store;
import cours.java.stic3.service.IAddress;
import cours.java.stic3.service.IStaff;
import cours.java.stic3.service.IStore;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import stic3.sn.Utilitaires;

public class StaffController implements Initializable
{
    @FXML
    private AnchorPane staffPage;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TableView<Staff> tableau;

    @FXML
    private TableColumn<Staff, String> colNom;

    @FXML
    private TableColumn<Staff, String> colPrenom;

    @FXML
    private TableColumn<Staff, Address> colAdresse;

    @FXML
    private TableColumn<Staff, String> colEmail;

    @FXML
    private TableColumn<Staff, Store> colStore;

    @FXML
    private TableColumn<Staff, String> colStatut;

    @FXML
    private TableColumn<Staff, String> colUsername;

    @FXML
    private TableColumn<Staff, String> colPassword;

    @FXML
    private TableColumn<Staff, Date> colLastUpdate;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtUsername;

    @FXML
    private ComboBox<Address> cbxAdresse;

    @FXML
    private TextField txtPassword;

    @FXML
    private ComboBox<String> cbxStatut;

    @FXML
    private ComboBox<Store> cbxStore;

    @FXML
    private Button btnAjouter;

    @FXML
    private ImageView txtPicture;

    @FXML
    private TextField txtParcourir;

    @FXML
    private Button btnParcourir;

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
    
    byte[] res;

    @FXML
    void actionActualiser(ActionEvent event) {
    	remplirTableau();
    }

    @FXML
    void actionAjouter(ActionEvent event) {
    	if(isValide()) {
    		Staff client = new Staff();
    		initClient(client);
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", 1002);
				IStaff istaff = (IStaff) registry.lookup("staffDistant");
				List<Staff> staffs = istaff.getStaffs();
				int verif = 0;
				for (Staff a : staffs) {
					if (a.getFirstName().equals(client.getFirstName()) && a.getLastName().equals(client.getLastName()) 
							&& a.getAddressId().equals(client.getAddressId()) && a.getStore().equals(client.getStoreId()))
						verif++;
				}
				if (verif != 0) {
					Utilitaires.showMessage("Error", "Erreur", "Le staff existe deja", 3);
				} 
				else {
					Staff c = istaff.addStaff(client);
					if (c != null) {
						Utilitaires.showMessage("Success", "Information", "Staff ajouter avec success", 1);
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

    private void rafraichir() {
    	remplirTableau();
		vider();
	}

	private void initClient(Staff client) {
    	client.setFirstName(txtPrenom.getText());
		client.setLastName(txtNom.getText());
		client.setEmail(txtEmail.getText());
		client.setPassword(txtPassword.getText());
		client.setUsername(txtUsername.getText());
		client.setAddressId(cbxAdresse.getValue());
		client.setStoreId(cbxStore.getValue());
		client.setPicture(this.res);
		client.setLastUpdate(new Date());
		if(cbxStatut.getValue().equals("Active"))
			client.setActive(true);
		else
			client.setActive(false);
	}

	private boolean isValide() {
    	if(txtNom.getText().isEmpty() || txtPrenom.getText().isEmpty() || txtEmail.getText().isEmpty() || txtParcourir.getText().isEmpty()
    			|| cbxAdresse.getValue() == null || cbxStatut.getValue().isEmpty() || cbxStore.getValue() == null)
			return false;
		return true;
	}

	@FXML
    void actionModifier(ActionEvent event) {

    }

    @FXML
    void actionParcourir(ActionEvent event) {
    	FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        if(file != null) {
            String imagepath;
			try 
			{
				imagepath = file.toURI().toURL().toString();
				
	            Image image = new Image(imagepath);
	            txtPicture.setImage(image);
	            
				BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
				ByteArrayOutputStream s = new ByteArrayOutputStream();
				ImageIO.write(bImage, "png", s);
				res  = s.toByteArray();
				txtParcourir.setText(res.toString());
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
            
        }
        else
        	Utilitaires.showMessage("Erreur", "Pas d' image selectionné", "Veuillez selectionner une image", 3);
    }

    @FXML
    void actionSupprimer(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Remplissage
		remplirTableau();
		remplirComboAdresse();
		remplirComboStore();
		remplirComboStatus();

		// Initialisation
		tableauSelectedItem();
	}

	private void tableauSelectedItem() {
		tableau.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Staff>() {

			@Override
			public void changed(ObservableValue<? extends Staff> arg0, Staff ancien, Staff nouveau) {
				txtNom.setText(nouveau.getLastName());
				txtPrenom.setText(nouveau.getFirstName());
				txtEmail.setText(nouveau.getEmail());
				txtUsername.setText(nouveau.getUsername());
				txtParcourir.setText(nouveau.getPicture().toString());
				txtPassword.setText(nouveau.getPassword());
				cbxAdresse.setValue(nouveau.getAddressId());
				cbxStore.setValue(nouveau.getStoreId());
				Image img = new Image(new ByteArrayInputStream(nouveau.getPicture()));
				txtPicture.setImage(img);
				if(nouveau.getActive())
					cbxStatut.setValue("Active");
				else
					cbxStatut.setValue("Desactive");
				
			}

		});
	}

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
		colNom.setCellValueFactory(new PropertyValueFactory<Staff, String>("lastName"));
		colPrenom.setCellValueFactory(new PropertyValueFactory<Staff, String>("firstName"));
		colAdresse.setCellValueFactory(new PropertyValueFactory<Staff, Address>("addressId"));
		colStore.setCellValueFactory(new PropertyValueFactory<Staff, Store>("storeId"));
		colEmail.setCellValueFactory(new PropertyValueFactory<Staff, String>("email"));
		colStatut.setCellValueFactory(new PropertyValueFactory<Staff, String>("active"));
		colUsername.setCellValueFactory(new PropertyValueFactory<Staff, String>("username"));
		colPassword.setCellValueFactory(new PropertyValueFactory<Staff, String>("password"));
		colLastUpdate.setCellValueFactory(new PropertyValueFactory<Staff, Date>("lastUpdate"));
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IStaff isatff = (IStaff) registry.lookup("staffDistant");
			List<Staff> staffs = isatff.getStaffs();
			ObservableList<Staff> data = FXCollections.observableArrayList();
			for (Staff ent : staffs) 
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
		cbxAdresse.getSelectionModel().clearSelection();
		cbxStore.getSelectionModel().clearSelection();
		cbxStatut.getSelectionModel().clearSelection();
		txtEmail.setText("");
		txtUsername.setText("");
		txtPassword.setText("");
		txtParcourir.setText("");
		txtRechercherNom.setText("");
		txtRechercherPrenom.setText("");
		
	}

}
