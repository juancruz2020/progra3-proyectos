package org.example.juego;

import org.example.objetos.Personaje;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaJuego extends JFrame {

    private JPanel panelPersonajes;
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

        // =========================
        // TÍTULO
        // =========================

        JLabel titulo = new JLabel(
                "¿QUIÉN ES QUIÉN?",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 30));

        add(titulo, BorderLayout.NORTH);


        // =========================
        // PERSONAJES
        // =========================

        panelPersonajes = new JPanel();

        panelPersonajes.setLayout(
                new GridLayout(4, 6, 10, 10)
        );

        JScrollPane scroll = new JScrollPane(panelPersonajes);

        add(scroll, BorderLayout.CENTER);


        // =========================
        // PARTE INFERIOR
        // =========================

        JPanel panelInferior = new JPanel();

        comboCaracteristicas = new JComboBox<>();

        comboCaracteristicas.addItem("genero");
        comboCaracteristicas.addItem("anteojos");
        comboCaracteristicas.addItem("sombrero");
        comboCaracteristicas.addItem("barba");
        comboCaracteristicas.addItem("sonrisa");
        comboCaracteristicas.addItem("pelo_largo");

        botonPreguntar = new JButton("Preguntar");

        panelInferior.add(comboCaracteristicas);
        panelInferior.add(botonPreguntar);

        add(panelInferior, BorderLayout.SOUTH);
    }


    // =====================================
    // MOSTRAR PERSONAJES
    // =====================================

    public void mostrarPersonajes(List<Personaje> personajes) {

        panelPersonajes.removeAll();

        for (Personaje personaje : personajes) {

            JPanel carta = crearCarta(personaje);

            panelPersonajes.add(carta);
        }

        panelPersonajes.revalidate();
        panelPersonajes.repaint();
    }


    // =====================================
    // CREAR CARTA
    // =====================================

    private JPanel crearCarta(Personaje personaje) {

        JPanel carta = new JPanel(new BorderLayout());

        carta.setBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2)
        );


        // ESPACIO PARA IMAGEN

        JLabel imagen = new JLabel(
                "IMAGEN",
                SwingConstants.CENTER
        );

        imagen.setPreferredSize(
                new Dimension(120, 120)
        );

        imagen.setBorder(
                BorderFactory.createLineBorder(Color.GRAY)
        );

        /*
         * DESPUÉS:
         *
         * ImageIcon icon =
         *      new ImageIcon("imagenes/juan.png");
         *
         * imagen.setIcon(icon);
         * imagen.setText("");
         */

        carta.add(imagen, BorderLayout.CENTER);


        // NOMBRE

        JLabel nombre = new JLabel(
                personaje.getNombre(),
                SwingConstants.CENTER
        );

        nombre.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        carta.add(nombre, BorderLayout.SOUTH);

        return carta;
    }


    // =====================================
    // OBTENER CARACTERÍSTICA SELECCIONADA
    // =====================================

    public String getCaracteristica() {

        return (String) comboCaracteristicas.getSelectedItem();
    }


    // =====================================
    // CONECTAR BOTÓN
    // =====================================

    public void agregarListenerPreguntar(ActionListener listener) {

        botonPreguntar.addActionListener(listener);
    }


    // =====================================
    // MOSTRAR RESPUESTA
    // =====================================

    public int pedirRespuesta() {

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿La respuesta es SÍ?",
                "Respuesta",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            return 0;
        }

        return 1;
    }


    // =====================================
    // MOSTRAR MENSAJE
    // =====================================

    public void mostrarMensaje(String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje
        );
    }
}