package com.tusolicitudes.ap.controller;

import com.tusolicitudes.ap.model.Solicitud;
import com.tusolicitudes.ap.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @GetMapping("/{usuario}")
    public List<Solicitud> obtenerSolicitudesPorUsuario(@PathVariable String usuario) {
        int idUsuario = buscarIdUsuario(usuario);
        return solicitudRepository.findByIdUsuario(idUsuario);
    }

    // Método auxiliar para buscar el ID del usuario por nombre
    private int buscarIdUsuario(String usuario) {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=UniversidadDB;encrypt=true;trustServerCertificate=true",
                "sa", "JOS3sit0"
            );
            PreparedStatement stmt = conn.prepareStatement("SELECT id FROM usuarios WHERE usuario = ?");
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}

