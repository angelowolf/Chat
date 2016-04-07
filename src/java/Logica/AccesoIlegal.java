/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

/**
 *
 * @author ang_2
 */
public class AccesoIlegal extends Exception {

    public AccesoIlegal() {
    }

    public AccesoIlegal(String message) {
        super(message);
    }

    public AccesoIlegal(String message, Throwable cause) {
        super(message, cause);
    }

    public AccesoIlegal(Throwable cause) {
        super(cause);
    }

    public AccesoIlegal(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
