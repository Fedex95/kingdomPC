package com.tienda.kpback.Repository;

import com.tienda.kpback.Entity.PedidoEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRep extends JpaRepository<PedidoEnt, Integer> {
}
