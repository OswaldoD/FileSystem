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
public class Archivo extends Propiedades{

    public Archivo(String pContenido, String pExtension,String _Nombre, String _Ubicacion, int _Tamaño, String _Usuario, Date _FechaCreacion, Date _UltimaModificacion, ArrayList _UbicaciónLogica, int permisos) {
        super(_Nombre, _Ubicacion, _Tamaño, _Usuario,  _FechaCreacion, _UltimaModificacion, _UbicaciónLogica, permisos);
        this._Contenido = pContenido;
        this._Extension = pExtension;
    }

    public String getContenido() {
        return _Contenido;
    }

    public void setContenido(String _Contenido) {
        this._Contenido = _Contenido;
    }

    public String getExtension() {
        return _Extension;
    }

    public void setExtension(String _Extension) {
        this._Extension = _Extension;
    }
    
    public String verPropiedades(){
        return super.toString()+this.toString();
    }

    @Override
    public String toString() {
        return ", _Extension=" + _Extension;
    }
    
    
    private String _Contenido;
    private String _Extension;
}
