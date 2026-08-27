package org.example.juego;

import org.example.algoritmo.BuscarGanador;
import org.example.algoritmo.TurnoHumano;
import org.example.algoritmo.TurnoIa;
import org.example.bd.Generador;
import org.example.objetos.Personaje;

import java.util.List;

public class HumanoVsIa {

    private boolean flag = false;
    Generador generador = new Generador();
    BuscarGanador buscarGanador = new BuscarGanador();
    TurnoHumano turnoHumano = new TurnoHumano();

    public void juego() {
        List<Personaje> humano = generador.generar();
        List<Personaje> ia = generador.generar();

        Personaje ganadorHumano = buscarGanador.buscarImpostor(humano);

        System.out.println("El personaje seleccionado es: " + ganadorHumano.getId());

        TurnoIa turnoIa = new TurnoIa();

        while (!flag) {

            Personaje resultadoHumano = turnoHumano.turno(ia);

            if (resultadoHumano != null) {
                System.out.println("¡El humano encontró al impostor: " + resultadoHumano.getNombre());
                flag = true;
                break;
            }

            Personaje resultado = turnoIa.turno(ia);

            if (resultado != null) {
                System.out.println("La IA encontró al impostor: "+ resultado.getNombre());
                flag = true;
            }
        }
    }
}