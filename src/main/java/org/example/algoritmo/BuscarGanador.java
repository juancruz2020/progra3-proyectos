package org.example.algoritmo;

import org.example.algoritmo.*;
import org.example.objetos.*;
import java.util.List;


public class BuscarGanador{
    
    public Personaje buscarImpostor(List<Personaje> personajes) {

    if (personajes.size() == 1) {
        if (personajes.get(0).isImpostor()) {
            return personajes.get(0);
        }
        return null;
    }

    int medio = personajes.size() / 2;

    List<Personaje> izquierda = personajes.subList(0, medio);
    List<Personaje> derecha = personajes.subList(medio, personajes.size());

    Personaje resultado = buscarImpostor(izquierda);

    if (resultado != null) {
        return resultado;
    }

    return buscarImpostor(derecha);
    }
}