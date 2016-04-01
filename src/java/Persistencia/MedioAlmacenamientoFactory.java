/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Persistencia.BD.BDMensaje;
import Persistencia.BD.BDUsuario;
import Persistencia.Archivo.ArchivoUsuario;
import Persistencia.Archivo.ArchivoMensaje;

/**
 *
 * @author ang_2
 */
public class MedioAlmacenamientoFactory {

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
