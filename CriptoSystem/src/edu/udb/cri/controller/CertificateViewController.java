package edu.udb.cri.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;

import edu.udb.cri.dto.CertInfoDto;
import edu.udb.cri.utils.UtilMessage;
import edu.udb.cri.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class CertificateViewController {

	private String keyStorePass = UtilMessage.getMensaje("edu.udb.cri.keystore.pass");
	private URL keyStoreUrl;

	@FXML
	private TextArea certText;
	private CertInfoDto certificado;


	@FXML
	private void initialize() {
		try {
			File keyStore = new File(UtilMessage.getMensaje("edu.udb.cri.keystore.path.resources.keystore"));
			keyStoreUrl = keyStore.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the certificate to be edited in the dialog.
	 * 
	 * @param person
	 */
	public void setCertificate(CertInfoDto certificate) {
		this.certificado = certificate;
		// Extraer certificado de almacen
		X509Certificate cert = Utils.getX509Certificate(keyStoreUrl, this.certificado.getAlias().getValue(),
				keyStorePass);
		certText.setText(String.valueOf(cert));
	}


}
