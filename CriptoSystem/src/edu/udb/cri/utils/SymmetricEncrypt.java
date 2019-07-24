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
import org.apache.commons.codec.binary.Base64;

public class SymmetricEncrypt {

	String strDataToEncrypt = new String();
	String strCipherText = new String();
	String strDecryptedText = new String();
	static KeyGenerator keyGen;
	private static String strHexVal = "0123456789abcdef";
	static String algoritmoAsimetrico = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";

	public static SecretKey getSecret() {
		try {
			keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
		} catch (Exception exp) {
			System.out.println(" Exception inside constructor " + exp);
		}

		SecretKey secretKey = keyGen.generateKey();
		return secretKey;
	}


	public byte[] encryptData(byte[] byteDataToEncrypt, Key secretKey, String Algorithm) {
		byte[] byteCipherText = new byte[200];

		try {
			Cipher aesCipher = Cipher.getInstance(Algorithm);

			if (Algorithm.equals("AES")) {
				aesCipher.init(Cipher.ENCRYPT_MODE, secretKey, aesCipher.getParameters());
			} else if (Algorithm.equals(algoritmoAsimetrico)) {
				aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
			}
			byteCipherText = aesCipher.doFinal(byteDataToEncrypt);
			strCipherText = new Base64().encodeToString(byteCipherText);

		} catch (NoSuchAlgorithmException noSuchAlgo) {
			System.out.println(" No Such Algorithm exists " + noSuchAlgo);
		} catch (NoSuchPaddingException noSuchPad) {
			System.out.println(" No Such Padding exists " + noSuchPad);
		} catch (InvalidKeyException invalidKey) {
			System.out.println(" Invalid Key " + invalidKey);
		} catch (BadPaddingException badPadding) {
			System.out.println(" Bad Padding " + badPadding);
		} catch (IllegalBlockSizeException illegalBlockSize) {
			System.out.println(" Illegal Block Size " + illegalBlockSize);
			illegalBlockSize.printStackTrace();
		} catch (Exception exp) {
			exp.printStackTrace();
		}

		return byteCipherText;
	}



	public byte[] decryptData(byte[] byteCipherText, Key secretKey, String Algorithm) {
		byte[] byteDecryptedText = new byte[200];

		try {
			Cipher aesCipher = Cipher.getInstance(Algorithm);
			if (Algorithm.equals("AES")) {
				aesCipher.init(Cipher.DECRYPT_MODE, secretKey, aesCipher.getParameters());
			} else if (Algorithm.equals(algoritmoAsimetrico)) {
				aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
			}

			byteDecryptedText = aesCipher.doFinal(byteCipherText);
			strDecryptedText = new String(byteDecryptedText);
		} catch (NoSuchAlgorithmException noSuchAlgo) {
			System.out.println(" No Such Algorithm exists " + noSuchAlgo);
		} catch (NoSuchPaddingException noSuchPad) {
			System.out.println(" No Such Padding exists " + noSuchPad);
		} catch (InvalidKeyException invalidKey) {
			System.out.println(" Invalid Key " + invalidKey);
			invalidKey.printStackTrace();
		} catch (BadPaddingException badPadding) {
			System.out.println(" Bad Padding " + badPadding);
			badPadding.printStackTrace();
		} catch (IllegalBlockSizeException illegalBlockSize) {
			System.out.println(" Illegal Block Size " + illegalBlockSize);
			illegalBlockSize.printStackTrace();
		} catch (InvalidAlgorithmParameterException invalidParam) {
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
