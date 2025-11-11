package com.tienda.kpback.Controller;

import com.tienda.kpback.Entity.Producto;
import com.tienda.kpback.Repository.ProductoRepository;
import com.tienda.kpback.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.tienda.kpback.Config.CustomUserDetails;
import com.tienda.kpback.Entity.UsuarioEnt;

@RestController
@RequestMapping("/producto")
@SecurityRequirement(name = "bearerAuth")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/find/all")
    public ResponseEntity<List<Producto>> findAllProductos() {
        List<Producto> productos = productoRepository.findAll();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public Optional<Producto> getProductoById(@PathVariable Long id) {
        return productoService.getProductoById(id);
    }

    @PostMapping("/newproducto")
    public ResponseEntity<Producto> createProducto(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Producto producto) {
        System.out.println("Usuario: " + userDetails.getUsername() + ", Rol: " + userDetails.getRol());
        if (userDetails.getRol() == UsuarioEnt.Rol.ADMIN) {
            Producto createdPr = productoService.saveProducto(producto);
            return new ResponseEntity<>(createdPr, HttpStatus.CREATED);
        } else {
            System.out.println("Acceso denegado: no es ADMIN");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Producto> updateProducto(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long id, @RequestBody Producto producto) {
        if (userDetails.getRol() == UsuarioEnt.Rol.ADMIN) {
            producto.setId(id);
            Producto updatedPr = productoService.updateProducto(producto);
            return new ResponseEntity<>(updatedPr, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProducto(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long id) {
        if (userDetails.getRol() == UsuarioEnt.Rol.ADMIN) {
            productoService.deleteProducto(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
