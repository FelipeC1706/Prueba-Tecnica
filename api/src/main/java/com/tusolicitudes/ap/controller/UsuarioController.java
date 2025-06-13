
package com.tusolicitudes.ap.controller;


import com.tusolicitudes.ap.model.Usuarios;
import com.tusolicitudes.ap.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/{usuario}")
    public Usuarios obtenerSolicitudesPorUsuario(@PathVariable String usuario) {
        
        return usuarioRepository.findByUsuario(usuario);
    }
    
}
