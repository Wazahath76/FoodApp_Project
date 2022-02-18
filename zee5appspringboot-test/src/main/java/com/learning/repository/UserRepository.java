package com.learning.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.dto.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

		Boolean existsByEmail(String email);
		Optional<User> findByUsername(String username);
		Boolean existsByUsername(String Username);
		Boolean existsByNameAndPassword(String name, String password);
		boolean existsById(int id);
		
}
		
