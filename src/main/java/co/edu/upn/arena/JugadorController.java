package co.edu.upn.arena;

import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class JugadorController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtAlias;
    @FXML private TextField txtCorreo;
    @FXML private TableView<Jugador> tabla;
    @FXML private TableColumn<Jugador, String> colNombre;
    @FXML private TableColumn<Jugador, String> colAlias;
    @FXML private TableColumn<Jugador, String> colCorreo;

    private ObservableList<Jugador> jugadores = FXCollections.observableArrayList();

    // Se ejecuta solo, apenas se abre la pantalla
    @FXML
    private void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colAlias.setCellValueFactory(new PropertyValueFactory<>("alias"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        tabla.setItems(jugadores);
        cargar();
    }

    // Trae los jugadores desde la base de datos
    private void cargar() {
        try {
            jugadores.setAll(JugadorDAO.listar());
        } catch (SQLException e) {
            Mensajes.error("No se pudieron cargar los jugadores.");
        }
    }

    @FXML
    private void guardar() {
        if (!datosValidos()) {
            return;
        }
        try {
            JugadorDAO.guardar(new Jugador(0, txtNombre.getText().trim(),
                    txtAlias.getText().trim(), txtCorreo.getText().trim(), 0));
            cargar();
            limpiar();
            Mensajes.info("Jugador registrado.");
        } catch (SQLException e) {
            Mensajes.error("No se pudo guardar. ¿Ese correo ya está registrado?");
        }
    }

    @FXML
    private void actualizar() {
        Jugador j = tabla.getSelectionModel().getSelectedItem();
        if (j == null) {
            Mensajes.error("Primero selecciona un jugador de la tabla.");
            return;
        }
        if (!datosValidos()) {
            return;
        }
        j.setNombre(txtNombre.getText().trim());
        j.setAlias(txtAlias.getText().trim());
        j.setCorreo(txtCorreo.getText().trim());
        try {
            JugadorDAO.actualizar(j);
            cargar();
            limpiar();
            Mensajes.info("Datos actualizados.");
        } catch (SQLException e) {
            Mensajes.error("No se pudo actualizar. ¿Ese correo ya está registrado?");
            cargar();
        }
    }

    @FXML
    private void eliminar() {
        Jugador j = tabla.getSelectionModel().getSelectedItem();
        if (j == null) {
            Mensajes.error("Primero selecciona un jugador de la tabla.");
            return;
        }
        if (Mensajes.confirmar("¿Seguro que quieres eliminar a " + j.getAlias() + "?")) {
            try {
                JugadorDAO.eliminar(j.getId());
                cargar();
                limpiar();
            } catch (SQLException e) {
                Mensajes.error("No se pudo eliminar.");
            }
        }
    }

    // Al hacer clic en una fila, sus datos pasan al formulario
    @FXML
    private void seleccionar() {
        Jugador j = tabla.getSelectionModel().getSelectedItem();
        if (j != null) {
            txtNombre.setText(j.getNombre());
            txtAlias.setText(j.getAlias());
            txtCorreo.setText(j.getCorreo());
        }
    }

    @FXML
    private void limpiar() {
        txtNombre.clear();
        txtAlias.clear();
        txtCorreo.clear();
        tabla.getSelectionModel().clearSelection();
    }

    private boolean datosValidos() {
        if (txtNombre.getText().isBlank() || txtAlias.getText().isBlank()
                || txtCorreo.getText().isBlank()) {
            Mensajes.error("Completa nombre, alias y correo.");
            return false;
        }
        if (!txtCorreo.getText().contains("@")) {
            Mensajes.error("El correo debe tener @.");
            return false;
        }
        return true;
    }
}
