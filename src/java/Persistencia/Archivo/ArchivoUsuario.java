/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.Archivo;

import Persistencia.MedioAlmacenamiento;
import Persistencia.MedioAlmacenamientoTipo;
import java.io.Serializable;

/**
 *
 * @author ang_2
 */
public class ArchivoUsuario extends MedioAlmacenamiento {

    public ArchivoUsuario() {
        super(MedioAlmacenamientoTipo.ARCHIVO);
    }

    @Override
    public boolean guardar(Serializable o) {
        System.out.println("guardo usuario desde archivo");
        return true;
    }

    @Override
    public boolean eliminar(Serializable o) {
        System.out.println("elimino desde archivo");
        return true;
    }

    @Override
    public boolean modificar(Serializable o) {
        System.out.println("modifico desde archivo");
        return true;
    }

    @Override
    public Serializable get(String arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
