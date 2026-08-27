package org.example;

import java.util.Scanner;
import org.example.juego.*;
public class Main {
    public static void main(String[] args) {

        HumanoVsIa hvi = new HumanoVsIa();

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== ADIVINA QUIÉN ===");
        System.out.println("1. Jugar contra la IA");
        System.out.println("2. IA contra IA");
        System.out.print("Elegí una opción: ");

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                System.out.println("Iniciando juego...");
                hvi.juego();
                break;

            case 2:
                System.out.println("Iniciando IA vs IA...");
                // Acá llamamos al juego IA vs IA
                break;

            default:
                System.out.println("Opción inválida.");
                break;
        }

        scanner.close();
    }
}