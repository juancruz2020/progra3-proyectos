package org.example.util;

// Segundo canal de verificación del proceso interno: además de lo que se ve
// en la interfaz Swing, cada algoritmo relevante imprime por consola sus
// decisiones internas paso a paso (útil para la defensa oral del trabajo).
public class Trazador {

    private Trazador() {
    }

    public static void log(String mensaje) {
        System.out.println("[TRAZA] " + mensaje);
    }

    public static void seccion(String titulo) {
        System.out.println();
        System.out.println("===== " + titulo + " =====");
    }
}
