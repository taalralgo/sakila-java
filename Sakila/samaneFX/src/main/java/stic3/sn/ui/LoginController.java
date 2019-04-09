package stic3.sn.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import stic3.sn.LoadView;

public class LoginController {

	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Button btnConnexion;

	@FXML
	private Label txtIncorrect;

	@FXML
	private ImageView splash;

	@FXML
	void connexionAction(ActionEvent event) throws InterruptedException 
	{

		if (txtUsername.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty()) 
		{
			System.out.println("Un champ est vide");
			txtIncorrect.setText("Un champ est vide");
			txtIncorrect.setVisible(true);
		} 
		else 
		{
			if (txtUsername.getText().equals("admin") && txtPassword.getText().equals("passer")) 
			{
				LoadView.showView("Gestion cinema", "DashBoard.fxml", 1);
			} 
			else 
			{
				txtIncorrect.setText("Login ou mot de passe incorrect");
				txtIncorrect.setVisible(true);
				splash.setVisible(false);
			}
		}

	}

}
