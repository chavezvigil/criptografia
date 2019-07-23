package edu.udb.cri.utils;

import java.security.MessageDigest;

public class Utils {

	public static String stringToDigest(String message, String digesto) {
		String strMDofDataToTransmit = new String();
		try {
			MessageDigest md = MessageDigest.getInstance(digesto);
			byte[] byteDataToTransmit = message.getBytes();

			// 3. Crear un resumen del mensaje de los datos a transmitir
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

}
