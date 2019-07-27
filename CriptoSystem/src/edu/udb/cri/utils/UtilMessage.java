package edu.udb.cri.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class UtilMessage {

    private static ResourceBundle rb;  

    static {
    	Locale locale = new Locale("es_SV");
        rb = ResourceBundle.getBundle("resources.properties.application", locale);
    }

    public UtilMessage() {
        super();
    }

    /**
     * Devuelve un mensaje contenido en el archivo de propiedades para mensajes
     * de la aplicaci�n.
     * @param llave Llave en el archivo de propiedades.
     * @param argumentos Argumentos adicionales que reemplazar�n los marcadores
     * de posici�n en el mensaje.
     * @return Mensaje seg�n el archivo de propiedades con los marcadores de
     * posici�n llenos seg�n los argumentos enviados en el �ltimo par�metro.
     */
    public static String getMensaje(String llave, Object... argumentos) {
        String mensaje = rb.getString(llave);
        mensaje = MessageFormat.format(mensaje, argumentos);
        return mensaje;
    }
}
