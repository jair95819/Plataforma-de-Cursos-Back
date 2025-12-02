package com.jar.controller;

import com.jar.model.Usuario;
import com.jar.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession; // <--- Import necesario para manejar la sesión
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional; // <--- Import necesario para buscar en el repositorio

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200") // Permite conexión desde Angular
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. Mostrar el formulario de registro (GET)
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // --- REGISTRO ---
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        // Validación básica: ver si el email ya existe
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El email ya está registrado");
        }

        // Configuración inicial
        usuario.setFechaCreacion(LocalDateTime.now());
        // NOTA DE SEGURIDAD: Aquí deberías encriptar la contraseña en un entorno real.
        // Por ahora, para el MVP, la guardamos tal cual llega.

        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    // --- LOGIN CON DIAGNÓSTICO ---
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String passwordIngresada = credenciales.get("password");

        System.out.println("----- INTENTO DE LOGIN -----");
        System.out.println("Email recibido: " + email);
        System.out.println("Password recibida: " + passwordIngresada);

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuarioEnBD = usuarioOpt.get();
            String passwordEnBD = usuarioEnBD.getContrasenaHash();

            System.out.println("Usuario encontrado en BD ID: " + usuarioEnBD.getUsuarioID());
            System.out.println("Password en BD: " + passwordEnBD);

            // Comparación (Texto plano para MVP)
            if (passwordEnBD != null && passwordEnBD.equals(passwordIngresada)) {
                System.out.println(">> ¡LOGIN EXITOSO!");
                return ResponseEntity.ok(usuarioEnBD);
            } else {
                System.out.println(">> ERROR: Las contraseñas no coinciden.");
            }
        } else {
            System.out.println(">> ERROR: Usuario no encontrado con ese email.");
        }

        return ResponseEntity.status(401).body("Credenciales incorrectas");
    }
    
    // 5. Menú Principal (GET)
    @GetMapping("/index")
    public String mostrarMenuPrincipal(HttpSession session) {
        // Protección: redirigir si no hay sesión
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/index"; 
        }
        return "index"; 
    }

    // 6. Cerrar Sesión (Logout)
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login"; 
    }
}