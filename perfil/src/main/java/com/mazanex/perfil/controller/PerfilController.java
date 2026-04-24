package com.mazanex.perfil.controller;

import com.mazanex.perfil.model.Usuario;
import com.mazanex.perfil.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/perfil")
@CrossOrigin(origins = "*")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @PostMapping("/sync")
    public ResponseEntity<Usuario> sincronizar(@RequestBody Usuario usuario) {
        Usuario guardado = perfilService.guardarPerfil(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(perfilService.listarPerfiles());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        perfilService.eliminarPerfil(id);
        return ResponseEntity.noContent().build();
    }
}