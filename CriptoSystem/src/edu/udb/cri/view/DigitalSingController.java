package edu.udb.cri.view;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;

import edu.udb.cri.utils.UtilMessage;
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
import javafx.stage.Stage;

public class DigitalSingController {

	private Stage dialogStage;
	private boolean okClicked = false;
	private URL keyStoreUrl;

	private String keyStorePass = UtilMessage.getMensaje("edu.udb.cri.keystore.pass");
	private String digestAlgoritm = UtilMessage.getMensaje("edu.udb.cri.system.algoritm.digest");

	// Fields for signing
	@FXML
	private Button firmarButton;
	@FXML
	private Button restaurarButton;
	@FXML
	private TextArea messageText;
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

	// Fields for verify
	@FXML
	private Button verifyButton;
	@FXML
	private Button restaurarButtonVerify;
	@FXML
	private TextArea messageTextVerify;
	@FXML
	private TextArea publicKey;
	@FXML
	private TextArea certTextVerify;
	@FXML
	private TextArea originalMessage;
	@FXML
	private TextArea firmaTextVerify;
	@FXML
	private ComboBox<String> certListVerify;

	public DigitalSingController() {

	}

	@FXML
	private void initialize() {
		firmarInitialize();
		try {
			File keyStore = new File(UtilMessage.getMensaje("edu.udb.cri.keystore.path.resources.keystore"));
			keyStoreUrl = keyStore.toURI().toURL();
			certList.getItems().addAll(Utils.getAllNameCerts(keyStoreUrl, keyStorePass));
			certListVerify.getItems().addAll(Utils.getAllNameCerts(keyStoreUrl, keyStorePass));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	public void firmarInitialize() {
		URL imgDigital = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.digital.sign"));
		URL imgReset = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.reset"));
		URL imgEscaneo = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.escaneo"));

		Image imageDigital = new Image(imgDigital.toString());
		Image imageReset = new Image(imgReset.toString());
		Image imageEscaneo = new Image(imgEscaneo.toString());

		firmarButton.setGraphic(new ImageView(imageDigital));
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

		verifyButton.setGraphic(new ImageView(imageEscaneo));
		verifyButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				verificarFirma();
			}
		});

		restaurarButtonVerify.setGraphic(new ImageView(imageReset));
		restaurarButtonVerify.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				restablecerDatosVerify();
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

	public void firmarMensaje() {
		try {
			boolean valid = true;
			String nameCert = certList.getValue();
			String passphase = passPhaseField.getText();
			String msg = messageText.getText();

			if (msg == null || msg.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.texto.firma"));
				alert.showAndWait();
			} else if (nameCert == null || nameCert.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.cert.firma"));
				alert.showAndWait();
			} else if (passphase == null || passphase.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.llave.privada"));
				alert.showAndWait();
			}

			if (valid == true) {
				Alert alert = new Alert(AlertType.CONFIRMATION,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.firmar.cert"), ButtonType.YES,
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
			Alert alert = new Alert(AlertType.CONFIRMATION,
					UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.reset"), ButtonType.YES,
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

	public void restablecerDatosVerify() {
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.reset"), ButtonType.YES,
					ButtonType.CANCEL);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				messageTextVerify.setText("");
				certListVerify.setValue("");
				publicKey.setText("");
				certTextVerify.setText("");
				firmaTextVerify.setText("");
				originalMessage.setText("");
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}

	public void verificarFirma() {
		try {
			boolean valid = true;
			String nameCert = certListVerify.getValue();
			String msg = messageTextVerify.getText();

			if (msg == null || msg.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.text.verificar"));
				alert.showAndWait();
			} else if (nameCert == null || nameCert.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.cert.verificar"));
				alert.showAndWait();
			}

			if (valid == true) {
				Alert alert = new Alert(AlertType.CONFIRMATION,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.verificar.cert"), ButtonType.YES,
						ButtonType.CANCEL);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.YES) {
					String message = messageTextVerify.getText();

					boolean base64 = Utils.isStringBase64(message);
					if (base64 == true) {
						String tramaOriginal = Utils.bytesToString(Utils.base64ToBytes(message));
						String originalText = Utils.getOriginalMessageFromTrama(tramaOriginal);
						String digesto = Utils.dataToDigest(originalText.getBytes(), digestAlgoritm);
						String firma = Utils.getDigitalSignFromTrama(tramaOriginal);
						boolean valido = Utils.validateSign(keyStoreUrl, keyStorePass, nameCert, digesto.getBytes(),
								firma);
						if (valido == true) {
							X509Certificate cert = Utils.getX509Certificate(keyStoreUrl, nameCert, keyStorePass);
							certTextVerify.setText(String.valueOf(cert));
							publicKey.setText(String.valueOf(Utils.getPublicKey(cert)));
							firmaTextVerify.setText(firma);
							originalMessage.setText(originalText);

							Alert msgVerify = new Alert(AlertType.INFORMATION,
									UtilMessage.getMensaje("edu.udb.cri.system.alert.information.firma"));
							msgVerify.showAndWait();

						} else {
							Alert msgVerify = new Alert(AlertType.ERROR,
									UtilMessage.getMensaje("edu.udb.cri.system.alert.error.firma.verificar"));
							msgVerify.showAndWait();
						}
					} else {
						Alert msgBase64 = new Alert(AlertType.ERROR,
								UtilMessage.getMensaje("edu.udb.cri.system.alert.error.format.base64"));
						msgBase64.showAndWait();
					}

				}
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}

}
