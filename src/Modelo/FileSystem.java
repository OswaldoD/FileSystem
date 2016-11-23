/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controladores.ControllerFileSystem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 *
 * @author esporras
 */
public class FileSystem implements Funciones {
    
    
    private void crearDiscoFisico(){
        String ruta = "disco.txt";
        File archivo = new File(ruta);
        return;
    }
    
    
    
    public String getRutaActual() {
        return _RutaActual;
    }

    public void setRutaActual(String _RutaActual) {
        this._RutaActual = _RutaActual;
    }

    public FileSystem() {
        this._Raiz = new Directorio("Raiz","C:",0,"Estefanny",new Date(),null,null,0,null);
        this._Actual = this._Raiz;
        this._RutaActual = this._Actual.getUbicacion();
    }

    
    @Override
    public void setController(ControllerFileSystem pController) {
        this._Controller = pController;
    }

    
    @Override
    public void create(int pNumSectores, int pTamSector) {
        crearDiscoFisico();
        this._Disk = new DiscoVirtual(pNumSectores, pTamSector);
    }

    @Override
    public String file(String pContenido, String pExtension, String pNombre) {
        // lo primero que hacemos es verificar si el archivo existe en el directorio actual
        if(this._Actual.archivoExiste(pNombre, pExtension)){
            // Aqui seria chiva darle la opcion al usuario de si desea reemplazarlo
            return "El archivo ya se encuentra en el directorio";
        }
        else{
            // intentamos meter el archivo en disco, si no se puede entonces mandamos false, si se pudo entonces true
            // luego creamos el archivo en el directorio actual del file system
            ArrayList sectoresArchivo = this._Disk.agregarInfo(pContenido, pContenido.length());
            if(!sectoresArchivo.isEmpty()){
                // como el estado es true entonces creamos el archivo en el directorio
                this._Actual.getContenido().add(new Archivo(pContenido,pExtension,pNombre,this._RutaActual,pContenido.length(),"Estefanny",new Date(),null,sectoresArchivo,0));
                // actualizamos el tamano del directorio actual
                this._Actual.aumentarTamano(pContenido.length());
                return "Se agregó el archivo exitosamente";
            }
            else{
                return "El archivo no pudo ser agregado";
            }
        }
    }

    @Override
    public void mkdir(String pNombre) {
        if(this._Actual.directorioExiste(pNombre)){
            // si el directorio existe mostramos un mensaje de error
            System.out.println("La carpeta ya se encuentra en el directorio");
        }
        else{
            // en caso contrario lo agregamos al directorio actual
            this._Actual.getContenido().add(new Directorio(pNombre,this._RutaActual,0,"Kevin",new Date(),null,null,0,this._Actual));
        }
    }

    @Override
    public void cd(String pNombre) {
        if(pNombre.equals("..")){
            // significa que hay que subir en el directorio
            // ahora nada mas se procede a validar que no sea la raiz el directorio actual
            if(!(this._Actual.getPadre() == null)){
                int indice = this._RutaActual.indexOf("/"+this._Actual.getNombre());
                
                // actualizamos la ruta que mostramos en pantalla
                this._RutaActual = this._RutaActual.substring(0,indice);
                // actualizamos el directorio
                this._Actual = this._Actual.getPadre();
            }
            return;
        }
        // si llegamos a este punto es porque nos queremos meter en una carpeta
        for(Object obj:this._Actual.getContenido()){
            try{
                // intentamos castear el objeto actual a directorio
                Directorio dir;
                dir = (Directorio) obj;
                if(dir.getNombre().compareTo(pNombre) == 0){
                    /* si el nombre es igual al nombre que nos pasan por parametro entonces
                       actualizamos el directorio actual y tambien la ruta actual del file system
                    */
                    this._Actual = dir;
                    this._RutaActual = this._RutaActual + "/" + dir.getNombre();
                    return;
                }
            }
            catch(Exception ex){
                // no pasa nada si es archivo
            }
        }
        // si llega aqui es porque el nombre que ingresaron no correspondia al de ningun archivo
        System.out.println("No se encuentra el directorio");
    }
    
