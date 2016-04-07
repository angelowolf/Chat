/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

/**
 * DESACARGAR
 * LIBRERIA...................http://mvnrepository.com/artifact/org.json/json/20160212
 *
 * @author ang_2
 */
import Logica.ControladorChat;
import Logica.ISesion;
import Logica.SesionWeb;
import Logica.TipoMensaje;
import Persistencia.Modelo.Mensaje;
import Persistencia.Modelo.Usuario;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONException;
import org.json.JSONObject;

@ServerEndpoint(value = "/chat")
public class ChatEndpoint {

    private ControladorChat ch = ControladorChat.getControlador();

    private final Logger log = Logger.getLogger(getClass().getName());

    @OnOpen
    public void open(final Session session) {

        log.info("entra");
    }

    @OnClose
    public void close(Session session) {
        String nick = (String) session.getUserProperties().get("nick");
        ch.quitarUsuario(nick);
        log.log(Level.INFO, "se fue {0}", nick);
    }

    /**
     * manda un mensaje al cliente indicando que algo paso mal, o hizo algo mal,
     * y cierra la sesion
     *
     * @param session
     * @param tipo
     * @param mensaje
     */
    private void fail(Session session, TipoMensaje tipo, String mensaje) {
        JSONObject o = new JSONObject();
        o.append("tipo", "" + tipo);
        o.append("mensaje", mensaje);
        {
            try {
                session.getBasicRemote().sendText(o.toString());
                session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Algo salio muy mal... o no tan mal...."));
            } catch (IOException ex) {
                Logger.getLogger(ChatEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @OnMessage
    public void onMessage(final Session session, String msg) throws IOException {
        System.out.println(msg);
        try {
            //obtiene el JSON que manda el cliente...
            JSONObject objetoJSON = new JSONObject(msg);
            switch (getTipo(objetoJSON.getString("tipo"))) {
                //nuevo usuario que se esta logeando
                case LOGIN: {
                    //creo el usuario y paso los datos..
                    Usuario u = new Usuario();
                    u.setNick(objetoJSON.getString("nick"));
                    u.setClave(objetoJSON.getString("clave"));
                    //creo el objeto sesion, que es el encargado de mandar los msj...
                    ISesion sesion = new SesionWeb(session);
                    u.setSesion(sesion);
                    //agrego al usuario, si estan mal las credenciales o ya esta logeado.. false
                    if (ch.agregarUsuario(u)) {
                        session.getUserProperties().put("nick", u.getNick());
                        //le digo al cliente que logio con exito...
                        JSONObject ok = new JSONObject();
                        ok.append("tipo", "OK");
                        session.getBasicRemote().sendText(ok.toString());
                    } else {
                        //le digo al cliente que no pudo logear...
                        fail(session, TipoMensaje.LOGIN, "no se pudo iniciar sesion");
                    }
                }
                break;

                case MENSAJE: {
                    Mensaje msj = new Mensaje();
                    msj.setEnvia(objetoJSON.getString("envia"));
                    msj.setRecibe(objetoJSON.getString("recibe"));
                    msj.setMensaje(objetoJSON.getString("mensaje"));
                    if (!msj.validar()) {
                        ch.mandarMensaje(msj);
                    } else {
                        fail(session, TipoMensaje.ERROR, "NO TE PODES ENVIAR MSJ A VOS :(");
                    }
                }
                break;
                //cuando alguien toca el codigo del cliente y el mendaje no es 
                //uno de los tipos definidos...
                case ERROR: {
                    try {
                        JSONObject o = new JSONObject();
                        o.append("tipo", "ERROR");
                        session.getBasicRemote().sendText(o.toString());
                    } catch (IOException ex) {
                        Logger.getLogger(ChatEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            }

        } catch (JSONException e) {
            System.out.println(e.toString());
            fail(session, TipoMensaje.ERROR, "error de JSON....");
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
