package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegistroSolicitudView {

    public void mostrarFormulario(int idUsuario, Stage stageAnterior) {
        Label lblTitulo = new Label("Registrar Solicitud");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label lblTipo = new Label("Tipo de solicitud:");
        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("Registro", "Bienestar", "Biblioteca", "Otro");
        cbTipo.setPromptText("Selecciona un tipo");

        Label lblDescripcion = new Label("Descripción:");
        TextArea txtDescripcion = new TextArea();
        txtDescripcion.setPromptText("Describe tu solicitud...");
        txtDescripcion.setPrefRowCount(4);

        Button btnEnviar = new Button("Enviar");
        Button btnVerSolicitudes = new Button("Ver Mis Solicitudes");
        Button btnVolver = new Button("Volver al inicio");

        btnEnviar.setPrefWidth(150);
        btnVerSolicitudes.setPrefWidth(150);
        btnVolver.setPrefWidth(150);
        
        btnEnviar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnVerSolicitudes.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnVolver.setStyle("-fx-background-color: #E11600; -fx-text-fill: white;");
        

        Label lblMensaje = new Label();

        // Botones principales (en fila)
        HBox botones = new HBox(10, btnEnviar, btnVerSolicitudes);
        botones.setAlignment(Pos.CENTER);

        // Layout principal
        VBox formulario = new VBox(10,
                lblTitulo,
                lblTipo, cbTipo,
                lblDescripcion, txtDescripcion,
                botones,
                btnVolver,
                lblMensaje
        );
        formulario.setAlignment(Pos.CENTER);
        formulario.setPadding(new Insets(20));
        formulario.setMaxWidth(400);

        BorderPane layout = new BorderPane();
        layout.setCenter(formulario);
        layout.setStyle("-fx-background-color: #f4f4f4;");

        Scene scene = new Scene(layout, 500, 480);
        Stage nuevoStage = new Stage();
        nuevoStage.setTitle("Formulario de Solicitud");
        nuevoStage.setScene(scene);
        nuevoStage.show();
        stageAnterior.close();

        // Evento enviar solicitud
        btnEnviar.setOnAction(e -> {
            String tipo = cbTipo.getValue();
            String descripcion = txtDescripcion.getText();

            if (tipo == null || descripcion.isEmpty()) {
                lblMensaje.setText("❗ Por favor completa todos los campos.");
                return;
            }

            try (Connection conn = ConexionDB.conectar()) {
                String sql = "INSERT INTO solicitudes (id_usuario, tipo_solicitud, descripcion) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idUsuario);
                stmt.setString(2, tipo);
                stmt.setString(3, descripcion);
                stmt.executeUpdate();

                lblMensaje.setText("✅ Solicitud registrada correctamente.");
                cbTipo.setValue(null);
                txtDescripcion.clear();

            } catch (Exception ex) {
                lblMensaje.setText("❌ Error al guardar: " + ex.getMessage());
            }
        });

        // Evento ver solicitudes
        btnVerSolicitudes.setOnAction(e -> {
            ListadoSolicitudesView listado = new ListadoSolicitudesView();
            listado.mostrarListado(idUsuario);
        });

        // Evento volver al login
        btnVolver.setOnAction(e -> {
            LoginView login = new LoginView();
            login.showLogin(new Stage());
            Stage actual = (Stage) btnVolver.getScene().getWindow();
            actual.close();
        });
    }
}
