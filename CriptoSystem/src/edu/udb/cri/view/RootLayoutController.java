package edu.udb.cri.view;

import java.net.URL;

import edu.udb.cri.MainApp;
import edu.udb.cri.utils.UtilMessage;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RootLayoutController {

	@FXML
	private MenuItem configuracionItem;
	
	private MainApp mainApp;
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	
	@FXML
	private void initialize() {
		// Configuration button
		configurationView();


	}

	public void configurationView() {
		URL imgConf = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.configuration"));
		Image imgConfImg = new Image(imgConf.toString());
		configuracionItem.setGraphic(new ImageView(imgConfImg));
	}
	
	
	public void handleConfigurationView() {
		mainApp.showConfigurationOverview();
	}
}
