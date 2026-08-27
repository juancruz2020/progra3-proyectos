package org.example.bd;

import org.example.objetos.Personaje;

public class Preguntas {


    public static String preguntar(String caracteristica) {

        switch (caracteristica) {

            case "genero":
                return "¿Es hombre?";

            case "anteojos":
                return "¿Tiene anteojos?";

            case "sombrero":
                return "¿Tiene sombrero?";

            case "barba":
                return "¿Tiene barba?";

            case "sonrisa":
                return "¿Está sonriendo?";

            case "pelo_largo":
                return "¿Tiene pelo largo?";

            default:
                return "";
        }
    }

    public static boolean tieneCaracteristica(
            Personaje personaje,
            String caracteristica) {

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