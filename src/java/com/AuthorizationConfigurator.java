/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import static java.util.Arrays.asList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.websocket.ClientEndpointConfig;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;

/**
 *
 * @author ang_2
 */
public class AuthorizationConfigurator extends ClientEndpointConfig.Configurator {

    private final Logger log = Logger.getLogger(getClass().getName());

    @Override
    public void beforeRequest(Map<String, List<String>> headers) {
      log.info("aqsdasdasd");
        headers.put("Authorization", asList("Basic " + printBase64Binary("Tomitribe:tomee".getBytes())));
    }
}
