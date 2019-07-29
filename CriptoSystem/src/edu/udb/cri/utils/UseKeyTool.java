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

//@SuppressWarnings("restriction")
public class UseKeyTool {

	private static final int keysize = 4096;
	private static final String rsa = UtilMessage.getMensaje("edu.udb.cri.system.algoritm.asimetric.rsa");
	private static final String asimetricAlgoritm = UtilMessage.getMensaje("edu.udb.cri.system.algoritm.asimetric.sha");
	private static final String password = UtilMessage.getMensaje("edu.udb.cri.keystore.pass");
	private static final String pathKeyStore = UtilMessage.getMensaje("edu.udb.cri.keystore.path.src");

	// public static void main(String[] args) throws Exception {
	public void main() {
		/*
		 * // Lchavez String alias = "Luis Chavez"; String commonName = "Luis Ch�vez";
		 * String organizationalUnit = "IT"; String organization = "BANDESAL"; String
		 * city = "San Salvador"; String state = "San Salvador"; String country = "SV";
		 * String passnewentry = "lchavez1234";
		 * 
		 * // Evert String aliasE = "Evert Juarez"; String commonNameE = "Evert Ju�rez";
		 * String organizationalUnitE = "IT"; String organizationE = "CLARO"; String
		 * cityE = "San Salvador"; String stateE = "San Salvador"; String countryE =
		 * "SV"; String passnewentryE = "ejuarez1234";
		 * 
		 * // Nestor String aliasN = "Nestor Flores"; String commonNameN =
		 * "Nestor FLores"; String organizationalUnitN = "IT"; String organizationN =
		 * "Grant Thronton El Salvador"; String cityN = "San Salvador"; String stateN =
		 * "San Salvador"; String countryN = "SV"; String passnewentryN = "nflores1234";
		 * 
		 * createCertificate(alias, commonName, organizationalUnit, organization, city,
		 * state, country, passnewentry); createCertificate(aliasE, commonNameE,
		 * organizationalUnitE, organizationE, cityE, stateE, countryE, passnewentryE);
		 * createCertificate(aliasN, commonNameN, organizationalUnitN, organizationN,
		 * cityN, stateN, countryN, passnewentryN);
		 */
		// Lchavez
		String alias = "Mario Cruz";
		String commonName = "Mario Cruz";
		String organizationalUnit = "IT";
		String organization = "BANDESAL";
		String city = "San Salvador";
		String state = "San Salvador";
		String country = "SV";
		String passnewentry = "mcruz1234";
		createCertificate(alias, commonName, organizationalUnit, organization, city, state, country, passnewentry);
	}

	public static void createCertificate(String alias, String commonName, String organizationalUnit,
			String organization, String city, String state, String country, String passNewEntry) {

		try {
			CertAndKeyGen keyGen = new CertAndKeyGen(rsa, asimetricAlgoritm, null);
			keyGen.generate(keysize);
			PrivateKey privateKey = keyGen.getPrivateKey();

			X500Name x500Name = new X500Name(commonName, organizationalUnit, organization, city, state, country);
			X509Certificate cert = keyGen.getSelfCertificate(x500Name, (long) 365 * 24 * 60 * 60);

			cert = createSignedCertificate(cert, cert, privateKey);

			X509Certificate[] chain = new X509Certificate[1];
			chain[0] = cert;

			char[] pass = password.toCharArray();

			// Store the certificate chain
			storeKeyAndCertificateChain(alias, pass, pathKeyStore, privateKey, passNewEntry.toCharArray(), chain);
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

	private static void storeKeyAndCertificateChain(String alias, char[] password, String pathkeystore, Key key,
			char[] passnewentry, X509Certificate[] chain) throws Exception {

		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(null, null);

		// Store away the keystore
		File file = new File(pathkeystore);
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
	}

	public static void clearKeyStore(String alias, KeyStore keystore) throws Exception {
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
