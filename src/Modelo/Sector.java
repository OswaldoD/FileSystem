/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author esporras
 */
public class Sector {

    public Sector(int _Tamanho, String pContenido) {
        this._Tamanho = _Tamanho;
        this._Contenido = pContenido;
        this._EspacioDisponible = this._Tamanho;
    }
    
    public Boolean hasInformation(){
        return this._Tamanho != this._EspacioDisponible;
    }

    public void setEspacioDisponible(int _EspacioDisponible) {
        this._EspacioDisponible = _EspacioDisponible;
    }

    public void setContenido(String _Contenido) {
        this._Contenido = _Contenido;
    }

    public String getContenido() {
        return _Contenido;
    }

    public int getEspacioDisponible() {
        return _EspacioDisponible;
    }   
    
    public int getTamanho() {
        return _Tamanho;
    }
    
    
    
    private String _Contenido;
    private int _Tamanho;
    private int _EspacioDisponible;
}
