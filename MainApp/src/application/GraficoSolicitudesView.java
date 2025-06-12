package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GraficoSolicitudesView {

    public void mostrarGraficoGlobal() {
        ObservableList<PieChart.Data> datos = FXCollections.observableArrayList();
        double total = 0;

        try (Connection conn = ConexionDB.conectar()) {
            String sql = "SELECT tipo_solicitud, COUNT(*) AS cantidad FROM solicitudes GROUP BY tipo_solicitud";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString("tipo_solicitud");
                int cantidad = rs.getInt("cantidad");
                datos.add(new PieChart.Data(tipo, cantidad));
                total += cantidad;
            }
        } catch (Exception e) {
            System.out.println("❌ Error al generar gráfico global: " + e.getMessage());
        }

        for (PieChart.Data data : datos) {
            double porcentaje = (data.getPieValue() / total) * 100;
            String tipoConPorcentaje = String.format("%s (%.1f%%)", data.getName(), porcentaje);
            data.setName(tipoConPorcentaje);
        }

        Label lblTitulo = new Label("Distribución de Solicitudes por Tipo");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        PieChart chart = new PieChart(datos);
        chart.setTitle("Solicitudes Globales");
        chart.setLabelsVisible(true);
        chart.setLegendVisible(true);

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setStyle("-fx-background-color: #E11600; -fx-text-fill: white;");
        btnCerrar.setPrefWidth(150);
        btnCerrar.setOnAction(e -> ((Stage) btnCerrar.getScene().getWindow()).close());

        VBox layout = new VBox(15, lblTitulo, chart, btnCerrar);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f4f4f4;");

        Scene scene = new Scene(layout, 600, 500);
        Stage ventana = new Stage();
        ventana.setTitle("Gráfico Global de Solicitudes");
        ventana.setScene(scene);
        ventana.show();
    }
}
