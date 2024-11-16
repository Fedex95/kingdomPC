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

    @Column(nullable = false, unique = true)
    private String nombreUsuario;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String password;

    @Column
    private String rolUsuario;
    @PrePersist
    public void prePersist(){
        if(this.rolUsuario == null || this.rolUsuario.isEmpty()){
            this.rolUsuario = "USER";
        }
    }

    @Column(nullable = false, unique = true)
    @Size(max = 10)
    private String cedula;

    @Column(nullable = false)
    @Size(max=10)
    private String numeroTelefono;

    @Column
    private String tipoTarjeta;

    @Column
    private String nombrTarjeta;

    @Column
    private String nombreUsuarioTarjeta;

    @Column
    private String numeroTarjeta;

    @Column
    private String fechaValidez;
}
