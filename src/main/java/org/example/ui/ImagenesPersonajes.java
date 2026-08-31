package org.example.ui;

import org.example.objetos.Personaje;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

// Carga los retratos recortados del sprite original (src/main/resources/personajes/*.png),
// uno por personaje, identificados por nombre normalizado (minúscula, sin tildes).
public class ImagenesPersonajes {

    private static final Pattern DIACRITICOS = Pattern.compile("\\p{M}");
    private static final Map<String, ImageIcon> cache = new HashMap<>();

    private ImagenesPersonajes() {
    }

    public static ImageIcon obtener(Personaje personaje, int lado) {
        String clave = normalizar(personaje.getNombre()) + "@" + lado;
        return cache.computeIfAbsent(clave, k -> cargar(personaje, lado));
    }

    private static ImageIcon cargar(Personaje personaje, int lado) {
        String archivo = "/personajes/" + normalizar(personaje.getNombre()) + ".png";
        URL recurso = ImagenesPersonajes.class.getResource(archivo);
        if (recurso == null) {
            return null;
        }
        ImageIcon original = new ImageIcon(recurso);
        Image escalada = original.getImage().getScaledInstance(lado, lado, Image.SCALE_SMOOTH);
        return new ImageIcon(escalada);
    }

    private static String normalizar(String nombre) {
        String sinTildes = DIACRITICOS.matcher(Normalizer.normalize(nombre, Normalizer.Form.NFD)).replaceAll("");
        return sinTildes.toLowerCase();
    }
}