    @Override
    public void cambiarDIR(String pRuta){
        //regresar a la raiz
        Directorio inicio = this._Actual;
        while(inicio.getPadre()!= null){
            inicio = inicio.getPadre();
            cd("..");
        }
        
        StringTokenizer tokensRuta = new StringTokenizer(pRuta,"/");

        while(tokensRuta.hasMoreTokens()){
            String directorioDestino = tokensRuta.nextElement().toString();
                cd(directorioDestino);
        }
    }

    @Override
    public void listDir() {
        // listamos el directorio
        this._Actual.listarDirectorio();
    }

    @Override
    public void modFile(String pNombre, String pContenido) {
        for(Object obj: this._Actual.getContenido()){
            // buscamos el archivo
            try{
                Archivo arc = (Archivo)obj;
                if(arc.getNombre().compareTo(pNombre) == 0){
                    // Lo primero que hacemos es guardar la info actual del archivo
                    String contenidoActual = arc.getContenido();
                    // Borramos la info del archivo en el disco
                    this._Disk.borrarInfoSectores(arc.getUbicacionLogica());
                    // Ahora lo que vamos a hacer es validar que haya espacio para el nuevo contenido del archivo
                    if(this._Disk.isTamanoArchivoValido(pContenido.length())){
                        // si el tamano del archivo es valido entonces lo introducimos en disco y actualizamos
                        // algunas cosas del archivo como la ubicacion logica, contenido, ultima modificacion y el tamano
                        ArrayList sectoresArchivo = this._Disk.agregarInfo(pContenido, pContenido.length());
                        arc.setUbicacionLogica(sectoresArchivo);
                        arc.setContenido(pContenido);
                        arc.setUltimaModificacion(new Date());
                        arc.setTamaño(pContenido.length());
                    }
                    else{
                        /* es porque el contenido no puede ser agregado
                           entonces lo que hacemos es mostrar un mensaje
                           de error, y ponemos la info vieja del archivo  
                        */
                        ArrayList sectoresArchivo = this._Disk.agregarInfo(contenidoActual, contenidoActual.length());
                        arc.setUbicacionLogica(sectoresArchivo);
                    }
                }
            }
            catch(Exception ex){
            
            }
        }
    }

    @Override
    public void properties(String pNombre) {
        for(Object obj: this._Actual.getContenido()){
            try{
                // vemos si el obje es un archivo
                Archivo arc = (Archivo)obj;
                if(arc.getNombre().compareTo(pNombre) == 0){
                    // si el nombre es igual entonces mostramos las propiedades del archivo
                    // y terminamos la busqueda
                    System.out.println("\n*******************************");
                    System.out.println("****Propiedades del Archivo****");
                    System.out.println("*******************************");
                    System.out.println(arc.verPropiedades()+"\n");
                    return;
                }
            }
            catch(Exception ex){
                
            }
        }
        // si llega a este punto es porque no se encontro el archivo
        System.out.println("El archivo no se encuentra en el directorio actual");
    }

    @Override
    public void fileCont(String pNombre) {
        for(Object obj: this._Actual.getContenido()){
            try{
                // vemos si el obj es un archivo
                Archivo arc = (Archivo)obj;
                if(arc.getNombre().compareTo(pNombre) == 0){
                    // si el nombre es igual al que nos pasan
                    // entonces mostramos el contenido
                    // y terminamos la busqueda
                    System.out.println(arc.getContenido());
                    return;
                }
            }
            catch(Exception ex){
                
            }
        }
        // si llega a este punto es porque el archivo no se encontraba
        System.out.println("El archivo no se encuentra en el directorio actual");
    }

    @Override
    public void copy(String pRutaProcedencia, String pRutaDestino, int pTipo) {
        if(pTipo == 1){ //real - virtual
            copyRV(pRutaProcedencia,pRutaDestino);
        }
        else if(pTipo == 2){ //virtual - real
            copyVR(pRutaProcedencia,pRutaDestino);
        }
        else{ //virtual - virtual 
            move(pRutaProcedencia,pRutaDestino,false);
        }
    }
    
