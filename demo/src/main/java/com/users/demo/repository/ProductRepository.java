package com.users.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.users.demo.domian.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    List<Product> findAllByCategoryId(int id);
}