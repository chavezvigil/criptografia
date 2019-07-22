package edu.udb.cri.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class DigitalSingController {

	private Stage dialogStage;
	private boolean okClicked = false;

	public DigitalSingController() {

	}

	@FXML
	private void initialize() {

	}


	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		okClicked = true;

	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

}
