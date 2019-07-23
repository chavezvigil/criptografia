package edu.udb.cri.utils;

import java.net.URL;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public class Utils {


	public static String stringToDigest(String message, String digesto) {
		String strMDofDataToTransmit = new String();
		try {
			MessageDigest md = MessageDigest.getInstance(digesto);
			byte[] byteDataToTransmit = message.getBytes();

			// Crear un resumen del mensaje de los datos a transmitir
			md.update(byteDataToTransmit);
			byte byteMDofDataToTransmit[] = md.digest();

			for (int i = 0; i < byteMDofDataToTransmit.length; i++) {
				strMDofDataToTransmit = strMDofDataToTransmit
						+ Integer.toHexString((int) byteMDofDataToTransmit[i] & 0xFF);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strMDofDataToTransmit;
	}

	public static X509Certificate getX509Certificate(URL keyStoreUrl, String certName, String passKeyStore) {
		X509Certificate recvcert = null;
		try {
			// Especifique el almacen de claves que se haya importado el certificado para
			// receptores
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			char[] password = passKeyStore.toCharArray();
			java.io.FileInputStream fis = new java.io.FileInputStream(keyStoreUrl.getPath());
			ks.load(fis, password);
			fis.close();

			recvcert = (X509Certificate) ks.getCertificate(certName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recvcert;
	}

	public static PublicKey getPublicKey(X509Certificate cert) {
		PublicKey pubKeyReceiver = null;
		try {
			if (cert != null) {
				//Obtencion de la llave publica de los certificados
				pubKeyReceiver = cert.getPublicKey();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pubKeyReceiver;
	}

}
