package com.tienda.kpback.Controller;

import com.tienda.kpback.Entity.UsuarioEnt;
import com.tienda.kpback.Repository.UsuarioRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@SuppressWarnings("All")
@RestController
@RequestMapping("/api")
@CrossOrigin

public class UsuarioController {
    @Autowired
    private UsuarioRep usuarioRep;

    @GetMapping("/usuarios")
    public List<UsuarioEnt> getAllUsuarios(){
        return usuarioRep.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity getUsuarioById(@PathVariable Integer id){
        if(id==null || id<0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id invalido");
        }
        UsuarioEnt users = usuarioRep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Usuario no encontrado"));
        return ResponseEntity.ok(users);
    }

    @PutMapping("/usuarios/add")
    public ResponseEntity addUsuario(@RequestBody UsuarioEnt usuario){
        try{
            UsuarioEnt usuariosSaved = usuarioRep.save(usuario);
            return new ResponseEntity<>(usuariosSaved, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/usuarios/update")
    public ResponseEntity<UsuarioEnt> updateUsuario(@RequestBody UsuarioEnt usuario){
        try{
            if(usuario.getId() == null || usuario.getId()<0){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            UsuarioEnt updatedUsuario = usuarioRep.save(usuario);
            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/usuarios/delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id){
        try{
            if(id==null || id<0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id invalido");
            }
            if(!usuarioRep.existsById(id)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
            }
            usuarioRep.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el usuario");
        }
    }
}
