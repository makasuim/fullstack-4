package com.mazanex.auth.controller;

import com.mazanex.auth.model.Usuario;
import com.mazanex.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Autenticación", description = "Operaciones relativas al registro, login y gestión de credenciales")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Registrar nuevo usuario", description = "Crea un nuevo registro de usuario en la base de datos de autenticación")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    @PostMapping("/register")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = authService.registrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @Operation(summary = "Login de usuario", description = "Valida nombre y contraseña para otorgar acceso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario loginData) {
        Usuario usuario = authService.validarCredenciales(loginData.getNombre(), loginData.getPassword());
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Operation(summary = "Listar usuarios", description = "Obtiene la lista completa de usuarios registrados")
    @GetMapping("/usuarios")
    public List<Usuario> listar() {
        return authService.listarTodos();
    }

    @Operation(summary = "Actualizar perfil", description = "Modifica los datos de un usuario existente mediante su ID")
    @PutMapping("/perfil/{id}")
    public ResponseEntity<Usuario> actualizarPerfil(@PathVariable Long id, @RequestBody Usuario data) {
        Usuario actualizado = authService.registrarOActualizar(data);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar usuario", description = "Remueve un usuario del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return authService.eliminar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Sincronizar perfil", description = "Endpoint de apoyo para mantener la consistencia de datos entre microservicios")
    @PostMapping("/sync-profile")
    public ResponseEntity<Usuario> syncProfile(@RequestBody Usuario data) {
        Usuario actualizado = authService.registrarOActualizar(data);
        return ResponseEntity.ok(actualizado);
    }
}