    private void copyVR(String pRutaProcedencia, String pRutaDestino){
        //crear las rutas correctamente y ademas obtener el nombre del archivo
        StringTokenizer tokensRutaProcedencia = new StringTokenizer(pRutaProcedencia,"/");
        String nombreProcedencia = "";
        //obtiene el nombre del archivo (el ultimo token)
        while(tokensRutaProcedencia.hasMoreTokens()){
            nombreProcedencia = tokensRutaProcedencia.nextElement().toString();
        }
        //System.out.println(nombreProcedencia);
        pRutaProcedencia = pRutaProcedencia.substring(0,pRutaProcedencia.indexOf("/"+nombreProcedencia)); //elimina el nombre de la ruta
        //System.out.println(pRutaProcedencia);
        
        
        //volver a generar ruta sin el nombre del archivos o directorio
        tokensRutaProcedencia = new StringTokenizer(pRutaProcedencia,"/");
        
        String extension = "";
        String nombre = "";
        extension = nombreProcedencia.substring(nombreProcedencia.indexOf("."));
        nombre = nombreProcedencia.substring(0,nombreProcedencia.indexOf("."));
        //    System.out.println("es archivo");
        
        // Lo primero que hacemos es navegar desde el directorio actual hasta la raiz
        Directorio inicio = this._Actual;
        while(inicio.getPadre()!= null){
            inicio = inicio.getPadre();
            cd("..");
        }
        
        while(tokensRutaProcedencia.hasMoreTokens()){
            String directorioProcedencia = tokensRutaProcedencia.nextElement().toString();
                cd(directorioProcedencia);
               
        }
        //System.out.println("Llego al final del directorio origen");
        
        String contenidoArchivo = "";
        for(Object obj: this._Actual.getContenido()){
            try{
                // vemos si el obj es un archivo
                Archivo arc = (Archivo)obj;
                if(arc.getNombre().compareTo(nombre) == 0){
                    // si el nombre es igual al que nos pasan
                    // entonces mostramos el contenido
                    // y terminamos la busqueda
                    contenidoArchivo = arc.getContenido();
                    break;
                }
            }
            catch(Exception ex){
                
            }
        }
        
        
        //crear archivo real(fisico)
        File archivo=new File(pRutaDestino);
        try{
            FileWriter escribir = new FileWriter(archivo);
            BufferedWriter objetoarchivo = new BufferedWriter(escribir);
            PrintWriter frase = new PrintWriter(objetoarchivo);
            frase.write(contenidoArchivo);
            //frase.append(contenidoArchivo);
            frase.close();
            objetoarchivo.close();
        } catch(IOException ioe){
            ioe.printStackTrace();
        }    
    }
    
    private void copyRV(String pRutaProcedencia, String pRutaDestino){
        String contenidoArchivoReal = "";
        File archivo=new File(pRutaProcedencia);
        //leer archivo real(fisico)
        try{
            FileReader leer=new FileReader(archivo);
            BufferedReader Objetoleer=new BufferedReader(leer);
            String cadena;
            while((cadena=Objetoleer.readLine())!=null){
                contenidoArchivoReal += cadena;
            }
            //System.out.println(contenidoArchivoReal);
            //termina de leer
            
            //Obtiene el nombre completo del archivo que se desea crear
            StringTokenizer tokensRutaDestino = new StringTokenizer(pRutaDestino, "/");
            String nombreDestino = "";
            //obtiene el nombre del archivo (el ultimo token)
            while(tokensRutaDestino.hasMoreTokens()){
                nombreDestino = tokensRutaDestino.nextElement().toString();
            }
            //obtiene el nombre y la extension de la ruta destuno
            String extension = nombreDestino.substring(nombreDestino.indexOf("."));
            String nombre = nombreDestino.substring(0,nombreDestino.indexOf("."));
            
            //System.out.println(nombreDestino);
            pRutaDestino = pRutaDestino.substring(0,pRutaDestino.indexOf("/"+nombreDestino)); //elimina el nombre de la ruta
            //System.out.println(pRutaDestino);
            tokensRutaDestino = new StringTokenizer(pRutaDestino, "/");
            
            //regresar a la raiz
            Directorio inicio = this._Actual;
            while(inicio.getPadre()!= null){
                inicio = inicio.getPadre();
                cd("..");
            }


            while(tokensRutaDestino.hasMoreTokens()){
                String directorioDestino = tokensRutaDestino.nextElement().toString();
                    cd(directorioDestino);
            }
            //System.out.println("Llego al final del directorio origen");

            file(contenidoArchivoReal, extension, nombre);
            
        }
        catch(IOException e){
        }
    }

