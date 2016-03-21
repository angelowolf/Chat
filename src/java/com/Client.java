/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.websocket.ClientEndpoint;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.OnMessage;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;

/**
 *
 * @author ang_2
 */
@ClientEndpoint(configurator = AuthorizationConfigurator.class)
public class Client {
 private final Logger log = Logger.getLogger(getClass().getName());
    @OnMessage
    public void onMessage(String content) {
        log.info("asdasdasddasgdgew222");
    }
}
