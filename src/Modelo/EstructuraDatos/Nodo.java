/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.EstructuraDatos;

import java.util.ArrayList;

/**
 *
 * @author esporras
 */
public class Nodo {
    
    public Nodo(Nodo pAnterior, Nodo pSiguiente ){
        this._Contenido = new ArrayList<Object>();
        this._Siguiente = pSiguiente;
        this._Anterior = pAnterior;
    }

    public ArrayList<Object> getContenido() {
        return _Contenido;
    }

    public Nodo getSiguiente() {
        return _Siguiente;
    }

    public Nodo getAnterior() {
        return _Anterior;
    }

    public void setSiguiente(Nodo _Siguiente) {
        this._Siguiente = _Siguiente;
    }

    public void setAnterior(Nodo _Anterior) {
        this._Anterior = _Anterior;
    }
    
    public void imprimirContenido(){
        for(Object contenido:getContenido()){
            try{
                System.out.println(contenido.toString());
            }
            catch(Exception ex){
                
            }
        }
    }
    
    private ArrayList<Object> _Contenido;
    private Nodo _Siguiente;
    private Nodo _Anterior;
}
