package org.example.algoritmo;

import org.example.objetos.personaje;

import java.util.List;

public class ponderado_caracteristicas {

    int ponderado;

    public int que_pregunto(List<personaje> personajes){
        for (int i = 0; i < personajes.size(); i++){
            personaje personaje = personajes.get(i);
            if(personaje.isGenero()){
                ponderado++;
            }
        }

    }

}
