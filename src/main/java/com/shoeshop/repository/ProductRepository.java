package com.shoeshop.repository;

import com.shoeshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
           select p from Product p
           where lower(p.name) like lower(concat('%', :q, '%'))
             and (:category = '' or p.category = :category)
           order by p.id
           """)
    List<Product> search(@Param("q") String q, @Param("category") String category);
}
