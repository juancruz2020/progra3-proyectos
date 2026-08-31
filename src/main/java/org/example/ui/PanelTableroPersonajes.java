package org.example.ui;

import org.example.objetos.Personaje;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

// Grilla reutilizable de tarjetas de texto (sin imágenes, a propósito: la
// app pedida es Swing simple). Si se le da un callback de click, las
// tarjetas funcionan como botones (para elegir el secreto); si no, quedan
// deshabilitadas y funcionan solo como panel informativo (para mostrar
// candidatos restantes).
public class PanelTableroPersonajes extends JPanel {

    private Consumer<Personaje> onClick;

    public PanelTableroPersonajes() {
        setLayout(new GridLayout(0, 4, 6, 6));
    }

    public void setOnClick(Consumer<Personaje> onClick) {
        this.onClick = onClick;
        for (Component componente : getComponents()) {
            componente.setEnabled(onClick != null);
        }
    }

    public void mostrar(List<Personaje> personajes) {
        removeAll();
        for (Personaje p : personajes) {
            add(crearTarjeta(p));
        }
        revalidate();
        repaint();
    }

    private JButton crearTarjeta(Personaje p) {
        String texto = "<html><b>" + p.getNombre() + "</b><br>"
                + (p.isGenero() ? "Hombre" : "Mujer") + "<br>"
                + "Anteojos: " + si(p.isAnteojos()) + "<br>"
                + "Sombrero: " + si(p.isSombrero()) + "<br>"
                + "Barba: " + si(p.isBarba()) + "<br>"
                + "Sonrisa: " + si(p.isSonrisa()) + "<br>"
                + "Pelo largo: " + si(p.isPelo_largo()) + "</html>";

        JButton boton = new JButton(texto);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setMargin(new Insets(6, 6, 6, 6));

        // Se consulta "onClick" recién al hacer click (no al crear la tarjeta),
        // así no importa si setOnClick() se llamó antes o después de mostrar().
        boton.setEnabled(onClick != null);
        boton.addActionListener(e -> {
            if (onClick != null) {
                onClick.accept(p);
            }
        });
        return boton;
    }

    private String si(boolean valor) {
        return valor ? "Sí" : "No";
    }
}
