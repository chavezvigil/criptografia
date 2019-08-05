package edu.udb.cri.test;

import java.security.Provider;
import java.security.Security;
import java.util.Enumeration;
import edu.udb.cri.utils.UseKeyTool;


public class TestMain {

	//public static void main(String[] arg) {
	public static void main(String[] arg) {
		
		try {
			algoritmosProvider();
			UseKeyTool.printAllCerts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	
}
