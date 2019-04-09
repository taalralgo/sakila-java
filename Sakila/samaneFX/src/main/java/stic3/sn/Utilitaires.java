package stic3.sn;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Utilitaires {

	public static void showMessage(String title, String header, String content, int type) {
		Alert a = null;
		if(type == 1)
			a = new Alert(AlertType.INFORMATION);
		if(type == 2)
			a = new Alert(AlertType.WARNING);
		if(type == 3)
			a = new Alert(AlertType.ERROR);
		a.setTitle(title);
		a.setHeaderText(header);
		a.setContentText(content);
		a.showAndWait();
	}

}
