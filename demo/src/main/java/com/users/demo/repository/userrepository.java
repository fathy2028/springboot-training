package com.users.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.users.demo.domian.user;

@Repository
public interface userrepository extends JpaRepository<user,Long> {
    user findByUsername(String username);
}
