package com.tusolicitudes.ap.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "solicitudes")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idUsuario;
    private String tipo_solicitud;
    private String descripcion;
    private Timestamp fecha;

    // Getters y setters
    public int getId() {
        return id;
    }

    public int getId_usuario() {
        return idUsuario;
    }

    public String getTipo_solicitud() {
        return tipo_solicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_usuario(int id_usuario) {
        this.idUsuario = id_usuario;
    }

    public void setTipo_solicitud(String tipo_solicitud) {
        this.tipo_solicitud = tipo_solicitud;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}