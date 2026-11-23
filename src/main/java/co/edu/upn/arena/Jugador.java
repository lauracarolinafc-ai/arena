package co.edu.upn.arena;

public class Jugador {

    private int id;
    private String nombre;
    private String alias;
    private String correo;
    private int equipoId; // 0 significa "sin equipo"

    public Jugador(int id, String nombre, String alias, String correo, int equipoId) {
        this.id = id;
        this.nombre = nombre;
        this.alias = alias;
        this.correo = correo;
        this.equipoId = equipoId;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getAlias() { return alias; }
    public String getCorreo() { return correo; }
    public int getEquipoId() { return equipoId; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setAlias(String alias) { this.alias = alias; }
    public void setCorreo(String correo) { this.correo = correo; }

    // Así se ve el jugador dentro de una lista
    @Override
    public String toString() {
        return alias + " (" + nombre + ")";
    }
}
