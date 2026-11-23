package co.edu.upn.arena;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Mensajes {

    public static void info(String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION, texto);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    public static void error(String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR, texto);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    // Devuelve true si la persona pulsa "Aceptar"
    public static boolean confirmar(String texto) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, texto);
        alerta.setHeaderText(null);
        return alerta.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}