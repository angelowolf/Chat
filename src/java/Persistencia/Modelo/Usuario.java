/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.Modelo;

import Logica.ISesion;
import Logica.TipoMensaje;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author ang_2
 */
public class Usuario implements Serializable {

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nick=" + nick + ", clave=" + clave + ", sesion=" + sesion + ", token=" + token + '}';
    }

    private int id;
    private String nick, clave;
    ISesion sesion;

    private String token;

    public Usuario(String nick, String clave, ISesion sesion) {
        this.nick = nick;
        this.clave = clave;
        this.sesion = sesion;
    }

    public Usuario() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ISesion getSesion() {
        return sesion;
    }

    public void setSesion(ISesion sesion) {
        this.sesion = sesion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void mandarMensaje(Mensaje mensaje) {
        sesion.mandarMensaje(mensaje);
    }

    public void notificarUsuariosLogeados(HashMap<String, Usuario> usuariosConectados) {
        sesion.notificarUsuariosLogeados(usuariosConectados);
    }

    /**
     * Verifica que las token sean iguales
     *
     * @param token
     * @return si lo son.
     */
    public boolean esEl(String token) {
        return this.token.equals(token);
    }

    /**
     * Le manda un mensaje al cliente del tipo OK, indicandole que logeo con
     * exito y la token generada.
     */
    public void exitoAlLogear() {
        sesion.exitoAlLogear(this);
    }

    /**
     * Le manda un mensaje al cliente del tipo LOGIN, indicandole que no pudo
     * iniciar sesion.
     *
     * @param tipoMensaje
     * @param mensaje
     */
    public void notificarError(TipoMensaje tipoMensaje, String mensaje) {
        sesion.notificarError(tipoMensaje, mensaje);
    }

}
