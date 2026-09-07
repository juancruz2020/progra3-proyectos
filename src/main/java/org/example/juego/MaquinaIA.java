package org.example.juego;

import org.example.algoritmo.Filtro;
import org.example.algoritmo.PonderadoCaracteristicas;
import org.example.objetos.Personaje;
import org.example.util.Trazador;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MaquinaIA implements Jugador {

    private final String nombre;
    private final PonderadoCaracteristicas selectorGreedy = new PonderadoCaracteristicas();
    private final Random random = new Random();

    private Personaje secreto;

    // Candidatos restantes para el secreto del RIVAL.
    private List<Personaje> candidatosRestantes;

    // Información del último proceso realizado por la IA.
    private String ultimoAnalisis = "";
    private String ultimoFiltrado = "";

    public MaquinaIA(String nombre) {
        this.nombre = nombre;
    }

    public void elegirSecreto(List<Personaje> tablero, Personaje excluir) {
        List<Personaje> opciones = new ArrayList<>(tablero);

        if (excluir != null) {
            opciones.removeIf(p -> p.getId() == excluir.getId());
        }

        this.secreto = opciones.get(random.nextInt(opciones.size()));

        Trazador.log(nombre + " eligió su secreto (no visible para el rival)");
    }

    public void iniciarBusqueda(List<Personaje> tablero) {
        this.candidatosRestantes = new ArrayList<>(tablero);
    }

    public List<Personaje> getCandidatosRestantes() {
        return candidatosRestantes;
    }

    public Filtro elegirMejorPregunta() {
        Trazador.seccion(nombre + " elige la mejor pregunta (Greedy)");

        Filtro mejorFiltro = selectorGreedy.elegirMejorFiltro(candidatosRestantes);

        // Guardamos el análisis que generó el algoritmo Greedy.
        ultimoAnalisis = selectorGreedy.getUltimoAnalisis();

        return mejorFiltro;
    }

    public void filtrarCandidatos(Filtro filtro, boolean respuesta) {

        int antes = candidatosRestantes.size();

        List<Personaje> eliminados = new ArrayList<>();

        for (Personaje personaje : candidatosRestantes) {
            if (filtro.valorDe(personaje) != respuesta) {
                eliminados.add(personaje);
            }
        }

        candidatosRestantes.removeIf(p -> filtro.valorDe(p) != respuesta);

        int despues = candidatosRestantes.size();

        StringBuilder reporte = new StringBuilder();

        reporte.append("FILTRADO DE CANDIDATOS\n");
        reporte.append("--------------------------------\n");
        reporte.append("Pregunta: ").append(filtro.getPregunta()).append("\n");
        reporte.append("Respuesta recibida: ")
                .append(respuesta ? "SÍ" : "NO")
                .append("\n");
        reporte.append("Candidatos antes: ").append(antes).append("\n");
        reporte.append("Candidatos eliminados: ")
                .append(eliminados.size())
                .append("\n");
        reporte.append("Candidatos restantes: ")
                .append(despues)
                .append("\n");

        if (!eliminados.isEmpty()) {
            reporte.append("\nEliminados:\n");

            for (Personaje personaje : eliminados) {
                reporte.append("  - ")
                        .append(personaje.getNombre())
                        .append("\n");
            }
        }

        if (!candidatosRestantes.isEmpty()) {
            reporte.append("\nCandidatos que quedan:\n");

            for (Personaje personaje : candidatosRestantes) {
                reporte.append("  - ")
                        .append(personaje.getNombre())
                        .append("\n");
            }
        } else {
            reporte.append("\nNo quedan candidatos.\n");
        }

        ultimoFiltrado = reporte.toString();

        Trazador.log(
                nombre + " filtró candidatos: "
                        + antes + " -> " + despues
        );
    }

    public String getUltimoAnalisis() {
        return ultimoAnalisis;
    }

    public String getUltimoFiltrado() {
        return ultimoFiltrado;
    }

    public boolean tieneCandidatoUnico() {
        return candidatosRestantes.size() == 1;
    }

    public Personaje getCandidatoMasProbable() {
        return candidatosRestantes.isEmpty()
                ? null
                : candidatosRestantes.get(0);
    }

    @Override
    public boolean responder(Filtro filtro) {
        return filtro.valorDe(secreto);
    }

    @Override
    public boolean esElSecreto(Personaje candidato) {
        return secreto != null
                && candidato != null
                && secreto.getId() == candidato.getId();
    }

    @Override
    public Personaje getSecretoParaMostrar() {
        return secreto;
    }

    @Override
    public String getNombreJugador() {
        return nombre;
    }
}