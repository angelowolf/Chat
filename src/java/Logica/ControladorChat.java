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
import Persistencia.ObjetoNoEncontrado;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ang_2
 */
public class ControladorChat {

    private static ControladorChat instancia = null;

    private static final MedioAlmacenamientoTipo MEDIO = MedioAlmacenamientoTipo.BD;
    private final HashMap<String, Usuario> usuariosConectados;

    /**
     * Verifica si el usuario existe y puede logear, si es el caso es agregado
     * al hashmap de usuarios logeados, casi contrario retorna falso.
     *
     * @param nick
     * @param clave
     * @param sesion
     */
    public synchronized void agregarUsuario(String nick, String clave, ISesion sesion) {
        Usuario usuario;
//        try {
//            usuario = FachadaPersistencia.getUsuario(MEDIO, nick);
        usuario = new Usuario(nick, Encriptar.encriptaEnMD5(clave), sesion);
        if (usuario.getClave().equals(Encriptar.encriptaEnMD5(clave))) {
            usuario.setToken(Encriptar.crearCodigo());
            usuario.setSesion(sesion);
            usuariosConectados.put(usuario.getNick(), usuario);
            usuario.exitoAlLogear();
            notificarUsuariosLogeados();
        } else {
            usuario.notificarError(TipoMensaje.LOGIN, "Credenciales no validas :(");
        }
//        } 
//        catch (ObjetoNoEncontrado e) {
//            sesion.notificarError(TipoMensaje.LOGIN, "Credenciales no validas :(");
//        }

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
     * Manda un mensaje al destinatario. Almacena el mensaje en un medio
     * persistente. Verifica que el usuario que esta mandando el mensaje, es
     * quien dice ser realmente, esto se hace con el TOKEN que el usuario recibe
     * al momento de logearse, en cada mensaje que envia tambien viaja el token
     * y es comparado con el token que se genero al momento de logearse, si no
     * llegan a ser las mismas salta una excepcion AccesoIlegal. Si el mensaje
     * es enviado a un destinatario que no existe o a si mismo se produce una
     * excepcion DestinatarioIncorrecto.
     *
     * @param mensaje
     * @param token
     * @throws Logica.AccesoIlegal
     */
    public synchronized void mandarMensaje(Mensaje mensaje, String token) throws AccesoIlegal {
        if (usuariosConectados.containsKey(mensaje.getEnvia())) {
            Usuario usuarioEnviaMensaje = usuariosConectados.get(mensaje.getEnvia());
            if (usuarioEnviaMensaje.esEl(token)) {
                if (usuariosConectados.containsKey(mensaje.getRecibe()) && !mensaje.validar()) {
                    usuariosConectados.get(mensaje.getRecibe()).mandarMensaje(mensaje);
                    //FachadaPersistencia.guardarMensaje(MEDIO, mensaje);
                } else {
                    usuarioEnviaMensaje.notificarError(TipoMensaje.ERROR, "El destinatario no es valido !  :(");
                }
            } else {
                throw new AccesoIlegal("Okey... quien eres ?! :(");
            }
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
