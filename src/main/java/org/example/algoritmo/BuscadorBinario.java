package org.example.algoritmo;

import org.example.objetos.Personaje;
import org.example.util.Trazador;

import java.util.List;

// Algoritmo de DIVIDE Y CONQUISTA: ubica/verifica un personaje por nombre
// dentro de la lista ya ordenada por OrdenadorMergeSort. En cada paso
// descarta la mitad de los candidatos restantes (O(log n)) en vez de
// recorrer la lista entera (O(n) de una búsqueda lineal). Se usa cada vez
// que un jugador "lanza su suposición" (adivina un nombre).
public class BuscadorBinario {

    public Personaje buscar(List<Personaje> ordenadaPorNombre, String nombre) {
        Trazador.seccion("Buscando \"" + nombre + "\" con Búsqueda Binaria (divide y conquista)");

        int izquierda = 0;
        int derecha = ordenadaPorNombre.size() - 1;

        while (izquierda <= derecha) {
            int medio = (izquierda + derecha) / 2;
            Personaje candidato = ordenadaPorNombre.get(medio);
            int comparacion = candidato.getNombre().compareToIgnoreCase(nombre);

            Trazador.log("Rango [" + izquierda + ".." + derecha + "], medio=" + medio
                    + " (\"" + candidato.getNombre() + "\") comparado con \"" + nombre + "\"");

            if (comparacion == 0) {
                Trazador.log("Encontrado: " + candidato.getNombre());
                return candidato;
            } else if (comparacion < 0) {
                Trazador.log("Descarto mitad izquierda, busco en [" + (medio + 1) + ".." + derecha + "]");
                izquierda = medio + 1;
            } else {
                Trazador.log("Descarto mitad derecha, busco en [" + izquierda + ".." + (medio - 1) + "]");
                derecha = medio - 1;
            }
        }

        Trazador.log("No se encontró ningún personaje llamado \"" + nombre + "\"");
        return null;
    }
}
