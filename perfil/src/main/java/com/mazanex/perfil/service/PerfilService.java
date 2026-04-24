package com.mazanex.perfil.service;

import com.mazanex.perfil.model.Usuario;
import com.mazanex.perfil.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

interface ProcesamientoStrategy {
    Usuario procesar(Usuario usuario, UsuarioRepository repo);
}

class RegistroSimpleStrategy implements ProcesamientoStrategy {
    @Override
    public Usuario procesar(Usuario usuario, UsuarioRepository repo) {
        return repo.save(usuario);
    }
}

@Service
public class PerfilService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private ProcesamientoStrategy estrategia = new RegistroSimpleStrategy();

    public Usuario guardarPerfil(Usuario usuario) {
        return estrategia.procesar(usuario, usuarioRepository);
    }

    public List<Usuario> listarPerfiles() {
        return usuarioRepository.findAll();
    }

    public void eliminarPerfil(Long id) {
        usuarioRepository.deleteById(id);
    }
}