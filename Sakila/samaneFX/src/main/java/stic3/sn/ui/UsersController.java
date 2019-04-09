package stic3.sn.ui;

import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import cours.java.stic3.model.Actor;
import cours.java.stic3.service.IActor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import stic3.sn.Utilitaires;

public class UsersController {

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnAdd;

    @FXML
    void addUser(ActionEvent event) throws NotBoundException {
    	Registry registry;
		try {
			registry = LocateRegistry.getRegistry("localhost",1002);
			//IUser iuser = (IUser)registry.lookup("actorDistant");
			IActor iactor = (IActor)registry.lookup("actorDistant");
			List<Actor> users = iactor.getActors();
	    	users.forEach((l)->System.out.println(l.getLastName()));
	    	//Utilitaires.showMessage("Cours java", "Information", "Nombre d'utilisateur = "+ users.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

}
