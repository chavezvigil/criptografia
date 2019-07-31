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
     * de la aplicacion.
     * @param llave Llave en el archivo de propiedades.
     * @param argumentos Argumentos adicionales que reemplazaron los marcadores
     * de posicion en el mensaje.
     * @return Mensaje segun el archivo de propiedades con los marcadores de
     * posicion llenos segun los argumentos enviados en el ultimo parametro.
     */
    public static String getMensaje(String llave, Object... argumentos) {
        String mensaje = rb.getString(llave);
        mensaje = MessageFormat.format(mensaje, argumentos);
        return mensaje;
    }
}
