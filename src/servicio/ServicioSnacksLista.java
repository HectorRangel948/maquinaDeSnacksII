package servicio;

import java.util.ArrayList;
import java.util.List;
import dominio.Snack;

public class ServicioSnacksLista implements IServicioSnacks{

    private static final List<Snack> snacks;

    //Bloque static inicializador
    static {
        snacks = new ArrayList<>();
        snacks.add(new Snack("Papitas", 20.00));
        snacks.add(new Snack("Refresco", 20.00));
        snacks.add(new Snack("Sandwich", 30.00));
    }

    public void agregarSnack(Snack snack) {
        snacks.add(snack);
    }

    public void mostrarSnacks() {
        var inventarioSnacks = "";
        for (Snack snack : snacks) {
            inventarioSnacks += snack.toString() + "\n";
        }
        System.out.println("---Snacks en el inventario---");
        System.out.println(inventarioSnacks);

    }
    
    public List<Snack> getSnacks(){
        return ServicioSnacksLista.snacks;
    }
    
}
