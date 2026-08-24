package org.example.bd;

import org.example.objetos.Personaje;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generador {

    public List<Personaje> generar() {

        List<Personaje> personajes = new ArrayList<>();

        personajes.add(new Personaje(1, "Juan", true,  false, true,  false, true,  false));
        personajes.add(new Personaje(2, "Pedro", true,  true,  false, true,  false, false));
        personajes.add(new Personaje(3, "Martín", true,  false, false, true,  true,  true));
        personajes.add(new Personaje(4, "Lucas", true,  true,  true,  false, false, true));
        personajes.add(new Personaje(5, "Mateo", true,  false, true,  true,  true,  false));

        personajes.add(new Personaje(6, "Santiago", true,  true,  false, false, true,  true));
        personajes.add(new Personaje(7, "Nicolás", true,  false, false, true,  false, false));
        personajes.add(new Personaje(8, "Tomás", true,  true,  true,  true,  true,  false));
        personajes.add(new Personaje(9, "Diego", true,  false, true,  false, false, true));
        personajes.add(new Personaje(10, "Sebastián", true, true, false, true, true, true));

        personajes.add(new Personaje(11, "Sofía", false, true,  false, false, true,  true));
        personajes.add(new Personaje(12, "Martina", false, false, true,  false, false, true));
        personajes.add(new Personaje(13, "Valentina", false, true, true,  false, true,  false));
        personajes.add(new Personaje(14, "Camila", false, false, false, false, true,  true));
        personajes.add(new Personaje(15, "Lucía", false, true,  false, false, false, true));

        personajes.add(new Personaje(16, "María", false, false, true,  false, true,  false));
        personajes.add(new Personaje(17, "Julieta", false, true,  true,  false, false, true));
        personajes.add(new Personaje(18, "Catalina", false, false, false, false, true,  true));
        personajes.add(new Personaje(19, "Agustina", false, true,  false, false, false, false));
        personajes.add(new Personaje(20, "Micaela", false, false, true,  false, true,  true));

        personajes.add(new Personaje(21, "Paula", false, true,  true,  false, false, false));
        personajes.add(new Personaje(22, "Florencia", false, false, false, false, true,  true));
        personajes.add(new Personaje(23, "Victoria", false, true,  false, false, true,  false));

        // Elegir el impostor aleatoriamente
        Random random = new Random();
        int posicionImpostor = random.nextInt(personajes.size());
        personajes.get(posicionImpostor).setImpostor(true);

        return personajes;
    }
}