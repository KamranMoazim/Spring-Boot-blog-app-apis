package com.kamran.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamran.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
