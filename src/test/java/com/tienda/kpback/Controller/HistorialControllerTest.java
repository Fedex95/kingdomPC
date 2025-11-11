package com.tienda.kpback.Controller;

import com.tienda.kpback.Entity.Historial;
import com.tienda.kpback.Service.HistorialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import com.tienda.kpback.Config.CustomUserDetails;
import com.tienda.kpback.Entity.UsuarioEnt;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HistorialControllerTest {

    @Mock
    private HistorialService historialService;

    @InjectMocks
    private HistorialController historialController;

    @Mock
    private CustomUserDetails userDetails;

    private Historial mockHistorial;

    @BeforeEach
    void setUp() {
        mockHistorial = new Historial();
    }

    @Test
    void testGetHistorial() {
        when(userDetails.getUserId()).thenReturn(1L);
        List<Historial> mockList = Arrays.asList(mockHistorial);
        when(historialService.findByUsuarioId(1L)).thenReturn(mockList);

        ResponseEntity<List<Historial>> response = historialController.getHistorial(userDetails);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
    }

    @Test
    void testGetAllHistorials_Success() {
        when(userDetails.getUserId()).thenReturn(1L);
        when(userDetails.getRol()).thenReturn(UsuarioEnt.Rol.ADMIN);
        List<Historial> mockList = Arrays.asList(mockHistorial);
        when(historialService.getHistorialAdmin(1L)).thenReturn(mockList);

        ResponseEntity<List<Historial>> response = historialController.getAllHistorials(userDetails);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
    }

    @Test
    void testGetAllHistorials_Error() {
        when(userDetails.getRol()).thenReturn(UsuarioEnt.Rol.USER);
        ResponseEntity<List<Historial>> response = historialController.getAllHistorials(userDetails);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
