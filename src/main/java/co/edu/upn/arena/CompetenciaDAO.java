package co.edu.upn.arena;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompetenciaDAO {

    public static void programar(int equipoA, int equipoB, String fecha) throws SQLException {
        String sql = "INSERT INTO competencia (equipo_a, equipo_b, fecha) VALUES (?, ?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, equipoA);
            ps.setInt(2, equipoB);
            ps.setString(3, fecha);
            ps.executeUpdate();
        }
    }

    public static void guardarResultado(Competencia c) throws SQLException {
        String sql = "UPDATE competencia SET marcador_a = ?, marcador_b = ?, finalizado = 1 WHERE id = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getMarcadorA());
            ps.setInt(2, c.getMarcadorB());
            ps.setInt(3, c.getId());
            ps.executeUpdate();
        }
    }

    public static List<Competencia> listar() throws SQLException {
        List<Competencia> lista = new ArrayList<>();
        // JOIN: trae también el nombre de cada equipo
        String sql = """
                SELECT c.*, a.nombre AS nombre_a, b.nombre AS nombre_b
                FROM competencia c
                JOIN equipo a ON c.equipo_a = a.id
                JOIN equipo b ON c.equipo_b = b.id
                ORDER BY c.fecha
                """;
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Competencia(
                        rs.getInt("id"),
                        rs.getInt("equipo_a"),
                        rs.getString("nombre_a"),
                        rs.getInt("equipo_b"),
                        rs.getString("nombre_b"),
                        rs.getString("fecha"),
                        rs.getInt("marcador_a"),
                        rs.getInt("marcador_b"),
                        rs.getInt("finalizado") == 1));
            }
        }
        return lista;
    }
}

