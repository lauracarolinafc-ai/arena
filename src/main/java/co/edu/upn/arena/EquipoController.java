package co.edu.upn.arena;

import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class EquipoController {

    private static final int MAXIMO = 3; // integrantes por equipo 3

    @FXML private TextField txtNombre;
    @FXML private TextField txtJuego;
    @FXML private ListView<Equipo> listaEquipos;
    @FXML private ListView<Jugador> listaIntegrantes;
    @FXML private ListView<Jugador> listaLibres;

    @FXML
    private void initialize() {
        cargarEquipos();
        cargarLibres();
    }

    private void cargarEquipos() {
        try {
            listaEquipos.getItems().setAll(EquipoDAO.listar());
        } catch (SQLException e) {
            Mensajes.error("No se pudieron cargar los equipos.");
        }
    }

    private void cargarLibres() {
        try {
            listaLibres.getItems().setAll(JugadorDAO.listarPorEquipo(0));
        } catch (SQLException e) {
            Mensajes.error("No se pudieron cargar los jugadores.");
        }
    }

    @FXML
    private void crear() {
        String nombre = txtNombre.getText().trim();
        String juego = txtJuego.getText().trim();
        if (nombre.isEmpty() || juego.isEmpty()) {
            Mensajes.error("Escribe el nombre del equipo y la disciplina.");
            return;
        }
        try {
            EquipoDAO.guardar(new Equipo(0, nombre, juego));
            txtNombre.clear();
            txtJuego.clear();
            cargarEquipos();
            Mensajes.info("Equipo creado.");
        } catch (SQLException e) {
            Mensajes.error("No se pudo crear. ¿Ya existe un equipo con ese nombre?");
        }
    }

    // Al hacer clic en un equipo, se muestran sus integrantes
    @FXML
    private void verIntegrantes() {
        Equipo equipo = listaEquipos.getSelectionModel().getSelectedItem();
        if (equipo == null) {
            return;
        }
        try {
            listaIntegrantes.getItems().setAll(JugadorDAO.listarPorEquipo(equipo.getId()));
        } catch (SQLException e) {
            Mensajes.error("No se pudieron cargar los integrantes.");
        }
    }

    @FXML
    private void agregar() {
        Equipo equipo = listaEquipos.getSelectionModel().getSelectedItem();
        Jugador jugador = listaLibres.getSelectionModel().getSelectedItem();
        if (equipo == null || jugador == null) {
            Mensajes.error("Selecciona un equipo y un jugador sin equipo.");
            return;
        }
        if (listaIntegrantes.getItems().size() >= MAXIMO) {
            Mensajes.error("Este equipo ya tiene " + MAXIMO + " integrantes.");
            return;
        }
        try {
            JugadorDAO.asignarEquipo(jugador.getId(), equipo.getId());
            verIntegrantes();
            cargarLibres();
        } catch (SQLException e) {
            Mensajes.error("No se pudo agregar el jugador.");
        }
    }

    @FXML
    private void quitar() {
        Jugador jugador = listaIntegrantes.getSelectionModel().getSelectedItem();
        if (jugador == null) {
            Mensajes.error("Selecciona un integrante para quitarlo.");
            return;
        }
        try {
            JugadorDAO.asignarEquipo(jugador.getId(), 0); // 0 = sin equipo
            verIntegrantes();
            cargarLibres();
        } catch (SQLException e) {
            Mensajes.error("No se pudo quitar el jugador.");
        }
    }
}

