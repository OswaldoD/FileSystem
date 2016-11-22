/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author esporras
 */
public class Directorio extends Propiedades{
    
    public Directorio(String _Nombre, String _Ubicacion, int _Tamaño, String _Usuario, Date _FechaCreacion, Date _UltimaModificacion, ArrayList _UbicaciónLogica, int permisos,Directorio pPadre) {
        super(_Nombre, _Ubicacion, _Tamaño, _Usuario, _FechaCreacion, _UltimaModificacion, _UbicaciónLogica, permisos);    
        this._Contenido = new ContenidoDirectorio();
        this._Padre = pPadre;
    }

    public ContenidoDirectorio getContenido() {
        return _Contenido;
    }

    public void setContenido(ContenidoDirectorio _Contenido) {
        this._Contenido = _Contenido;
    }

    public Directorio getPadre() {
        return _Padre;
    }
    
    
    public void listarDirectorio(){
        for(Object obj: getContenido()){
            try{
                Archivo arch = (Archivo)obj;
                System.out.println(arch.getNombre()+arch.getExtension() + " archivo");
            }
            catch(Exception ex){
                Directorio dir = (Directorio)obj;
                System.out.println(dir.getNombre() + " directorio");
            }
        }
    }
    
    public ArrayList borrarDirectorio(){
        ArrayList sectores = new ArrayList();
        Iterator<Object> elementos = this._Contenido.iterator();
        while(elementos.hasNext()){
            Object obj = elementos.next();
            try{
                Directorio dir = (Directorio)obj;
                dir.borrarDirectorio();
                elementos.remove();
            }
            catch(Exception ex){
                Archivo arc = (Archivo)obj;
                for(Object sec:arc.getUbicacionLogica()){
                    sectores.add(sec);
                }
            }
        }
        return sectores;
    }
    
    public boolean archivoExiste(String pNombreArchivo, String pExtension){
        for(Object obj:getContenido()){
            try{
                Archivo arc = (Archivo)obj;
                if(arc.getNombre().equals(pNombreArchivo) && arc.getExtension().equals(pExtension)){
                    return true;
                }
            }
            catch(Exception ex){
                // el casting no funciono porque era un directorio
                // no nos importa, seguimos buscando en el directorio
            }
        }
        return false;
    }
    
    public boolean directorioExiste(String pNombreDirectorio){
        for(Object obj:getContenido()){
            try{
                Directorio dir = (Directorio)obj;
                if(dir.getNombre().equals(pNombreDirectorio)){
                    return true;
                }
            }
            catch(Exception ex){
                // el casting no funciono porque era un directorio
                // no nos importa, seguimos buscando en el directorio
            }
        }
        return false;
    }
    
    public void aumentarTamano(int pTamano){
        super.setTamaño(super.getTamaño()+ pTamano);
    }
    
    private ContenidoDirectorio _Contenido;
    private Directorio _Padre;
    
}
