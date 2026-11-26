package co.edu.upn.arena;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    public static void guardar(Equipo e) throws SQLException {
        String sql = "INSERT INTO equipo (nombre, juego) VALUES (?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getJuego());
            ps.executeUpdate();
        }
    }

    public static List<Equipo> listar() throws SQLException {
        List<Equipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipo ORDER BY nombre";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Equipo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("juego")));
            }
        }
        return lista;
    }
}
