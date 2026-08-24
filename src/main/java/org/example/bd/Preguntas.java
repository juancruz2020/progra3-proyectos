package org.example.bd;

import java.util.ArrayList;
import java.util.List;

public class Preguntas {
    private List<String> pregunta = new ArrayList<>();
    private Preguntas() {
        pregunta.add("¿Es hombre?");
        pregunta.add("¿Tiene anteojos?");
        pregunta.add("¿Tiene sombrero?");
        pregunta.add("¿Tiene barba?");
        pregunta.add("¿Está sonriendo?");
        pregunta.add("¿Tiene el pelo largo?");
    }
    public void preguntar(int i){
        System.out.println(pregunta.get(i));
    }
}
