package com.tienda.kpback.Controller;

import com.tienda.kpback.Entity.Products;
import com.tienda.kpback.Repository.productRep;
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
public class productController {
    @Autowired
    private productRep prep;
    @Autowired
    private com.tienda.kpback.Repository.productRep productRep;

    @GetMapping("/products")
    public List<Products> getAllProducts() {
        return prep.findAll();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id) {
        if (id == null || id < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido");
        }
        Products products = productRep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Product not found"));
        return ResponseEntity.ok(products);
    }

    @PostMapping("/products/add")
    public ResponseEntity<Products> addProduct(@RequestBody Products products) {
        try {
            Products savedProduct = prep.save(products);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/products/update")
    public ResponseEntity<Products> updateProduct(@RequestBody Products products) {
        try {
            if (products.getId() == null || products.getId() < 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Products updatedProduct = prep.save(products);

            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        try {
            if (id == null || id < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID invalido");
            }
            if (!prep.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }

            prep.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el producto");
        }
    }
}