/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controladores.ControllerFileSystem;

/**
 *
 * @author esporras
 */
public interface Funciones {
    public void setController(ControllerFileSystem pController);
    public void create(int pNumSectores, int pTamSector);
    public String file(String pContenido, String pExtension, String pNombre);
    public void mkdir(String pNombre);
    public void cd(String pNombre);
    public void cambiarDIR(String pRuta);
    public void listDir();
    public void modFile(String pNombre, String pContenido);
    public void properties(String pNombre);
    public void fileCont(String pNombre);
    public void copy(String pRutaProcedencia, String pRutaDestino, int pTipo);
    public void move(String pRutaProcedencia, String pRutaDestino, boolean pBorrar);
    public void remove(String pNombre);
    public void find(String pNombre);
    public void tree();
    public void help();
}
