package com.tienda.kpback.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class PedidoEnt implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false)
    private Products producto;

    @Size(min=1, max=100)
    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private int cantidad;

    private double valorEnvio;

    @Column(nullable = false)
    private String direccionEnvio;

    @Column(nullable = false)
    private double precioTotal;

    @Column(name = "fecha", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime fecha;

    @Size(min=1, max=100)
    @Column(nullable = false)
    private String nombreCliente;

    @Size(min=1, max = 100)
    @Column(nullable = false)
    private String apellidoCliente;

    @Size(min=1, max=10)
    private String telefonoCliente;

    @Size(min=1, max=100)
    private String correoCliente;
}
