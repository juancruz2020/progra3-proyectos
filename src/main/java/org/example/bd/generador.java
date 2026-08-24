package org.example.bd;

import org.example.objetos.personaje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class generador {

    ArrayList<String> nombres = new ArrayList<>(Arrays.asList(
            "Juan", "Pedro", "Martín", "Lucas", "Mateo",
            "Santiago", "Nicolás", "Tomás", "Diego", "Sebastián",
            "Alejandro", "Facundo", "Gabriel", "Franco", "Agustín",
            "Joaquín", "Bruno", "Lautaro", "Matías", "Federico",
            "Sofía", "Martina", "Valentina", "Camila", "Lucía",
            "María", "Julieta", "Catalina", "Agustina", "Micaela",
            "Paula", "Florencia", "Victoria", "Emilia", "Antonella",
            "Carolina", "Daniela", "Natalia", "Rocío", "Belén",
            "Milagros", "Carla", "Abril", "Clara", "Renata",
            "Josefina", "Guadalupe", "Zoe", "Malena", "Delfina"
    ));

    Random random = new Random();

    public List<personaje> generar() {

        List<personaje> personajes = new ArrayList<>();

        for (int i = 1; i <= 22; i++) {

            // seleccion de nombre
            int posicion = random.nextInt(nombres.size());
            String nombre = nombres.get(posicion);
            nombres.remove(posicion);

            // Crear personaje
            personaje p = new personaje(i, nombre, random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean());
            personajes.add(p);
        }

        return personajes;
    }
}