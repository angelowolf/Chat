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

    public void mandarMensaje(Mensaje mensaje);

    public void notificarUsuariosLogeados(HashMap<String, Usuario> usuariosConectados);
    
}
