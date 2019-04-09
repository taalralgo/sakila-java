package stic3.sn.ui;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import cours.java.stic3.model.Actor;
import cours.java.stic3.service.IActor;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class DashBoardController {

    @FXML
    private Button btnStaff;

    @FXML
    private Button btnFilm;

    @FXML
    private Button btnActeur;

    @FXML
    private Button btnCategorie;

    @FXML
    private Button btnStore;

    @FXML
    private Button btnInventaire;
    
    @FXML
    private Button btnLocation;
    
    @FXML
    private Button btnPayment;
    
    @FXML
    private Button btnClient;

    @FXML
    private Button btnSettings;

    @FXML
    private AnchorPane holderPane;
    
    AnchorPane page;
    
    @FXML
    void actionActeur(ActionEvent event) {
    	createPage(page, "Acteur");
    }

    @FXML
    void actionCategorie(ActionEvent event) {
    	createPage(page, "Categorie");
    }
    
    @FXML
    void actionClient(ActionEvent event) {
    	createPage(page, "Customer");
    }

    @FXML
    void actionFilm(ActionEvent event) {
    	createPage(page, "Film");
    }

    @FXML
    void actionInventaire(ActionEvent event) {
    	createPage(page, "Inventory");
    }
    
    @FXML
    void actionLocation(ActionEvent event) {
    	createPage(page, "Location");
    }
    
    @FXML
    void actionPayment(ActionEvent event) {
    	createPage(page, "Payment");
    }

    @FXML
    void actionSettings(ActionEvent event) {

    }

    @FXML
    void actionStaff(ActionEvent event) throws RemoteException, NotBoundException {
    	createPage(page, "Staff");
    }

    @FXML
    void actionStore(ActionEvent event) {
    	createPage(page, "Store");
    }

	private void createPage(AnchorPane nomPage, String className) 
	{
		try 
		{
			nomPage = FXMLLoader.load(getClass().getResource("/stic3/sn/ui/"+className+".fxml"));
			setNode(nomPage);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void setNode(Node node)
	{
		holderPane.getChildren().clear();
		holderPane.getChildren().add(node);
		
		FadeTransition ft = new FadeTransition(Duration.millis(9000));
		ft.setNode(node);
		ft.setFromValue(0.1);
		ft.setToValue(1);
		ft.setCycleCount(1);
		ft.setAutoReverse(false);
		ft.play();
	}
    

}
