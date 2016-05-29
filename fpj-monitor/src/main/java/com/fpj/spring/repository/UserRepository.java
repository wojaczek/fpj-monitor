package com.fpj.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpj.spring.entities.EntUser;

public interface UserRepository extends JpaRepository<EntUser, Integer> {
	EntUser findByUsernameIgnoreCase(String username);
}
