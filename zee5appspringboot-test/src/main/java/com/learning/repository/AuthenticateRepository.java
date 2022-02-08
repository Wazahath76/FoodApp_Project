package com.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.dto.Authenticate;



@Repository
public interface AuthenticateRepository extends JpaRepository<Authenticate, String> {
	
	Boolean existsByUserName(String userName);

}