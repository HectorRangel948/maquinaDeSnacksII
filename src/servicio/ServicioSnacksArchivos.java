package servicio;

import dominio.Snack;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServicioSnacksArchivos implements IServicioSnacks{
    
    private final String NOMBRE_ARCHIVO = "snacks.txt";
    //Crear lista de snacks
    private List<Snack> snacks = new ArrayList<>();
    
    public ServicioSnacksArchivos(){
        //Creamos el archivo si no existe
        var archivo = new File(NOMBRE_ARCHIVO);
        var existe  = false;
        try{
            existe = archivo.exists();
            if(existe){
                this.snacks = obtenerSnacks();
            }
            else{
                var salida = new PrintWriter(new FileWriter(archivo));
                salida.close(); //Se guarda el archivo en disco
                System.out.println("Se ha creado el archivo");
            }
        }catch(Exception e){System.out.println("Error al crear el archivo: " +e.getMessage());}
        
        //Si no existe el archivo cargamos algunos elementos de manera inicial
        if(!existe){
            cargarSnacksIniciales();
        }
    }
    
     public List<Snack> obtenerSnacks(){
        var snacks = new ArrayList<Snack>();
        try{
            List<String> lineas= Files.readAllLines(Paths.get(NOMBRE_ARCHIVO));
            for(String linea : lineas){
                String[] lineaSnack = linea.split(",");
                var idSnack = lineaSnack[0];
                var nombreSnack = lineaSnack[1];
                var precioSnack = Double.parseDouble(lineaSnack[2]);
                
                Snack snack = new Snack(nombreSnack, precioSnack);
                snacks.add(snack); //Agregamos el snack leído a la lista
            }
        
        }catch(Exception e){System.out.println("Error al leer archivo de snacks: " + e.getMessage());
        e.printStackTrace();
        }
        return snacks;
    }
    
    private void cargarSnacksIniciales(){
        this.agregarSnack(new Snack("Papas", 20.00));
        this.agregarSnack(new Snack("Refresco", 20.00));
        this.agregarSnack(new Snack("Sandwich", 20.00));
    }

    @Override
    public void agregarSnack(Snack snack) {
        //Agregamos el nuevo snack
        //Se guarda el snack en la lista
        this.snacks.add(snack);
        //Guardamos el nuevo snack en el archivo
        this.agregarSnackArchivo(snack);
    }
    
    public void agregarSnackArchivo(Snack snack){
        boolean anexar = false;
        var archivo = new File(NOMBRE_ARCHIVO);
        try{
            anexar = archivo.exists();
            var salida = new PrintWriter(new FileWriter(archivo, anexar));
            salida.println(snack.escribirSnack());
            salida.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void mostrarSnacks() {
        System.out.println("---Snacks en el inventario---");
        var inventarioSnacks = "";
        for(var snack : this.snacks){
            inventarioSnacks += snack.toString() + "\n";
        }
        System.out.println(inventarioSnacks);
    }

    @Override
    public List<Snack> getSnacks() {
        return this.snacks;
    }
}
