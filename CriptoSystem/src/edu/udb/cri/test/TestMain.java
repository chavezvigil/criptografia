package edu.udb.cri.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
//import java.security.cert.X509Certificate;
import java.util.Enumeration;
import edu.udb.cri.utils.PublicKeyCryptography;
import edu.udb.cri.utils.UtilMessage;

public class TestMain {

	//public static void main(String[] arg) {
	public static void main(String[] arg) {
		File keyStore = new File(UtilMessage.getMensaje("edu.udb.cri.keystore.path.resources.keystore"));
		URL keyStoreUrl;
		try {
			keyStoreUrl = keyStore.toURI().toURL();
			String password = UtilMessage.getMensaje("edu.udb.cri.keystore.pass");
			printAllCerts(keyStoreUrl, password);
			//algoritmosProvider();
			//testCypherMain();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void printAllCerts(URL keyStoreUrl, String password) {
		try {

			URLConnection conn = (URLConnection) keyStoreUrl.openConnection();
			InputStream is = conn.getInputStream();
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());

			keystore.load(is, password.toCharArray());

			Enumeration<String> enumeration = keystore.aliases();
			while (enumeration.hasMoreElements()) {
				String alias = enumeration.nextElement();
				System.out.println("alias name: " + alias);

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

	public static void algoritmosProvider() {

		try {
			Provider p[] = Security.getProviders();
			for (int i = 0; i < p.length; i++) {
				System.out.println(p[i]);
				for (Enumeration<Object> e = p[i].keys(); e.hasMoreElements();)
					System.out.println("\t" + e.nextElement());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testCypherMain() {
		PublicKeyCryptography.cipherMain();
	}
	
	
	
	
	
}
