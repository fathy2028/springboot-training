package com.users.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.users.demo.domian.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}