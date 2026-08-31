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

        if (onClick != null) {
            boton.addActionListener(e -> onClick.accept(p));
        } else {
            boton.setEnabled(false);
        }
        return boton;
    }

    private String si(boolean valor) {
        return valor ? "Sí" : "No";
    }
}
