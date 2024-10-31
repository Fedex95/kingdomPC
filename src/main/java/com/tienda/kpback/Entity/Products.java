package com.tienda.kpback.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(min=1, max=30)
    private String nombreProducto;

    @Column(nullable = false)
    @Size(min=1, max=100)
    private String descripcionProducto;

    @Column(nullable = false)
    private float precioProducto;

    @Column(nullable = false)
    private boolean estadoProducto;
}
