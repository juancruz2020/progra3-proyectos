package org.example.algoritmo;

import org.example.objetos.Personaje;
import org.example.util.Trazador;

import java.util.ArrayList;
import java.util.List;

// Algoritmo de DIVIDE Y CONQUISTA: ordena por nombre, alfabéticamente, la
// lista de 23 personajes que Generador entrega agrupada solo por género.
// Merge Sort divide recursivamente la lista en mitades hasta listas de un
// solo elemento (ya ordenadas por definición) y las va mezclando de a pares
// en orden. Al terminar, reasigna los ids 1..23 de forma autoincremental
// según el orden final ("según se agregan los personajes" a la lista ordenada).
public class OrdenadorMergeSort {

    public List<Personaje> ordenar(List<Personaje> personajes) {
        Trazador.seccion("Ordenando el tablero con Merge Sort (divide y conquista)");

        List<Personaje> copia = new ArrayList<>(personajes);
        mergeSort(copia, 0, copia.size() - 1);

        for (int i = 0; i < copia.size(); i++) {
            copia.get(i).setId(i + 1);
        }

        Trazador.log("Orden final asignado (id autoincremental): " + copia);
        return copia;
    }

    private void mergeSort(List<Personaje> lista, int izquierda, int derecha) {
        if (izquierda >= derecha) {
            return;
        }

        int medio = (izquierda + derecha) / 2;
        Trazador.log("Dividiendo [" + izquierda + ".." + derecha + "] en [" + izquierda + ".." + medio
                + "] y [" + (medio + 1) + ".." + derecha + "]");

        mergeSort(lista, izquierda, medio);
        mergeSort(lista, medio + 1, derecha);
        mezclar(lista, izquierda, medio, derecha);
    }

    private void mezclar(List<Personaje> lista, int izquierda, int medio, int derecha) {
        List<Personaje> mitadIzquierda = new ArrayList<>(lista.subList(izquierda, medio + 1));
        List<Personaje> mitadDerecha = new ArrayList<>(lista.subList(medio + 1, derecha + 1));

        int i = 0, j = 0, k = izquierda;

        while (i < mitadIzquierda.size() && j < mitadDerecha.size()) {
            if (mitadIzquierda.get(i).getNombre().compareToIgnoreCase(mitadDerecha.get(j).getNombre()) <= 0) {
                lista.set(k++, mitadIzquierda.get(i++));
            } else {
                lista.set(k++, mitadDerecha.get(j++));
            }
        }
        while (i < mitadIzquierda.size()) {
            lista.set(k++, mitadIzquierda.get(i++));
        }
        while (j < mitadDerecha.size()) {
            lista.set(k++, mitadDerecha.get(j++));
        }

        Trazador.log("Mezclando [" + izquierda + ".." + medio + "] con [" + (medio + 1) + ".." + derecha
                + "] -> " + lista.subList(izquierda, derecha + 1));
    }
}
