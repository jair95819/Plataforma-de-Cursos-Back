package com.jar.repository;

import com.jar.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByTipoContainingIgnoreCase(String area);

    // Consulta personalizada para el Chatbot:
    // Busca la palabra clave en el Título, Descripción o Tipo
    @Query("SELECT c FROM Curso c WHERE " +
            "LOWER(c.titulo) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.tipo) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Curso> buscarCursosPorKeyword(@Param("keyword") String keyword);
}
