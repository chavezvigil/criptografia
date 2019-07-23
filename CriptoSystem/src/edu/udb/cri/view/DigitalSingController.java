package edu.udb.cri.view;

import java.io.File;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import edu.udb.cri.MainApp;
import edu.udb.cri.utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DigitalSingController {

	private Stage dialogStage;
	private boolean okClicked = false;

	@FXML
	private TextArea messageText;
	@FXML
	private Button firmarButton;
	@FXML
	private TextField digestText;
	
	@FXML
	private TextArea certText;
	
	@FXML
	private TextArea publicKeyText;
	
	@FXML
	private TextArea firmaText;

	private MainApp mainApp;

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public DigitalSingController() {

	}

	@FXML
	private void initialize() {
		firmarInitialize();
	}
	
	public void firmarInitialize() {
		URL imgSimetric = getClass().getResource("/resources/digital_sing.png");
		Image imageSimetric = new Image(imgSimetric.toString());
		firmarButton.setGraphic(new ImageView(imageSimetric));
		firmarButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				firmarMensaje();
			}
		});
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
	
	public void firmarMensaje() {
		try {
			String message = messageText.getText();
			String digesto = Utils.stringToDigest(message,"MD5");
			digestText.setText(digesto);
			
			//Extraer certificado de almacen
			X509Certificate cert = Utils.getX509Certificate(getClass().getResource("/resources/keystore/testkeystore.ks"), "recev", "test1234");
			certText.setText(String.valueOf(cert));
			
			//Extraer clave pública
			PublicKey publicKey = Utils.getPublicKey(cert);
			publicKeyText.setText(String.valueOf(publicKey));
			
			//Firmar mensaje
			String firma = Utils.singMessage(publicKey, digesto.getBytes());
			firmaText.setText(firma);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
