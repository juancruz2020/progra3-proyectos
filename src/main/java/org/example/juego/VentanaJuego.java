package org.example.juego;

import javax.swing.*;
import java.awt.*;

public class VentanaJuego extends JFrame {

    private JPanel panelPersonajes;
    private JPanel panelInferior;
    private JLabel titulo;
    private JComboBox<String> comboCaracteristicas;
    private JButton botonPreguntar;

    public VentanaJuego() {

        setTitle("¿Quién es Quién?");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearVentana();
    }

    private void crearVentana() {

        setLayout(new BorderLayout());

        // TÍTULO
        titulo = new JLabel(
                "¿QUIÉN ES QUIÉN?",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 30));

        add(titulo, BorderLayout.NORTH);


        // PERSONAJES
        panelPersonajes = new JPanel();

        panelPersonajes.setLayout(
                new GridLayout(4, 6, 10, 10)
        );

        add(panelPersonajes, BorderLayout.CENTER);


        // PARTE INFERIOR
        panelInferior = new JPanel();

        comboCaracteristicas = new JComboBox<>();

        comboCaracteristicas.addItem("Género");
        comboCaracteristicas.addItem("Anteojos");
        comboCaracteristicas.addItem("Sombrero");
        comboCaracteristicas.addItem("Barba");
        comboCaracteristicas.addItem("Sonrisa");
        comboCaracteristicas.addItem("Pelo largo");

        botonPreguntar = new JButton("Preguntar");

        panelInferior.add(comboCaracteristicas);
        panelInferior.add(botonPreguntar);

        add(panelInferior, BorderLayout.SOUTH);
    }
}