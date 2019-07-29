package edu.udb.cri.view;

import java.net.URL;
import edu.udb.cri.MainApp;
import edu.udb.cri.utils.UtilMessage;
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
		URL imgSimetric = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.simetric"));
		Image imageSimetric = new Image(imgSimetric.toString());
		simetricoButton.setGraphic(new ImageView(imageSimetric));
	}

	public void asimetricCipher() {
		URL imgAsimetric = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.asimetric"));
		Image imageAsimetric = new Image(imgAsimetric.toString());
		asimetricoButton.setGraphic(new ImageView(imageAsimetric));
	}

	public void digitalSing() {
		URL imgDs = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.digital.sign"));
		Image imageDs = new Image(imgDs.toString());
		digitalSingButton.setGraphic(new ImageView(imageDs));
	}
	
	
	public void handleDigitalSing() {
		mainApp.showDigitalSingOverview();
	}
	
	public void handleSimetricCipher() {
		mainApp.showSymmetricCipherOverview();
	}

	

}
