package edu.udb.cri.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Symmetric {
	
    //Código interno que representa el algoritmo AES.
    public final static int ALGORITMO_AES = 20;
    public final static int ALGORITMO_DES = 22;
    //Código interno que representa el modo CBC.
    public final static int MODO_CBC = 30;
    public final static int MODO_CFB = 31;
    public final static int MODO_ECB = 32;
    public final static int MODO_OFB = 33;
    //Código interno que representa el padding PKC5
    public final static int PADDING_PKCS = 40;
    public final static int PADDING_ISO = 41;
    //Código interno que representa el modo cifrador
    public final static int CIFRADOR = 100;
    public final static int DESCIFRADOR = 101;

    private int algoritmo;
    private int modo;
    private int padding;
    private KeyGenerator generadorClave_aes;
    private KeyGenerator generadoClave_des;
    private int IntTamBit;

    //Constructor de clase.
    public Symmetric(String inputStrTamBit, String inputStrModOpe, int inputStrAlg,String inputStrDefVec) {
    	IntTamBit = Integer.parseInt(inputStrTamBit);    	
    	
        try {            
            // Generar claves segun algoritmo y tipo
            if(inputStrAlg == Symmetric.ALGORITMO_AES) {
            	this.algoritmo = Symmetric.ALGORITMO_AES;
            	
            	//Validar modo de cifrado
            	if (inputStrModOpe.contentEquals("ECB")) {
            		this.modo = Symmetric.MODO_ECB;
            	}else if(inputStrModOpe.contentEquals("CBC")) {
            		this.modo = Symmetric.MODO_CBC;
            	}else if(inputStrModOpe.contentEquals("OFB")){
            		this.modo = Symmetric.MODO_OFB;
            	}else {
            		this.modo = Symmetric.MODO_CFB;
            	}
            	this.padding = Symmetric.PADDING_PKCS;
            	generadorClave_aes = KeyGenerator.getInstance("AES");
            	generadorClave_aes.init(IntTamBit);
            }else {
            	this.algoritmo = Symmetric.ALGORITMO_DES;
            	
            	//Validar modo de cifrado
            	if (inputStrModOpe.contentEquals("ECB")) {
            		this.modo = Symmetric.MODO_ECB;
            	}else if(inputStrModOpe.contentEquals("CBC")) {
            		this.modo = Symmetric.MODO_CBC;
            	}else if(inputStrModOpe.contentEquals("OFB")){
            		this.modo = Symmetric.MODO_OFB;
            	}else {
            		this.modo = Symmetric.MODO_CFB;
            	}
            	this.padding = Symmetric.PADDING_ISO;
                generadoClave_des = KeyGenerator.getInstance("DES");
                generadoClave_des.init(IntTamBit);
            }
            
        } catch (NoSuchAlgorithmException nsae) {
            System.err.println("Error: Alguno de los algoritmos de encriptación (AES o DES) no"
                    + " están disponible en el sistema. " + nsae.getMessage());
        }
    }

    // Método que devuelve el código interno del algoritmo instanciado
    public int getAlgoritmo() {
        return this.algoritmo;
    }

    //Método que devuelve el código interno del modo actual del encriptador.
    public int getModo() {
        return this.modo;
    }

    //Método que devuelve el código interno del padding actual del encriptador.
    public int getPadding() {
        return this.padding;
    }

    //Método para cambiar el algoritmo actual del encriptador
    public void setAlgoritmo(int algoritmo) {
        this.algoritmo = algoritmo;
    }

    //Método para cambiar el modo actual del encriptador
    public void setModo(int modo) {
        this.modo = modo;
    }

    //Método para cambiar el padding actual del encriptador
    public void setPadding(int padding) {
        this.padding = padding;
    }

    //Método que devuelve una cadena de texto con el algoritmo seleccionado
    public String cadenaAlgoritmo() {
        String valor = null;

        switch (this.getAlgoritmo()) {
            case Symmetric.ALGORITMO_AES:
                valor = "AES";
                break;
            case Symmetric.ALGORITMO_DES:
                valor = "DES";
                break;
        }

        return valor;
    }

    //Método que devuelve una cadena de texto con el modo seleccionado para el cifrado
    public String cadenaModo() {
        String valor = null;

        switch (this.modo) {
            case Symmetric.MODO_CBC:
                valor = "CBC";
                break;
            case Symmetric.MODO_CFB:
                valor = "CFB";
                break;
            case Symmetric.MODO_ECB:
                valor = "ECB";
                break;
            case Symmetric.MODO_OFB:
                valor = "OFB";
                break;
        }

        return valor;
    }

    //Método que devuelve una cadena de texto con el padding seleccionado
    public String cadenaPadding() {
        String valor = null;

        switch (this.padding) {
            case Symmetric.PADDING_PKCS:
                valor = "PKCS5Padding";
                break;
            case Symmetric.PADDING_ISO:
                valor = "ISO10126Padding";
                break;
        }

        return valor;
    }

    //Método que genera el cifrador con el que se realizarán las operaciones
    public Cipher generarCifrador(int func, String inputStrKeyCif, String inputStrVecCif) {
        Cipher cifrador = null;
        SecretKey clave = null;
        IvParameterSpec iv = null;
        String valorAlgoritmo = this.cadenaAlgoritmo();
        String valorModo = this.cadenaModo();
        String valorPadding = this.cadenaPadding();
        try {
            switch (this.algoritmo) {
                case Symmetric.ALGORITMO_AES:
                    clave = generaKey(inputStrKeyCif.getBytes(), "AES");//clave_aes;
                    iv = new IvParameterSpec(inputStrVecCif.getBytes());
                    break;
                case Symmetric.ALGORITMO_DES:
                    clave = generaKey(inputStrKeyCif.getBytes(), "DES");;
                    iv = new IvParameterSpec(inputStrVecCif.getBytes());
                    break;
            }

            if (valorAlgoritmo != null && valorModo != null && valorPadding != null && clave != null && iv != null) {
                cifrador = Cipher.getInstance(valorAlgoritmo + "/" + valorModo + "/" + valorPadding);

                if (this.modo != Symmetric.MODO_ECB) {
                    if (func == Symmetric.CIFRADOR) {
                        cifrador.init(Cipher.ENCRYPT_MODE, clave, iv);
                    } else if (func == Symmetric.DESCIFRADOR) {
                        cifrador.init(Cipher.DECRYPT_MODE, clave, iv);
                    }
                } else {
                    if (func == Symmetric.CIFRADOR) {
                        cifrador.init(Cipher.ENCRYPT_MODE, clave);
                    } else if (func == Symmetric.DESCIFRADOR) {
                        cifrador.init(Cipher.DECRYPT_MODE, clave);
                    }
                }
            }
        } catch (NoSuchAlgorithmException nsae) {
            System.err.println("Error: Alguno de los algoritmos de encriptación (AES o DES) no"
                    + " están disponible en el sistema. " + nsae.getMessage());
        } catch (NoSuchPaddingException nspe) {
            System.err.println("Error: Alguno mecanismos de padding (PKCS5 o ISO 10126) no están disponibles en"
                    + " el sistema. " + nspe.getMessage());
        } catch (InvalidKeyException ike) {
            System.err.println("Error: Llave inválida (codificación no válida, longitud incorrecta, no inicializada). "
                    + ike.getMessage());
        } catch (InvalidAlgorithmParameterException iape) {
            System.err.println("Error: Parámetro del algoritmo inválido (Revisar IV). "
                    + iape.getMessage());
        }
        return cifrador;
    }
    
    private SecretKey generaKey(byte[] key,String algoritmo) {
    	return new SecretKeySpec(key, algoritmo);
    }
    
    // Método que cifrará el texto según los valores actuales del encriptador
    public String cifrar(String inputText, String inputStrKeyCif, String inputStrVecCif) {
        String textoCifrado = null;

        // Generar cifrador
        Cipher cifrador = generarCifrador(Symmetric.CIFRADOR,inputStrKeyCif,inputStrVecCif);

        if (cifrador != null) {
            try {
    			byte[] txtPlain = inputText.getBytes("UTF8");
    			byte[] txtEncrypte = cifrador.doFinal(txtPlain);
    			textoCifrado = Base64.getEncoder().encodeToString(txtEncrypte);				
			} catch (Exception ex) {
                System.err.println("Error: No se ha generado el valores para el cifrador, " + ex.getMessage());
			}
        }
        return textoCifrado;
    }
       
    //Método que descifrará el texto según los valores actuales del encriptador

    public String descifrar(String inputText, String inputStrKeyCif, String inputStrVecCif) {
        String textoDescifrado = null;
        Cipher descifrador = generarCifrador(Symmetric.DESCIFRADOR, inputStrKeyCif,inputStrVecCif);

        if (descifrador != null) {
            // Descifrar
        	try {
    			byte[] txtEncrypte = Base64.getDecoder().decode(inputText);
    			byte[] txtPlain = descifrador.doFinal(txtEncrypte);
    			textoDescifrado = byte2String(txtPlain);
			} catch (Exception ex) {
                System.err.println("Error: Se ha producido un problema de entrada/salida " + ex.getMessage());
			}
        }
        return textoDescifrado;
    }
    
    private static String byte2String (byte[] bytes) {
    	StringBuilder StringBuffer = new StringBuilder();
    	for (int i=0; i<bytes.length; i++) {
    		StringBuffer.append((char) bytes[i]);
    	}    	
    	return StringBuffer.toString();
    }


    /**
     * Método que devuelve el número de bloques que tiene un texto.
     * 
     * @param texto Texto del que se quieren conocer el número de bloques.
     * @return Número de bloques del texto.
     */
    public int numeroBloques(String texto) {
        StringBuilder sb = new StringBuilder();
        int aux = 0;

        byte[] bytes = texto.getBytes();

        for (byte b : bytes) {

            for (int i = 0; i < 8; i++) {
                sb.append((b & 128) == 0 ? 0 : 1);
                b <<= 1;
            }
            aux++;
        }
        return aux;
    }
}
