package com.kamran.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kamran.blog.entities.Role;


public interface RoleRepo extends JpaRepository<Role, Integer> {

}
