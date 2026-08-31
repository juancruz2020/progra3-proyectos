package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        super("Adivina Quién — Programación 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 260);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnHumanoVsIa = new JButton("Jugar contra la IA");
        JButton btnIaVsIa = new JButton("IA contra IA");
        JButton btnSalir = new JButton("Salir");

        btnHumanoVsIa.addActionListener(e -> new VentanaHumanoVsIA().setVisible(true));
        btnIaVsIa.addActionListener(e -> new VentanaIaVsIa().setVisible(true));
        btnSalir.addActionListener(e -> System.exit(0));

        panel.add(btnHumanoVsIa);
        panel.add(btnIaVsIa);
        panel.add(btnSalir);

        add(panel);
    }
}
