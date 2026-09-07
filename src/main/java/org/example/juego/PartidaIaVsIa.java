package org.example.juego;

import org.example.algoritmo.Filtro;
import org.example.algoritmo.OrdenadorMergeSort;
import org.example.bd.Generador;
import org.example.objetos.Personaje;
import org.example.util.Trazador;

import java.util.ArrayList;
import java.util.List;

// Orquesta una partida IA vs IA, turno a turno.
// La lógica de decisión pertenece a MaquinaIA y sus algoritmos.
// Esta clase solamente coordina la partida y construye el log.
public class PartidaIaVsIa {

    private final List<Personaje> tablero;
    private final MaquinaIA ia1 = new MaquinaIA("IA-1");
    private final MaquinaIA ia2 = new MaquinaIA("IA-2");
    private final List<String> log = new ArrayList<>();

    private MaquinaIA turnoActivo;
    private MaquinaIA turnoRival;
    private boolean terminada = false;
    private int numeroTurno = 0;

    public PartidaIaVsIa() {
        Trazador.seccion("Nueva partida: IA vs IA");

        List<Personaje> generados = new Generador().generar();

        // El tablero se ordena mediante Merge Sort.
        this.tablero = new OrdenadorMergeSort().ordenar(generados);

        ia1.elegirSecreto(tablero, null);
        ia2.elegirSecreto(tablero, ia1.getSecretoParaMostrar());

        ia1.iniciarBusqueda(tablero);
        ia2.iniciarBusqueda(tablero);

        turnoActivo = ia1;
        turnoRival = ia2;
    }

    public List<Personaje> getTablero() {
        return tablero;
    }

    public MaquinaIA getIa1() {
        return ia1;
    }

    public MaquinaIA getIa2() {
        return ia2;
    }

    public List<String> getLog() {
        return log;
    }

    public boolean isTerminada() {
        return terminada;
    }

