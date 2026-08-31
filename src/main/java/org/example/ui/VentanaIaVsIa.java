package org.example.ui;

import org.example.juego.PartidaIaVsIa;

import javax.swing.*;
import java.awt.*;

public class VentanaIaVsIa extends JFrame {

    private final PartidaIaVsIa partida = new PartidaIaVsIa();

    private final PanelTableroPersonajes panelIa1 = new PanelTableroPersonajes();
    private final PanelTableroPersonajes panelIa2 = new PanelTableroPersonajes();
    private final JTextArea areaLog = new JTextArea();
    private final Timer timerAutoplay;

    public VentanaIaVsIa() {
        super("IA vs IA — proceso de búsqueda paso a paso");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1050, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelTableros = new JPanel(new GridLayout(1, 2, 10, 10));
        panelTableros.add(envolver("Candidatos de IA-1 (para adivinar a IA-2)", panelIa1));
        panelTableros.add(envolver("Candidatos de IA-2 (para adivinar a IA-1)", panelIa2));
        add(panelTableros, BorderLayout.CENTER);

        areaLog.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setPreferredSize(new Dimension(1050, 220));
        add(scrollLog, BorderLayout.SOUTH);

        JPanel panelAcciones = new JPanel(new FlowLayout());
        JButton btnPaso = new JButton("Siguiente paso");
        JButton btnAutoplay = new JButton("Reproducir");
        JButton btnDetener = new JButton("Detener");

        btnPaso.addActionListener(e -> ejecutarPaso());

        timerAutoplay = new Timer(900, e -> ejecutarPaso());
        btnAutoplay.addActionListener(e -> timerAutoplay.start());
        btnDetener.addActionListener(e -> timerAutoplay.stop());

        panelAcciones.add(btnPaso);
        panelAcciones.add(btnAutoplay);
        panelAcciones.add(btnDetener);
        add(panelAcciones, BorderLayout.NORTH);

        actualizarTableros();
    }

    private JPanel envolver(String titulo, JPanel panel) {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(BorderFactory.createTitledBorder(titulo));
        contenedor.add(new JScrollPane(panel), BorderLayout.CENTER);
        return contenedor;
    }

    private void ejecutarPaso() {
        if (partida.isTerminada()) {
            timerAutoplay.stop();
            return;
        }

        String descripcion = partida.siguientePaso();
        areaLog.append(descripcion + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
        actualizarTableros();

        if (partida.isTerminada()) {
            timerAutoplay.stop();
            JOptionPane.showMessageDialog(this, "Partida terminada:\n" + descripcion);
        }
    }

    private void actualizarTableros() {
        panelIa1.mostrar(partida.getIa1().getCandidatosRestantes());
        panelIa2.mostrar(partida.getIa2().getCandidatosRestantes());
    }
}
