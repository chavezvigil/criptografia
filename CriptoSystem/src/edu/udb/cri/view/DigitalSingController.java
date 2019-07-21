package edu.udb.cri.view;

import edu.udb.cri.MainApp;
import javafx.fxml.FXML;

public class DigitalSingController {

	private MainApp mainApp;
	
	public DigitalSingController() {
		
	}
	
	@FXML
	private void initialize() {
		
	
	}
	
	
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
