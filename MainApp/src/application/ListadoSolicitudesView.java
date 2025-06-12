package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ListadoSolicitudesView {

    public void mostrarListado(int idUsuario) {
        Label lblTitulo = new Label("Mis Solicitudes");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TableView<Solicitud> tabla = new TableView<>();
        TableColumn<Solicitud, String> colTipo = new TableColumn<>("Tipo");
        TableColumn<Solicitud, String> colDescripcion = new TableColumn<>("Descripción");
        TableColumn<Solicitud, String> colFecha = new TableColumn<>("Fecha");

        colTipo.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
        colDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        colFecha.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());

        tabla.getColumns().addAll(colTipo, colDescripcion, colFecha);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Solicitud> datos = FXCollections.observableArrayList();

        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT tipo_solicitud, descripcion, fecha FROM solicitudes WHERE id_usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                datos.add(new Solicitud(
                    rs.getString("tipo_solicitud"),
                    rs.getString("descripcion"),
                    rs.getString("fecha")
                ));
            }

            tabla.setItems(datos);

        } catch (Exception e) {
            System.out.println("❌ Error al cargar solicitudes: " + e.getMessage());
        }

        Button btnGrafico = new Button("Ver gráfico global");
        Button btnCerrar = new Button("Cerrar");

        btnGrafico.setPrefWidth(150);
        btnCerrar.setPrefWidth(150);
        
        btnGrafico.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnCerrar.setStyle("-fx-background-color: #E11600; -fx-text-fill: white;");

        btnGrafico.setOnAction(e -> {
            new GraficoSolicitudesView().mostrarGraficoGlobal();
        });

        btnCerrar.setOnAction(e -> ((Stage) btnCerrar.getScene().getWindow()).close());

        HBox botones = new HBox(10, btnGrafico, btnCerrar);
        botones.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, lblTitulo, tabla, botones);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f4f4f4;");

        Scene scene = new Scene(layout, 700, 450);

        Stage ventana = new Stage();
        ventana.setTitle("Mis Solicitudes");
        ventana.setScene(scene);
        ventana.show();
    }
}

