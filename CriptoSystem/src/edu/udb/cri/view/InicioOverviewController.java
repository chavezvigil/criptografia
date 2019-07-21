package edu.udb.cri.view;

import java.net.URL;

import javax.swing.JOptionPane;


import edu.udb.cri.MainApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InicioOverviewController {

	@FXML
	private Button simetricoButton;
	@FXML
	private Button asimetricoButton;
	@FXML
	private Button digitalSingButton;

	public InicioOverviewController() {

	}

	@FXML
	private void initialize() {
		// Initialize the start buttons

		URL imgUrl = getClass().getResource("/simetric_cipher.png");
		Image image = new Image(imgUrl.toString());
		simetricoButton.setGraphic(new ImageView(image));
		simetricoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Click en boton simetrico");
            }
        });

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {

		// Add observable list data to the table
		// personTable.setItems(mainApp.getPersonData());
	}

}
