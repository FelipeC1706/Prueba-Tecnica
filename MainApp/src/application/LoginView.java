package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginView {

    public void showLogin(Stage primaryStage) {
        Label lblTitulo = new Label("Iniciar Sesión");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label lblUsuario = new Label("Usuario:");
        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Ingresa tu usuario");

        Label lblContrasena = new Label("Contraseña:");
        PasswordField txtContrasena = new PasswordField();
        txtContrasena.setPromptText("Ingresa tu contraseña");

        Button btnLogin = new Button("Iniciar sesión");
       
        
        Button btnRegistrar = new Button("Registrarse");
        
        
        Label lblMensaje = new Label();

        btnLogin.setPrefWidth(120);
        btnLogin.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; ");
        btnRegistrar.setPrefWidth(120);
        btnRegistrar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        
       

        HBox botones = new HBox(10, btnLogin, btnRegistrar);
        botones.setAlignment(Pos.CENTER);

        VBox layoutCentral = new VBox(10,
                lblTitulo,
                lblUsuario, txtUsuario,
                lblContrasena, txtContrasena,
                botones,
                lblMensaje
        );
        layoutCentral.setAlignment(Pos.CENTER);
        layoutCentral.setPadding(new Insets(20));
        layoutCentral.setMaxWidth(300);

        BorderPane layout = new BorderPane();
        layout.setCenter(layoutCentral);
        layout.setStyle("-fx-background-color: #f4f4f4;");

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setTitle("Gestor Solicitudes");
        primaryStage.setScene(scene);
        primaryStage.show();

        btnLogin.setOnAction(e -> {
            String usuario = txtUsuario.getText();
            String contrasena = txtContrasena.getText();

            if (validarCredenciales(usuario, contrasena)) {
                RegistroSolicitudView registro = new RegistroSolicitudView();
                registro.mostrarFormulario(obtenerIdUsuario(usuario), primaryStage);
            } else {
                lblMensaje.setText("❌ Usuario o contraseña incorrectos");
            }
        });

        btnRegistrar.setOnAction(e -> {
            RegistroEstudianteView registro = new RegistroEstudianteView();
            registro.mostrarFormulario(primaryStage);
        });
    }

    private boolean validarCredenciales(String usuario, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";

        System.out.println("🔎 Validando: " + usuario + " / " + contrasena);

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Usuario encontrado en la base de datos.");
                return true;
            } else {
                System.out.println("❌ Usuario o contraseña incorrectos.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("❌ Error al validar credenciales: " + e.getMessage());
            return false;
        }
    }

    private int obtenerIdUsuario(String usuario) {
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM usuarios WHERE usuario = ?")) {
            stmt.setString(1, usuario);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("❌ Error al obtener ID del usuario: " + e.getMessage());
        }
        return -1;
    }
}
