package edu.udb.cri.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.apache.commons.codec.binary.Base64;

public class Utils {

	static SymmetricEncrypt encryptUtil = new SymmetricEncrypt();

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
				// Obtencion de la llave publica de los certificados
				pubKeyReceiver = cert.getPublicKey();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pubKeyReceiver;
	}

	public static String singMessage(PublicKey pubKeyReceiver, byte[] byteDataToSing) {
		String strSenbyteEncryptWithPublicKey = new String();
		try {
			byte[] byteEncryptWithPublicKey = encryptUtil.encryptData(byteDataToSing, pubKeyReceiver,
					"RSA/ECB/PKCS1Padding");
			strSenbyteEncryptWithPublicKey = new Base64().encodeToString(byteEncryptWithPublicKey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strSenbyteEncryptWithPublicKey;
	}

	public static void printAllCerts(URL keyStoreUrl, String password) {
		try {

			File file = new File(keyStoreUrl.getPath());
			InputStream is = new FileInputStream(file);
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			
			keystore.load(is, password.toCharArray());

			Enumeration<String> enumeration = keystore.aliases();
			while (enumeration.hasMoreElements()) {
				String alias = enumeration.nextElement();
				System.out.println("alias name: " + alias);
				X509Certificate certificate = (X509Certificate) keystore.getCertificate(alias);
				System.out.println(certificate.toString());

			}

		} catch (java.security.cert.CertificateException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
