package com.tienda.kpback.Controller;

import com.tienda.kpback.Entity.Historial;
import com.tienda.kpback.Service.HistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.tienda.kpback.Config.CustomUserDetails;
import com.tienda.kpback.Entity.UsuarioEnt;

@RestController
@RequestMapping("/api/historial")
@SecurityRequirement(name = "bearerAuth")

public class HistorialController {
    @Autowired
    private HistorialService historialService;


    @GetMapping("/user")
    public ResponseEntity<List<Historial>> getHistorial(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        List<Historial> historials = historialService.findByUsuarioId(userId);
        return ResponseEntity.ok(historials);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Historial>> getAllHistorials(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails.getRol() == UsuarioEnt.Rol.ADMIN) {
            List<Historial> historials = historialService.getHistorialAdmin(userDetails.getUserId());
            return ResponseEntity.ok(historials);
        } else {
            return ResponseEntity.status(403).build();
        }
    }
}