    @Override
    public void move(String pRutaProcedencia, String pRutaDestino, boolean pBorrar) {
        
        //crear las rutas correctamente y ademas obtener el nombre del archivo
        StringTokenizer tokensRutaProcedencia = new StringTokenizer(pRutaProcedencia,"/");
        String nombreProcedencia = "";
        //obtiene el nombre del archivo (el ultimo token)
        while(tokensRutaProcedencia.hasMoreTokens()){
            nombreProcedencia = tokensRutaProcedencia.nextElement().toString();
        }
        //System.out.println(nombreProcedencia);
        pRutaProcedencia = pRutaProcedencia.substring(0,pRutaProcedencia.indexOf("/"+nombreProcedencia)); //elimina el nombre de la ruta
        //System.out.println(pRutaProcedencia);
        
        
        
        StringTokenizer tokensRutaDestino = new StringTokenizer(pRutaDestino, "/");
        String nombreDestino = "";
        //obtiene el nombre del archivo (el ultimo token)
        while(tokensRutaDestino.hasMoreTokens()){
            nombreDestino = tokensRutaDestino.nextElement().toString();
        }
        //System.out.println(nombreDestino);
        pRutaDestino = pRutaDestino.substring(0,pRutaDestino.indexOf("/"+nombreDestino)); //elimina el nombre de la ruta
        //System.out.println(pRutaDestino);
        
        
        //volver a generar ruta sin el nombre del archivos o directorio
        tokensRutaProcedencia = new StringTokenizer(pRutaProcedencia,"/");
        tokensRutaDestino = new StringTokenizer(pRutaDestino, "/");
        
        String extension = "";
        String nombre = "";
        if(nombreProcedencia.contains(".")){
            extension = nombreProcedencia.substring(nombreProcedencia.indexOf("."));
            nombre = nombreProcedencia.substring(0,nombreProcedencia.indexOf("."));
        //    System.out.println("es archivo");
        }
        else{
            nombre = nombreProcedencia;
        //    System.out.println("es directorio");
        }
        
        // Lo primero que hacemos es navegar desde el directorio actual hasta la raiz
        Directorio inicio = this._Actual;
        Object objetoCopiar;
        while(inicio.getPadre()!= null){
            inicio = inicio.getPadre();
        }
        
        
        
        while(tokensRutaProcedencia.hasMoreTokens()){
            String directorioProcedencia = tokensRutaProcedencia.nextElement().toString();
                cd(directorioProcedencia);
               
        }
        //System.out.println("Llego al final del directorio origen");
        
        String contenidoArchivo = "";
        for(Object obj: this._Actual.getContenido()){
            try{
                // vemos si el obj es un archivo
                Archivo arc = (Archivo)obj;
                if(arc.getNombre().compareTo(nombre) == 0){
                    // si el nombre es igual al que nos pasan
                    // entonces mostramos el contenido
                    // y terminamos la busqueda
                    contenidoArchivo = arc.getContenido();
                    break;
                }
            }
            catch(Exception ex){
                
            }
        }
        //System.out.println(contenidoArchivo);
        if(pBorrar){
            remove(nombre);
        }
        
        
        //para el move nuevo :)
        extension = "";
        nombre = "";
        if(nombreDestino.contains(".")){
            extension = nombreDestino.substring(nombreDestino.indexOf("."));
            nombre = nombreDestino.substring(0,nombreDestino.indexOf("."));
        //    System.out.println("es archivo");
        }
        else{
            nombre = nombreDestino;
        //    System.out.println("es directorio");
        }
        
        //regresar a la raiz
        inicio = this._Actual;
        while(inicio.getPadre()!= null){
            inicio = inicio.getPadre();
            cd("..");
        }
        
        
        while(tokensRutaDestino.hasMoreTokens()){
            String directorioDestino = tokensRutaDestino.nextElement().toString();
                cd(directorioDestino);
        }
        //System.out.println("Llego al final del directorio origen");
        
        if(contenidoArchivo == ""){ //es por que no tiene contenido entonces es un directorio
            mkdir(nombre);
        }
        else{ //es por que es un archivo
            file(contenidoArchivo, extension, nombre);
        }
        
    }
    
