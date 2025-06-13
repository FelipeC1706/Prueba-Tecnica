
package com.tusolicitudes.ap.repository;


import com.tusolicitudes.ap.model.Usuarios;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {
    
    // Método personalizado: encuentra todas las solicitudes por ID de usuario
    Usuarios findById(int id_usuario);
   
    Usuarios findByUsuario(String usuario);
    
}
