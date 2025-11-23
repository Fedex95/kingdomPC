package com.tienda.kpback.Controller;

import com.tienda.kpback.Entity.Prestamo;
import com.tienda.kpback.Service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.tienda.kpback.Config.CustomUserDetails;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import com.tienda.kpback.Entity.UsuarioEnt;

@RestController
@RequestMapping("/api/prestamos")
@SecurityRequirement(name = "bearerAuth")

public class PrestamoController {
    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/all")
    @Transactional(readOnly = true)  
    public ResponseEntity<List<Prestamo>> getAllPrestamos(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails.getRol() == UsuarioEnt.Rol.ADMIN) {  
            List<Prestamo> prestamos = prestamoService.findAll();
            return ResponseEntity.ok(prestamos);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping("/{id}/estado") 
    @Transactional  
    public ResponseEntity<?> updateEstado(@PathVariable UUID id, @RequestBody Map<String, Object> request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails.getRol() != UsuarioEnt.Rol.ADMIN) {  
            return ResponseEntity.status(403).build();
        }
        try {
            String estadoStr = (String) request.get("estado");
            Prestamo.Estado estado = Prestamo.Estado.valueOf(estadoStr);  
            prestamoService.updateEstado(id, estado);
            return ResponseEntity.noContent().build();  // 204: evita serializar entidad con proxies
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
