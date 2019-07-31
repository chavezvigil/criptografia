package edu.udb.cri.view;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import edu.udb.cri.utils.UtilMessage;
import edu.udb.cri.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;

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

	public AsymmetricEncryptionController() {

	}

	@FXML
	private void initialize() {
		try {
			File keyStore = new File(UtilMessage.getMensaje("edu.udb.cri.keystore.path.resources.keystore"));
			keyStoreUrl = keyStore.toURI().toURL();
			certList.getItems().addAll(Utils.getAllNameCerts(keyStoreUrl, keyStorePass));
			certListDecrypt.getItems().addAll(Utils.getAllNameCerts(keyStoreUrl, keyStorePass));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
}
