package com.tienda.kpback.Test;

import com.tienda.kpback.Entity.Producto;
import com.tienda.kpback.Entity.UsuarioEnt;
import com.tienda.kpback.Entity.Cart;
import com.tienda.kpback.Repository.ProductoRepository;
import com.tienda.kpback.Repository.UsuarioRepository;
import com.tienda.kpback.Repository.CartRepository;
import com.tienda.kpback.Service.ProductoService;
import com.tienda.kpback.Service.UsuarioService;
import com.tienda.kpback.Service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class KpbackTest {

    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private ProductoService productoService;
    @InjectMocks
    private UsuarioService usuarioService;
    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProducto() {
        Producto producto = new Producto();
        producto.setNombre("Monitor");
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto saved = productoService.saveProducto(producto);

        assertEquals("Monitor", saved.getNombre());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void testSaveUsuario() {
        UsuarioEnt usuario = new UsuarioEnt();
        usuario.setUsuario("testuser");
        usuario.setPass("1234");
        when(usuarioRepository.save(any(UsuarioEnt.class))).thenReturn(usuario);

        UsuarioEnt saved = usuarioService.saveUsuario(usuario);

        assertEquals("testuser", saved.getUsuario());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testCreateCarrito() {
        UsuarioEnt usuario = new UsuarioEnt();
        usuario.setId(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(cartRepository.findByUsuario(usuario)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cart cart = cartService.createCarrito(1L);

        assertNotNull(cart);
        assertEquals(usuario, cart.getUsuario());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testCreateCarritoReturnsExistingCart() {
        UsuarioEnt usuario = new UsuarioEnt();
        usuario.setId(10L);
        Cart existingCart = new Cart();
        existingCart.setUsuario(usuario);

        when(usuarioRepository.findById(10L)).thenReturn(Optional.of(usuario));
        when(cartRepository.findByUsuario(usuario)).thenReturn(Optional.of(existingCart));

        Cart result = cartService.createCarrito(10L);

        assertNotNull(result);
        assertEquals(usuario, result.getUsuario());
        // Verifica que no se haya intentado guardar un nuevo carrito
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void testCreateCarritoCreatesNewCartIfNotExists() {
        UsuarioEnt usuario = new UsuarioEnt();
        usuario.setId(11L);
        Cart newCart = new Cart();
        newCart.setUsuario(usuario);

        when(usuarioRepository.findById(11L)).thenReturn(Optional.of(usuario));
        when(cartRepository.findByUsuario(usuario)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(newCart);

        Cart result = cartService.createCarrito(11L);

        assertNotNull(result);
        assertEquals(usuario, result.getUsuario());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testGetProductoByIdNotFound() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Producto> result = productoService.getProductoById(99L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetUsuarioByIdNotFound() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<UsuarioEnt> result = usuarioService.getUsuarioById(99L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllProductos() {
        Producto p1 = new Producto();
        p1.setNombre("Teclado");
        Producto p2 = new Producto();
        p2.setNombre("Mouse");
        when(productoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Producto> productos = productoService.findAllProductos();

        assertEquals(2, productos.size());
        assertEquals("Teclado", productos.get(0).getNombre());
        assertEquals("Mouse", productos.get(1).getNombre());
    }

    @Test
    void testGetUsuarioByUsuario() {
        UsuarioEnt usuario = new UsuarioEnt();
        usuario.setUsuario("admin");
        when(usuarioRepository.findByUsuario("admin")).thenReturn(Optional.of(usuario));

        Optional<UsuarioEnt> result = usuarioService.getUsuarioByUsuario("admin");

        assertTrue(result.isPresent());
        assertEquals("admin", result.get().getUsuario());
    }
}
