package edu.udb.cri.controller;

import java.net.URL;

import edu.udb.cri.MainApp;
import edu.udb.cri.utils.UtilMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RootLayoutController {

	@FXML
	private MenuItem configuracionItem;
	@FXML
	private MenuItem closeItem;
	@FXML
	private MenuItem aboutItem;

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

		URL imgClose = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.close"));
		Image imgCloseImg = new Image(imgClose.toString());
		closeItem.setGraphic(new ImageView(imgCloseImg));

		closeItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});

		URL imgAbout = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.help"));
		Image imgAboutImg = new Image(imgAbout.toString());
		aboutItem.setGraphic(new ImageView(imgAboutImg));
	}

	public void handleConfigurationView() {
		mainApp.showConfigurationOverview();
	}
	
	public void handleAboutView() {
		mainApp.showAboutOverview();
	}
}
