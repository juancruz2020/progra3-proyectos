package org.example.algoritmo;

import org.example.objetos.personaje;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ponderado_caracteristicas {

    private Map<String, Integer> ponderaciones = new HashMap<>();

    private void init_map() {
        ponderaciones.put("genero", 0);
        ponderaciones.put("anteojos", 0);
        ponderaciones.put("sombrero", 0);
        ponderaciones.put("barba", 0);
        ponderaciones.put("sonrisa", 0);
        ponderaciones.put("pelo_largo", 0);
    }

    public Map<String, Integer> que_pregunto(List<personaje> personajes) {
        init_map();
        for (personaje personaje : personajes) {
            if (personaje.isGenero()) {
                ponderaciones.put("genero", ponderaciones.get("genero") + 1);
            }
            if (personaje.isAnteojos()) {
                ponderaciones.put("anteojos", ponderaciones.get("anteojos") + 1);
            }
            if (personaje.isSombrero()) {
                ponderaciones.put("sombrero", ponderaciones.get("sombrero") + 1);
            }
            if (personaje.isBarba()) {
                ponderaciones.put("barba", ponderaciones.get("barba") + 1);
            }
            if (personaje.isSonrisa()) {
                ponderaciones.put("sonrisa", ponderaciones.get("sonrisa") + 1);
            }
            if (personaje.isPelo_largo()) {
                ponderaciones.put("pelo_largo", ponderaciones.get("pelo_largo") + 1);
            }
        }

        return this.ponderaciones;
    }
}