    @Override
    public void remove(String pNombre) {
        // llamamos a la funcion auxiliar que recibe el nombre
        // del archivo y el contenido actual del directorio, que es una lista
        remove_aux(pNombre, this._Actual.getContenido());
    }
    
    public void remove_aux(String pNombre, ContenidoDirectorio contenido){
        Iterator<Object> elementos = contenido.iterator();
        while(elementos.hasNext()){
            Object obj = elementos.next();
            try{
                // vemos si en el contenido actual es un archivo
                Archivo arc = (Archivo)obj;
                if(arc.getNombre().compareTo(pNombre) == 0){
                    // si el nombre coincide entonces lo borramos
                    // ademas borramos los sectores que usa en el disco
                    // terminamos la busqueda
                    elementos.remove();
                    this._Disk.borrarInfoSectores(arc.getUbicacionLogica());
                    return;
                }
                
            }
            catch(Exception ex){
                // es un directorio
                Directorio dir = (Directorio)obj;
                if(dir.getNombre().compareTo(pNombre) == 0){
                    // si el nombre es igual entonces lo que hay que hacer es un borrado en cascada
                    ArrayList sectores = dir.borrarDirectorio();
                    elementos.remove();
                    // borramos los sectores de cada uno de los archivos que se encontraban en el directorio
                    this._Disk.borrarInfoSectores(sectores);
                    // terminamos la busqueda
                    return;
                }
            }
        }
        // si llega a este punto es porque no se encontro el nombre que ingresaron
        System.out.println("El archivo o directorio no se encuentra");
    }

    @Override
    public void find(String pNombre) {
        String extension = "";
        String nombre = "";
        int tipoBusqueda = 0;
        Directorio nuevo = this._Actual;
        // nos ubicamos en la raiz del file system
        while(nuevo.getPadre() != null){
            nuevo = nuevo.getPadre();
        }
        // existen 3 tipos de busqueda
        /*
        1- *.extension
        2- nombreArchivo.extension
        3- nombreDirectorio
        */
        
        if(pNombre.startsWith("*")){
            extension = pNombre.substring(pNombre.indexOf("*")+1);
            tipoBusqueda = 1;
        }
        else if(pNombre.indexOf(".") == -1){
            tipoBusqueda = 3;
        }
        else{
            extension = pNombre.substring(pNombre.indexOf("."));
            nombre = pNombre.substring(0,pNombre.indexOf("."));
            tipoBusqueda = 2; 
        }
        
        if(tipoBusqueda == 1){
            // significa que hay que buscar todos los archivos que sean de esa extension
            find_aux(extension,nuevo.getContenido());
        }
        else if(tipoBusqueda == 2){
            // significa que hay que buscar un archivo por su nombre y extension
            find_aux(nombre,extension,nuevo.getContenido());
        }
        else{
            // significa que estamos buscando un directorio
            find_aux(pNombre,null,nuevo.getContenido());
        }
    }
    
    public void find_aux(String pExtension, ContenidoDirectorio pContenido){
        for(Object obj:pContenido){
            try{
                // vemos si el obj actual es un archivo
                Archivo arc = (Archivo)obj;
                if(arc.getExtension().compareTo(pExtension) == 0){
                    // si coinciden los nombres entonces imprimimos la info que solicitan
                    System.out.println(arc.getUbicacion()+"/"+arc.getNombre()+arc.getExtension());
                }
            }
            catch(Exception ex){
                // si es un directorio entonces lo que hacemos es llamar recursivamente a 
                // esta funcion pero con el contenido del directorio del objeto actual
                Directorio dir = (Directorio)obj;
                find_aux(pExtension,dir.getContenido());
            }
        }
    }
    
