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

		// Symmetric button
		URL imgSimetric = getClass().getResource("/resources/simetric.png");
		Image imageSimetric = new Image(imgSimetric.toString());
		simetricoButton.setGraphic(new ImageView(imageSimetric));
		simetricoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Click en boton simetrico");
			}
		});

		// Asymmetric button
		URL imgAsimetric = getClass().getResource("/resources/simetric.png");
		Image imageAsimetric = new Image(imgAsimetric.toString());
		asimetricoButton.setGraphic(new ImageView(imageAsimetric));
		asimetricoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Click en boton simetrico");
			}
		});

		// Digital sing button
		URL imgDs = getClass().getResource("/resources/simetric.png");
		Image imageDs = new Image(imgDs.toString());
		digitalSingButton.setGraphic(new ImageView(imageDs));
		digitalSingButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Click en boton simetrico");
			}
		});
	}

	public void simetricCipher() {

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
