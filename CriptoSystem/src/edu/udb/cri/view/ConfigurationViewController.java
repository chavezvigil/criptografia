package edu.udb.cri.view;

import java.io.File;
import java.net.URL;

import edu.udb.cri.MainApp;
import edu.udb.cri.utils.UtilMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ConfigurationViewController {

	private MainApp mainApp;
	@FXML
	private Button createKeystoreButton;
	
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
		// Create button
		createKeytore();

	}
	
	
	public void createKeytore() {
		URL imgKeystore = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.save.keystore"));
		Image imageKeystore = new Image(imgKeystore.toString());
		createKeystoreButton.setGraphic(new ImageView(imageKeystore));
	}
	
	  /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }

        }
    }
}
