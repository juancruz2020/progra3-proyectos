package org.example.bd;

import org.example.objetos.Personaje;

public class Preguntas {

    public static void preguntar(String caracteristica) {

        switch (caracteristica) {
            case "genero":
                System.out.println("¿Es hombre?");
                break;

            case "anteojos":
                System.out.println("¿Tiene anteojos?");
                break;

            case "sombrero":
                System.out.println("¿Tiene sombrero?");
                break;

            case "barba":
                System.out.println("¿Tiene barba?");
                break;

            case "sonrisa":
                System.out.println("¿Está sonriendo?");
                break;

            case "pelo_largo":
                System.out.println("¿Tiene pelo largo?");
                break;
        }
    }

    public static boolean tieneCaracteristica(Personaje personaje, String caracteristica) {

    switch (caracteristica) {
        case "genero":
            return personaje.isGenero();

        case "anteojos":
            return personaje.isAnteojos();

        case "sombrero":
            return personaje.isSombrero();

        case "barba":
            return personaje.isBarba();

        case "sonrisa":
            return personaje.isSonrisa();

        case "pelo_largo":
            return personaje.isPelo_largo();

        default:
            return false;
    }
}
}