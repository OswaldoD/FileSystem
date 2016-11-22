/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.EstructuraDatos;

/**
 *
 * @author esporras
 */
public class ListaDobleEnlazada {
    
    public ListaDobleEnlazada(){
        this._Primero = this._Ultimo = null;
    }
    
    public void insertarObjeto(Object pObjeto){
        if(getPrimero() == null){
            // la lista esta vacia
            this._Primero = new Nodo(null,null);
            getPrimero().getContenido().add(pObjeto);
            this._Ultimo = this._Primero;
        }
        else{
            Nodo nuevo = new Nodo(null,this._Primero);
            nuevo.getContenido().add(pObjeto);
            getUltimo().setSiguiente(nuevo);
            this._Ultimo = nuevo;
        }
    }
    
    public void imprimir(){
        Nodo recorrido = getPrimero();
        while(recorrido != null){
            recorrido.imprimirContenido();
            recorrido = recorrido.getSiguiente();
        }
    }

    public Nodo getUltimo() {
        return _Ultimo;
    }

    public Nodo getPrimero() {
        return _Primero;
    }
    
    private Nodo _Ultimo;
    private Nodo _Primero;
}
