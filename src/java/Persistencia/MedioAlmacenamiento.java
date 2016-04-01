/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.io.Serializable;

/**
 *
 * @author ang_2
 */
public abstract class MedioAlmacenamiento {

    private MedioAlmacenamientoTipo mat = null;

    public MedioAlmacenamiento(MedioAlmacenamientoTipo mat) {
        this.mat = mat;
    }

    public MedioAlmacenamientoTipo getMedioAlmacenamientoTipo() {
        return mat;
    }

    public void setMedioAlmacenamientoTipo(MedioAlmacenamientoTipo mat) {
        this.mat = mat;
    }

    public abstract boolean guardar(Serializable o);

    public abstract boolean eliminar(Serializable o);

    public abstract boolean modificar(Serializable o);
    
    public abstract Serializable get(String arg);
}
