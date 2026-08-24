package org.example.objetos;

//genero 1 = mujer, 0 hombre,   el resto 1 si y 0 no
public class personaje {
    private int id;
    private String nombre;
    private boolean ;
    private boolean genero;
    private boolean anteojos;
    private boolean sombrero;
    private boolean barba;
    private boolean sonrisa;
    private boolean pelo_largo;



    public personaje(int id, String nombre, boolean genero, boolean anteojos,
                     boolean sombrero, boolean barba, boolean sonrisa,
                     boolean pelo_largo) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.anteojos = anteojos;
        this.sombrero = sombrero;
        this.barba = barba;
        this.sonrisa = sonrisa;
        this.pelo_largo = pelo_largo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isGenero() {
        return genero;
    }

    public void setGenero(boolean genero) {
        this.genero = genero;
    }

    public boolean isAnteojos() {
        return anteojos;
    }

    public void setAnteojos(boolean anteojos) {
        this.anteojos = anteojos;
    }

    public boolean isSombrero() {
        return sombrero;
    }

    public void setSombrero(boolean sombrero) {
        this.sombrero = sombrero;
    }

    public boolean isBarba() {
        return barba;
    }

    public void setBarba(boolean barba) {
        this.barba = barba;
    }

    public boolean isSonrisa() {
        return sonrisa;
    }

    public void setSonrisa(boolean sonrisa) {
        this.sonrisa = sonrisa;
    }

    public boolean isPelo_largo() {
        return pelo_largo;
    }

    public void setPelo_largo(boolean pelo_largo) {
        this.pelo_largo = pelo_largo;
    }
}