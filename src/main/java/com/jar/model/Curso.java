package com.jar.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cursos") // Mapea exactamente a tu tabla SQL
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CursoID") // Mapeo exacto a la columna SQL
    private Long id;

    @Column(name = "Titulo", nullable = false)
    private String titulo;

    @Column(name = "Descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "URL", nullable = false)
    private String url;

    @Column(name = "PlataformaID")
    private Long plataformaId;

    @Column(name = "Tipo", length = 50)
    private String tipo;

    @Column(name = "Duracion", length = 50)
    private String duracion;

    @Column(name = "Nivel", length = 50)
    private String nivel;

    @Column(name = "Rating", precision = 3, scale = 1)
    private BigDecimal rating;

    @Column(name = "Estudiantes")
    private Integer estudiantes;

    public Curso() {
    }

    // Constructor con campos principales
    public Curso(String titulo, String descripcion, String url, String tipo, String nivel) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.url = url;
        this.tipo = tipo;
        this.nivel = nivel;
    }

    // --- Getters y Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Long getPlataformaId() { return plataformaId; }
    public void setPlataformaId(Long plataformaId) { this.plataformaId = plataformaId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }

    public Integer getEstudiantes() { return estudiantes; }
    public void setEstudiantes(Integer estudiantes) { this.estudiantes = estudiantes; }
}
