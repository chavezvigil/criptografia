package edu.udb.cri.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import java.security.Key;

import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.InvalidAlgorithmParameterException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import sun.misc.BASE64Encoder;

/**
 * @author Luis Chávez Este programa ofrece las funcionalidades criptográficas
 *         siguientes 1. Cifrado con AES 2. El descifrado con AES
 *
 *         Algoritmo de alto nivel: 1. Generar una clave DES (especificar el
 *         tamaño de la clave durante esta fase) 2. Cree el Cipher 3. Para
 *         cifrar: Inicializar el cifrado para el cifrado 4. Para descifrar:
 *         Inicializar el cifrado para descifrar
 */
public class SymmetricEncrypt {
	String strDataToEncrypt = new String();
	String strCipherText = new String();
	String strDecryptedText = new String();
	static KeyGenerator keyGen;
	private static String strHexVal = "0123456789abcdef";

	public static SecretKey getSecret() {
		/**
		 * Paso 1. Generación de una clave AES mediante keygenerator Inicializa el
		 * tamaño de clave de 128
		 * 
		 */

		try {
			keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);

		}

		catch (Exception exp) {
			System.out.println(" Exception inside constructor " + exp);
		}

		SecretKey secretKey = keyGen.generateKey();
		return secretKey;
	}

	/**
	 * Paso 2. Crear un sistema de cifrado mediante la especificación de los
	 * siguientes parámetros a. Nombre del algoritmo - aquí es AES
	 */

	public byte[] encryptData(byte[] byteDataToEncrypt, Key secretKey, String Algorithm) {
		byte[] byteCipherText = new byte[200];

		try {
			Cipher aesCipher = Cipher.getInstance(Algorithm);

			/**
			 * Paso 3. Inicialice el cifrado para el cifrado
			 */
			if (Algorithm.equals("AES")) {
				aesCipher.init(Cipher.ENCRYPT_MODE, secretKey, aesCipher.getParameters());
			} else if (Algorithm.equals("RSA/ECB/PKCS1Padding")) {
				aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
			}

			/**
			 * Paso 4. Cifrar los datos 1. Declarar / inicializar los datos. Aquí los datos
			 * son de tipo String 2. Convertir el texto de entrada a Bytes 3. Cifrado de los
			 * bytes, utilizando el método doFinal
			 */
			byteCipherText = aesCipher.doFinal(byteDataToEncrypt);
			strCipherText = new BASE64Encoder().encode(byteCipherText);

		}

		catch (NoSuchAlgorithmException noSuchAlgo) {
			System.out.println(" No Such Algorithm exists " + noSuchAlgo);
		}

		catch (NoSuchPaddingException noSuchPad) {
			System.out.println(" No Such Padding exists " + noSuchPad);
		}

		catch (InvalidKeyException invalidKey) {
			System.out.println(" Invalid Key " + invalidKey);
		}

		catch (BadPaddingException badPadding) {
			System.out.println(" Bad Padding " + badPadding);
		}

		catch (IllegalBlockSizeException illegalBlockSize) {
			System.out.println(" Illegal Block Size " + illegalBlockSize);
			illegalBlockSize.printStackTrace();
		} catch (Exception exp) {
			exp.printStackTrace();
		}

		return byteCipherText;
	}

	/**
	 * Paso 5. Descifrar los datos 1. Inicialice el cifrado para descifrar 2.
	 * Descifrar los bytes cifrados utilizando el método doFinal
	 */

	public byte[] decryptData(byte[] byteCipherText, Key secretKey, String Algorithm) {
		byte[] byteDecryptedText = new byte[200];

		try {
			Cipher aesCipher = Cipher.getInstance(Algorithm);
			if (Algorithm.equals("AES")) {
				aesCipher.init(Cipher.DECRYPT_MODE, secretKey, aesCipher.getParameters());
			} else if (Algorithm.equals("RSA/ECB/PKCS1Padding")) {
				aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
			}

			byteDecryptedText = aesCipher.doFinal(byteCipherText);
			strDecryptedText = new String(byteDecryptedText);
		}

		catch (NoSuchAlgorithmException noSuchAlgo) {
			System.out.println(" No Such Algorithm exists " + noSuchAlgo);
		}

		catch (NoSuchPaddingException noSuchPad) {
			System.out.println(" No Such Padding exists " + noSuchPad);
		}

		catch (InvalidKeyException invalidKey) {
			System.out.println(" Invalid Key " + invalidKey);
			invalidKey.printStackTrace();
		}

		catch (BadPaddingException badPadding) {
			System.out.println(" Bad Padding " + badPadding);
			badPadding.printStackTrace();
		}

		catch (IllegalBlockSizeException illegalBlockSize) {
			System.out.println(" Illegal Block Size " + illegalBlockSize);
			illegalBlockSize.printStackTrace();
		}

		catch (InvalidAlgorithmParameterException invalidParam) {
			System.out.println(" Invalid Parameter " + invalidParam);
		}

		return byteDecryptedText;
	}

	public static byte[] convertStringToByteArray(String strInput) {
		strInput = strInput.toLowerCase();
		byte[] byteConverted = new byte[(strInput.length() + 1) / 2];
		int j = 0;
		int interimVal;
		int nibble = -1;

		for (int i = 0; i < strInput.length(); ++i) {
			interimVal = strHexVal.indexOf(strInput.charAt(i));
			if (interimVal >= 0) {
				if (nibble < 0) {
					nibble = interimVal;
				} else {
					byteConverted[j++] = (byte) ((nibble << 4) + interimVal);
					nibble = -1;
				}
			}
		}

		if (nibble >= 0) {
			byteConverted[j++] = (byte) (nibble << 4);
		}

		if (j < byteConverted.length) {
			byte[] byteTemp = new byte[j];
			System.arraycopy(byteConverted, 0, byteTemp, 0, j);
			byteConverted = byteTemp;
		}

		return byteConverted;
	}

	public static String convertByteArrayToString(byte[] block) {
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < block.length; ++i) {
			buf.append(strHexVal.charAt((block[i] >>> 4) & 0xf));
			buf.append(strHexVal.charAt(block[i] & 0xf));
		}

		return buf.toString();
	}
}