    public void find_aux(String pNombre, String pExtension, ContenidoDirectorio pContenido){
        for(Object obj:pContenido){
            try{
                // vemos si es un archivo
                Archivo arc = (Archivo)obj;
                if(arc.getExtension().compareTo(pExtension) == 0 && arc.getNombre().compareTo(pNombre) == 0){
                    // si coinciden los nombres y la extension entonces imprimimos la info
                    System.out.println(arc.getUbicacion()+"/"+arc.getNombre()+arc.getExtension());
                }
            }
            catch(Exception ex){
                // sino entonces era un directorio
                Directorio dir = (Directorio)obj;
                if(dir.getNombre().compareTo(pNombre) == 0)
                    // si coincide el nombre del directorio entonces imprimimos la info
                    System.out.println(dir.getUbicacion()+"/" +dir.getNombre());
                // buscamos recursivamente en este directorio para ver si hay mas archivos o carpetas
                // con el mismo nombre
                //find_aux(pNombre,pExtension,dir.getContenido());
            }
        }
    }

    @Override
    public void tree() {
        Directorio nuevo = this._Actual;
        // nos ubicamos en la raiz del file system
        while(nuevo.getPadre() != null){
            nuevo = nuevo.getPadre();
        }
        // imprimimos la ubicacion de la raiz
        System.out.println(nuevo.getUbicacion());
        // llamamos a la funcion auxiliar que recibe la raiz del file system y un numero
        // dicho numero es usado para la cantidad de tabs de la impresion del arbol
        tree_aux(nuevo,1);
    }
    
    public void tree_aux(Directorio pDir, int pCantidadNewLines){
        String saltosLinea = "\n";
        String tabs = "";
        int lineas = 0;
        // formamos la cantidad de tabs
        while(lineas < pCantidadNewLines){
            tabs += "\t";
            lineas ++;
        }
        // para cada uno de los objetos
        for(Object obj:pDir.getContenido()){
            try{
                // vemos si es un archivo
                Archivo arc = (Archivo)obj;
                // imprimimos el nombre y la extension
                System.out.print(tabs + arc.getNombre()+arc.getExtension() +saltosLinea);
            }
            catch(Exception ex){
                // si es un directorio
                Directorio dir = (Directorio)obj;
                // imprimimos la info
                System.out.print(tabs + dir.getNombre()+saltosLinea);
                // llamamos recursivamente para que genere el arbol de ese directorio
                tree_aux(dir, pCantidadNewLines+1);
            }
        }
    }

    public DiscoVirtual getDisk() {
        return _Disk;
    }
    
    public void help(){
        String ayuda = "\n**********************************\n"+
                       "**** Guía en el uso de comandos **** \n " +
                       "**********************************\n"+  
                       "A continuación se enumeran todos los comandos y su descripción de uso:\n\n"+
                       "1- CREATE cantidadSectores tamanoSector \n"+
                       "2- FILE nombreArchivo extensionArchivo contenidoArchivo \n"+
                       "3- MKDIR nombreDirectorio\n" + 
                       "4- cd nombreDirectorio ó .. \n"  +
                       "5- ListarDIR \n" +
                       "6- ModFILE nombreArchivo(sin extensión) nuevoContenido\n"+
                       "7- VerPropiedades nombreArchivo(sin extensión)\n" +
                       "8- ContFile nombreArchivo(sin extensión)\n" + 
                       "9- CoPY tipo rutaOrigen rutaDestino \n"+
                       "\nNota: *tipo = -rv, -vv, -vr\n" +
                       "        *Los nombres de los archivos en las rutas deben incluir la extensión\n"+
                       "10- MoVer rutaOrigen rutaDestino \n" +
                       "11- ReMove nombreArchivo(sin extensión) o nombreDirectorio\n"+
                       "12- FIND nombreArchivo.extension o nombreDirectorio. \n" +
                       "13- TREE \n" +
                       "14- cambiarDIR \n" +
                       "15- DISK \n"+
                       "16- help\n"+
                       "17- exit\n"
                        ;
        System.out.println(ayuda);
    }
    
    
    
    
    private String _RutaActual;
    private ControllerFileSystem _Controller;
    private Directorio _Raiz;
    private Directorio _Actual;
    private DiscoVirtual _Disk;
    
}
