package org.example.juego;

import java.util.List;

import org.example.algoritmo.TurnoHumano;
import org.example.algoritmo.TurnoIa;
import org.example.bd.Generador;
import org.example.objetos.Personaje;
import org.example.bd.*;

public class HumanoVsIa {

    private boolean terminado = false;

    private Generador generador = new Generador();

    private TurnoHumano turnoHumano = new TurnoHumano();
    private TurnoIa turnoIa = new TurnoIa();

    private List<Personaje> humano;
    private List<Personaje> ia;

    private VentanaJuego ventana;


    // =====================================
    // INICIAR JUEGO
    // =====================================

    public void iniciarJuego() {

        humano = generador.generar();
        ia = generador.generar();

        terminado = false;

        ventana = new VentanaJuego();

        // Mostrar los personajes que tiene que descubrir el humano
        ventana.mostrarPersonajes(ia);

        // Conectar botón de preguntar
        ventana.agregarListenerPreguntar(
                e -> turnoHumano()
        );

        ventana.setVisible(true);
    }


    // =====================================
    // TURNO HUMANO
    // =====================================

    private void turnoHumano() {

        if (terminado) {
            return;
        }

        String caracteristica =
                ventana.getCaracteristica();

        int respuesta =
                ventana.pedirRespuesta();

        Personaje resultado =
                turnoHumano.turno(
                        ia,
                        caracteristica,
                        respuesta
                );

        // Actualizar las cartas
        ventana.mostrarPersonajes(ia);

        // ¿Ganó el humano?
        if (resultado != null) {

            terminado = true;

            ventana.mostrarMensaje(
                    "¡Ganaste!\n" +
                    "El impostor era: " +
                    resultado.getNombre()
            );

            return;
        }

        // Después del humano juega la IA
        turnoIa();
    }


    // =====================================
    // TURNO IA
    // =====================================

    private void turnoIa() {

        if (terminado) {
            return;
        }

        Personaje resultado =
                turnoIa.turno(humano);

        // Mostrar la pregunta que hizo la IA
        ventana.mostrarPreguntaIa(
                turnoIa.getUltimaPregunta()
        );

        // Actualizar las cartas que está descartando la IA
        ventana.mostrarPersonajes(humano);

        // ¿Ganó la IA?
        if (resultado != null) {

            terminado = true;

            ventana.mostrarMensaje(
                    "¡La IA encontró al impostor!\n" +
                    "Era: " +
                    resultado.getNombre()
            );
        }
    }
}