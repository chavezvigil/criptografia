package edu.udb.cri.test;

import java.net.URL;

import edu.udb.cri.utils.Utils;

public class TestMain {

	public static void main(String[] arg) {
		URL keyStoreUrl = TestMain.class.getResource("/resources/keystore/testkeystore.ks");
		String password = "test1234";
		Utils.printAllCerts(keyStoreUrl, password);
	}
}
