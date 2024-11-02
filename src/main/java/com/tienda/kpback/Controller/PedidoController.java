package com.tienda.kpback.Controller;

import com.tienda.kpback.Entity.PedidoEnt;
import com.tienda.kpback.Repository.PedidoRep;
import com.tienda.kpback.Repository.ProductRep;
import com.tienda.kpback.Entity.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class PedidoController {
    @Autowired
    private PedidoRep pedidoRep;

    @Autowired
    private ProductRep productRep;

    @GetMapping("/pedidos")
    public List<PedidoEnt> getAllPedidos() {
        return pedidoRep.findAll();
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<PedidoEnt> getPedidoById(@PathVariable Integer id) {
        if (id == null || id < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID inválido");
        }
        PedidoEnt pedido = pedidoRep.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
        return ResponseEntity.ok(pedido);
    }

    @PostMapping("/pedidos/add")
    public ResponseEntity<PedidoEnt> addPedido(@RequestBody PedidoEnt pedido) {
        try {
            Products producto = productRep.findById(pedido.getProducto().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Producto no encontrado con ID: " + pedido.getProducto().getId()));
            pedido.setProducto(producto);
            
            if (pedido.getPrecioTotal() == 0) {
                pedido.setPrecioTotal(
                    (producto.getPrecioProducto()) + pedido.getValorEnvio()
                );
            }
            
            PedidoEnt savedPedido = pedidoRep.save(pedido);
            return new ResponseEntity<>(savedPedido, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error al crear el pedido: " + e.getMessage()
            );
        }
    }

    @PutMapping("/pedidos/update")
    public ResponseEntity<PedidoEnt> updatePedido(@RequestBody PedidoEnt pedido) {
        try {
            if (pedido.getId() == null || pedido.getId() < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID de pedido inválido");
            }

            pedidoRep.findById(pedido.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado con ID: " + pedido.getId()));

            Products producto = productRep.findById(pedido.getProducto().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con ID: " + pedido.getProducto().getId()));
            
            pedido.setProducto(producto);
            
            if (pedido.getPrecioTotal() == 0) {
                pedido.setPrecioTotal(
                    (producto.getPrecioProducto()) + pedido.getValorEnvio());
            }

            PedidoEnt updatedPedido = pedidoRep.save(pedido);
            return new ResponseEntity<>(updatedPedido, HttpStatus.OK);
        } catch (ResponseStatusException e) {throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error al actualizar el pedido: " + e.getMessage()
            );
        }
    }

    @DeleteMapping("/pedidos/delete/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Integer id) {
        try {
            if (id == null || id < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID inválido");
            }
            if (!pedidoRep.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado");
            }

            pedidoRep.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el pedido");
        }
    }
}
