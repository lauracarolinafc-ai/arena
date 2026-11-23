package co.edu.upn.arena;

public class Competencia {

    private int id;
    private int equipoAId;
    private String nombreA;
    private int equipoBId;
    private String nombreB;
    private String fecha;
    private int marcadorA;
    private int marcadorB;
    private boolean finalizado;

    public Competencia(int id, int equipoAId, String nombreA, int equipoBId, String nombreB, String fecha, int marcadorA, int marcadorB, boolean finalizado) {
        this.id = id;
        this.equipoAId = equipoAId;
        this.nombreA = nombreA;
        this.equipoBId = equipoBId;
        this.nombreB = nombreB;
        this.fecha = fecha;
        this.marcadorA = marcadorA;
        this.marcadorB = marcadorB;
        this.finalizado = finalizado;
    }

    // La competencia protege sus propias reglas
    public void registrarResultado(int a, int b) {
        if (finalizado) {
            throw new IllegalArgumentException("Esta competencia ya tiene resultado.");
        }
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("El marcador no puede ser negativo.");
        }
        marcadorA = a;
        marcadorB = b;
        finalizado = true;
    }

    public String getEstado() {
        if (finalizado) {
            return "Finalizado";
        }
        return "Programado";
    }

    public String getMarcador() {
        if (finalizado) {
            return marcadorA + " - " + marcadorB;
        }
        return "Por jugar";
    }

    public int getId() { return id; }
    public int getEquipoAId() { return equipoAId; }
    public String getNombreA() { return nombreA; }
    public int getEquipoBId() { return equipoBId; }
    public String getNombreB() { return nombreB; }
    public String getFecha() { return fecha; }
    public int getMarcadorA() { return marcadorA; }
    public int getMarcadorB() { return marcadorB; }
    public boolean isFinalizado() { return finalizado; }
}
