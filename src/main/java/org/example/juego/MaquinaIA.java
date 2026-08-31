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
    // Candidatos restantes para el secreto del RIVAL (lo que esta IA está tratando de adivinar).
    private List<Personaje> candidatosRestantes;

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
        return selectorGreedy.elegirMejorFiltro(candidatosRestantes);
    }

    public void filtrarCandidatos(Filtro filtro, boolean respuesta) {
        candidatosRestantes.removeIf(p -> filtro.valorDe(p) != respuesta);
    }

    public boolean tieneCandidatoUnico() {
        return candidatosRestantes.size() == 1;
    }

    public Personaje getCandidatoMasProbable() {
        return candidatosRestantes.isEmpty() ? null : candidatosRestantes.get(0);
    }

    @Override
    public boolean responder(Filtro filtro) {
        return filtro.valorDe(secreto);
    }

    @Override
    public boolean esElSecreto(Personaje candidato) {
        return secreto != null && candidato != null && secreto.getId() == candidato.getId();
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
