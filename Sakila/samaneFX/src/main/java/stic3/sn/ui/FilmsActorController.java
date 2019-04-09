package stic3.sn.ui;

import java.io.IOException;
import java.util.Date;

import cours.java.stic3.model.Film;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class FilmsActorController {
	
	@FXML
    private AnchorPane holderPane;

    @FXML
    private TableView<Film> tableauFilmsActor;

    @FXML
    private TableColumn<Film, String> colTitre;

    @FXML
    private TableColumn<Film, Date> colReleaseYear;

    @FXML
    private TableColumn<Film, Short> colLengh;

    @FXML
    private Button btnValider;
    
    AnchorPane page;

    @FXML
    void actionValider(ActionEvent event) {

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
