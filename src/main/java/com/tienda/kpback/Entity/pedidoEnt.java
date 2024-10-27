package com.tienda.kpback.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="pedido")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class pedidoEnt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Size(min=1, max=30)
    private String nombresCliente;

    @Column(nullable = false)
    @Size(min = 1, max = 30)
    private String apellidosCliente;

    @Column(nullable = false)
    @Size(min=10, max=10)
    private int telfCliente;

    @Column(nullable = false)
    @Size(min = 1, max = 20)
    private String emailCliente;

    @Column(nullable = false)
    @Size(min = 1, max = 30)
    private String direccionCliente;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pedido", cascade = {CascadeType.PERSIST,
    CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @Column(nullable = true)
    private List<pedidoEnt> pedidos;
}
