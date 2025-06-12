package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashSet;

public class RegistroEstudianteView {

    public void mostrarFormulario(Stage loginStage) {
        Label lblTitulo = new Label("Registro de Estudiante");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label lblNombre = new Label("Nombre completo:");
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Ej: Juan Pérez");

        Label lblUsuario = new Label("Nombre de usuario:");
        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Ej: juanp");

        Label lblContrasena = new Label("Contraseña:");
        PasswordField txtContrasena = new PasswordField();
        txtContrasena.setPromptText("********");

        Button btnRegistrar = new Button("Registrar");
        Label lblMensaje = new Label();
        
        Button btnVolver = new Button("Volver al inicio");
        btnVolver.setPrefWidth(150);

        btnRegistrar.setPrefWidth(150);

        VBox layoutCentral = new VBox(10,
                lblTitulo,
                lblNombre, txtNombre,
                lblUsuario, txtUsuario,
                lblContrasena, txtContrasena,
                btnRegistrar,
                lblMensaje,
                btnVolver
        );
        layoutCentral.setAlignment(Pos.CENTER);
        layoutCentral.setPadding(new Insets(20));
        layoutCentral.setMaxWidth(350);

        BorderPane layout = new BorderPane();
        layout.setCenter(layoutCentral);
        layout.setStyle("-fx-background-color: #f4f4f4;");

        Scene scene = new Scene(layout, 450, 420);

        Stage ventana = new Stage();
        ventana.setTitle("Registro de Estudiante");
        ventana.setScene(scene);
        ventana.show();

        // Acción del botón registrar
        btnRegistrar.setOnAction(e -> {
            String nombre = txtNombre.getText();
            String usuario = txtUsuario.getText();
            String contrasena = txtContrasena.getText();

            if (nombre.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()) {
                lblMensaje.setText("❗ Todos los campos son obligatorios.");
                return;
            }

            try (Connection conn = ConexionDB.conectar()) {
                String sql = "INSERT INTO usuarios (usuario, contrasena, nombre_completo) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, usuario);
                stmt.setString(2, contrasena);
                stmt.setString(3, nombre);
                stmt.executeUpdate();

                lblMensaje.setText("✅ Usuario registrado correctamente.");
                txtNombre.clear();
                txtUsuario.clear();
                txtContrasena.clear();

            } catch (Exception ex) {
                lblMensaje.setText("❌ Error: " + ex.getMessage());
            }
        });
        

        
        
        //Acción del botón volver
        btnVolver.setOnAction(e->{
            LoginView login = new LoginView();
            login.showLogin(new Stage());
            
            Stage ventanaActual = (Stage)btnVolver.getScene().getWindow();
            ventanaActual.close();
        });
        
       btnRegistrar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
       btnVolver.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
    }
}

