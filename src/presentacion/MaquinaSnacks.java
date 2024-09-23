package presentacion;

import java.util.*;
import dominio.Snack;
import servicio.IServicioSnacks;
import servicio.ServicioSnacksArchivos;
import servicio.ServicioSnacksLista;

public class MaquinaSnacks {

    public static void main(String[] args) {
        maquinaSnacks();
    }

    public static void maquinaSnacks() {
        var salir = false;
        Scanner consola = new Scanner(System.in);
        
        //Se crea el objeto para obtener el servicio de snacks (lista)
        IServicioSnacks servicioSnacks = new ServicioSnacksArchivos();

        //Lista de productos de tipo snack
        List<Snack> productos = new ArrayList<>();
        System.out.println("***Máquina de Snacks***");
        servicioSnacks.mostrarSnacks();
        while (!salir) { 
            try {
                var opcion = mostrarMenu(consola);
                salir = ejecutarOpciones(opcion, consola, productos, servicioSnacks);
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            } finally {
                System.out.println();
            } //Imprime un salto de línea con cada iteración}

        }
    }
    
    private static int mostrarMenu(Scanner consola){
        System.out.println("""
                           Menú:
                           1.-Comprar snack
                           2.-Mostrar ticket
                           3.-Agregar nuevo snack
                           4.-Mostrar inventario de snacks
                           5.-Salir
                           
                           Elige una opción:\s
                           """);
        return Integer.parseInt(consola.nextLine());
    }
    
    private static boolean ejecutarOpciones(int opcion, Scanner consola, List<Snack> productos,  IServicioSnacks servicioSnacks){
        var salir= false;
            switch(opcion){
                case 1-> comprarSnack(consola,productos, servicioSnacks);
                case 2-> mostrarTicket(productos);
                case 3-> agregarSnack(consola, servicioSnacks);
                case 4-> listarInventarioSnacks(servicioSnacks);
                case 5-> {System.out.println("Hasta pronto"); salir=true;}
                default -> System.out.println("Opción inválida");
            }
        return salir;
    }
    
    private static void listarInventarioSnacks(IServicioSnacks servicioSnacks){
        servicioSnacks.mostrarSnacks();
    }
    
    private static void comprarSnack(Scanner consola, List<Snack> productos, IServicioSnacks servicioSnacks){
        System.out.print("¿Qué snack quieres comprar? (id): ");
        var idSnack = Integer.parseInt(consola.nextLine());
        //Validar que el snack existe en la lista de snacks
        var snackEncontrado = false;
        for(Snack snack: servicioSnacks.getSnacks()){
            if(snack.getIdSnack()==idSnack){
                //Se agrega el snack a la lista de productos
                productos.add(snack);
                System.out.println("Snack agregado: " + snack);
                snackEncontrado=true;
                break;
            }
        }
        if(!snackEncontrado){
            System.out.println("No se encontró el snack con id: " + idSnack);
        }
    }
    
    private static void mostrarTicket(List<Snack> productos){
        var ticket = "*** Ticket de venta ***";
        var total = 0.0;
        for(Snack producto : productos){
            ticket+="\n\t-" + producto.getNombre() + " $"+producto.getPrecio();
            total += producto.getPrecio();
        }
        ticket+="\n\tTotal ->$"+ total;
        System.out.println(ticket);
    }
    
    private static void agregarSnack(Scanner consola, IServicioSnacks servicioSnacks){
        Snack nuevoSnack = new Snack();
        System.out.print("Nombre del nuevo snack: ");
        nuevoSnack.setNombre(consola.nextLine());
        System.out.print("Precio del nuevo snack: ");
        nuevoSnack.setPrecio(Double.parseDouble(consola.nextLine()));
        servicioSnacks.agregarSnack(nuevoSnack);
        System.out.println("Se agregó con éxito el snack: " + nuevoSnack.toString());
        servicioSnacks.mostrarSnacks();
    }
}
