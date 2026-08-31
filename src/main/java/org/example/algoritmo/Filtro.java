package org.example.algoritmo;

import org.example.objetos.Personaje;

import java.util.function.Predicate;

// Une cada atributo booleano de Personaje con su pregunta en español,
// para no repetir un bloque if/else por cada atributo (como pasaba antes
// en PonderadoCaracteristicas).
public enum Filtro {

    GENERO("¿Es hombre?", Personaje::isGenero),
    ANTEOJOS("¿Usa anteojos?", Personaje::isAnteojos),
    SOMBRERO("¿Usa sombrero?", Personaje::isSombrero),
    BARBA("¿Tiene barba?", Personaje::isBarba),
    SONRISA("¿Está sonriendo?", Personaje::isSonrisa),
    PELO_LARGO("¿Tiene el pelo largo?", Personaje::isPelo_largo);

    private final String pregunta;
    private final Predicate<Personaje> extractor;

    Filtro(String pregunta, Predicate<Personaje> extractor) {
        this.pregunta = pregunta;
        this.extractor = extractor;
    }

    public String getPregunta() {
        return pregunta;
    }

    public boolean valorDe(Personaje personaje) {
        return extractor.test(personaje);
    }
}
