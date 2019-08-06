package edu.udb.cri.utils;

import java.net.URL;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UseAsymmetricTool {

	private static final Logger LOGGER = Logger.getLogger(UseAsymmetricTool.class.getName());
	static String asimetricRSA = UtilMessage.getMensaje("edu.udb.cri.system.algoritm.asimetric");
	static SymmetricEncrypt cifrado = new SymmetricEncrypt();

	public static String cifrarAsimetrico(X509Certificate cert, String msg) throws Exception {
		String strgCipherData = new String();
		try {
			if (msg != null && !msg.isEmpty()) {
				byte[] msgToRSA = cifrado.encryptData(msg.getBytes(), cert.getPublicKey(), asimetricRSA);
				strgCipherData = Utils.bytesToBase64(msgToRSA);
			}

		} catch (Exception exception) {
			LOGGER.log(Level.SEVERE, "Exception occur", exception);
			throw exception;
		}

		return strgCipherData;
	}

	public static String descifrarAsimetrico(URL keyStoreUrl, String passKeyStore, String certName, String passphase,
			String msg) throws Exception {
		String strgCipherData = new String();
		try {
			KeyStore ks = Utils.getKeyStore(keyStoreUrl, passKeyStore);
			char[] keypassword = passphase.toCharArray();
			Key myKey = ks.getKey(certName, keypassword);
			PrivateKey myPrivateKey = (PrivateKey) myKey;

			if (msg != null && !msg.isEmpty()) {
				byte[] base64ToBytes = Utils.base64ToBytes(msg);
				byte[] byteCipherText = cifrado.decryptData(base64ToBytes, myPrivateKey, asimetricRSA);
				strgCipherData = new String(byteCipherText);
			}
		} catch (Exception exception) {
			LOGGER.log(Level.SEVERE, "Exception occur", exception);
			throw exception;
		}
		return strgCipherData;
	}
}
