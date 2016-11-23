/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import Modelo.FileSystem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * @author Phoenix
 */
public class Main {
    
    public static String leerEntradaString(){
        String p= "";
        try
        {
           BufferedReader leerEntrada = new BufferedReader(new InputStreamReader(System.in));
           p = leerEntrada.readLine();
        }
        catch(IOException e)
        {
            System.err.println("Error:" + e.getMessage());
        }
        return p;
    }
    
    public static boolean analizarOpcion(StringTokenizer pTokens,FileSystem pFileSystem){
        while(pTokens.hasMoreElements()){
            String comando = pTokens.nextElement().toString();
            
            //Comando CREATE cantidadSectores tamanoSectores
            if(comando.toLowerCase().compareTo("create") == 0){
                int cantSectores = Integer.parseInt(pTokens.nextElement().toString());
                int tamSector = Integer.parseInt(pTokens.nextElement().toString());
                pFileSystem.create(cantSectores, tamSector);
            }
            
            //Comando FILE nombreArchivo extensionArchivo contenidoArchivo
            else if(comando.toLowerCase().compareTo("file") == 0){
                try{
                    // el orden de los parametros seria: file nombreArchivo extension contenido
                    String nombreArchivo = pTokens.nextElement().toString();
                    String extensionArchivo = pTokens.nextElement().toString();                    
                    String contenidoArchivo = pTokens.nextToken("");
                    pFileSystem.file(contenidoArchivo, extensionArchivo, nombreArchivo);
                    //pFileSystem.getDisk().imprimirInfoDisco();
                    return false;
                }
                catch(Exception ex){
                    System.out.println("\nError, parámetros incorrectos.\n");
                    //System.out.println(ex.getMessage());
                    return false;
                }
               
            }
            
            // Comando listaDIR
            else if(comando.toLowerCase().compareTo("listardir") == 0){
                try{
                    pFileSystem.listDir();
                }
                catch(Exception ex){
                    
                }
            }
            else if(comando.toLowerCase().compareTo("mkdir") == 0){
                try{
                    pFileSystem.mkdir(pTokens.nextElement().toString());
                }
                catch(Exception ex){
                }
                
            }
            else if(comando.toLowerCase().compareTo("cd") == 0){
                try{
                    pFileSystem.cd(pTokens.nextElement().toString());
                }
                catch(Exception ex){
                    
                }
            }
            else if(comando.toLowerCase().compareTo("cambiardir") == 0){
                try{
                    pFileSystem.cambiarDIR(pTokens.nextElement().toString());
                }
                catch(Exception ex){
                    
                }
            }
            else if(comando.toLowerCase().compareTo("modfile")==0){
                try{
                    pFileSystem.modFile(pTokens.nextElement().toString(), pTokens.nextElement().toString());
                }
                catch(Exception ex){
                    
                }
            }
            else if(comando.toLowerCase().compareTo("verpropiedades") == 0){
                try{
                    pFileSystem.properties(pTokens.nextElement().toString());
                }
                catch(Exception ex){
                
                }
            }
            else if(comando.toLowerCase().compareTo("contfile") ==0){
                try{
                    pFileSystem.fileCont(pTokens.nextElement().toString());
                }
                catch(Exception ex){
                
                }
            }
            
            else if(comando.toLowerCase().compareTo("copy") == 0){
                try{
                    String tipo = pTokens.nextElement().toString();
                    
                    if(tipo.compareTo("-rv") == 0){
                        // copiar de archivo real a uno virtual
                        String rutaProcedencia = pTokens.nextElement().toString();
                        String rutaDestino = pTokens.nextElement().toString();
                        pFileSystem.copy(rutaProcedencia, rutaDestino, 1);
                    }
                    else if(tipo.compareTo("-vr") == 0){
                        // copiar de un archivo virtual a uno real
                        String rutaProcedencia = pTokens.nextElement().toString();
                        String rutaDestino = pTokens.nextElement().toString();
                        pFileSystem.copy(rutaProcedencia, rutaDestino, 2);
                    }
                    else{
                        // copiar de un archivo virtual a otro virtual
                        String rutaProcedencia = pTokens.nextElement().toString();
                        String rutaDestino = pTokens.nextElement().toString();
                        pFileSystem.move(rutaProcedencia,rutaDestino,false); // el false es para indicar que NO hay que borrar el directorio/archivo de origen
                        
                    }
                }
                catch(Exception ex){
                    
                }
            }
            else if(comando.toLowerCase().compareTo("mover") == 0){
                String rutaProcedencia = pTokens.nextElement().toString();
                String rutaDestino = pTokens.nextElement().toString();
                pFileSystem.move(rutaProcedencia,rutaDestino,true); // el true es para indicar que hay que borrar el directorio/archivo de origen
            }
            else if(comando.toLowerCase().compareTo("remove") == 0){
                pFileSystem.remove(pTokens.nextElement().toString());
            }
            else if(comando.toLowerCase().compareTo("find") == 0){
                pFileSystem.find(pTokens.nextElement().toString());
            }
            
            else if(comando.toLowerCase().compareTo("tree") == 0){
                pFileSystem.tree();
            }
            else if(comando.toLowerCase().compareTo("disk") == 0){
                pFileSystem.getDisk().imprimirInfoDisco();
            }
            else if(comando.toLowerCase().compareTo("help") == 0){
                pFileSystem.help();
            }
            else if(comando.toLowerCase().compareTo("exit") == 0){
                return true;
            }
            else{
                System.out.println("Comando invalido");
                //pFileSystem.getDisk().imprimirInfoDisco();
                //return false;
            }
        }
        return false;
    }
    
    
    
    public static void main(String args[]){
        System.out.println("Indique la ruta donde se almacenara el disco: \n");        
        String rutaDisco = leerEntradaString();
        
        FileSystem fs = new FileSystem();
        boolean ciclo = true;
        fs.help();
        while(ciclo){
            System.out.print(fs.getRutaActual()+" ");
            StringTokenizer opcion = new StringTokenizer(leerEntradaString(), " ");
            boolean resultado = analizarOpcion(opcion,fs);
            if(resultado){
                ciclo = false;
            }
            
            fs.getDisk().crearArchivoInfoDisco(rutaDisco);
        }
    }
}
