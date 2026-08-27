package org.example.algoritmo;

import org.example.objetos.Personaje;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PonderadoCaracteristicas {

    private Map<String, Integer> ponderaciones = new HashMap<>();


    private void init_map() {
        ponderaciones.put("genero", 0);
        ponderaciones.put("anteojos", 0);
        ponderaciones.put("sombrero", 0);
        ponderaciones.put("barba", 0);
        ponderaciones.put("sonrisa", 0);
        ponderaciones.put("pelo_largo", 0);
    }

    public Map<String, Integer> que_pregunto(List<Personaje> personajes, List<String> preguntadas) {
        init_map();
        for (Personaje personaje : personajes) {
            if (personaje.isGenero() && !preguntadas.contains("genero")) {
                ponderaciones.put("genero", ponderaciones.get("genero") + 1);
            }

            if (personaje.isAnteojos() && !preguntadas.contains("anteojos")) {
                ponderaciones.put("anteojos", ponderaciones.get("anteojos") + 1);
            }

            if (personaje.isSombrero() && !preguntadas.contains("sombrero")) {
                ponderaciones.put("sombrero", ponderaciones.get("sombrero") + 1);
            }

            if (personaje.isBarba() && !preguntadas.contains("barba")) {
                ponderaciones.put("barba", ponderaciones.get("barba") + 1);
            }

            if (personaje.isSonrisa() && !preguntadas.contains("sonrisa")) {
                ponderaciones.put("sonrisa", ponderaciones.get("sonrisa") + 1);
            }

            if (personaje.isPelo_largo() && !preguntadas.contains("pelo_largo")) {
                ponderaciones.put("pelo_largo", ponderaciones.get("pelo_largo") + 1);
            }
}

        return this.ponderaciones;
}

}