package com.tusolicitudes.ap.repository;

import com.tusolicitudes.ap.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {
    
    // Método personalizado: encuentra todas las solicitudes por ID de usuario
    List<Solicitud> findByIdUsuario(int id_usuario);
}
