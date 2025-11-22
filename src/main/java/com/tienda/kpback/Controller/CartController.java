package com.tienda.kpback.Controller;

import com.tienda.kpback.Entity.Cart;
import com.tienda.kpback.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.tienda.kpback.Config.CustomUserDetails;
import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/get")
    public ResponseEntity<Cart> getCart(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UUID userId = userDetails.getUserId();
        Cart cart = cartService.getCartByUsuarioId(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }


    @PostMapping("/actualizar/{cartItemId}")
    public ResponseEntity<Cart> updateItemCantidad(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID cartItemId, @RequestBody int cantidad) {
        UUID userId = userDetails.getUserId();
        Cart cart = cartService.updateItemCart(cartItemId, cantidad, userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{itemId}")
    public ResponseEntity<Void> deleteItemCart(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID itemId) {
        UUID userId = userDetails.getUserId();
        cartService.deleteItemCart(userId, itemId);
        return ResponseEntity.noContent().build();
    }
}
