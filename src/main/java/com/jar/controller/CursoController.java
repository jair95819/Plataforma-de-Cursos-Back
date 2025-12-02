package com.jar.controller;

import com.jar.model.Curso;
import com.jar.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "http://localhost:4200") // ¡Importante para permitir que Angular se conecte!
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    // 1. Obtener todos los cursos
    @GetMapping
    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    // 2. Endpoint para el CHATBOT: Recomendación por área/keyword
    // Ejemplo de uso: GET /api/cursos/recomendar?area=programacion
    @GetMapping("/recomendar")
    public ResponseEntity<List<Curso>> recomendarCursos(@RequestParam("area") String area) {
        List<Curso> resultados = cursoRepository.buscarCursosPorKeyword(area);

        if (resultados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultados);
    }

    // 3. Crear un curso nuevo (útil para poblar tu base de datos desde Postman/Angular)
    @PostMapping
    public Curso crearCurso(@RequestBody Curso curso) {
        return cursoRepository.save(curso);
    }

    // 4. Obtener detalle de un curso específico
    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerCurso(@PathVariable Long id) {
        return cursoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
