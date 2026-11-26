package co.edu.upn.arena;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {

    public static void guardar(Jugador j) throws SQLException {
        String sql = "INSERT INTO jugador (nombre, alias, correo) VALUES (?, ?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, j.getNombre());
            ps.setString(2, j.getAlias());
            ps.setString(3, j.getCorreo());
            ps.executeUpdate();
        }
    }

    public static void actualizar(Jugador j) throws SQLException {
        String sql = "UPDATE jugador SET nombre = ?, alias = ?, correo = ? WHERE id = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, j.getNombre());
            ps.setString(2, j.getAlias());
            ps.setString(3, j.getCorreo());
            ps.setInt(4, j.getId());
            ps.executeUpdate();
        }
    }

    public static void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM jugador WHERE id = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public static List<Jugador> listar() throws SQLException {
        return consultar("SELECT * FROM jugador ORDER BY nombre", -1);
    }

    // equipoId = 0 devuelve los jugadores que todavía no tienen equipo
    public static List<Jugador> listarPorEquipo(int equipoId) throws SQLException {
        return consultar("SELECT * FROM jugador WHERE equipo_id = ? ORDER BY alias", equipoId);
    }

    public static void asignarEquipo(int jugadorId, int equipoId) throws SQLException {
        String sql = "UPDATE jugador SET equipo_id = ? WHERE id = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, equipoId);
            ps.setInt(2, jugadorId);
            ps.executeUpdate();
        }
    }

    // Método de apoyo: ejecuta un SELECT y convierte cada fila en un Jugador
    private static List<Jugador> consultar(String sql, int equipoId) throws SQLException {
        List<Jugador> lista = new ArrayList<>();
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (equipoId >= 0) {
                ps.setInt(1, equipoId);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Jugador(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("alias"),
                        rs.getString("correo"),
                        rs.getInt("equipo_id")));
            }
        }
        return lista;
    }
}
