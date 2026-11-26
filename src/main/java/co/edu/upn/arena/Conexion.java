package co.edu.upn.arena;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    // El archivo arena.db se crea solo, en la carpeta del proyecto
    private static final String URL = "jdbc:sqlite:arena.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void crearTablas() {
        String jugador = """
                CREATE TABLE IF NOT EXISTS jugador (
                    id        INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre    TEXT NOT NULL,
                    alias     TEXT NOT NULL,
                    correo    TEXT NOT NULL UNIQUE,
                    equipo_id INTEGER DEFAULT 0
                )""";

        String equipo = """
                CREATE TABLE IF NOT EXISTS equipo (
                    id     INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL UNIQUE,
                    juego  TEXT NOT NULL
                )""";

        String competencia = """
                CREATE TABLE IF NOT EXISTS competencia (
                    id         INTEGER PRIMARY KEY AUTOINCREMENT,
                    equipo_a   INTEGER NOT NULL,
                    equipo_b   INTEGER NOT NULL,
                    fecha      TEXT NOT NULL,
                    marcador_a INTEGER DEFAULT 0,
                    marcador_b INTEGER DEFAULT 0,
                    finalizado INTEGER DEFAULT 0
                )""";

        try (Connection con = conectar();
             Statement st = con.createStatement()) {
            st.execute(jugador);
            st.execute(equipo);
            st.execute(competencia);
        } catch (SQLException e) {
            Mensajes.error("No se pudo crear la base de datos: " + e.getMessage());
        }
    }
}
