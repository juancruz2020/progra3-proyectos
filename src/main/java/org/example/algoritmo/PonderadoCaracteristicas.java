package org.example.algoritmo;

import org.example.objetos.Caracteristica;
import org.example.objetos.Personaje;
import org.example.util.Trazador;

import java.util.List;

// Algoritmo GREEDY: en cada turno elige, de forma local (sin planificar
// turnos futuros), el filtro que separa a los candidatos restantes lo más
// parejo posible entre "sí" y "no". Esa es la elección que maximiza el
// descarte ESPERADO en ese turno puntual: no garantiza el camino más corto
// global (para eso haría falta explorar todo el árbol de decisión posible,
// ver DOCUMENTACION.md), pero es una aproximación simple y efectiva.
public class PonderadoCaracteristicas {

    public Filtro elegirMejorFiltro(List<Personaje> candidatos) {

        Filtro mejorFiltro = null;
        int menorDiferencia = Integer.MAX_VALUE;

        for (Filtro filtro : Filtro.values()) {

            Caracteristica enTrue = new Caracteristica();
            Caracteristica enFalse = new Caracteristica();

            for (Personaje personaje : candidatos) {
                if (filtro.valorDe(personaje)) {
                    enTrue.setCantidad(enTrue.getCantidad() + 1);
                } else {
                    enFalse.setCantidad(enFalse.getCantidad() + 1);
                }
            }

            int diferencia = Math.abs(enTrue.getCantidad() - enFalse.getCantidad());
            Trazador.log(String.format(
                    "Evaluando %s (\"%s\") -> sí:%d no:%d diferencia:%d",
                    filtro, filtro.getPregunta(), enTrue.getCantidad(), enFalse.getCantidad(), diferencia));

            if (diferencia < menorDiferencia) {
                menorDiferencia = diferencia;
                mejorFiltro = filtro;
            }
        }

        Trazador.log("Mejor filtro elegido: " + mejorFiltro + " (diferencia mínima: " + menorDiferencia + ")");
        return mejorFiltro;
    }
}
