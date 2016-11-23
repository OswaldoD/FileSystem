/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author esporras
 */
public class Propiedades {

    public Propiedades(String _Nombre, String _Ubicacion, int _Tamaño, String _Usuario, Date _FechaCreacion, Date _UltimaModificacion, ArrayList _UbicaciónLogica, int permisos) {
        this._Nombre = _Nombre;
        this._Ubicacion = _Ubicacion;
        this._Tamaño = _Tamaño;
        this._Usuario = _Usuario;
        this._FechaCreacion = _FechaCreacion;
        this._UltimaModificacion = _UltimaModificacion;
        this._UbicacionLogica = _UbicaciónLogica;
        this.permisos = permisos;
    }

    public String getNombre() {
        return _Nombre;
    }

    public void setNombre(String _Nombre) {
        this._Nombre = _Nombre;
    }

    public String getUbicacion() {
        return _Ubicacion;
    }

    public void setUbicacion(String _Ubicacion) {
        this._Ubicacion = _Ubicacion;
    }

    public int getTamaño() {
        return _Tamaño;
    }

    public void setTamaño(int _Tamaño) {
        this._Tamaño = _Tamaño;
    }

    public String getUsuario() {
        return _Usuario;
    }

    public void setUsuario(String _Usuario) {
        this._Usuario = _Usuario;
    }

    public Date getFechaCreacion() {
        return _FechaCreacion;
    }

    public void setFechaCreacion(Date _FechaCreacion) {
        this._FechaCreacion = _FechaCreacion;
    }

    public Date getUltimaModificacion() {
        return _UltimaModificacion;
    }

    public void setUltimaModificacion(Date _UltimaModificacion) {
        this._UltimaModificacion = _UltimaModificacion;
    }

    public ArrayList getUbicacionLogica() {
        return _UbicacionLogica;
    }

    public void setUbicacionLogica(ArrayList _UbicacionLogica) {
        this._UbicacionLogica = _UbicacionLogica;
    }

    public int getPermisos() {
        return permisos;
    }

    public void setPermisos(int permisos) {
        this.permisos = permisos;
    }

    @Override
    public String toString() {
        if(_UltimaModificacion==null){
            _UltimaModificacion=_FechaCreacion;
        }
        return "->Nombre = " + _Nombre +"\n"+ "->Ubicacion = " + _Ubicacion+"\n" + 
                "->Tamaño = " + _Tamaño+"\n" + "->Fecha de la Creacion = " + _FechaCreacion+"\n" + "->Ultima Fecha de Modificacion = " + _UltimaModificacion+"\n";
    }
    
    
    private String _Nombre;
    private String _Ubicacion;
    private int _Tamaño;
    private String _Usuario;
    private Date _FechaCreacion;
    private Date _UltimaModificacion;
    private ArrayList _UbicacionLogica;
    private int permisos;
    
}
