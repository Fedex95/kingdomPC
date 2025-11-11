package com.tienda.kpback.Controller;

import com.tienda.kpback.Entity.Cart;
import com.tienda.kpback.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Map;
import java.util.HashMap;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.tienda.kpback.Config.CustomUserDetails;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/get")
    public ResponseEntity<Cart> getCart(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        Cart cart = cartService.getCartByUserId(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/agregar")
    public ResponseEntity<Cart> addItemToCart(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Map<String, Object> request) {
        Long userId = userDetails.getUserId();
        Long productoId = ((Number) request.get("productoId")).longValue();
        int cantidad = (int) request.get("cantidad");
        Cart updatedCart = cartService.addItemToCart(userId, productoId, cantidad);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @PostMapping("/actualizar/{cartItemId}")
    public ResponseEntity<Cart> updateItemCantidad(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long cartItemId, @RequestBody int cantidad) {
        Long userId = userDetails.getUserId();
        Cart cart = cartService.updateItemCart(cartItemId, cantidad, userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{itemId}")
    public ResponseEntity<Void> deleteItemCart(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long itemId) {
        Long userId = userDetails.getUserId();
        cartService.deleteItemCart(userId, itemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/pagar")
    public ResponseEntity<Map<String, String>> payCart(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        cartService.Pago(userId);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
