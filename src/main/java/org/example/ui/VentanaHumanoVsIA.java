package org.example.ui;

import org.example.algoritmo.Filtro;
import org.example.juego.PartidaHumanoVsIA;
import org.example.objetos.Personaje;

import javax.swing.*;
import java.awt.*;

public class VentanaHumanoVsIA extends JFrame {

    private final PartidaHumanoVsIA partida = new PartidaHumanoVsIA();

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel panelCentral = new JPanel(cardLayout);

    private final PanelTableroPersonajes panelSeleccion = new PanelTableroPersonajes();
    private final PanelTableroPersonajes panelCandidatos = new PanelTableroPersonajes();
    private final JTextArea areaLog = new JTextArea();
    private final JComboBox<Filtro> comboFiltro = new JComboBox<>(Filtro.values());
    private final JComboBox<Personaje> comboAdivinar = new JComboBox<>();
    private final JLabel labelEstado = new JLabel("Elegí tu personaje secreto (clic en una tarjeta)", SwingConstants.CENTER);
    private final JLabel labelSecreto = new JLabel(" ", SwingConstants.CENTER);

    public VentanaHumanoVsIA() {
        super("Humano vs IA");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 680);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(labelEstado, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);

        panelCentral.add(new JScrollPane(panelSeleccion), "seleccion");
        panelCentral.add(construirPanelJuego(), "juego");

        panelSeleccion.setOnClick(this::elegirSecreto);
        panelSeleccion.mostrar(partida.getTablero());

        for (Personaje p : partida.getTablero()) {
            comboAdivinar.addItem(p);
        }
    }

    private JPanel construirPanelJuego() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel panelTablero = new JPanel(new BorderLayout());
        panelTablero.setBorder(BorderFactory.createTitledBorder("Candidatos restantes para adivinar el secreto de la IA"));
        panelTablero.add(new JScrollPane(panelCandidatos), BorderLayout.CENTER);
        panel.add(panelTablero, BorderLayout.CENTER);

        areaLog.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setPreferredSize(new Dimension(900, 160));
        panel.add(scrollLog, BorderLayout.SOUTH);

        JPanel panelAcciones = new JPanel(new FlowLayout());
        JButton btnPreguntar = new JButton("Preguntar");
        JButton btnAdivinar = new JButton("Adivinar");

        btnPreguntar.addActionListener(e -> preguntar());
        btnAdivinar.addActionListener(e -> adivinar());

        panelAcciones.add(new JLabel("Pregunta:"));
        panelAcciones.add(comboFiltro);
        panelAcciones.add(btnPreguntar);
        panelAcciones.add(new JLabel("Adivinar:"));
        panelAcciones.add(comboAdivinar);
        panelAcciones.add(btnAdivinar);

        labelSecreto.setFont(labelSecreto.getFont().deriveFont(Font.BOLD, 14f));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(labelSecreto, BorderLayout.NORTH);
        panelSuperior.add(panelAcciones, BorderLayout.SOUTH);
        panel.add(panelSuperior, BorderLayout.NORTH);

        return panel;
    }

    private void elegirSecreto(Personaje personaje) {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Elegís a " + personaje.getNombre() + " como tu secreto?",
                "Confirmar secreto", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        partida.iniciar(personaje);
        labelSecreto.setText("Tu secreto: " + personaje.getNombre());
        panelCandidatos.mostrar(partida.getCandidatosHumano());
        labelEstado.setText("Tu turno — tratá de adivinar el secreto de la IA");
        log("Elegiste tu secreto. Empieza la partida.");
        cardLayout.show(panelCentral, "juego");
    }

    private void preguntar() {
        Filtro filtro = (Filtro) comboFiltro.getSelectedItem();
        boolean respuesta = partida.humanoPregunta(filtro);
        log("Preguntaste \"" + filtro.getPregunta() + "\" -> " + (respuesta ? "Sí" : "No"));
        panelCandidatos.mostrar(partida.getCandidatosHumano());
        turnoDeLaMaquina();
    }

    private void adivinar() {
        Personaje elegido = (Personaje) comboAdivinar.getSelectedItem();
        if (elegido == null) {
            return;
        }
        boolean acierto = partida.humanoAdivina(elegido.getNombre());
        log("Adivinaste \"" + elegido.getNombre() + "\" -> " + (acierto ? "¡Acierto!" : "Error"));
        finDePartidaSiCorresponde();
    }

    private void turnoDeLaMaquina() {
        if (partida.isTerminada()) {
            finDePartidaSiCorresponde();
            return;
        }

        if (partida.maquinaListaParaAdivinar()) {
            boolean acierto = partida.maquinaAdivina();
            log("La IA intentó adivinar tu secreto -> " + (acierto ? "acertó" : "falló"));
        } else {
            Filtro filtro = partida.maquinaElegirPregunta();
            int opcion = JOptionPane.showConfirmDialog(this,
                    "La IA pregunta: " + filtro.getPregunta(),
                    "Pregunta de la IA", JOptionPane.YES_NO_OPTION);
            boolean respuesta = (opcion == JOptionPane.YES_OPTION);
            partida.maquinaRecibirRespuesta(filtro, respuesta);
            log("La IA preguntó \"" + filtro.getPregunta() + "\" -> " + (respuesta ? "Sí" : "No"));
        }

        finDePartidaSiCorresponde();
        if (!partida.isTerminada()) {
            labelEstado.setText("Tu turno — tratá de adivinar el secreto de la IA");
        }
    }

    private void finDePartidaSiCorresponde() {
        if (partida.isTerminada()) {
            labelEstado.setText(partida.getResultado());
            log(partida.getResultado());
            JOptionPane.showMessageDialog(this, partida.getResultado());
        }
    }

    private void log(String mensaje) {
        areaLog.append(mensaje + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }
}
