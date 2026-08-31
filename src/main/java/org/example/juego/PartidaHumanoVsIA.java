package org.example.juego;

import org.example.algoritmo.BuscadorBinario;
import org.example.algoritmo.Filtro;
import org.example.algoritmo.OrdenadorMergeSort;
import org.example.bd.Generador;
import org.example.objetos.Personaje;
import org.example.util.Trazador;

import java.util.ArrayList;
import java.util.List;

// Orquesta una partida Humano vs IA. No tiene nada de Swing: la UI llama a
// estos métodos y solo se encarga de mostrar el resultado y pedir datos al
// usuario (qué pregunta hacer, qué nombre adivinar, responder sí/no a la IA).
public class PartidaHumanoVsIA {

    private final List<Personaje> tablero;
    private final Humano humano = new Humano();
    private final MaquinaIA maquina = new MaquinaIA("IA");
    private final BuscadorBinario buscador = new BuscadorBinario();

    private List<Personaje> candidatosHumano;
    private boolean terminada = false;
    private String resultado = "";

    public PartidaHumanoVsIA() {
        Trazador.seccion("Nueva partida: Humano vs IA");
        List<Personaje> generados = new Generador().generar();
        this.tablero = new OrdenadorMergeSort().ordenar(generados);
    }

    public List<Personaje> getTablero() {
        return tablero;
    }

    public void iniciar(Personaje secretoHumano) {
        humano.setSecreto(secretoHumano);
        maquina.elegirSecreto(tablero, secretoHumano);
        maquina.iniciarBusqueda(tablero);
        candidatosHumano = new ArrayList<>(tablero);
        Trazador.log("Humano eligió su secreto: " + secretoHumano.getNombre());
    }

    public List<Personaje> getCandidatosHumano() {
        return candidatosHumano;
    }

    public List<Personaje> getCandidatosMaquina() {
        return maquina.getCandidatosRestantes();
    }

    public boolean isTerminada() {
        return terminada;
    }

    public String getResultado() {
        return resultado;
    }

    // El humano pregunta un filtro sobre el secreto de la IA.
    public boolean humanoPregunta(Filtro filtro) {
        boolean respuesta = maquina.responder(filtro);
        candidatosHumano.removeIf(p -> filtro.valorDe(p) != respuesta);
        Trazador.log("Humano pregunta \"" + filtro.getPregunta() + "\" -> " + (respuesta ? "Sí" : "No")
                + " (le quedan " + candidatosHumano.size() + " candidatos)");
        return respuesta;
    }

    // El humano lanza su suposición. Devuelve true si acertó (gana la partida).
    public boolean humanoAdivina(String nombre) {
        Personaje candidato = buscador.buscar(tablero, nombre);
        boolean acierto = candidato != null && maquina.esElSecreto(candidato);
        terminada = true;
        resultado = acierto
                ? "¡Ganaste! El secreto de la IA era " + maquina.getSecretoParaMostrar().getNombre() + "."
                : "Perdiste. El secreto de la IA era " + maquina.getSecretoParaMostrar().getNombre() + ".";
        Trazador.log("Humano adivina \"" + nombre + "\" -> " + (acierto ? "ACIERTO" : "ERROR"));
        return acierto;
    }

    // La IA elige, con el algoritmo greedy, la mejor pregunta sobre su lista de candidatos.
    public Filtro maquinaElegirPregunta() {
        return maquina.elegirMejorPregunta();
    }

    // La UI le pasa la respuesta honesta que dio el humano a la pregunta de la IA.
    public void maquinaRecibirRespuesta(Filtro filtro, boolean respuesta) {
        maquina.filtrarCandidatos(filtro, respuesta);
        Trazador.log("IA pregunta \"" + filtro.getPregunta() + "\" -> " + (respuesta ? "Sí" : "No")
                + " (le quedan " + maquina.getCandidatosRestantes().size() + " candidatos)");
    }

    public boolean maquinaListaParaAdivinar() {
        return maquina.getCandidatosRestantes().size() <= 1;
    }

    // La IA lanza su suposición. Devuelve true si acertó (gana la IA, pierde el humano).
    public boolean maquinaAdivina() {
        Personaje candidato = maquina.getCandidatoMasProbable();
        boolean acierto = candidato != null && humano.esElSecreto(candidato);
        terminada = true;
        String nombreAdivinado = candidato != null ? candidato.getNombre() : "(sin candidatos)";
        resultado = acierto
                ? "La IA adivinó tu secreto: " + nombreAdivinado + ". ¡Perdiste!"
                : "La IA falló su suposición (" + nombreAdivinado + "). ¡Ganaste!";
        Trazador.log("IA adivina \"" + nombreAdivinado + "\" -> " + (acierto ? "ACIERTO" : "ERROR"));
        return acierto;
    }
}
