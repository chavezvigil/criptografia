package edu.udb.cri.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class UtilMessage {

    private static ResourceBundle rb;  

    static {
        rb = ResourceBundle.getBundle("resources.properties.CriptoSystem");
    }

    public UtilMessage() {
        super();
    }

    /**
     * Devuelve un mensaje contenido en el archivo de propiedades para mensajes
     * de la aplicación.
     * @param llave Llave en el archivo de propiedades.
     * @param argumentos Argumentos adicionales que reemplazarán los marcadores
     * de posición en el mensaje.
     * @return Mensaje según el archivo de propiedades con los marcadores de
     * posición llenos según los argumentos enviados en el último parámetro.
     */
    public static String getMensaje(String llave, Object... argumentos) {
        String mensaje = rb.getString(llave);
        mensaje = MessageFormat.format(mensaje, argumentos);
        return mensaje;
    }
}
