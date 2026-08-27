package org.example.algoritmo;

import org.example.objetos.Personaje;
import org.example.bd.*;
import java.util.List;
import java.util.Scanner;

public class TurnoHumano {

    private String caracteristica;

    public Personaje turno(List<Personaje> personajes) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Qué característica querés preguntar?");
        System.out.println("0 - genero");
        System.out.println("1 - anteojos");
        System.out.println("2 - sombrero");
        System.out.println("3 - barba");
        System.out.println("4 - sonrisa");
        System.out.println("5 - pelo largo");

        int opcion = scanner.nextInt();


        switch (opcion) {
            case 0:
                caracteristica = "genero";
                break;
            case 1:
                caracteristica = "anteojos";
                break;
            case 2:
                caracteristica = "sombrero";
                break;
            case 3:
                caracteristica = "barba";
                break;
            case 4:
                caracteristica = "sonrisa";
                break;
            case 5:
                caracteristica = "pelo_largo";
                break;
        }

        Preguntas.preguntar(caracteristica);

        System.out.println("0 = Sí");
        System.out.println("1 = No");

        int respuesta = scanner.nextInt();

        if (respuesta == 0) {
            personajes.removeIf(
                    p -> !Preguntas.tieneCaracteristica(p, caracteristica)
            );
        } else {
            personajes.removeIf(
                    p -> Preguntas.tieneCaracteristica(p, caracteristica)
            );
        }

        return null;
    }
}