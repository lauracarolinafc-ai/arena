package co.edu.upn.arena;

public class Equipo {

    private int id;
    private String nombre;
    private String juego;

    public Equipo(int id, String nombre, String juego) {
        this.id = id;
        this.nombre = nombre;
        this.juego = juego;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getJuego() { return juego; }

    @Override
    public String toString() {
        return nombre + " · " + juego;
    }
}
