package com.tienda.kpback.Repository;

import com.tienda.kpback.Entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRep extends JpaRepository<Products, Integer>{
}
