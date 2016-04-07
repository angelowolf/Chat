/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Persistencia.Modelo.Mensaje;
import Persistencia.Modelo.Usuario;
import java.util.HashMap;

/**
 *
 * @author ang_2
 */
public interface ISesion {

    /**
     * Le manda un mensaje al cliente que representa una conversacion con
     * alguien.
     *
     * @param mensaje
     */
    public void mandarMensaje(Mensaje mensaje);

    /**
     * Le manda todos los contactos que estan logeados al cliente.
     *
     * @param usuariosConectados
     */
    public void notificarUsuariosLogeados(HashMap<String, Usuario> usuariosConectados);

    /**
     * Manda un mensaje al cliente indicandole que pudo logear con exito, como
     * la token generada.
     *
     * @param usuario
     */
    public void exitoAlLogear(Usuario usuario);

    /**
     * Manda un mensaje al cliente indicando que algo salio mal, o hizo algo
     * mal, o esta intentando hacer algo malo y cierra la sesion.
     *
     * @param tipoMensaje Tipo de mensaje.
     * @param mensaje Mensaje del error.
     */
    public void notificarError(TipoMensaje tipoMensaje, String mensaje);

}
