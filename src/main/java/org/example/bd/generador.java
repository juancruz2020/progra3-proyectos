package org.example.bd;

import org.example.objetos.personaje;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class generador {

    public List<personaje> generar() {

        List<personaje> personajes = new ArrayList<>();

        personajes.add(new personaje(1, "Juan", true,  false, true,  false, true,  false));
        personajes.add(new personaje(2, "Pedro", true,  true,  false, true,  false, false));
        personajes.add(new personaje(3, "Martín", true,  false, false, true,  true,  true));
        personajes.add(new personaje(4, "Lucas", true,  true,  true,  false, false, true));
        personajes.add(new personaje(5, "Mateo", true,  false, true,  true,  true,  false));

        personajes.add(new personaje(6, "Santiago", true,  true,  false, false, true,  true));
        personajes.add(new personaje(7, "Nicolás", true,  false, false, true,  false, false));
        personajes.add(new personaje(8, "Tomás", true,  true,  true,  true,  true,  false));
        personajes.add(new personaje(9, "Diego", true,  false, true,  false, false, true));
        personajes.add(new personaje(10, "Sebastián", true, true, false, true, true, true));

        personajes.add(new personaje(11, "Sofía", false, true,  false, false, true,  true));
        personajes.add(new personaje(12, "Martina", false, false, true,  false, false, true));
        personajes.add(new personaje(13, "Valentina", false, true, true,  false, true,  false));
        personajes.add(new personaje(14, "Camila", false, false, false, false, true,  true));
        personajes.add(new personaje(15, "Lucía", false, true,  false, false, false, true));

        personajes.add(new personaje(16, "María", false, false, true,  false, true,  false));
        personajes.add(new personaje(17, "Julieta", false, true,  true,  false, false, true));
        personajes.add(new personaje(18, "Catalina", false, false, false, false, true,  true));
        personajes.add(new personaje(19, "Agustina", false, true,  false, false, false, false));
        personajes.add(new personaje(20, "Micaela", false, false, true,  false, true,  true));

        personajes.add(new personaje(21, "Paula", false, true,  true,  false, false, false));
        personajes.add(new personaje(22, "Florencia", false, false, false, false, true,  true));
        personajes.add(new personaje(23, "Victoria", false, true,  false, false, true,  false));

        // Elegir el impostor aleatoriamente
        Random random = new Random();
        int posicionImpostor = random.nextInt(personajes.size());
        personajes.get(posicionImpostor).setImpostor(true);

        return personajes;
    }
}