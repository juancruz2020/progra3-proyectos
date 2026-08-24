package org.example.algoritmo;

import org.example.objetos.Caracteristica;
import org.example.objetos.Personaje;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PonderadoCaracteristicas {

    private Map<String, List<Caracteristica>> ponderaciones = new HashMap<>();

    public PonderadoCaracteristicas() {

        ponderaciones.put("genero", new ArrayList<>());
        ponderaciones.put("anteojos", new ArrayList<>());
        ponderaciones.put("sombrero", new ArrayList<>());
        ponderaciones.put("barba", new ArrayList<>());
        ponderaciones.put("sonrisa", new ArrayList<>());
        ponderaciones.put("pelo_largo", new ArrayList<>());

        // Posición 0 = true
        // Posición 1 = false
        for (List<Caracteristica> lista : ponderaciones.values()) {
            lista.add(new Caracteristica(0, false));
            lista.add(new Caracteristica(0, false));
        }
    }

    public Map<String, List<Caracteristica>> que_pregunto(List<Personaje> personajes) {

        for (Personaje personaje : personajes) {

            if (personaje.isGenero()) {
                ponderaciones.get("genero").get(0).setCantidad(
                        ponderaciones.get("genero").get(0).getCantidad() + 1
                );
            } else {
                ponderaciones.get("genero").get(1).setCantidad(
                        ponderaciones.get("genero").get(1).getCantidad() + 1
                );
            }

            if (personaje.isAnteojos()) {
                ponderaciones.get("anteojos").get(0).setCantidad(
                        ponderaciones.get("anteojos").get(0).getCantidad() + 1
                );
            } else {
                ponderaciones.get("anteojos").get(1).setCantidad(
                        ponderaciones.get("anteojos").get(1).getCantidad() + 1
                );
            }

            if (personaje.isSombrero()) {
                ponderaciones.get("sombrero").get(0).setCantidad(
                        ponderaciones.get("sombrero").get(0).getCantidad() + 1
                );
            } else {
                ponderaciones.get("sombrero").get(1).setCantidad(
                        ponderaciones.get("sombrero").get(1).getCantidad() + 1
                );
            }

            if (personaje.isBarba()) {
                ponderaciones.get("barba").get(0).setCantidad(
                        ponderaciones.get("barba").get(0).getCantidad() + 1
                );
            } else {
                ponderaciones.get("barba").get(1).setCantidad(
                        ponderaciones.get("barba").get(1).getCantidad() + 1
                );
            }

            if (personaje.isSonrisa()) {
                ponderaciones.get("sonrisa").get(0).setCantidad(
                        ponderaciones.get("sonrisa").get(0).getCantidad() + 1
                );
            } else {
                ponderaciones.get("sonrisa").get(1).setCantidad(
                        ponderaciones.get("sonrisa").get(1).getCantidad() + 1
                );
            }

            if (personaje.isPelo_largo()) {
                ponderaciones.get("pelo_largo").get(0).setCantidad(
                        ponderaciones.get("pelo_largo").get(0).getCantidad() + 1
                );
            } else {
                ponderaciones.get("pelo_largo").get(1).setCantidad(
                        ponderaciones.get("pelo_largo").get(1).getCantidad() + 1
                );
            }
        }

        // -----------------------------------------
        // ELEGIR EL VALOR CON MAYOR CANTIDAD
        // -----------------------------------------

        if (ponderaciones.get("genero").get(0).getCantidad() >
                ponderaciones.get("genero").get(1).getCantidad()
                && !ponderaciones.get("genero").get(0).isValor()) {

            ponderaciones.get("genero").get(0).setValor(true);
            ponderaciones.get("genero").get(1).setValor(false);

        } else {
            ponderaciones.get("genero").get(0).setValor(false);
            ponderaciones.get("genero").get(1).setValor(true);
        }


        if (ponderaciones.get("anteojos").get(0).getCantidad() >
                ponderaciones.get("anteojos").get(1).getCantidad()
                && !ponderaciones.get("anteojos").get(0).isValor()) {

            ponderaciones.get("anteojos").get(0).setValor(true);
            ponderaciones.get("anteojos").get(1).setValor(false);

        } else {
            ponderaciones.get("anteojos").get(0).setValor(false);
            ponderaciones.get("anteojos").get(1).setValor(true);
        }


        if (ponderaciones.get("sombrero").get(0).getCantidad() >
                ponderaciones.get("sombrero").get(1).getCantidad()
                && !ponderaciones.get("sombrero").get(0).isValor()) {

            ponderaciones.get("sombrero").get(0).setValor(true);
            ponderaciones.get("sombrero").get(1).setValor(false);

        } else {
            ponderaciones.get("sombrero").get(0).setValor(false);
            ponderaciones.get("sombrero").get(1).setValor(true);
        }


        if (ponderaciones.get("barba").get(0).getCantidad() >
                ponderaciones.get("barba").get(1).getCantidad()
                && !ponderaciones.get("barba").get(0).isValor()) {

            ponderaciones.get("barba").get(0).setValor(true);
            ponderaciones.get("barba").get(1).setValor(false);

        } else {
            ponderaciones.get("barba").get(0).setValor(false);
            ponderaciones.get("barba").get(1).setValor(true);
        }


        if (ponderaciones.get("sonrisa").get(0).getCantidad() >
                ponderaciones.get("sonrisa").get(1).getCantidad()
                && !ponderaciones.get("sonrisa").get(0).isValor()) {

            ponderaciones.get("sonrisa").get(0).setValor(true);
            ponderaciones.get("sonrisa").get(1).setValor(false);

        } else {
            ponderaciones.get("sonrisa").get(0).setValor(false);
            ponderaciones.get("sonrisa").get(1).setValor(true);
        }


        if (ponderaciones.get("pelo_largo").get(0).getCantidad() >
                ponderaciones.get("pelo_largo").get(1).getCantidad()
                && !ponderaciones.get("pelo_largo").get(0).isValor()) {

            ponderaciones.get("pelo_largo").get(0).setValor(true);
            ponderaciones.get("pelo_largo").get(1).setValor(false);

        } else {
            ponderaciones.get("pelo_largo").get(0).setValor(false);
            ponderaciones.get("pelo_largo").get(1).setValor(true);
        }

        return ponderaciones;
    }
}