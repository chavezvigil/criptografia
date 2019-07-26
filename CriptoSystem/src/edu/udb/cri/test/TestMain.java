package edu.udb.cri.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import edu.udb.cri.utils.PublicKeyCryptography;

public class TestMain {

	public static void main(String[] arg) {
		URL keyStoreUrl = TestMain.class.getResource("/resources/keystore/testkeystore.ks");
		String password = "test1234";
		printAllCerts(keyStoreUrl, password);
		algoritmosProvider();
		//testCypherMain();
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
