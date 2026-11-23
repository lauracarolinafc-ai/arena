package co.edu.upn.arena;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class PrincipalController {

    @FXML
    private StackPane contenido; // el área central de la ventana

    // Carga un archivo .fxml y lo pone en el centro
    private void abrir(String archivo) {
        try {
            Parent vista = FXMLLoader.load(getClass().getResource(archivo));
            contenido.getChildren().setAll(vista);
        } catch (Exception e) {
            Mensajes.error("No se pudo abrir " + archivo);
            e.printStackTrace(); // el detalle aparece en la consola de NetBeans
        }
    }

    @FXML
    private void verJugadores() {
        abrir("jugadores.fxml");
    }

    @FXML
    private void verEquipos() {
        abrir("equipos.fxml");
    }

    @FXML
    private void verCompetencias() {
        abrir("competencias.fxml");
    }

    @FXML
    private void verClasificacion() {
        abrir("clasificacion.fxml");
    }
}
