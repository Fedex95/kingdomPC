package com.tienda.kpback.Controller;

import com.tienda.kpback.Config.CustomUserDetails;
import com.tienda.kpback.Entity.Producto;
import com.tienda.kpback.Entity.UsuarioEnt;
import com.tienda.kpback.Service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.tienda.kpback.Repository.ProductoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @Mock
    private ProductoRepository productoRepository;  

    @InjectMocks
    private ProductoController productoController;

    @Mock
    private CustomUserDetails userDetails;

    private Producto mockProducto;

    @BeforeEach
    void setUp() {
        mockProducto = new Producto();
        mockProducto.setId(1L);
        mockProducto.setNombre("Producto Test");
    }

    @Test
    void testFindAllProductos() {
        List<Producto> mockList = Arrays.asList(mockProducto);
        when(productoRepository.findAll()).thenReturn(mockList);

        ResponseEntity<List<Producto>> response = productoController.findAllProductos();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
    }

    @Test
    void testGetProductoById() {
        Optional<Producto> mockOptional = Optional.of(mockProducto);
        when(productoService.getProductoById(1L)).thenReturn(mockOptional);

        Optional<Producto> response = productoController.getProductoById(1L);
        assertEquals(mockOptional, response);
    }

    @Test
    void testCreateProducto_Success() {
        when(userDetails.getRol()).thenReturn(UsuarioEnt.Rol.ADMIN);
        when(productoService.saveProducto(any(Producto.class))).thenReturn(mockProducto);

        ResponseEntity<Producto> response = productoController.createProducto(userDetails, mockProducto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockProducto, response.getBody());
    }

    @Test
    void testCreateProducto_Forbidden() {
        when(userDetails.getRol()).thenReturn(UsuarioEnt.Rol.USER);

        ResponseEntity<Producto> response = productoController.createProducto(userDetails, mockProducto);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testUpdateProducto_Success() {
        when(userDetails.getRol()).thenReturn(UsuarioEnt.Rol.ADMIN);
        when(productoService.updateProducto(any(Producto.class))).thenReturn(mockProducto);

        ResponseEntity<Producto> response = productoController.updateProducto(userDetails, 1L, mockProducto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProducto, response.getBody());
    }

    @Test
    void testUpdateProducto_Forbidden() {
        when(userDetails.getRol()).thenReturn(UsuarioEnt.Rol.USER);

        ResponseEntity<Producto> response = productoController.updateProducto(userDetails, 1L, mockProducto);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testDeleteProducto_Success() {
        when(userDetails.getRol()).thenReturn(UsuarioEnt.Rol.ADMIN);
        doNothing().when(productoService).deleteProducto(1L);

        ResponseEntity<Void> response = productoController.deleteProducto(userDetails, 1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteProducto_Forbidden() {
        when(userDetails.getRol()).thenReturn(UsuarioEnt.Rol.USER);

        ResponseEntity<Void> response = productoController.deleteProducto(userDetails, 1L);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
