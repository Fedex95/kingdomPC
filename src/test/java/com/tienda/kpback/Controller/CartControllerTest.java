package com.tienda.kpback.Controller;

import com.tienda.kpback.Entity.Cart;
import com.tienda.kpback.Service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tienda.kpback.Config.CustomUserDetails;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @Mock
    private CustomUserDetails userDetails;
    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetCart() {
        when(userDetails.getUserId()).thenReturn(1L);
        Cart mockCart = new Cart();
        when(cartService.getCartByUserId(1L)).thenReturn(mockCart);

        ResponseEntity<Cart> response = cartController.getCart(userDetails);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCart, response.getBody());
        verify(cartService).getCartByUserId(1L);
    }

    @Test
    void testAddItemToCart() {
        when(userDetails.getUserId()).thenReturn(1L);
        Cart mockCart = new Cart();
        when(cartService.addItemToCart(1L, 2L, 3)).thenReturn(mockCart);

        Map<String, Object> request = new HashMap<>();
        request.put("productoId", 2L);
        request.put("cantidad", 3);

        ResponseEntity<Cart> response = cartController.addItemToCart(userDetails, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCart, response.getBody());
        verify(cartService).addItemToCart(1L, 2L, 3);
    }

    @Test
    void testUpdateItemCantidad() {
        when(userDetails.getUserId()).thenReturn(1L);
        Cart mockCart = new Cart();
        when(cartService.updateItemCart(1L, 5, 1L)).thenReturn(mockCart);

        ResponseEntity<Cart> response = cartController.updateItemCantidad(userDetails, 1L, 5);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCart, response.getBody());
        verify(cartService).updateItemCart(1L, 5, 1L);
    }

    @Test
    void testDeleteItemCart() {
        when(userDetails.getUserId()).thenReturn(1L);
        doNothing().when(cartService).deleteItemCart(1L, 2L);

        ResponseEntity<Void> response = cartController.deleteItemCart(userDetails, 2L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(cartService).deleteItemCart(1L, 2L);
    }

    @Test
    void testPayCart() {
        when(userDetails.getUserId()).thenReturn(1L);
        doNothing().when(cartService).Pago(1L);

        ResponseEntity<Map<String, String>> response = cartController.payCart(userDetails);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody().get("status"));
        verify(cartService).Pago(1L);
    }
}
