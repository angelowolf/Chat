/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Persistencia.Modelo.Mensaje;
import Persistencia.Modelo.Usuario;

/**
 *
 * @author ang_2
 */
public class FachadaPersistencia {

    public static Usuario getUsuario(MedioAlmacenamientoTipo tipo, String nick) {
        MedioAlmacenamiento ma = MedioAlmacenamientoFactory.crearMedioAlmacenamiento(tipo, ModeloTipo.USUARIO);
        return (Usuario) ma.get(nick);
    }

    public static boolean guardarUsuario(MedioAlmacenamientoTipo tipo, Usuario u) {
        MedioAlmacenamiento ma = MedioAlmacenamientoFactory.crearMedioAlmacenamiento(tipo, ModeloTipo.USUARIO);
        return ma.guardar(u);
    }

    public static boolean guardarMensaje(MedioAlmacenamientoTipo tipo, Mensaje u) {
        MedioAlmacenamiento ma = MedioAlmacenamientoFactory.crearMedioAlmacenamiento(tipo, ModeloTipo.MENSAJE);
        return ma.guardar(u);
    }

    public static boolean modificarUsuario(MedioAlmacenamientoTipo tipo, Usuario u) {
        MedioAlmacenamiento ma = MedioAlmacenamientoFactory.crearMedioAlmacenamiento(tipo, ModeloTipo.USUARIO);
        return ma.modificar(u);
    }

    public static boolean modificarMensaje(MedioAlmacenamientoTipo tipo, Mensaje u) {
        MedioAlmacenamiento ma = MedioAlmacenamientoFactory.crearMedioAlmacenamiento(tipo, ModeloTipo.MENSAJE);
        return ma.modificar(u);
    }

    public static boolean eliminarUsuario(MedioAlmacenamientoTipo tipo, Usuario u) {
        MedioAlmacenamiento ma = MedioAlmacenamientoFactory.crearMedioAlmacenamiento(tipo, ModeloTipo.USUARIO);
        return ma.eliminar(u);
    }

    public static boolean eliminarMensaje(MedioAlmacenamientoTipo tipo, Mensaje u) {
        MedioAlmacenamiento ma = MedioAlmacenamientoFactory.crearMedioAlmacenamiento(tipo, ModeloTipo.MENSAJE);
        return ma.eliminar(u);
    }
}
