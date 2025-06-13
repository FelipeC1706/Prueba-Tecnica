package com.tusolicitudes.ap.controller;

import com.tusolicitudes.ap.model.Solicitud;
import com.tusolicitudes.ap.model.Usuarios;
import com.tusolicitudes.ap.repository.SolicitudRepository;
import com.tusolicitudes.ap.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private UsuarioRepository  usuarioRepository;

    @GetMapping("/{usuario}")
    public List<Solicitud> obtenerSolicitudesPorUsuario(@PathVariable String usuario) {
        Usuarios user = usuarioRepository.findByUsuario(usuario);
        return solicitudRepository.findByIdUsuario(user.getId());
    }

}

