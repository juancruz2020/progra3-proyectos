package org.example.ui;

import org.example.objetos.Personaje;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

// Grilla reutilizable de tarjetas (foto + atributos) para los 23 personajes.
// Si se le da un callback de click, las tarjetas funcionan como botones (para
// elegir el secreto); si no, quedan deshabilitadas y funcionan solo como
// panel informativo (para mostrar candidatos restantes).
public class PanelTableroPersonajes extends JPanel {

    private static final int TAMANIO_ICONO = 100;

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

    // Tarjeta de solo lectura (no es un botón): para mostrar un personaje fijo,
    // como el propio secreto elegido.
    public static JPanel crearTarjetaEstatica(Personaje p, int tamanioIcono) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        panel.setBackground(Color.WHITE);

        JLabel labelIcono = new JLabel(ImagenesPersonajes.obtener(p, tamanioIcono));
        labelIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelTexto = new JLabel(textoDescriptivo(p));
        labelTexto.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(labelIcono);
        panel.add(labelTexto);
        return panel;
    }

    private JButton crearTarjeta(Personaje p) {
        JButton boton = new JButton(textoDescriptivo(p));
        boton.setIcon(ImagenesPersonajes.obtener(p, TAMANIO_ICONO));
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setMargin(new Insets(6, 6, 6, 6));

        boton.setEnabled(onClick != null);
        boton.addActionListener(e -> {
            if (onClick != null) {
                onClick.accept(p);
            }
        });
        return boton;
    }

    private static String textoDescriptivo(Personaje p) {
        return "<html><div style='text-align:center'><b>" + p.getNombre() + "</b><br>"
                + (p.isGenero() ? "Hombre" : "Mujer") + "<br>"
                + "Anteojos: " + si(p.isAnteojos()) + "<br>"
                + "Sombrero: " + si(p.isSombrero()) + "<br>"
                + "Barba: " + si(p.isBarba()) + "<br>"
                + "Sonrisa: " + si(p.isSonrisa()) + "<br>"
                + "Pelo largo: " + si(p.isPelo_largo()) + "</div></html>";
    }

    private static String si(boolean valor) {
        return valor ? "Sí" : "No";
    }
}
