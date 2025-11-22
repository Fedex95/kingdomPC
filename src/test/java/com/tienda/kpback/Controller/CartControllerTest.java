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

import java.util.UUID;  

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

    private UUID mockUserId; 

    @BeforeEach
    void setUp() {
        mockUserId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");   
    }

    @Test
    void testGetCart() {
        when(userDetails.getUserId()).thenReturn(mockUserId);  
        Cart mockCart = new Cart();
        when(cartService.getCartByUsuarioId(mockUserId)).thenReturn(mockCart);  

        ResponseEntity<Cart> response = cartController.getCart(userDetails);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCart, response.getBody());
        verify(cartService).getCartByUsuarioId(mockUserId); 
    }
}
