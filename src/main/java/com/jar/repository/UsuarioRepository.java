package com.jar.repository;

import com.jar.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    /**
     * Busca un Usuario por su Email.
     * Spring Data JPA genera automáticamente la implementación de esta consulta.
     * @param email El email del usuario a buscar.
     * @return Un Optional que contiene el Usuario si se encuentra, o vacío si no.
     */
    Optional<Usuario> findByEmail(String email);
}