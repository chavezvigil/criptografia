package edu.udb.cri.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;

import edu.udb.cri.MainApp;
import edu.udb.cri.utils.UtilMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ConfigurationViewController {

	private MainApp mainApp;
	@FXML
	private Button createKeystoreButton;
	
	
	@FXML
	private Button createCertButton;
	@FXML
	private Button resetCertButton;
	
	
	@FXML
	private TextField aliasText;
	@FXML
	private TextField commonNameText;
	@FXML
	private TextField organizationText;
	@FXML
	private TextField organizationUnitText;
	@FXML
	private TextField countryText;
	@FXML
	private TextField cityText;
	@FXML
	private TextField stateText;

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
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
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

	public void handleSaveKeyStore() {
		try {
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("KeyStore files (*.txt)", "*.txt");
			fileChooser.getExtensionFilters().add(extFilter);
			// Show save file dialog
			File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
			if (file != null) {
				// Make sure it has the correct extension
				if (!file.getPath().endsWith(".txt")) {
					file = new File(file.getPath() + ".txt");
					//SaveKeyStore(file, "1234".toCharArray());
					SaveFile("Puesb archivoa", file);
				}

			}
			System.out.println("Archivo: " + file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void SaveKeyStore(File file, char[] password) {
		try {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(null, null);
			ks.store(new FileOutputStream(file), password);
		} catch (IOException | KeyStoreException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;
             
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
        	ex.getMessage();
        }
         
    }
}
