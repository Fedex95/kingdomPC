package com.tienda.kpback.Controller;

import com.tienda.kpback.Entity.UsuarioEnt;
import com.tienda.kpback.Service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioEnt mockUsuario;  

    @Test
    void testGetAllUsuarios() {
        List<UsuarioEnt> mockList = Arrays.asList(mockUsuario);
        when(usuarioService.getAllUsuarios()).thenReturn(mockList);

        ResponseEntity<List<UsuarioEnt>> response = usuarioController.getAllUsuarios();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
    }

    @Test
    void testGetUsuarioById_Found() {
        Optional<UsuarioEnt> mockOptional = Optional.of(mockUsuario);
        when(usuarioService.getUsuarioById(anyLong())).thenReturn(mockOptional);

        ResponseEntity<UsuarioEnt> response = usuarioController.getUsuarioById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUsuario, response.getBody());
    }

    @Test
    void testGetUsuarioById_NotFound() {
        when(usuarioService.getUsuarioById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<UsuarioEnt> response = usuarioController.getUsuarioById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateUsuario() {
        when(usuarioService.saveUsuario(any(UsuarioEnt.class))).thenReturn(mockUsuario);

        ResponseEntity<UsuarioEnt> response = usuarioController.createUsuario(mockUsuario);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockUsuario, response.getBody());
    }

    @Test
    void testEditUsuario_Success() {
        when(usuarioService.updateUsuario(anyLong(), any(UsuarioEnt.class))).thenReturn(mockUsuario);

        ResponseEntity<UsuarioEnt> response = usuarioController.editUsuario(1L, mockUsuario);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUsuario, response.getBody());
    }

    @Test
    void testEditUsuario_NotFound() {
        when(usuarioService.updateUsuario(anyLong(), any(UsuarioEnt.class))).thenThrow(new RuntimeException("Not found"));

        ResponseEntity<UsuarioEnt> response = usuarioController.editUsuario(1L, mockUsuario);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteUsuario_Success() {
        doNothing().when(usuarioService).deleteUsuario(anyLong());

        ResponseEntity<UsuarioEnt> response = usuarioController.deleteUsuario(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteUsuario_NotFound() {
        doThrow(new RuntimeException("Not found")).when(usuarioService).deleteUsuario(anyLong());

        ResponseEntity<UsuarioEnt> response = usuarioController.deleteUsuario(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testViewPass_Correct() throws NoSuchAlgorithmException {
        Optional<UsuarioEnt> mockOptional = Optional.of(mockUsuario);
        when(usuarioService.getUsuarioByUsuario(anyString())).thenReturn(mockOptional);
        when(mockUsuario.getPass()).thenReturn("hashed");   
        doReturn(true).when(usuarioService).checkPass(anyString(), anyString());

        ResponseEntity<String> response = usuarioController.viewPass("user", "pass");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Correct Password", response.getBody());
    }

    @Test
    void testViewPass_Wrong() throws NoSuchAlgorithmException {
        Optional<UsuarioEnt> mockOptional = Optional.of(mockUsuario);
        when(usuarioService.getUsuarioByUsuario(anyString())).thenReturn(mockOptional);
        when(mockUsuario.getPass()).thenReturn("hashed");  
        doReturn(false).when(usuarioService).checkPass(anyString(), anyString());

        ResponseEntity<String> response = usuarioController.viewPass("user", "pass");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Wrong Password", response.getBody());
    }

    @Test
    void testViewPass_UserNotFound() {
        when(usuarioService.getUsuarioByUsuario(anyString())).thenReturn(Optional.empty());

        ResponseEntity<String> response = usuarioController.viewPass("user", "pass");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuario no existente", response.getBody());
    }

    @Test
    void testViewPass_Error() throws NoSuchAlgorithmException {
        Optional<UsuarioEnt> mockOptional = Optional.of(mockUsuario);
        when(usuarioService.getUsuarioByUsuario(anyString())).thenReturn(mockOptional);
        when(mockUsuario.getPass()).thenReturn("hashed");  
        doThrow(new NoSuchAlgorithmException()).when(usuarioService).checkPass(anyString(), anyString());

        ResponseEntity<String> response = usuarioController.viewPass("user", "pass");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error", response.getBody());
    }

    @Test
    void testAdmin() {
        when(usuarioService.Admin(anyLong())).thenReturn(true);

        ResponseEntity<Boolean> response = usuarioController.Admin(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }
}