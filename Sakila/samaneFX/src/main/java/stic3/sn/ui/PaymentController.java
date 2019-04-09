package stic3.sn.ui;

import java.math.BigDecimal;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import cours.java.stic3.model.Customer;
import cours.java.stic3.model.Payment;
import cours.java.stic3.model.Rental;
import cours.java.stic3.model.Staff;
import cours.java.stic3.service.IPayment;
import cours.java.stic3.service.IRental;
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

public class PaymentController implements Initializable
{

    @FXML
    private AnchorPane staffPage;

    @FXML
    private ComboBox<Rental> cbxLocation;

    @FXML
    private ComboBox<Customer> cbxClient;

    @FXML
    private ComboBox<Staff> cbxStaff;

    @FXML
    private TextField txtMontant;

    @FXML
    private TableView<Payment> tableau;

    @FXML
    private TableColumn<Payment, Customer> colClient;

    @FXML
    private TableColumn<Payment, Staff> colStaff;

    @FXML
    private TableColumn<Payment, Rental> colRental;

    @FXML
    private TableColumn<Payment, BigDecimal> colMontant;

    @FXML
    private TableColumn<Payment, Date> colDatePayment;

    @FXML
    private TableColumn<Payment, Date> colLastUpdate;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnActualiser;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;
    
    private Short paymentId;

    @FXML
    void actionActualiser(ActionEvent event) {
    	
    }

    @FXML
    void actionAjouter(ActionEvent event) {
    	if (cbxClient.getValue().toString().isEmpty() || cbxLocation.getValue().toString().isEmpty() || cbxStaff.getValue().toString().isEmpty()) {
			Utilitaires.showMessage("Error", "Erreur", "Une liste de selection est vide", 3);
		} 
		else {
			Payment category = new Payment();
			category.setCustomerId(cbxClient.getValue());
			category.setRentalId(cbxLocation.getValue());
			category.setStaffId(cbxStaff.getValue());
			category.setAmount(new BigDecimal(txtMontant.getText()));
			category.setPaymentDate(new Date());
			category.setLastUpdate(new Date());
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", 1002);
				IPayment icatego = (IPayment) registry.lookup("PaymentDistant");
				List<Payment> categos = icatego.getPayments();
				int verif = 0;
				for (Payment a : categos) {
					if (a.getStaffId().equals(category.getStaffId()) && a.getCustomerId().equals(category.getCustomerId()) 
					&& a.getRentalId().equals(category.getRentalId()) && a.getPaymentDate().equals(category.getPaymentDate()))
						verif++;
				}
				if (verif != 0) {
					Utilitaires.showMessage("Error", "Erreur", "Le Le payment a deja été effectué", 3);
				} else {
					Payment n = icatego.addPayment(category);
					if (n != null) {
						Utilitaires.showMessage("Success", "Information", "Payment effectué avec success", 1);
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
    	if (this.paymentId == null || cbxClient.getValue().toString().isEmpty() || cbxLocation.getValue().toString().isEmpty() 
    			|| cbxStaff.getValue().toString().isEmpty()) {
			Utilitaires.showMessage("Error", "Erreur", "Un champ est vide", 3);
		} 
    	else {
    		Payment category = new Payment();
			category.setCustomerId(cbxClient.getValue());
			category.setRentalId(cbxLocation.getValue());
			category.setStaffId(cbxStaff.getValue());
			category.setAmount(new BigDecimal(txtMontant.getText()));
			category.setPaymentDate(new Date());
			category.setLastUpdate(new Date());
			category.setPaymentId(this.paymentId);
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", 1002);
				IPayment icatego = (IPayment) registry.lookup("PaymentDistant");
				List<Payment> categos = icatego.getPayments();
				int verif = 0;
				for (Payment a : categos) {
					if (a.getStaffId().equals(category.getStaffId()) && a.getCustomerId().equals(category.getCustomerId()) 
					&& a.getRentalId().equals(category.getRentalId()) && a.getPaymentDate().equals(category.getPaymentDate()))
						verif++;
				}
				if (verif != 0) {
					Utilitaires.showMessage("Error", "Erreur", "Le Le payment a deja été effectué", 3);
				} else {
					Payment n = icatego.modifierPayment(category);
					if (n != null) {
						Utilitaires.showMessage("Success", "Information", "Payment effectué avec success", 1);
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
		//remplirClient();
		//remplirStaff();
		remplirRental();
		//////////////////////
		cbxLocation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Rental>() {

			@Override
			public void changed(ObservableValue<? extends Rental> observable, Rental oldValue, Rental newValue) {
				cbxClient.setValue(newValue.getCustomerId());
				cbxStaff.setValue(newValue.getStaffId());
				BigDecimal montant;
				montant = newValue.getInventoryId().getFilmId().getRentalRate();
				txtMontant.setText(String.valueOf(montant));
			}
			
		});
		//////////////////////
		tableauSelectedItem();
	}

	private void remplirTableau() {
		colClient.setCellValueFactory(new PropertyValueFactory<Payment, Customer>("customerId"));
		colStaff.setCellValueFactory(new PropertyValueFactory<Payment, Staff>("staffId"));
		colRental.setCellValueFactory(new PropertyValueFactory<Payment, Rental>("rentalId"));
		colMontant.setCellValueFactory(new PropertyValueFactory<Payment, BigDecimal>("amount"));
		colDatePayment.setCellValueFactory(new PropertyValueFactory<Payment, Date>("paymentDate"));
		colLastUpdate.setCellValueFactory(new PropertyValueFactory<Payment, Date>("lastUpdate"));
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IPayment iinventory = (IPayment) registry.lookup("rentalDistant");
			List<Payment> inventories = iinventory.getPayments();
			ObservableList<Payment> data = FXCollections.observableArrayList();
			for (Payment ent : inventories) 
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

	/*private void remplirClient() {
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
	}*/

	private void remplirRental() {
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			IRental ilanguage = (IRental) registry.lookup("rentalDistant");
			List<Rental> languages = ilanguage.getRentals();
			ObservableList<Rental> data = FXCollections.observableArrayList();
			StringConverter<Rental> convertir = new StringConverter<Rental>() {
				
				@Override
				public String toString(Rental object) {
					return object.getInventoryId().getFilmId().getTitle();
				}
				
				@Override
				public Rental fromString(String string) {
					return null;
				}
			};
			for (Rental ent : languages) 
			{
				data.add(ent);
			}
			cbxLocation.setConverter(convertir);
			cbxLocation.setItems(data);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private void tableauSelectedItem() {
		tableau.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Payment>() {

			@Override
			public void changed(ObservableValue<? extends Payment> arg0, Payment ancien, Payment nouveau) {
				PaymentController.this.paymentId = nouveau.getPaymentId();
				cbxClient.setValue(nouveau.getCustomerId());
				cbxLocation.setValue(nouveau.getRentalId());
				cbxStaff.setValue(nouveau.getStaffId());
				txtMontant.setText(String.valueOf(nouveau.getAmount()));
			}

		});
	}
	
	void vider() {
		cbxClient.getSelectionModel().clearSelection();
		cbxLocation.getSelectionModel().clearSelection();
		cbxStaff.getSelectionModel().clearSelection();
		txtMontant.setText("");
	}
}
