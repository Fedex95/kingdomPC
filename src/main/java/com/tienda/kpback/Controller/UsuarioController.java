package com.tienda.kpback.Controller;

import com.tienda.kpback.Entity.UsuarioEnt;
import com.tienda.kpback.Repository.UsuarioRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
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
}
