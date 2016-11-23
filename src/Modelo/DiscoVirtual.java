/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author esporras
 */
public class DiscoVirtual {

    public DiscoVirtual(int _cantSectores, int _tamSector) {
        this._cantSectores = _cantSectores;
        this._tamSector = _tamSector;
        this._listaSectores = new ArrayList<>();
        inicializarSectores();
    }
    
    public ArrayList<Sector> getListaSectores() {
        return _listaSectores;
    }

    public int getCantSectores() {
        return _cantSectores;
    }

    public void setCantSectores(int _cantSectores) {
        this._cantSectores = _cantSectores;
    }

    public int getTamSector() {
        return _tamSector;
    }

    public void setTamSector(int _tamSector) {
        this._tamSector = _tamSector;
    }
    
    private void inicializarSectores(){
        String valorInicialSector= "";
        for(int i = 0;i< getTamSector();i++){
            valorInicialSector+="0";
        }
        for(int i = 0;i< getCantSectores();i++){
            getListaSectores().add(new Sector(getTamSector(),valorInicialSector));
        }
    }
    
    public boolean isTamanoArchivoValido(int pTamanoArchivo){
        int sectoresVacios = 0;
        int espacioDisponible = 0;
        // obtengo la cantidad de sectores vacios
        for(Sector sec: getListaSectores()){
            if(!sec.hasInformation()){
                sectoresVacios++;
            }
        }
        espacioDisponible = sectoresVacios * getTamSector();
        if(espacioDisponible > pTamanoArchivo)
            return true;
        return false;
    }
    
    public ArrayList agregarInfo(String pContenido, int tamanho){
        int largo = pContenido.length();
        int sectoresVacios = 0;
        ArrayList sectores = new ArrayList<>();
        // averiguamos cuantos sectores estan vacios
        for(Sector sec: getListaSectores()){
            if(!sec.hasInformation()){
                sectoresVacios++;
            }
        }
        // si la cantidad de sectores es suficiente para guardar el archivo 
        int cantSectores = 0;
        if(sectoresVacios * getTamSector() > tamanho){
            for(Sector sec: getListaSectores()){
                if(!sec.hasInformation()){
                    if(largo > sec.getTamanho()){
                        sec.setContenido(pContenido.substring(0,sec.getTamanho()));
                        pContenido = pContenido.substring(sec.getTamanho());
                        largo = pContenido.length();
                        sec.setEspacioDisponible(0);
                        sectores.add(cantSectores);
                    }
                    else{
                        sec.setContenido(pContenido.substring(0,largo)+sec.getContenido().substring(largo));
                        sec.setEspacioDisponible(sec.getEspacioDisponible()-largo);
                        sectores.add(cantSectores);
                        return sectores;
                    }   
                }
                cantSectores++;
            }
            return sectores;
        }
        else{
            return sectores;
        }
    }
    
    public void imprimirInfoDisco(){
        int contador = 1;
        for(Sector sec: getListaSectores()){
            System.out.println("Sector: "+ contador + " Contenido: "+ sec.getContenido());
            contador ++;
        }
    }
    
    public void crearArchivoInfoDisco(String pRuta){
        File archivo=new File(pRuta); //"/Users/esporras//Desktop/fileSystem.txt"
        try{
            FileWriter escribir = new FileWriter(archivo);
            BufferedWriter objetoarchivo = new BufferedWriter(escribir);
            PrintWriter frase = new PrintWriter(objetoarchivo);
            frase.write("");
            int contador = 1;
            for(Sector sec: getListaSectores()){
                frase.append(sec.getContenido() + "\n");
                contador ++;
            }
            frase.close();
            objetoarchivo.close();
        } catch(IOException ioe){
            ioe.printStackTrace();            
        }    
    }
    
    public void borrarInfoSectores(ArrayList sectores){
        for(int i = 0;i<sectores.size();i++){
            for(int j = 0;j<getListaSectores().size();j++){
                if((int)sectores.get(i) == j){
                    String valorInicialSector = "";
                    for(int k = 0;k< getTamSector();k++){
                        valorInicialSector+="0";
                    }
                    getListaSectores().get(j).setContenido(valorInicialSector);
                    getListaSectores().get(j).setEspacioDisponible(getTamSector());
                }
            }
        }
    }
    private int _cantSectores;
    private int _tamSector;
    private ArrayList<Sector> _listaSectores;
}
