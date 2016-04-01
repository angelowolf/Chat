/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.Modelo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ang_2
 */
public class Mensaje implements Serializable {

    @Override
    public String toString() {
        return "Mensaje{" + "id=" + id + ", mensaje=" + mensaje + ", envia=" + envia + ", recibe=" + recibe + ", fecha=" + fecha + '}';
    }

    private int id;
    private String mensaje, envia, recibe;
    private Date fecha;

    public String getEnvia() {
        return envia;
    }

    public void setEnvia(String envia) {
        this.envia = envia;
    }

    public String getRecibe() {
        return recibe;
    }

    public void setRecibe(String recibe) {
        this.recibe = recibe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * verifica que los nicks sean distintos...
     *
     * @return true si son !=
     */
    public boolean validar() {
        return (getEnvia().equals(getRecibe()));
    }

}
