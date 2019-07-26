package edu.udb.cri.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;


public class UseKeyTool {

	/*
	 * private static final int keysize = 1024; private static final String
	 * commonName = "www.test.de"; private static final String organizationalUnit =
	 * "IT"; private static final String organization = "test"; private static final
	 * String city = "test"; private static final String state = "test"; private
	 * static final String country = "DE"; private static final long validity =
	 * 1096; // 3 years private static final String alias = "tomcat"; private static
	 * final char[] keyPass = "changeit".toCharArray();
	 */

	public static void main(String[] args) throws Exception {

		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		String pass = UtilMessage.getMensaje("edu.udb.cri.keystore.pass");
		String keyStorePath = UtilMessage.getMensaje("edu.udb.cri.keystore.path");

		char[] password = pass.toCharArray();
		ks.load(null, password);

		// Store away the keystore		
		File file = new File(keyStorePath);
		if (file.exists()) {
			// if exists, load
			ks.load(new FileInputStream(file), password);
		} else {
			// if not exists, create
			ks.load(null, null);
			ks.store(new FileOutputStream(file), password);
		}
		
		

		/*
		 * java.security.KeyPairGenerator generator =
		 * KeyPairGenerator.getInstance("RSA"); generator.initialize(4096,
		 * SecureRandom.getInstance("SHA1WithRSA")); java.security.KeyPair keyPair =
		 * generator.generateKeyPair(); java.security.PrivateKey privatekey =
		 * keyPair.getPrivate();
		 * 
		 * javax.security.auth.x500.X500Principal principal = new
		 * X500Principal("Prueba de certificado");
		 * 
		 * java.security.cert.CertificateFactory certFactory =
		 * CertificateFactory.getInstance("X.509"); // Proposed new API:
		 * java.security.cert.X509Certificate selfSignedCert =
		 * certFactory.getSelfSignedCertificate(principal, 365, 8); // this allows to
		 * generate a self-signed X.509 v3 certificate, which is valid for 365 days.
		 * 
		 * 
		 * 
		 * KeyStore keyStore = KeyStore.getInstance("JKS"); keyStore.load(null, null);
		 * 
		 * CertAndKeyGen keypaiar = new CertAndKeyGen("RSA", "SHA1WithRSA", null);
		 * 
		 * X500Name x500Name = new X500Name(commonName, organizationalUnit,
		 * organization, city, state, country); javax.security.auth.x500.X500Principal
		 * principal = new X500Principal("DN");
		 * 
		 * keypair.generate(keysize); PrivateKey privKey = keypair.getPrivateKey();
		 * 
		 * X509Certificate[] chain = new X509Certificate[1];
		 * 
		 * chain[0] = keypair.getSelfCertificate(principal, new Date(), (long) validity
		 * * 24 * 60 * 60);
		 * 
		 * keyStore.setKeyEntry(alias, privKey, keyPass, chain);
		 * 
		 * keyStore.store(new FileOutputStream(".keystore"), keyPass);
		 * 
		 * 
		 * 
		 * RSAPrivateKeySpec serPrivateSpec = new RSAPrivateKeySpec(new BigInteger(8),
		 * new BigInteger(10)); fact = KeyFactory.getInstance("RSA"); PrivateKey
		 * serverPrivateKey = fact.generatePrivate(serPrivateSpec);
		 * 
		 * RSAPublicKeySpec serPublicSpec = new RSAPublicKeySpec( new
		 * BigInteger(agentCL.getSerPubMod()), new BigInteger(agentCL.getSerPubExp()));
		 * PublicKey serverPublicKey = fact.generatePublic(serPublicSpec);
		 * 
		 * keyStore = KeyStore.getInstance(IMXAgentCL.STORE_TYPE); keyStore.load(null,
		 * SOMEPWD.toCharArray());
		 * 
		 * Security.addProvider(new
		 * org.bouncycastle.jce.provider.BouncyCastleProvider());
		 * 
		 * X509Certificate[] serverChain = new X509Certificate[1];
		 * X509V3CertificateGenerator serverCertGen = new X509V3CertificateGenerator();
		 * X500Principal serverSubjectName = new X500Principal("CN=OrganizationName");
		 * serverCertGen.setSerialNumber(new BigInteger("123456789")); //
		 * X509Certificate caCert=null; serverCertGen.setIssuerDN(somename);
		 * serverCertGen.setNotBefore(new Date()); serverCertGen.setNotAfter(new
		 * Date()); serverCertGen.setSubjectDN(somename);
		 * serverCertGen.setPublicKey(serverPublicKey);
		 * serverCertGen.setSignatureAlgorithm("MD5WithRSA"); //
		 * certGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false,new //
		 * AuthorityKeyIdentifierStructure(caCert));
		 * serverCertGen.addExtension(X509Extensions.SubjectKeyIdentifier, false, new
		 * SubjectKeyIdentifierStructure(serverPublicKey)); serverChain[0] =
		 * serverCertGen.generateX509Certificate(serverPrivateKey, "BC"); // note:
		 * private key of CA
		 * 
		 * keyStore.setEntry("xyz", new KeyStore.PrivateKeyEntry(serverPrivateKey,
		 * serverChain), new KeyStore.PasswordProtection("".toCharArray()));
		 */

	}

}
