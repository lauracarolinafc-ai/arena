package co.edu.upn.arena;

import java.sql.SQLException;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CompetenciaController {

    @FXML private ComboBox<Equipo> cbEquipoA;
    @FXML private ComboBox<Equipo> cbEquipoB;
    @FXML private DatePicker dpFecha;
    @FXML private TableView<Competencia> tabla;
    @FXML private TableColumn<Competencia, String> colEquipoA;
    @FXML private TableColumn<Competencia, String> colEquipoB;
    @FXML private TableColumn<Competencia, String> colFecha;
    @FXML private TableColumn<Competencia, String> colMarcador;
    @FXML private TableColumn<Competencia, String> colEstado;
    @FXML private TextField txtMarcadorA;
    @FXML private TextField txtMarcadorB;

    @FXML
    private void initialize() {
        colEquipoA.setCellValueFactory(new PropertyValueFactory<>("nombreA"));
        colEquipoB.setCellValueFactory(new PropertyValueFactory<>("nombreB"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colMarcador.setCellValueFactory(new PropertyValueFactory<>("marcador"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        try {
            cbEquipoA.getItems().setAll(EquipoDAO.listar());
            cbEquipoB.getItems().setAll(EquipoDAO.listar());
        } catch (SQLException e) {
            Mensajes.error("No se pudieron cargar los equipos.");
        }
        cargar();
    }

    private void cargar() {
        try {
            tabla.getItems().setAll(CompetenciaDAO.listar());
        } catch (SQLException e) {
            Mensajes.error("No se pudieron cargar las competencias.");
        }
    }

    @FXML
    private void programar() {
        Equipo a = cbEquipoA.getValue();
        Equipo b = cbEquipoB.getValue();
        LocalDate fecha = dpFecha.getValue();
        if (a == null || b == null || fecha == null) {
            Mensajes.error("Elige los dos equipos y la fecha.");
            return;
        }
        if (a.getId() == b.getId()) {
            Mensajes.error("Un equipo no puede competir contra sí mismo.");
            return;
        }
        try {
            CompetenciaDAO.programar(a.getId(), b.getId(), fecha.toString());
            cargar();
            Mensajes.info("Competencia programada.");
        } catch (SQLException e) {
            Mensajes.error("No se pudo programar la competencia.");
        }
    }

    @FXML
    private void registrarResultado() {
        Competencia c = tabla.getSelectionModel().getSelectedItem();
        if (c == null) {
            Mensajes.error("Primero selecciona una competencia de la tabla.");
            return;
        }
        try {
            int a = Integer.parseInt(txtMarcadorA.getText().trim());
            int b = Integer.parseInt(txtMarcadorB.getText().trim());
            c.registrarResultado(a, b); // aquí la competencia revisa sus reglas
            CompetenciaDAO.guardarResultado(c);
            txtMarcadorA.clear();
            txtMarcadorB.clear();
            cargar();
            Mensajes.info("Resultado guardado.");
        } catch (NumberFormatException e) {
            Mensajes.error("El marcador debe ser un número entero.");
        } catch (IllegalArgumentException e) {
            Mensajes.error(e.getMessage()); // muestra el mensaje que escribimos en Competencia
        } catch (SQLException e) {
            Mensajes.error("No se pudo guardar el resultado.");
            cargar();
        }
    }
}
