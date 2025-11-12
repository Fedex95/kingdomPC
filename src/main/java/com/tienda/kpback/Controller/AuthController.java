package com.tienda.kpback.Controller;

import com.tienda.kpback.Entity.UsuarioEnt;
import com.tienda.kpback.Service.JwtService;
import com.tienda.kpback.Service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JwtService jwtService;

    public static class LoginRequest {
        @NotBlank(message = "Usuario es obligatorio")
        private String usuario;
        @NotBlank(message = "Contraseña es obligatoria")
        private String pass;

        public String getUsuario() { return usuario; }
        public void setUsuario(String usuario) { this.usuario = usuario; }
        public String getPass() { return pass; }
        public void setPass(String pass) { this.pass = pass; }
    }

    public static class RegisterRequest {
        @NotBlank(message = "Usuario es obligatorio")
        @Size(min = 3, max = 20, message = "Usuario debe tener entre 3 y 20 caracteres")
        private String usuario;
        @NotBlank(message = "Contraseña es obligatoria")
        @Size(min = 6, message = "Contraseña debe tener al menos 6 caracteres")
        private String pass;
        @NotBlank(message = "Nombre es obligatorio")
        private String nombre;
        @NotBlank(message = "Apellido es obligatorio")
        private String apellido;
        @NotBlank(message = "Cédula es obligatoria")
        private String cedula;
        @Email(message = "Email inválido")
        private String email;

        public String getUsuario() { return usuario; }
        public void setUsuario(String usuario) { this.usuario = usuario; }
        public String getPass() { return pass; }
        public void setPass(String pass) { this.pass = pass; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getApellido() { return apellido; }
        public void setApellido(String apellido) { this.apellido = apellido; }
        public String getCedula() { return cedula; }
        public void setCedula(String cedula) { this.cedula = cedula; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest credentials) {
        String usuario = credentials.getUsuario();
        String pass = credentials.getPass();

        Optional<UsuarioEnt> userOpt = usuarioService.getUsuarioByUsuario(usuario);
        if (userOpt.isPresent()) {
            UsuarioEnt user = userOpt.get();
            try {
                if (usuarioService.checkPass(pass, user.getPass())) {
                    String token = jwtService.generateToken(
                            user.getUsuario(),
                            user.getId(),
                            user.getNombre(),
                            user.getDireccion(),
                            user.getRol()
                    );
                    return ResponseEntity.ok(Map.of("token", token));
                }
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Error interno en autenticación");
            }
        }
        return ResponseEntity.status(401).body("Credenciales inválidas");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        String usuario = request.getUsuario();

        if (usuarioService.getUsuarioByUsuario(usuario).isPresent()) {
            return ResponseEntity.status(400).body("Usuario ya existe");
        }

        try {
            UsuarioEnt newUser = new UsuarioEnt();
            newUser.setUsuario(usuario);
            newUser.setPass(request.getPass());
            newUser.setNombre(request.getNombre());
            newUser.setApellido(request.getApellido());
            newUser.setCedula(request.getCedula());
            newUser.setEmail(request.getEmail());
            newUser.setRol(UsuarioEnt.Rol.USER);

            usuarioService.saveUsuario(newUser);
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al registrar usuario: " + e.getMessage());
        }
    }
}