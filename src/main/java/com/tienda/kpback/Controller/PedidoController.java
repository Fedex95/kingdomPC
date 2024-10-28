package com.tienda.kpback.Controller;

import com.tienda.kpback.Entity.PedidoEnt;
import com.tienda.kpback.Repository.PedidoRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@SuppressWarnings("ALL")
@RestController

@RequestMapping("/api")
@CrossOrigin("*")

public class PedidoController {
    @Autowired
    private PedidoRep pedidoRep;

    @GetMapping("/pedidos")
    public List<PedidoEnt> getAllPedidos() {
        return pedidoRep.findAll();
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity getPedidoById(@PathVariable Integer id) {
        if (id == null || id < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido");
        }
        PedidoEnt pedido = pedidoRep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Pedido no encontrado"));
        return ResponseEntity.ok(pedido);
    }

    //Agregar hashmap
    @PostMapping("/pedidos/add")
    public ResponseEntity<PedidoEnt> addPedido(@RequestBody PedidoEnt pedido) {
        try {
            PedidoEnt savedPedido = pedidoRep.save(pedido);
            return new ResponseEntity<>(savedPedido, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/pedidos/update")
    public ResponseEntity<PedidoEnt> updatePedido(@RequestBody PedidoEnt pedido){
        try {
            if(pedido.getId() == null || pedido.getId() < 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido");
            }
            PedidoEnt updatedPedido = pedidoRep.save(pedido);
            return new ResponseEntity<>(updatedPedido, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/pedidos/delete/{id}")
    public ResponseEntity<PedidoEnt> deletePedido(@PathVariable Integer id){
        try{
            if(id == null || id < 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido");
            }
            if(!pedidoRep.existsById(id)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado");
            }
            pedidoRep.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error al eliminar el pedido");
        }
    }
}