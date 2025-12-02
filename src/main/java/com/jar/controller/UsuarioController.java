package com.jar.controller;

import com.jar.model.Usuario;
import com.jar.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession; // <--- Import necesario para manejar la sesión
import java.time.LocalDateTime;
import java.util.Optional; // <--- Import necesario para buscar en el repositorio

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. Mostrar el formulario de registro (GET)
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(Usuario usuario) {

        String contrasenaIngresada = usuario.getContrasenaHash();
        if (contrasenaIngresada == null || contrasenaIngresada.isEmpty()) {
            System.err.println("ERROR DE REGISTRO: La contraseña es nula o vacía.");
            return "redirect:/registro";
        }
        usuario.setContrasenaHash(contrasenaIngresada); 
        usuario.setFechaCreacion(LocalDateTime.now());
        usuarioRepository.save(usuario);
        return "redirect:/login"; 
    }
    
    // 3. Mostrar el formulario de Login (GET)
    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login"; 
    }

    // 4. Procesar el Login (POST)
    @PostMapping("/login")
    public String iniciarSesion(Usuario formUsuario, Model model, HttpSession session) {
        
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(formUsuario.getEmail());

        if (usuarioOpt.isEmpty()) {
            model.addAttribute("error", "Email o contraseña incorrectos.");
            model.addAttribute("usuario", new Usuario());
            return "login"; 
        }

        Usuario usuarioEnBD = usuarioOpt.get();
        
        // Simulación de verificación de contraseña
        if (formUsuario.getContrasenaHash().equals(usuarioEnBD.getContrasenaHash())) {
            
            // Guardar el usuario en la sesión
            session.setAttribute("usuarioLogeado", usuarioEnBD);
            
            // Redirigir al Menú Principal
            return "redirect:/menu"; 
        } else {
            model.addAttribute("error", "Email o contraseña incorrectos.");
            model.addAttribute("usuario", new Usuario());
            return "login";
        }
    }
    
    // 5. Menú Principal (GET)
    @GetMapping("/menu")
    public String mostrarMenuPrincipal(HttpSession session) {
        // Protección: redirigir si no hay sesión
        if (session.getAttribute("usuarioLogeado") == null) {
            return "redirect:/login"; 
        }
        return "menu"; 
    }

    // 6. Cerrar Sesión (Logout)
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login"; 
    }
}