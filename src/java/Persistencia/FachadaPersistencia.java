/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import static Persistencia.MedioAlmacenamientoTipo.ARCHIVO;
import static Persistencia.MedioAlmacenamientoTipo.BD;
import static Persistencia.ModeloTipo.MENSAJE;
import static Persistencia.ModeloTipo.USUARIO;
import Persistencia.Modelo.Mensaje;
import Persistencia.Modelo.Usuario;
import java.io.Serializable;

/**
 *
 * @author ang_2
 */
public class FachadaPersistencia {

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

    abstract class MedioAlmacenamiento {

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
    }

    static class ArchivoMensaje extends MedioAlmacenamiento {

        public ArchivoMensaje() {
            super(MedioAlmacenamientoTipo.ARCHIVO);
        }

        @Override
        public boolean guardar(Serializable o) {
            System.out.println("guardo usuario desde archivo msj");
            return true;
        }

        @Override
        public boolean eliminar(Serializable o) {
            System.out.println("elimino desde archivo msj");
            return true;
        }

        @Override
        public boolean modificar(Serializable o) {
            System.out.println("modifico desde archivo msj");
            return true;
        }

    }

    static class ArchivoUsuario extends MedioAlmacenamiento {

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

    }

    static class BDMensaje extends MedioAlmacenamiento {

        public BDMensaje() {
            super(MedioAlmacenamientoTipo.BD);
        }

        @Override
        public boolean guardar(Serializable o) {
            System.out.println("guardo usuario desde bd msj");
            return true;
        }

        @Override
        public boolean eliminar(Serializable o) {
            System.out.println("elimino desde bd msj");
            return true;
        }

        @Override
        public boolean modificar(Serializable o) {
            System.out.println("modifico desde bd msj");
            return true;
        }
    }

    static class BDUsuario extends MedioAlmacenamiento {

        public BDUsuario() {
            super(MedioAlmacenamientoTipo.BD);
        }

        @Override
        public boolean guardar(Serializable o) {
            System.out.println("guardo usuario desde bd");
            return true;
        }

        @Override
        public boolean eliminar(Serializable o) {
            System.out.println("elimino desde bd");
            return true;
        }

        @Override
        public boolean modificar(Serializable o) {
            System.out.println("modifico desde bd");
            return true;
        }
    }

    static class MedioAlmacenamientoFactory {

        public static MedioAlmacenamiento crearMedioAlmacenamiento(MedioAlmacenamientoTipo medioAlmacenamientoTipo, ModeloTipo modeloTipo) {
            MedioAlmacenamiento medioAlmacenamiento = null;
            switch (medioAlmacenamientoTipo) {
                case BD:
                    switch (modeloTipo) {
                        case USUARIO:
                            medioAlmacenamiento = new BDUsuario();
                            break;
                        case MENSAJE:
                            medioAlmacenamiento = new BDMensaje();
                            break;
                    }
                    break;

                case ARCHIVO:
                    switch (modeloTipo) {
                        case USUARIO:
                            medioAlmacenamiento = new ArchivoUsuario();
                            break;
                        case MENSAJE:
                            medioAlmacenamiento = new ArchivoMensaje();
                            break;
                    }
                    break;
            }
            return medioAlmacenamiento;
        }
    }
  
}
