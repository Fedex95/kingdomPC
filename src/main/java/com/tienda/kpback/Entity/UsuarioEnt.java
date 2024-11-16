package com.tienda.kpback.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name="usuarios")
@NoArgsConstructor
@Getter
@Setter

public class UsuarioEnt implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String nombreUsuario;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String rolUsuario;

    @Column(nullable = false, unique = true)
    @Size(max = 10)
    private String cedula;

    @Column(nullable = false)
    @Size(max=10)
    private String numeroTelefono;

    @Column(nullable = false)
    private String nombreTarjeta;

    @Column(nullable = false)
    private String numeroTarjeta;

    @Column(nullable = false)
    private String fechaValidez;
}