    // Ejecuta un único turno de la partida.
    // La clase coordina el proceso, pero no realiza el algoritmo Greedy.
    public String siguientePaso() {

        if (terminada) {
            return "La partida ya terminó.";
        }

        numeroTurno++;

        /*
         * ==========================================================
         * CASO 1: LA IA YA TIENE UN SOLO CANDIDATO
         * ==========================================================
         */
        if (turnoActivo.getCandidatosRestantes().size() <= 1) {

            Personaje candidato = turnoActivo.getCandidatoMasProbable();

            boolean acierto =
                    candidato != null
                            && turnoRival.esElSecreto(candidato);

            String nombreAdivinado =
                    candidato != null
                            ? candidato.getNombre()
                            : "(sin candidatos)";

            StringBuilder descripcion = new StringBuilder();

            descripcion.append("══════════════════════════════════════\n");
            descripcion.append("TURNO ").append(numeroTurno).append("\n");
            descripcion.append("══════════════════════════════════════\n");

            descripcion.append(turnoActivo.getNombreJugador())
                    .append(" ya redujo la búsqueda a ")
                    .append(turnoActivo.getCandidatosRestantes().size())
                    .append(" candidato(s).\n\n");

            descripcion.append("SUPOSICIÓN FINAL\n");
            descripcion.append("--------------------------------------\n");
            descripcion.append("La IA intenta adivinar: \"")
                    .append(nombreAdivinado)
                    .append("\"\n\n");

            descripcion.append("RESULTADO\n");
            descripcion.append("--------------------------------------\n");

            if (acierto) {

                descripcion.append("ACIERTO.\n");
                descripcion.append("¡")
                        .append(turnoActivo.getNombreJugador())
                        .append(" gana la partida!");

                terminada = true;

            } else {

                descripcion.append("ERROR.\n");
                descripcion.append("La suposición no era correcta.\n");
                descripcion.append("Continúa jugando ")
                        .append(turnoRival.getNombreJugador())
                        .append(".");

                cambiarTurno();
            }

            String resultado = descripcion.toString();

            Trazador.log(resultado);
            log.add(resultado);

            return resultado;
        }

        /*
         * ==========================================================
         * CASO 2: LA IA TIENE QUE ELEGIR UNA PREGUNTA
         * ==========================================================
         */

        int candidatosAntes =
                turnoActivo.getCandidatosRestantes().size();

        StringBuilder descripcion = new StringBuilder();

        descripcion.append("══════════════════════════════════════\n");
        descripcion.append("TURNO ").append(numeroTurno).append("\n");
        descripcion.append("══════════════════════════════════════\n");

        descripcion.append("IA ACTIVA: ")
                .append(turnoActivo.getNombreJugador())
                .append("\n");

        descripcion.append("Objetivo: encontrar el secreto de ")
                .append(turnoRival.getNombreJugador())
                .append("\n");

        descripcion.append("Candidatos actuales: ")
                .append(candidatosAntes)
                .append("\n\n");

        /*
         * La IA elige la pregunta.
         *
         * La decisión Greedy NO se realiza acá.
         * MaquinaIA -> PonderadoCaracteristicas se encarga de eso.
         */
        Filtro filtro = turnoActivo.elegirMejorPregunta();

        /*
         * Recuperamos el análisis generado por PonderadoCaracteristicas.
         */
        String analisis = turnoActivo.getUltimoAnalisis();

        if (analisis != null && !analisis.isBlank()) {
            descripcion.append("ANÁLISIS DEL ALGORITMO GREEDY\n");
            descripcion.append("--------------------------------------\n");
            descripcion.append(analisis);
            descripcion.append("\n");
        }

        /*
         * Preguntamos al rival.
         */
        boolean respuesta = turnoRival.responder(filtro);

        descripcion.append("PREGUNTA ELEGIDA\n");
        descripcion.append("--------------------------------------\n");
        descripcion.append(filtro.getPregunta())
                .append("\n\n");

        descripcion.append("RESPUESTA DE ")
                .append(turnoRival.getNombreJugador())
                .append(": ")
                .append(respuesta ? "SÍ" : "NO")
                .append("\n\n");

        /*
         * La IA activa elimina los candidatos que no coinciden
         * con la respuesta.
         *
         * El filtrado lo realiza MaquinaIA.
         */
        turnoActivo.filtrarCandidatos(filtro, respuesta);

        int candidatosDespues =
                turnoActivo.getCandidatosRestantes().size();

        /*
         * Recuperamos el detalle producido por MaquinaIA.
         */
        String filtrado = turnoActivo.getUltimoFiltrado();

        if (filtrado != null && !filtrado.isBlank()) {
            descripcion.append(filtrado);
            descripcion.append("\n");
        }

        /*
         * Resumen del turno.
         */
        descripcion.append("RESUMEN DEL TURNO\n");
        descripcion.append("--------------------------------------\n");

        descripcion.append("Candidatos: ")
                .append(candidatosAntes)
                .append(" → ")
                .append(candidatosDespues)
                .append("\n");

        descripcion.append("Eliminados: ")
                .append(candidatosAntes - candidatosDespues)
                .append("\n");

        if (candidatosDespues == 1) {

            Personaje candidato =
                    turnoActivo.getCandidatoMasProbable();

            descripcion.append("\n");
            descripcion.append("★ La IA quedó con un único candidato:\n");
            descripcion.append("  ")
                    .append(candidato.getNombre())
                    .append("\n");

        } else if (candidatosDespues == 0) {

            descripcion.append("\n");
            descripcion.append("⚠ No quedaron candidatos.\n");

        } else {

            descripcion.append("\n");
            descripcion.append("La IA continuará buscando.\n");
        }

        String resultado = descripcion.toString();

        Trazador.log(resultado);
        log.add(resultado);

        /*
         * Cambiamos el turno después de terminar completamente
         * el proceso de la IA actual.
         */
        cambiarTurno();

        return resultado;
    }

    private void cambiarTurno() {
        MaquinaIA temp = turnoActivo;
        turnoActivo = turnoRival;
        turnoRival = temp;
    }
}