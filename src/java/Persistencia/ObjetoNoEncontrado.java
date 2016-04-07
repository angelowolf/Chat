/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

/**
 *
 * @author ang_2
 */
public class ObjetoNoEncontrado extends Exception {

    public ObjetoNoEncontrado() {
    }

    public ObjetoNoEncontrado(String message) {
        super(message);
    }

    public ObjetoNoEncontrado(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjetoNoEncontrado(Throwable cause) {
        super(cause);
    }

    public ObjetoNoEncontrado(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
