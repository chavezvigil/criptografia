package edu.udb.cri.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.security.auth.x500.X500Principal;
import sun.security.x509.X500Name;

import org.apache.commons.codec.binary.Base64;

import edu.udb.cri.dto.CertInfoDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("restriction")
public class Utils {

	static SymmetricEncrypt encryptUtil = new SymmetricEncrypt();
	static String algoritmoAsimetrico = UtilMessage.getMensaje("edu.udb.cri.system.algoritm.asimetric");
	static String algoritmoFirma = UtilMessage.getMensaje("edu.udb.cri.system.algoritm.asimetric.sha");
	static String keyStorePass = UtilMessage.getMensaje("edu.udb.cri.keystore.pass");

	public static String dataToDigest(byte[] byteDataToDigest, String digesto) {
		String strMDofDataToDigest = new String();
		try {
			MessageDigest md = MessageDigest.getInstance(digesto);
			// Crear un resumen del mensaje de los datos
			md.update(byteDataToDigest);
			byte byteMDofDataToDigest[] = md.digest();

			for (int i = 0; i < byteMDofDataToDigest.length; i++) {
				strMDofDataToDigest = strMDofDataToDigest + Integer.toHexString((int) byteMDofDataToDigest[i] & 0xFF);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strMDofDataToDigest;
	}

	public static X509Certificate getX509Certificate(URL keyStoreUrl, String certName, String passKeyStore) {
		X509Certificate recvcert = null;
		try {
			// Especifique el almacen de claves que se haya importado el certificado
			KeyStore ks = getKeyStore(keyStoreUrl, passKeyStore);
			recvcert = (X509Certificate) ks.getCertificate(certName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recvcert;
	}

	public static KeyStore getKeyStore(URL keyStoreUrl, String passKeyStore) {
		KeyStore ks = null;
		try {
			ks = KeyStore.getInstance(KeyStore.getDefaultType());
			char[] password = passKeyStore.toCharArray();
			URLConnection conn = (URLConnection) keyStoreUrl.openConnection();
			InputStream is = conn.getInputStream();
			ks.load(is, password);
			is.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ks;
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

	public static String cypherMessageWithPublicKey(PublicKey pubKeyReceiver, byte[] byteDataToSing) {
		String strSenbyteEncryptWithPublicKey = new String();
		try {
			byte[] byteEncryptWithPublicKey = encryptUtil.encryptData(byteDataToSing, pubKeyReceiver,
					algoritmoAsimetrico);
			strSenbyteEncryptWithPublicKey = new Base64().encodeToString(byteEncryptWithPublicKey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strSenbyteEncryptWithPublicKey;
	}

	public static String signMessageWithPrivateKey(URL keyStoreUrl, String passKeyStore, String certName,
			String passphase, byte[] byteDataToSing) throws Exception {

		String strgSignedData = null;
		try {
			KeyStore ks = getKeyStore(keyStoreUrl, passKeyStore);
			char[] keypassword = passphase.toCharArray();
			Key myKey = ks.getKey(certName, keypassword);
			PrivateKey myPrivateKey = (PrivateKey) myKey;

			// Firmar el mensaje
			Signature mySign = Signature.getInstance(algoritmoFirma);
			mySign.initSign(myPrivateKey);
			mySign.update(byteDataToSing);
			byte[] byteSignedData = mySign.sign();

			strgSignedData = bytesToBase64(byteSignedData);
		} catch (Exception exception) {
			throw exception;
		}

		return strgSignedData;
	}

	public static String messageToTransmitBase64(byte[] message, String digitalSing) throws Exception {
		String strgDataToTransmit = new String();
		try {
			String messageBase64 = bytesToBase64(message);
			strgDataToTransmit = messageBase64 + "|" + digitalSing;
		} catch (Exception exception) {
			throw exception;
		}

		return strgDataToTransmit;
	}

	public static String messageToTransmit(String message, String digitalSing) throws Exception {
		String strgDataToTransmit = new String();
		try {
			strgDataToTransmit = message + "_" + digitalSing;
			strgDataToTransmit = bytesToBase64(strgDataToTransmit.getBytes());
		} catch (Exception exception) {
			throw exception;
		}

		return strgDataToTransmit;
	}

	public static boolean validateSign(URL keyStoreUrl, String passKeyStore, String certName, byte[] digestMessage,
			String stringDataToVerify) throws Exception {
		boolean verifySign = false;
		try {
			X509Certificate cert = getX509Certificate(keyStoreUrl, certName, passKeyStore);
			PublicKey pubKey = cert.getPublicKey();
			// Verificar la Firma
			Signature myVerifySign = Signature.getInstance(algoritmoFirma);
			myVerifySign.initVerify(pubKey);
			myVerifySign.update(digestMessage);

			byte[] byteDataToVerify = base64ToBytes(stringDataToVerify);
			verifySign = myVerifySign.verify(byteDataToVerify);
		} catch (Exception exception) {
			throw exception;
		}
		return verifySign;

	}

	public static String bytesToBase64(byte[] byteDataToTransform) throws Exception {
		String strSenbyteEncryptWithPrivateKey = new String();
		try {
			strSenbyteEncryptWithPrivateKey = new Base64().encodeToString(byteDataToTransform);
		} catch (Exception exception) {
			throw exception;
		}
		return strSenbyteEncryptWithPrivateKey;
	}

	public static byte[] base64ToBytes(String stringDataToTransform) throws Exception {
		byte[] bytesRecoverer = null;
		try {
			bytesRecoverer = new Base64().decode(stringDataToTransform);
		} catch (Exception exception) {
			throw exception;
		}
		return bytesRecoverer;
	}

	public static String bytesToString(byte[] bytesConvert) throws Exception {
		String strResult = new String();
		try {
			strResult = new String(bytesConvert);
		} catch (Exception exception) {
			throw exception;
		}

		return strResult;
	}

	public static ObservableList<String> getAllNameCerts(URL keyStoreUrl, String password) {
		ObservableList<String> items = FXCollections.observableArrayList();
		try {

			URLConnection conn = (URLConnection) keyStoreUrl.openConnection();
			InputStream is = conn.getInputStream();
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			keystore.load(is, password.toCharArray());

			Enumeration<String> enumeration = keystore.aliases();
			while (enumeration.hasMoreElements()) {
				String alias = enumeration.nextElement();
				if (alias != null && !alias.isEmpty()) {
					items.add(alias);
				}
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

		return items;
	}

	public static ObservableList<CertInfoDto> getAllCerts(URL keyStoreUrl, String password) {
		ObservableList<CertInfoDto> items = FXCollections.observableArrayList();
		CertInfoDto dto;
		try {
			URLConnection conn = (URLConnection) keyStoreUrl.openConnection();
			InputStream is = conn.getInputStream();
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			keystore.load(is, password.toCharArray());

			Enumeration<String> enumeration = keystore.aliases();
			int count = 0;
			while (enumeration.hasMoreElements()) {
				count = count + 1;
				dto = new CertInfoDto();
				String alias = enumeration.nextElement();
				if (alias != null && !alias.isEmpty()) {
					dto.setNumber(count);
					dto.setAlias(alias);

					try {
						X509Certificate cert = Utils.getX509Certificate(keyStoreUrl, alias, keyStorePass);
						X500Principal principal = cert.getSubjectX500Principal();
						X500Name x500name = new X500Name(principal.getName());

						if (x500name != null) {
							dto.setCommonName(x500name.getCommonName());
							dto.setOrganization(x500name.getOrganization());
							dto.setOrganizationUnit(x500name.getOrganizationalUnit());
							dto.setCity(x500name.getLocality());
							dto.setState(x500name.getState());
							dto.setCountry(x500name.getCountry());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					items.add(dto);
				}
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

		return items;
	}

	public static String getOriginalMessageFromTrama(String tramaOriginal) throws Exception {
		String originalMessage = new String();
		try {
			String[] arreglo = tramaOriginal.split("_");
			if (arreglo != null && arreglo.length > 0) {
				originalMessage = arreglo[0];
			}
		} catch (Exception exception) {
			throw exception;
		}
		return originalMessage;
	}

	public static String getDigitalSignFromTrama(String tramaOriginal) throws Exception {
		String originalMessage = new String();
		try {
			String[] arreglo = tramaOriginal.split("_");
			if (arreglo != null && arreglo.length > 1) {
				originalMessage = arreglo[1];
			}
		} catch (Exception exception) {
			throw exception;
		}
		return originalMessage;
	}

	public static boolean isStringBase64(String mensajeBase64) throws Exception {
		boolean valid = true;
		try {
			valid = Base64.isBase64(mensajeBase64.getBytes());
		} catch (Exception exception) {
			throw exception;
		}
		return valid;
	}

	public static ObservableList<String> getAllAsymmetricAlgoritm() {
		ObservableList<String> items = FXCollections.observableArrayList();
		try {

			items.add(UtilMessage.getMensaje("edu.udb.cri.system.algoritm.asimetric.rsa"));
			items.add(UtilMessage.getMensaje("edu.udb.cri.system.algoritm.asimetric.dsa"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return items;
	}

}
