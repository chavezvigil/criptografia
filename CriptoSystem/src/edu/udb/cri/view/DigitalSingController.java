package edu.udb.cri.view;

import java.io.File;
import java.net.URL;
import java.security.cert.X509Certificate;

import edu.udb.cri.MainApp;
import edu.udb.cri.utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DigitalSingController {

	private Stage dialogStage;
	private boolean okClicked = false;
	private URL keyStoreUrl = getClass().getResource("/resources/keystore/testkeystore.ks");
	private String keyStorePass = "test1234";
	private String digestAlgoritm = "SHA-512";

	@FXML
	private TextArea messageText;
	@FXML
	private Button firmarButton;
	@FXML
	private Button restaurarButton;
	@FXML
	private TextArea digestText;
	@FXML
	private TextArea certText;
	@FXML
	private TextArea messageToTransmitText;
	@FXML
	private TextArea firmaText;
	@FXML
	private ComboBox<String> certList;
	@FXML
	PasswordField passPhaseField;

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
		certList.getItems().addAll(Utils.getAllNameCerts(keyStoreUrl, keyStorePass));
	}

	public void firmarInitialize() {
		URL imgSimetric = getClass().getResource("/resources/digital_sing.png");
		URL imgReset = getClass().getResource("/resources/reset.png");
		Image imageSimetric = new Image(imgSimetric.toString());
		Image imageReset = new Image(imgReset.toString());

		firmarButton.setGraphic(new ImageView(imageSimetric));
		firmarButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				firmarMensaje();
			}
		});

		restaurarButton.setGraphic(new ImageView(imageReset));
		restaurarButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				restablecerDatos();
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
			boolean valid = true;
			String nameCert = certList.getValue();
			String passphase = passPhaseField.getText();
			if (nameCert == null || nameCert.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR, "Por favor, seleccione un certificado para firmar");
				alert.showAndWait();
			} else if (passphase == null || passphase.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR, "Por favor, ingrese el passphase de la clave privada");
				alert.showAndWait();
			}

			if (valid == true) {
				Alert alert = new Alert(AlertType.CONFIRMATION,
						"�Esta seguro de firmar con el certificado " + nameCert + " seleccionado?", ButtonType.YES,
						ButtonType.CANCEL);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.YES) {
					String message = messageText.getText();
					String digesto = Utils.dataToDigest(message.getBytes(), digestAlgoritm);
					// Extraer certificado de almacen
					X509Certificate cert = Utils.getX509Certificate(keyStoreUrl, nameCert, keyStorePass);
					// Firmar mensaje
					String firma = Utils.signMessageWithPrivateKey(keyStoreUrl, keyStorePass, nameCert, passphase,
							digesto.getBytes());
					// Mensaje a transmitir
					String messageToTransmit = Utils.messageToTransmit(message, firma);

					digestText.setText(digesto);
					certText.setText(String.valueOf(cert));
					firmaText.setText(firma);
					messageToTransmitText.setText(messageToTransmit);
				}
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}

	public void restablecerDatos() {
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION, "�Desea restablecer los campos?",ButtonType.YES,
					ButtonType.CANCEL);
			alert.showAndWait();
			
			if (alert.getResult() == ButtonType.YES) {
				messageText.setText("");
				certList.setValue("");
				passPhaseField.setText("");
				digestText.setText("");
				certText.setText("");
				firmaText.setText("");
				messageToTransmitText.setText("");
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}

	public void verificarFirma() {
		try {
			/*
			 * boolean valido = Utils.validateSign(keyStoreUrl, keyStorePass, nameCert,
			 * digesto.getBytes(), firma); if (valido == true) { Alert msg = new
			 * Alert(AlertType.INFORMATION, "Firma verificada!"); msg.showAndWait(); }
			 */
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}

}
