package org.example;

import javax.swing.SwingUtilities;

import org.example.juego.HumanoVsIa;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            HumanoVsIa juego = new HumanoVsIa();

            juego.iniciarJuego();
        });
    }
}