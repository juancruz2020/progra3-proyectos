package org.example.juego;

import org.example.algoritmo.Filtro;
import org.example.algoritmo.OrdenadorMergeSort;
import org.example.bd.Generador;
import org.example.objetos.Personaje;
import org.example.util.Trazador;

import java.util.ArrayList;
import java.util.List;

// Orquesta una partida IA vs IA, turno a turno, dejando un log legible de
// cada paso para que la UI (y la consola, vía Trazador) permitan
// "presenciar todos los procesos realizados" por la máquina, como pide la
// consigna.
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

    // Ejecuta un único turno de la partida y devuelve una descripción para la UI.
    public String siguientePaso() {
        if (terminada) {
            return "La partida ya terminó.";
        }

        numeroTurno++;
        String descripcion;

        if (turnoActivo.getCandidatosRestantes().size() <= 1) {
            Personaje candidato = turnoActivo.getCandidatoMasProbable();
            boolean acierto = candidato != null && turnoRival.esElSecreto(candidato);
            String nombreAdivinado = candidato != null ? candidato.getNombre() : "(sin candidatos)";

            descripcion = "Turno " + numeroTurno + " — " + turnoActivo.getNombreJugador()
                    + " adivina \"" + nombreAdivinado + "\" -> "
                    + (acierto ? "ACIERTO. ¡" + turnoActivo.getNombreJugador() + " gana!"
                               : "ERROR. Sigue jugando " + turnoRival.getNombreJugador() + ".");
            Trazador.log(descripcion);
            log.add(descripcion);

            if (acierto) {
                terminada = true;
            } else {
                cambiarTurno();
            }
            return descripcion;
        }

        Filtro filtro = turnoActivo.elegirMejorPregunta();
        boolean respuesta = turnoRival.responder(filtro);
        int antes = turnoActivo.getCandidatosRestantes().size();
        turnoActivo.filtrarCandidatos(filtro, respuesta);
        int despues = turnoActivo.getCandidatosRestantes().size();

        descripcion = "Turno " + numeroTurno + " — " + turnoActivo.getNombreJugador()
                + " pregunta \"" + filtro.getPregunta() + "\" -> " + (respuesta ? "Sí" : "No")
                + " (candidatos: " + antes + " -> " + despues + ")";
        Trazador.log(descripcion);
        log.add(descripcion);

        cambiarTurno();
        return descripcion;
    }

    private void cambiarTurno() {
        MaquinaIA temp = turnoActivo;
        turnoActivo = turnoRival;
        turnoRival = temp;
    }
}
