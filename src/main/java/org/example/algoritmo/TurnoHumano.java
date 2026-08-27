package org.example.algoritmo;

import org.example.objetos.Personaje;
import org.example.bd.*;

import java.util.List;

public class TurnoHumano {

    private String caracteristica;

    public Personaje turno(List<Personaje> personajes, String caracteristica, int respuesta) {

        this.caracteristica = caracteristica;

        if (respuesta == 0) {
            personajes.removeIf(
                    p -> !Preguntas.tieneCaracteristica(p, caracteristica)
            );
        } else {
            personajes.removeIf(
                    p -> Preguntas.tieneCaracteristica(p, caracteristica)
            );
        }

        if (personajes.size() == 1 && personajes.get(0).isImpostor()) {
            return personajes.get(0);
        }

        return null;
    }
}