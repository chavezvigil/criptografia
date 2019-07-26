package edu.udb.cri.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;

import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import sun.security.x509.BasicConstraintsExtension;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.CertificateExtensions;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

@SuppressWarnings("restriction")
public class UseKeyTool {

	private static final int keysize = 4096;
	private static final String rsa = UtilMessage.getMensaje("edu.udb.cri.system.algoritm.asimetric.rsa");
	private static final String asimetricalgoritm = UtilMessage.getMensaje("edu.udb.cri.system.algoritm.asimetric.sha");
	private static final String password = UtilMessage.getMensaje("edu.udb.cri.keystore.pass.test");
	private static final String keystore = UtilMessage.getMensaje("edu.udb.cri.keystore.path.test.path");

	public static void main(String[] args) throws Exception {
		String alias = "Luis Chavez";
		String commonName = "Luis Chavez";
		String organizationalUnit = "IT";
		String organization = "BANDESAL";
		String city = "San Salvador";
		String state = "San Salvador";
		String country = "SV";
		String passnewentry = "lchavez1234";
		
		createCertificate(alias, commonName, organizationalUnit, organization, city, state, country, passnewentry);
	}

	public static void createCertificate(String alias, String commonName, String organizationalUnit, String organization,
			String city, String state, String country, String passnewentry) {
		
		try {
			CertAndKeyGen keyGen = new CertAndKeyGen(rsa, asimetricalgoritm, null);
			keyGen.generate(keysize);
			PrivateKey privateKey = keyGen.getPrivateKey();
			System.out.println(privateKey);

			X500Name x500Name = new X500Name(commonName, organizationalUnit, organization, city, state, country);
			X509Certificate cert = keyGen.getSelfCertificate(x500Name, (long) 365 * 24 * 60 * 60);

			cert = createSignedCertificate(cert, cert, privateKey);

			X509Certificate[] chain = new X509Certificate[1];
			chain[0] = cert;

			char[] pass = password.toCharArray();

			// Store the certificate chain
			KeyStore ks = storeKeyAndCertificateChain(alias, pass, keystore, privateKey, passnewentry.toCharArray(),
					chain);
			// clearKeyStore(alias, ks);
			System.out.println("Metodo print");
			printAllCerts(ks);
		} catch (

		Exception ex) {
			ex.printStackTrace();
		}
	}

	private static X509Certificate createSignedCertificate(X509Certificate cetrificate,
			X509Certificate issuerCertificate, PrivateKey issuerPrivateKey) {
		X509CertImpl outCert = null;
		try {
			Principal issuer = issuerCertificate.getSubjectDN();
			String issuerSigAlg = issuerCertificate.getSigAlgName();

			byte[] inCertBytes = cetrificate.getTBSCertificate();
			X509CertInfo info = new X509CertInfo(inCertBytes);

			info.set(X509CertInfo.ISSUER, issuer);

			// No need to add the BasicContraint for leaf cert
			CertificateExtensions exts = new CertificateExtensions();
			BasicConstraintsExtension bce = new BasicConstraintsExtension(true, -1);
			exts.set(BasicConstraintsExtension.NAME, new BasicConstraintsExtension(false, bce.getExtensionValue()));
			info.set(X509CertInfo.EXTENSIONS, exts);

			outCert = new X509CertImpl(info);
			outCert.sign(issuerPrivateKey, issuerSigAlg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return outCert;
	}

	private static KeyStore storeKeyAndCertificateChain(String alias, char[] password, String keystore, Key key,
			char[] passnewentry, X509Certificate[] chain) throws Exception {

		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(null, null);

		// Store away the keystore
		File file = new File(keystore);
		if (file.exists()) {
			// if exists, load
			ks.load(new FileInputStream(file), password);
			ks.setKeyEntry(alias, key, passnewentry, chain);
			ks.store(new FileOutputStream(file), password);
		} else {
			// if not exists, create
			ks.load(null, null);
			ks.setKeyEntry(alias, key, passnewentry, chain);
			ks.store(new FileOutputStream(file), password);

		}
		return ks;
	}

	private static void clearKeyStore(String alias, KeyStore keystore) throws Exception {
		try {
			keystore.deleteEntry(alias);
		} catch (Exception exception) {
			throw exception;
		}
	}

	public static void printAllCerts(KeyStore keystore) {
		try {
			Enumeration<String> enumeration = keystore.aliases();
			while (enumeration.hasMoreElements()) {
				String alias = enumeration.nextElement();
				System.out.println("alias name: " + alias);
				X509Certificate certificate = (X509Certificate) keystore.getCertificate(alias);
				System.out.println(certificate.toString());

			}

		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}

}
