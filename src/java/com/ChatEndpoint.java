/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

/**
 * DESACARGAR
 * LIBRERIA...................http://mvnrepository.com/artifact/org.json/json/20160212
 * http://mvnrepository.com/artifact/javax.websocket/javax.websocket-api/1.1
 *
 * @author ang_2
 */
import Logica.AccesoIlegal;
import Logica.ControladorChat;
import Logica.SesionWeb;
import Logica.TipoMensaje;
import Persistencia.Modelo.Mensaje;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONException;
import org.json.JSONObject;

@ServerEndpoint(value = "/chat")
public class ChatEndpoint {

    private final ControladorChat ch = ControladorChat.getControlador();

    private static final Logger log = Logger.getLogger("ChatEndpoint");

    @OnError
    public void onError(Throwable t) {
        log.info(t.toString());
    }

    @OnOpen
    public void onOpen(final Session session) {
        log.info("conexion abierta");
    }

    @OnClose
    public void onClose(Session session) {
        String nick = (String) session.getUserProperties().get("nick");
        ch.quitarUsuario(nick);
        log.log(Level.INFO, "conexion cerrada {0}", nick);
    }

    @OnMessage
    public void onMessage(final Session session, String msg) throws IOException {
        System.out.println(msg);
        SesionWeb sesion = new SesionWeb(session);
        try {
            JSONObject objetoJSON = new JSONObject(msg);
            switch (getTipo(objetoJSON.getString("tipo"))) {
                /**
                 * Inciar sesion por parte del cliente.
                 */
                case LOGIN: {
                    this.logear(session, objetoJSON);
                }
                break;
                /**
                 * Al recibir algun mensaje(chat) del cliente.
                 */
                case MENSAJE: {
                    this.mensaje(session, objetoJSON);
                }
                break;
                /**
                 * Cuando alguien toca el codigo js y el tipo mensaje no es uno
                 * de los posibles.
                 */
                case ERROR: {
                    sesion.notificarError(TipoMensaje.ERROR, "Okey... Que intentas hacer? :(");
                }
                break;
            }
        } catch (JSONException e) {
            log.log(Level.INFO, "{0}Error de json ", e.toString());
            sesion.notificarError(TipoMensaje.ERROR, "Error sintaxis JSON. Mira la consola.");
        }
    }

    private void logear(Session session, JSONObject objetoJSON) {
        ch.agregarUsuario(objetoJSON.getString("nick"), objetoJSON.getString("clave"), new SesionWeb(session));
    }

    private void mensaje(Session session, JSONObject objetoJSON) {
        try {
            ch.mandarMensaje(new Mensaje(objetoJSON.getString("mensaje"), objetoJSON.getString("envia"), objetoJSON.getString("recibe")), objetoJSON.getString("token"));
        } catch (AccesoIlegal e) {
            new SesionWeb(session).notificarError(TipoMensaje.ERROR, e.getMessage());
        }
    }

    private TipoMensaje getTipo(String tipo) {
        if (tipo.equals("LOGIN")) {
            return TipoMensaje.LOGIN;
        } else if (tipo.equals("MENSAJE")) {
            return TipoMensaje.MENSAJE;
        }
        return TipoMensaje.ERROR;
    }

}
