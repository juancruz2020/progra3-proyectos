package org.example.algoritmo;

import org.example.objetos.Caracteristica;
import org.example.objetos.Personaje;
import org.example.util.Trazador;

import java.util.List;

// Algoritmo GREEDY: en cada turno elige, de forma local (sin planificar
// turnos futuros), el filtro que separa a los candidatos restantes lo más
// parejo posible entre "sí" y "no". Esa es la elección que maximiza el
// descarte ESPERADO en ese turno puntual: no garantiza el camino más corto
// global, pero es una aproximación simple y efectiva.
public class PonderadoCaracteristicas {

    private String ultimoAnalisis = "";

    public Filtro elegirMejorFiltro(List<Personaje> candidatos) {

        Filtro mejorFiltro = null;
        int menorDiferencia = Integer.MAX_VALUE;

        StringBuilder analisis = new StringBuilder();

        analisis.append("\n");
        analisis.append("========== ANALISIS GREEDY ==========\n");
        analisis.append("Cantidad de candidatos actuales: ")
                .append(candidatos.size())
                .append("\n\n");

        for (Filtro filtro : Filtro.values()) {

            Caracteristica enTrue = new Caracteristica();
            Caracteristica enFalse = new Caracteristica();

            for (Personaje personaje : candidatos) {

                if (filtro.valorDe(personaje)) {
                    enTrue.setCantidad(
                            enTrue.getCantidad() + 1
                    );
                } else {
                    enFalse.setCantidad(
                            enFalse.getCantidad() + 1
                    );
                }
            }

            int cantidadSi = enTrue.getCantidad();
            int cantidadNo = enFalse.getCantidad();

            int diferencia =
                    Math.abs(cantidadSi - cantidadNo);

            analisis.append(filtro.getPregunta())
                    .append("\n");

            analisis.append("   Si: ")
                    .append(cantidadSi)
                    .append(" | No: ")
                    .append(cantidadNo)
                    .append(" | Diferencia: ")
                    .append(diferencia);

            if (diferencia < menorDiferencia) {

                analisis.append("  <- mejor hasta ahora");

                menorDiferencia = diferencia;
                mejorFiltro = filtro;
            }

            analisis.append("\n\n");

            Trazador.log(String.format(
                    "Evaluando %s (\"%s\") -> sí:%d no:%d diferencia:%d",
                    filtro,
                    filtro.getPregunta(),
                    cantidadSi,
                    cantidadNo,
                    diferencia
            ));
        }

        analisis.append("-------------------------------------\n");
        analisis.append("MEJOR PREGUNTA ELEGIDA POR GREEDY\n");

        if (mejorFiltro != null) {

            int cantidadSi = 0;
            int cantidadNo = 0;

            for (Personaje personaje : candidatos) {

                if (mejorFiltro.valorDe(personaje)) {
                    cantidadSi++;
                } else {
                    cantidadNo++;
                }
            }

            analisis.append("Pregunta: ")
                    .append(mejorFiltro.getPregunta())
                    .append("\n");

            analisis.append("Si: ")
                    .append(cantidadSi)
                    .append("\n");

            analisis.append("No: ")
                    .append(cantidadNo)
                    .append("\n");

            analisis.append("Diferencia minima: ")
                    .append(menorDiferencia)
                    .append("\n");

            analisis.append("\n");
            analisis.append("La IA elige esta pregunta porque ")
                    .append("divide los candidatos de la forma ")
                    .append("mas equilibrada posible.\n");
        }

        analisis.append("=====================================\n");

        ultimoAnalisis = analisis.toString();

        Trazador.log(
                "Mejor filtro elegido: "
                        + mejorFiltro
                        + " (diferencia mínima: "
                        + menorDiferencia
                        + ")"
        );

        return mejorFiltro;
    }

    public String getUltimoAnalisis() {
        return ultimoAnalisis;
    }
}