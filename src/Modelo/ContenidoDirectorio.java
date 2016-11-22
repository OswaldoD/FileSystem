/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author esporras
 */
public class ContenidoDirectorio extends ArrayList{
    
    public void insertar(Object pObjeto){
        this.add(pObjeto);
    }
    
    public boolean isVacio(){
        return this.isEmpty();
    }
    public Object extraerPrimero(){
        if(!isVacio()){
            return this.get(0);
        }
        return null;
    }
}
