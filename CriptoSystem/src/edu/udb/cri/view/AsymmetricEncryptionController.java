package edu.udb.cri.view;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;

import edu.udb.cri.utils.UseAsymmetricTool;
import edu.udb.cri.utils.UtilMessage;
import edu.udb.cri.utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AsymmetricEncryptionController {
	
	private URL keyStoreUrl;
	private String keyStorePass = UtilMessage.getMensaje("edu.udb.cri.keystore.pass");

	// Fields for crypt
	@FXML
	private Button cifrarButton;
	@FXML
	private Button restaurarButton;
	@FXML
	private TextArea messageText;
	@FXML
	private TextArea certText;
	@FXML
	private TextArea publicKey;
	@FXML
	private TextArea textoCifrado;
	@FXML
	private ComboBox<String> certList;


	// Fields for decrypt
	@FXML
	private Button decryptButton;
	@FXML
	private Button restaurarButtonDecrypt;
	@FXML
	private TextArea messageTextDecrypt;
	@FXML
	private TextArea certTextDecrypt;
	@FXML
	private TextArea originalMessage;
	@FXML
	private ComboBox<String> certListDecrypt;
	@FXML
	PasswordField passPhaseField;
	
	// Fields for analysis
	@FXML
	private Button analysisButton;
	@FXML
	private Button restaurarButtonAnalysis;
	@FXML
	private TextArea messageTextAnalysis;
	@FXML
	private TextArea textAnalysis;
	@FXML
	private TextArea textPrimo;

	public AsymmetricEncryptionController() {

	}

	@FXML
	private void initialize() {
		initializeGui();
		try {
			File keyStore = new File(UtilMessage.getMensaje("edu.udb.cri.keystore.path.resources.keystore"));
			keyStoreUrl = keyStore.toURI().toURL();
			certList.getItems().addAll(Utils.getAllNameCerts(keyStoreUrl, keyStorePass));
			certListDecrypt.getItems().addAll(Utils.getAllNameCerts(keyStoreUrl, keyStorePass));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void resetFields() {
		messageText.setText("");
		certList.setValue("");
		textoCifrado.setText("");
		certText.setText("");
		publicKey.setText("");
	}
	
	public void resetFieldsDecrypt() {
		messageTextDecrypt.setText("");
		certListDecrypt.setValue("");
		passPhaseField.setText("");
		originalMessage.setText("");
		certTextDecrypt.setText("");
	}
	
	public void resetFieldsAnalysis() {
		messageTextAnalysis.setText("");
		textAnalysis.setText("");
		textPrimo.setText("");
	}
	
	public void restablecerDatos() {
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.reset"), ButtonType.YES,
					ButtonType.CANCEL);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				resetFields();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}
	
	public void restablecerDatosAnalysis() {
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.reset"), ButtonType.YES,
					ButtonType.CANCEL);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				resetFieldsAnalysis();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}
	
	public void restablecerDatosDecrypt() {
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.reset"), ButtonType.YES,
					ButtonType.CANCEL);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				resetFieldsDecrypt();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}
	
	public void initializeGui() {

		URL imgCipher = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.asimetric"));
		Image imageCipher = new Image(imgCipher.toString());
		cifrarButton.setGraphic(new ImageView(imageCipher));
		cifrarButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				cifrarMensaje();
			}
		});


		URL imgReset = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.reset"));
		Image imageReset = new Image(imgReset.toString());
		restaurarButton.setGraphic(new ImageView(imageReset));
		restaurarButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				restablecerDatos();
			}
		});
		
		restaurarButtonDecrypt.setGraphic(new ImageView(imageReset));
		restaurarButtonDecrypt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				restablecerDatosDecrypt();
			}
		});
		
		restaurarButtonAnalysis.setGraphic(new ImageView(imageReset));
		restaurarButtonAnalysis.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				restablecerDatosAnalysis();
			}
		});
		
		
		URL imgDecryp = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.asimetric.decrypt"));
		Image imageDecrypt = new Image(imgDecryp.toString());
		decryptButton.setGraphic(new ImageView(imageDecrypt));
		decryptButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				descifrarMensaje();
			}
		});
		
		URL imgAnalysis = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.asimetric.analysis"));
		Image imageAnalysis = new Image(imgAnalysis.toString());
		analysisButton.setGraphic(new ImageView(imageAnalysis));
		analysisButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				analizarMensaje();
			}
		});
		
	}
	
	public void cifrarMensaje() {
		try {
			
			boolean valid = true;
			String nameCert = certList.getValue();
			String msg = messageText.getText();

			if (msg == null || msg.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.asymmetric.texto"));
				alert.showAndWait();
			} else if (nameCert == null || nameCert.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.asymmetric.cert"));
				alert.showAndWait();
			}

			if (valid == true) {
				Alert alert = new Alert(AlertType.CONFIRMATION,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.cifrar.cert"), ButtonType.YES,
						ButtonType.CANCEL);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.YES) {
					// Extraer certificado de almacen
					X509Certificate cert = Utils.getX509Certificate(keyStoreUrl, nameCert, keyStorePass);
					String strgCipherData = UseAsymmetricTool.cifrarAsimetrico(cert, msg);
					textoCifrado.setText(strgCipherData);
					certText.setText(String.valueOf(cert));
					publicKey.setText(String.valueOf(Utils.getPublicKey(cert)));
				}
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}
	
	public void descifrarMensaje() {
		try {
			
			boolean valid = true;
			String nameCert = certListDecrypt.getValue();
			String msg = messageTextDecrypt.getText();
			String passphase = passPhaseField.getText();

			if (msg == null || msg.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.decrypt.texto"));
				alert.showAndWait();
			} else if (nameCert == null || nameCert.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.decrypt.cert"));
				alert.showAndWait();
			} else if (passphase == null || passphase.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.decrypt.pass"));
				alert.showAndWait();
			}

			if (valid == true) {
				Alert alert = new Alert(AlertType.CONFIRMATION,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.descifrar.cert"), ButtonType.YES,
						ButtonType.CANCEL);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.YES) {
					//String message = messageTextDecrypt.getText();
					// Extraer certificado de almacen
					X509Certificate cert = Utils.getX509Certificate(keyStoreUrl, nameCert, keyStorePass);
					String strgCipherData = UseAsymmetricTool.descifrarAsimetrico(keyStoreUrl, keyStorePass, nameCert, passphase, msg);
					originalMessage.setText(strgCipherData);
					certTextDecrypt.setText(String.valueOf(cert));
					
				}
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}
	
	public void analizarMensaje() {
		try {
			
			boolean valid = true;
			String msg = messageTextAnalysis.getText();

			if (msg == null || msg.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.analysis.texto"));
				alert.showAndWait();
			}

			if (valid == true) {
				Alert alert = new Alert(AlertType.CONFIRMATION,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.analysis.text"), ButtonType.YES,
						ButtonType.CANCEL);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.YES) {
					String message = UseAsymmetricTool.getStringMsg(msg);
					textAnalysis.setText(message);
					
				}
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}

}
