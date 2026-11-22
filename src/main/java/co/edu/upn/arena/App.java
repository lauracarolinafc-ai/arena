package co.edu.upn.arena;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override

    public void start(Stage ventana) throws Exception {
        FXMLLoader cargador = new FXMLLoader(App.class.getResource("principal.fxml"));
        Scene escena = new Scene(cargador.load(), 1000, 650);
        escena.getStylesheets().add(App.class.getResource("estilos.css").toExternalForm());
        ventana.setTitle("UPN Arena");
        ventana.setScene(escena);
        ventana.show();
    }
    public static void main(String[] args) {
        launch();
    }
}