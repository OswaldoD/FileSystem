/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesystem;

import java.util.Scanner;

/**
 *
 * @author Phoenix
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private String directorioActual;

    public static void main(String[] args) {
        // TODO code application logic here
        Main main = new Main();
    }

    public Main() {
        startApp();

    }

    public void startApp() {

        while (true) {
            String actualDirectory = "C:/directorio/"; // no se puede /
            System.out.print(actualDirectory + ">");

            String input = Scan();
            String[] parameters = input.split(" ");

            switch (parameters[0].toLowerCase()) {
                case "create":
                    create(parameters);
                    break;
                case "file":
                    file(parameters);
                    break;
                case "mkdir":
                    mkdir(parameters);
                    break;
                case "changedir":
                    changedir(parameters);
                    break;
                case "listdir":
                    listdir(parameters);
                    break;
                case "modfile":
                    modfile(parameters);
                    break;
                case "prop":
                    properties(parameters);
                    break;
                case "contfile":
                    contfile(parameters);
                    break;
                case "copy":
                    copy(parameters);
                    break;
                case "move":
                    move(parameters);
                    break;
                case "remove":
                    remove(parameters);
                    break;
                case "find":
                    find(parameters);
                    break;
                case "tree":
                    tree(parameters);
                    break;
                default:
                    // comando no encontrado o no válido
                    Errors(0);
                    break;
            }
        }
    }

    /**
     * Método encargado de los parametros para cear un disco virtual
     *
     * @param create <cantidad_sectores> <tamaño_sector>
     * @return ninguno
     */
    private void create(String[] parameters) {
        if (parameters.length == 3) {
            try {
                int p1 = Integer.parseInt(parameters[1]); //cantidad sectores
                int p2 = Integer.parseInt(parameters[2]); //tamaño sector

                System.out.println("Crear un disco virtual de " + p1 + " sectores, "
                        + p2 + " tamaño de cada sector");

            } catch (NumberFormatException e) {
                Errors(2);
            }
        } else {
            Errors(1);
        }
    }

    /**
     * Método encargado de los parametros para crear un archivo
     *
     * @param file <nombre> <extension>
     * @return ninguno
     * @code Después se solicita el contenido del archivo
     */
    private void file(String[] parameters) {
        if (parameters.length == 3) {
            String nombre, extension;
            nombre = parameters[1]; // nombre del archivo
            extension = parameters[2]; // extension
            System.out.println("Creando archivo " + nombre + " extensión "
                    + extension);
            System.out.println("Ingrese el contenido del archivo: ");

            String contenido = Scan(); // contenido
        } else {
            Errors(1);
        }
    }

    /**
     * Método para manejar la creación de un directorio
     *
     * @param mkdir <nombre_directorio>
     * @return ninguno
     */
    private void mkdir(String[] parameters) {
        if (parameters.length == 2) {
            String nombre = parameters[1]; //nombre nuevo directorio
            System.out.println("Create directorio " + nombre);
        } else {
            Errors(1);
        }
    }

    /**
     * Método para cambiar de directorio
     *
     * @param changedir <nombre_directorio>
     */
    private void changedir(String[] parameters) {
        if (parameters.length == 2) {
            String nombre = parameters[1];// nombre directorio a cambiar
            System.out.println("Moverse al directorio " + nombre);
        } else {
            Errors(1);
        }
    }

    /**
     * Método para listar todos los directorios. No recibe parámetros de ningún
     * tipo
     */
    private void listdir(String[] parameters) {
        if (parameters.length == 1) {
            System.out.println("Listando todos los archivos y directorios");
        } else {
            Errors(1);
        }
    }

    /**
     * Método para modificar el contenido de un archivo
     *
     * @param modfile <nombre_archivo>
     * @return ninguno
     */
    private void modfile(String[] parameters) {
        if (parameters.length == 2) {
            String nombre = parameters[1]; // nombre del archivo a modificar
            System.out.println("Modificar el archivo " + nombre);
        } else {
            Errors(1);
        }
    }

    /**
     * Método para ver las propiedades de un archivo
     *
     * @param prop <nombre_archivo>
     * @return ningunoS
     */
    private void properties(String[] parameters) {
        if (parameters.length == 2) {
            String nombre = parameters[1]; //nombre del archivo
            System.out.println("Propiedades del archivo " + nombre);
            System.out.println("Nombre: ");
            System.out.println("Extensiòn: ");
            System.out.println("Fecha de Creaciòn: ");
            System.out.println("Fecha de Modificación: ");
            System.out.println("Tamaño: ");
        } else {
            Errors(1);
        }
    }

    /**
     * Método que permite ver el contenido de un archivo
     *
     * @param contfile <nombre_archivo>
     * @return ninguno
     */
    private void contfile(String[] parameters) {
        if (parameters.length == 2) {
            String nombre = parameters[1]; //nombre del archivo
            System.out.println("Contenido del archivo " + nombre);
        } else {
            Errors(1);
        }
    }

    /**
     * Método para copiar un archivo
     *
     */
    private void copy(String[] parameters) {
        System.out.println(parameters[0]);
    }

    /**
     * Método para mover un archivo o un directorio
     *
     * @param move <nombre_archivo> <nombre_destino>
     * @param move <nombre_directorio> <nombre_destino>
     * @return ninguno
     */
    private void move(String[] parameters) {
        if (parameters.length == 3) {
            String name = parameters[1]; //nombre del archivo
            String destino = parameters[2]; //ruta de destino
            System.out.println("Mover " + name);
        } else {
            Errors(1);
        }
    }

    /**
     * Método para remover un archivo
     *
     * @param remove <nombre_archivo>
     * @return ninguno
     */
    private void remove(String[] parameters) {
        //  Debería de remover directorios?
        if (parameters.length == 2) {
            String name = parameters[1]; //nombre del archivo
            System.out.println("Remover el archivo " + name);
        } else {
            Errors(1);
        }
    }

    /**
     * Método para buscar un archivo o directorio
     *
     * @param find <string_busqueda>
     * @return ninguno
     */
    private void find(String[] parameters) {
        if (parameters.length == 2) {
            String name = parameters[1]; //string para buscar
            System.out.println("Buscar: " + name);
        } else {
            Errors(1);
        }
    }

    /**
     * Método que despliega todo el árbol del FileSystem
     *
     * @param tree
     * @return ninguno
     */
    private void tree(String[] parameters) {
        if (parameters.length == 1) {
            System.out.println("Estructura del FileSystem");
        } else {
            Errors(1);
        }
    }

    /**
     * Método para manejar la impresión de los errores en consola Estos son los
     * errores comunes.
     *
     */
    private void Errors(int error) {
        switch (error) {
            case 1:
                System.out.println("Error en input");
                break;
            case 2:
                System.out.println("Error en parámetros");
                break;
            default:
                System.out.println("Comando desconocido");
                break;
        }
    }

    private String Scan() {
        Scanner reader = new Scanner(System.in);
        String text = reader.nextLine();
        return text;
    }
}
