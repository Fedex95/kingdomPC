package com.tienda.kpback.Repository;

import com.tienda.kpback.Entity.UsuarioEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRep extends JpaRepository<UsuarioEnt, Integer> {
}
