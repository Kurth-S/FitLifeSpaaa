package com.FitLifSpa.usuario_vm.Controller;
import com.FitLifSpa.usuario_vm.Model.UsuarioModel;
import com.FitLifSpa.usuario_vm.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listar() {
        List<UsuarioModel> usuarioModels = usuarioService.findAll();
        if (usuarioModels.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioModels);
    }

    // Guardar un nuevo usuario
    @PostMapping
    public ResponseEntity<UsuarioModel> guardar(@RequestBody UsuarioModel usuarioModel) {
        if (usuarioModel.getRun() == null || usuarioModel.getNombre() == null || usuarioModel.getContraseña() == null) {
            return ResponseEntity.badRequest().build();
        }
        UsuarioModel usuarioNuevo = usuarioService.save(usuarioModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
    }

    // Buscar un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> buscar(@PathVariable Integer id) {
        try {
            UsuarioModel usuarioModel = usuarioService.findById(id);
            return ResponseEntity.ok(usuarioModel);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> actualizar(@PathVariable Integer id, @RequestBody UsuarioModel usuarioModel) {
        try {
            UsuarioModel usu = usuarioService.findById(id);
            if (usu != null) {
                usu.setRun(usuarioModel.getRun());
                usu.setNombre(usuarioModel.getNombre());
                usu.setContraseña(usuarioModel.getContraseña());

                usuarioService.save(usu);
                return ResponseEntity.ok(usu);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
