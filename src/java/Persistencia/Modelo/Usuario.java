/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.Modelo;

import Logica.ISesion;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author ang_2
 */
public class Usuario implements Serializable {

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nick=" + nick + ", clave=" + clave + ", sesion=" + sesion + '}';
    }

    private int id;
    private String nick, clave;
    ISesion sesion;

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

}
