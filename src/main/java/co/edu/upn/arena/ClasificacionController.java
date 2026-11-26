package co.edu.upn.arena;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClasificacionController {

    @FXML private TableView<FilaTabla> tabla;
    @FXML private TableColumn<FilaTabla, Integer> colPos;
    @FXML private TableColumn<FilaTabla, String> colEquipo;
    @FXML private TableColumn<FilaTabla, Integer> colPJ;
    @FXML private TableColumn<FilaTabla, Integer> colPG;
    @FXML private TableColumn<FilaTabla, Integer> colPE;
    @FXML private TableColumn<FilaTabla, Integer> colPP;
    @FXML private TableColumn<FilaTabla, Integer> colDif;
    @FXML private TableColumn<FilaTabla, Integer> colPts;

    @FXML
    private void initialize() {
        colPos.setCellValueFactory(new PropertyValueFactory<>("posicion"));
        colEquipo.setCellValueFactory(new PropertyValueFactory<>("equipo"));
        colPJ.setCellValueFactory(new PropertyValueFactory<>("jugados"));
        colPG.setCellValueFactory(new PropertyValueFactory<>("ganados"));
        colPE.setCellValueFactory(new PropertyValueFactory<>("empatados"));
        colPP.setCellValueFactory(new PropertyValueFactory<>("perdidos"));
        colDif.setCellValueFactory(new PropertyValueFactory<>("diferencia"));
        colPts.setCellValueFactory(new PropertyValueFactory<>("puntos"));
        calcular();
    }

    @FXML
    private void calcular() {
        try {
            // 1. Una fila vacía por cada equipo. El Map es como un diccionario: id -> fila
            Map<Integer, FilaTabla> filas = new HashMap<>();
            for (Equipo e : EquipoDAO.listar()) {
                filas.put(e.getId(), new FilaTabla(e.getNombre()));
            }

            // 2. Cada competencia finalizada suma a los dos equipos
            for (Competencia c : CompetenciaDAO.listar()) {
                if (c.isFinalizado()) {
                    filas.get(c.getEquipoAId()).sumarPartido(c.getMarcadorA(), c.getMarcadorB());
                    filas.get(c.getEquipoBId()).sumarPartido(c.getMarcadorB(), c.getMarcadorA());
                }
            }

            // 3. Ordenar: más puntos arriba; si empatan, mejor diferencia
            List<FilaTabla> lista = new ArrayList<>(filas.values());
            lista.sort((x, y) -> {
                if (x.getPuntos() != y.getPuntos()) {
                    return y.getPuntos() - x.getPuntos();
                }
                return y.getDiferencia() - x.getDiferencia();
            });

            // 4. Numerar las posiciones y mostrar
            for (int i = 0; i < lista.size(); i++) {
                lista.get(i).setPosicion(i + 1);
            }
            tabla.getItems().setAll(lista);
        } catch (SQLException e) {
            Mensajes.error("No se pudo calcular la clasificación.");
        }
    }
}
