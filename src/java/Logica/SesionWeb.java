/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Persistencia.Modelo.Mensaje;
import Persistencia.Modelo.Usuario;
import com.ChatEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.Session;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ang_2
 */
public class SesionWeb implements ISesion {

    Session sesion;

    public SesionWeb(Session session) {
        this.sesion = session;

    }

    @Override
    public void mandarMensaje(Mensaje mensaje) {
        try {
            if (sesion.isOpen()) {
                JSONObject json = new JSONObject();
                json.append("envia", mensaje.getEnvia());
                json.append("recibe", mensaje.getRecibe());
                json.append("mensaje", mensaje.getMensaje());
                json.append("fecha", mensaje.getFecha());
                json.append("tipo", "MENSAJE");
                sesion.getBasicRemote().sendText(json.toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(SesionWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void notificarUsuariosLogeados(HashMap<String, Usuario> usuariosConectados) {
        try {
            if (sesion.isOpen()) {
                JSONObject objetoJSON = new JSONObject();
                JSONArray usuarios = new JSONArray();
                for (Map.Entry<String, Usuario> entry : usuariosConectados.entrySet()) {
                    String nick = entry.getKey();
                    usuarios.put(nick);
                }
                objetoJSON.put("usuarios", usuarios);
                objetoJSON.append("tipo", "CONTACTOS");
                sesion.getBasicRemote().sendText(objetoJSON.toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(SesionWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void exitoAlLogear(Usuario usuario) {
        try {
            if (sesion.isOpen()) {
                sesion.getUserProperties().put("nick", usuario.getNick());
                JSONObject objetoJSON = new JSONObject();
                objetoJSON.append("tipo", "OK");
                objetoJSON.append("token", usuario.getToken());
                System.out.println(usuario.toString());
                System.out.println(usuario.getToken());
                sesion.getBasicRemote().sendText(objetoJSON.toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(SesionWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void notificarError(TipoMensaje tipoMensaje, String mensaje) {
        try {
            JSONObject o = new JSONObject();
            o.append("tipo", "" + tipoMensaje);
            o.append("mensaje", mensaje);
            sesion.getBasicRemote().sendText(o.toString());
            sesion.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Algo salio muy mal... o no tan mal(como login mal viste..)"));
        } catch (IOException ex) {
            Logger.getLogger(ChatEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
