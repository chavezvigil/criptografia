package edu.udb.cri.utils;

import java.net.URL;
import java.security.*;
import java.security.cert.*;
import javax.crypto.*;
import org.apache.commons.codec.binary.Base64;

public class PublicKeyCryptography {
	/**
	 * @param args
	 */
	public void main() {

		SymmetricEncrypt encryptUtil = new SymmetricEncrypt();
		String strDataToEncrypt = "Hello World";
		byte[] byteDataToTransmit = strDataToEncrypt.getBytes();

		// Generación de un SecretKey para el cifrado simétrico
		SecretKey senderSecretKey = SymmetricEncrypt.getSecret();

		// 1. Cifrar los datos utilizando una clave simétrica
		byte[] byteCipherText = encryptUtil.encryptData(byteDataToTransmit, senderSecretKey, "AES");
		//String strCipherText = new Base64().encodeToString(byteCipherText);

		// 2. Cifrar la clave simétrica con la clave pública
		try {
			// 2.1 Especifique el almacén de claves que se haya importado el certificado
			// Receptores
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			char[] password = "test1234".toCharArray();
			
			URL keyStoreUrl = getClass().getResource("/resources/keystore/testkeystore.ks");
			
			java.io.FileInputStream fis = new java.io.FileInputStream(keyStoreUrl.toString());
			ks.load(fis, password);
			fis.close();

			// 2.2 Creación de un certificado X509 del receptor
			X509Certificate recvcert;
			MessageDigest md = MessageDigest.getInstance("MD5");
			recvcert = (X509Certificate) ks.getCertificate("recev");
			// 2.3 Obtención de la llave pública de los certificados
			PublicKey pubKeyReceiver = recvcert.getPublicKey();

			// 2.4 Cifrado de la SecretKey con la clave pública Receptores
			byte[] byteEncryptWithPublicKey = encryptUtil.encryptData(senderSecretKey.getEncoded(), pubKeyReceiver,
					"RSA/ECB/OAEPWithSHA1AndMGF1Padding");
			String strSenbyteEncryptWithPublicKey = new Base64().encodeToString(byteEncryptWithPublicKey);

			// 3. Crear un resumen del mensaje de los datos a transmitir
			md.update(byteDataToTransmit);
			byte byteMDofDataToTransmit[] = md.digest();

			String strMDofDataToTransmit = new String();
			for (int i = 0; i < byteMDofDataToTransmit.length; i++) {
				strMDofDataToTransmit = strMDofDataToTransmit
						+ Integer.toHexString((int) byteMDofDataToTransmit[i] & 0xFF);
			}

			// 3.1 Mensaje que se Firmado = clave secreta cifrada + MAC de los datos a
			// transmitir
			String strMsgToSign = strSenbyteEncryptWithPublicKey + "|" + strMDofDataToTransmit;

			// 4. Firma el mensaje
			// 4.1 Obtener la clave privada del remitente desde el almacén de claves,
			// proporcionando la contraseña establecida para la llave privada mientras se
			// crea las llaves usados.
			char[] keypassword = "test1234".toCharArray();
			Key myKey = ks.getKey("sender", keypassword);
			PrivateKey myPrivateKey = (PrivateKey) myKey;

			// 4.2 Firmar el mensaje
			Signature mySign = Signature.getInstance("MD5withRSA");
			mySign.initSign(myPrivateKey);
			mySign.update(strMsgToSign.getBytes());
			byte[] byteSignedData = mySign.sign();

			// 5. Los valores byteSignedData (la firma) y strMsgToSign (los datos que se
			// firmó) se pueden enviar a través del receptor

			// 6. Validar la firma
			// 6.1 Extraer la clave pública de su certificado de remitentes
			X509Certificate sendercert;
			sendercert = (X509Certificate) ks.getCertificate("sender");
			PublicKey pubKeySender = sendercert.getPublicKey();
			// 6.2 Verificar la Firma
			Signature myVerifySign = Signature.getInstance("MD5withRSA");
			myVerifySign.initVerify(pubKeySender);
			myVerifySign.update(strMsgToSign.getBytes());

			boolean verifySign = myVerifySign.verify(byteSignedData);
			if (verifySign == false) {
				System.out.println(" Error in validating Signature ");
			}

			else
				System.out.println(" Successfully validated Signature ");

			// 7. Descifrar el mensaje usando la llave privada Pets para obtener la clave
			// simétrica
			char[] recvpassword = "recv123".toCharArray();
			Key recvKey = ks.getKey("testrecv", recvpassword);
			PrivateKey recvPrivateKey = (PrivateKey) recvKey;

			// Analizar el MessageDigest y el valor cifrado
			//String strRecvSignedData = new String(byteSignedData);
			//String[] strRecvSignedDataArray = new String[10];
			//strRecvSignedDataArray = strMsgToSign.split("|");
			int intindexofsep = strMsgToSign.indexOf("|");
			//String strEncryptWithPublicKey = strMsgToSign.substring(0, intindexofsep);
			String strHashOfData = strMsgToSign.substring(intindexofsep + 1);

			// Descifrado para obtener la clave simétrica
			//byte[] bytestrEncryptWithPublicKey = new Base64().decode(strEncryptWithPublicKey);
			byte[] byteDecryptWithPrivateKey = encryptUtil.decryptData(byteEncryptWithPublicKey, recvPrivateKey,
					"RSA/ECB/PKCS1Padding");

			// 8. Descifrar los datos usando la clave simétrica
			javax.crypto.spec.SecretKeySpec secretKeySpecDecrypted = new javax.crypto.spec.SecretKeySpec(
					byteDecryptWithPrivateKey, "AES");
			byte[] byteDecryptText = encryptUtil.decryptData(byteCipherText, secretKeySpecDecrypted, "AES");
			String strDecryptedText = new String(byteDecryptText);
			System.out.println(" Decrypted data is " + strDecryptedText);

			// 9. Calcule MessageDigest de datos + mensaje firmado
			MessageDigest recvmd = MessageDigest.getInstance("MD5");
			recvmd.update(byteDecryptText);
			byte byteHashOfRecvSignedData[] = recvmd.digest();

			String strHashOfRecvSignedData = new String();

			for (int i = 0; i < byteHashOfRecvSignedData.length; i++) {
				strHashOfRecvSignedData = strHashOfRecvSignedData
						+ Integer.toHexString((int) byteHashOfRecvSignedData[i] & 0xFF);
			}
			// 10. Validar si la síntesis del mensaje del texto coincide con el mensaje
			// descifrado
			// Digest of the Original Message

			if (!strHashOfRecvSignedData.equals(strHashOfData)) {
				System.out.println(" Message has been tampered ");
			}

		}

		catch (Exception exp) {
			System.out.println(" Exception caught " + exp);
			exp.printStackTrace();
		}

	}
}
