package org.example.algoritmo;

import org.example.objetos.Personaje;
import org.example.bd.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TurnoIa {

    private List<String> preguntadas = new ArrayList<>();
    private PonderadoCaracteristicas ponde = new PonderadoCaracteristicas();
    private BuscarGanador busca = new BuscarGanador();

    private String mayor;
    private String ultimaPregunta;

    public Personaje turno(List<Personaje> personaje) {

        int largo = personaje.size();

        int limite;

        if (largo >= 18) {
            limite = 25;
        } else if (largo >= 15) {
            limite = 20;
        } else if (largo >= 10) {
            limite = 15;
        } else if (largo >= 8) {
            limite = 10;
        } else if (largo >= 5) {
            limite = 5;
        } else if (largo >= 3) {
            limite = 3;
        } else {
            limite = 1;
        }

        Random random = new Random();
        int numero = random.nextInt(limite) + 1;

        // INTENTA ADIVINAR
        if (numero == 1) {

            if (personaje.isEmpty()) {
                return null;
            }

            Personaje ganador = busca.buscarImpostor(personaje);

            if (ganador != null &&
                    ganador.getId() == personaje.get(0).getId()) {

                return personaje.get(0);

            } else {

                personaje.remove(0);
                return null;
            }
        }

        // ELIGE PREGUNTA
        Map<String, Integer> ponderaciones =
                ponde.que_pregunto(personaje, preguntadas);

        mayor = "";
        int valorMayor = 0;

        for (String clave : ponderaciones.keySet()) {

            if (ponderaciones.get(clave) > valorMayor) {
                valorMayor = ponderaciones.get(clave);
                mayor = clave;
            }
        }

        if (mayor.equals("")) {
            return null;
        }

        preguntadas.add(mayor);

        // Guardamos la pregunta para que la ventana la muestre
        ultimaPregunta = Preguntas.preguntar(mayor);

        // Buscamos al impostor para saber automáticamente la respuesta
        Personaje impostor = busca.buscarImpostor(personaje);

        boolean respuesta =
                Preguntas.tieneCaracteristica(impostor, mayor);

        // Filtramos según la respuesta
        if (respuesta) {

            personaje.removeIf(
                    p -> !Preguntas.tieneCaracteristica(p, mayor)
            );

        } else {

            personaje.removeIf(
                    p -> Preguntas.tieneCaracteristica(p, mayor)
            );
        }

        // Comprobar si quedó el impostor
        if (personaje.size() == 1 &&
                personaje.get(0).isImpostor()) {

            return personaje.get(0);
        }

        return null;
    }

    public String getUltimaPregunta() {
        return ultimaPregunta;
    }
}