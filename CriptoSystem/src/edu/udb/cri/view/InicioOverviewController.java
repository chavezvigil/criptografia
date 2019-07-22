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
	
	private MainApp mainApp;
	
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	

	public InicioOverviewController() {

	}

	@FXML
	private void initialize() {
		// Symmetric button
		simetricCipher();
		// Asymmetric button
		asimetricCipher();
		// Digital sing button
		digitalSing();

	}

	public void simetricCipher() {
		URL imgSimetric = getClass().getResource("/resources/simetric.png");
		Image imageSimetric = new Image(imgSimetric.toString());
		simetricoButton.setGraphic(new ImageView(imageSimetric));
		simetricoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Click en boton simetrico");
			}
		});
	}

	public void asimetricCipher() {
		URL imgAsimetric = getClass().getResource("/resources/asimetric.png");
		Image imageAsimetric = new Image(imgAsimetric.toString());
		asimetricoButton.setGraphic(new ImageView(imageAsimetric));
		asimetricoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Click en boton asimetrico");
			}
		});
	}

	public void digitalSing() {
		URL imgDs = getClass().getResource("/resources/digital_sing.png");
		Image imageDs = new Image(imgDs.toString());
		digitalSingButton.setGraphic(new ImageView(imageDs));
	}
	
	
	public void handleDigitalSing() {
		mainApp.showDigitalSingOverview();
	}

	

}
