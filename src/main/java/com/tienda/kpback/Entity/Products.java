package com.tienda.kpback.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name="producto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Products implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombreProducto;

    @Column(nullable = false)
    private String descripcionProducto;

    @Column(nullable = false)
    private String categoriaProducto;

    @Column(nullable = false)
    private float precioProducto;

    @Column(nullable = false)
    private boolean estadoProducto;

    @Column(nullable = false)
    private String imagenUrl;
}
