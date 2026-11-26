package co.edu.upn.arena;

public class FilaTabla {

    private int posicion;
    private String equipo;
    private int jugados;
    private int ganados;
    private int empatados;
    private int perdidos;
    private int diferencia;
    private int puntos;

    public FilaTabla(String equipo) {
        this.equipo = equipo;
    }

    // Suma una competencia: puntos a favor y en contra
    public void sumarPartido(int aFavor, int enContra) {
        jugados++;
        diferencia = diferencia + aFavor - enContra;

        if (aFavor > enContra) {
            ganados++;
            puntos = puntos + 3;
        } else if (aFavor == enContra) {
            empatados++;
            puntos = puntos + 1;
        } else {
            perdidos++;
        }
    }

    public void setPosicion(int posicion) { this.posicion = posicion; }

    public int getPosicion() { return posicion; }
    public String getEquipo() { return equipo; }
    public int getJugados() { return jugados; }
    public int getGanados() { return ganados; }
    public int getEmpatados() { return empatados; }
    public int getPerdidos() { return perdidos; }
    public int getDiferencia() { return diferencia; }
    public int getPuntos() { return puntos; }
}
