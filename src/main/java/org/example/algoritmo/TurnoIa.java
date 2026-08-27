package org.example.algoritmo;

import org.example.objetos.Personaje;
import org.example.bd.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class TurnoIa {

    private List<String> preguntadas = new ArrayList<>();
    private int largo;
    private int probabilidad;
    private PonderadoCaracteristicas ponde = new PonderadoCaracteristicas();
    private BuscarGanador busca = new BuscarGanador();
    private String mayor;

    public Personaje turno(List<Personaje> personaje) {

        largo = personaje.size();


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

        if (numero == 1) {

            Personaje ganador = busca.buscarImpostor(personaje);

            if (ganador.getId() == personaje.get(0).getId()) {
                return personaje.get(0);
            } else {
                personaje.remove(0);
                return null;
            }

        } else {

            Map<String, Integer> ponderaciones =
                    ponde.que_pregunto(personaje, preguntadas);

            int valorMayor = 0;

            for (String clave : ponderaciones.keySet()) {

                if (ponderaciones.get(clave) > valorMayor) {
                    valorMayor = ponderaciones.get(clave);
                    mayor = clave;
                }
            }

            preguntadas.add(mayor);

            Preguntas.preguntar(mayor);

            Scanner scanner = new Scanner(System.in);

            System.out.println("0 = Sí");
            System.out.println("1 = No");

            int respuesta = scanner.nextInt();

            if (respuesta == 0) {
                personaje.removeIf(
                        p -> !Preguntas.tieneCaracteristica(p, mayor));
            } else {
                personaje.removeIf(p -> Preguntas.tieneCaracteristica(p, mayor));
            }
            if (personaje.size() == 1) {
                if (personaje.get(0).isImpostor()) {
                    return personaje.get(0);
            }
        }
        }

        return null;
    }
}