/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Persistencia.FachadaPersistencia;
import Persistencia.MedioAlmacenamientoTipo;
import Persistencia.Modelo.Mensaje;
import Persistencia.Modelo.Usuario;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ang_2
 */
public class ControladorChat {

    private static ControladorChat instancia = null;

    private static final MedioAlmacenamientoTipo MEDIO = MedioAlmacenamientoTipo.BD;
    private HashMap<String, Usuario> usuariosConectados;

    /**
     * Verifica si el usuario existe y puede logear, si es el caso es agregado
     * al hashmap de usuarios logeados, casi contrario retorna falso.
     *
     * @param u
     * @return true si logea, false sino.
     */
    public synchronized boolean agregarUsuario(Usuario u) {

////        try {
//        Usuario usuario = FachadaPersistencia.getUsuario(MEDIO, u.getNick());
//        if (usuario.getClave().equals(u.getClave())) {
//            u.setId(usuario.getId());
//            usuariosConectados.put(usuario.getNick(), u);
//            return true;
//        } else {
//            return false;
//        }
////        } catch (org.hibernate.ObjectNotFoundException e) {
////            return false;
////        }
        usuariosConectados.put(u.getNick(), u);
        notificarUsuariosLogeados();
        return true;

    }

    /**
     * Quita al usuario de los usuarios conectados.
     *
     * @param nick
     */
    public synchronized void quitarUsuario(String nick) {
        if (usuariosConectados.containsKey(nick)) {
            usuariosConectados.remove(nick);
            notificarUsuariosLogeados();
        }
    }

    /**
     * manda un mensaje al destinatario y lo guarda en la bd...
     *
     * @param mensaje
     */
    public synchronized void mandarMensaje(Mensaje mensaje) {
        if (usuariosConectados.containsKey(mensaje.getRecibe())) {
            usuariosConectados.get(mensaje.getRecibe()).mandarMensaje(mensaje);
            //FachadaPersistencia.guardarMensaje(MEDIO, mensaje);
        }
    }

    /**
     * notifica a todos los usuarios logeados cuando un usuario se va o inicia
     * sesion
     *
     */
    private synchronized void notificarUsuariosLogeados() {
        for (Map.Entry<String, Usuario> entry : usuariosConectados.entrySet()) {
            Usuario user = entry.getValue();
            //creo una copia de los usuarios conectados...
            HashMap<String, Usuario> temporal = new HashMap<>(usuariosConectados);
            //saco de la copia a si mismo 
            temporal.remove(user.getNick());
            //envio los usuarios logeados..
            user.notificarUsuariosLogeados(temporal);
        }
    }

    public synchronized static ControladorChat getControlador() {
        if (instancia == null) {
            instancia = new ControladorChat();
        }
        return instancia;
    }

    private ControladorChat() {
        this.usuariosConectados = new HashMap<>();
    }
}
