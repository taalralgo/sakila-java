package stic3.sn.ui;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import cours.java.stic3.model.Category;
import cours.java.stic3.service.ICategory;
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

public class CategoryController implements Initializable{

    @FXML
    private AnchorPane staffPage;

    @FXML
    private TextField txtNom;

    @FXML
    private TableView<Category> tableau;

    @FXML
    private TableColumn<Category, String> colNom;

    @FXML
    private TableColumn<Category, Date> colLastUpdate;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnActualiser;

    @FXML
    private TextField txtRechercherNom;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private Button btnRechercher;
    
    @FXML
    private TextField txtId;

    @FXML
    void actionActualiser(ActionEvent event) {
    	remplirTableau();
    }

    @FXML
    void actionAjouter(ActionEvent event) {
    	if (txtNom.getText().toString().isEmpty() ) {
			Utilitaires.showMessage("Error", "Erreur", "Le champ est vide", 3);
		} 
		else {
			Category category = new Category();
			category.setName(((txtNom.getText().toString())));
			category.setLastUpdate(new Date());
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", 1002);
				ICategory icatego = (ICategory) registry.lookup("categoryDistant");
				List<Category> categos = icatego.getCategories();
				int verif = 0;
				for (Category a : categos) {
					if (a.getName().equals(category.getName()))
						verif++;
				}
				if (verif != 0) {
					Utilitaires.showMessage("Error", "Erreur", "Categerie existe deja", 3);
				} else {
					Category n = icatego.addCategory(category);
					if (n != null) {
						Utilitaires.showMessage("Success", "Information", "Category ajouter avec success", 1);
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

    private void vider() {
    	txtNom.setText("");
    	txtId.setText("");
		
	}

	@FXML
    void actionModifier(ActionEvent event) {

    }

    @FXML
    void actionRechercher(ActionEvent event) {

    }

    @FXML
    void actionSupprimer(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		remplirTableau();
		tableauSelectedItem();
		
	}

	private void tableauSelectedItem() {
		tableau.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Category>() {

			@Override
			public void changed(ObservableValue<? extends Category> arg0, Category ancienCatego, Category nouveauCatego) {
				txtNom.setText(nouveauCatego.getName());
				txtId.setText(String.valueOf(nouveauCatego.getCategoryId()));
			}

		});
		
	}

	private void remplirTableau() {
		colNom.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));
		colLastUpdate.setCellValueFactory(new PropertyValueFactory<Category, Date>("lastUpdate"));
		Registry registry;
		try 
		{
			registry = LocateRegistry.getRegistry("localhost", 1002);
			ICategory icatego = (ICategory) registry.lookup("categoryDistant");
			List<Category> categos = icatego.getCategories();
			ObservableList<Category> data = FXCollections.observableArrayList();
			for (Category ent : categos) 
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
