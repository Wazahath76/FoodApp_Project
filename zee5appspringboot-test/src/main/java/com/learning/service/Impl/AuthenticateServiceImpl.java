package com.learning.service.Impl;

import java.io.IOException;
import java.util.Optional;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.dto.Authenticate;
import com.learning.dto.Authenticate;
import com.learning.dto.Role;
import com.learning.dto.Register;
import com.learning.exception.IdNotFoundException;
import com.learning.repository.AuthenticateRepository;
import com.learning.repository.AuthenticateRepository;
import com.learning.service.AuthenticateService;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {


	@Autowired
	private AuthenticateRepository repository ;
	
	@Override
	public String addCredentials(Authenticate authenticate) {
		// TODO Auto-generated method stub
		Authenticate authenticate2 = repository.save(authenticate);
		if (authenticate2 != null) {
			return "success";
		} else {
			return "fail";
		}
	}

	@Override
	public String deleteCredentials(String userName) {
		// TODO Auto-generated method stub
		
		Optional<Authenticate> optional;
		try {
			optional = repository.findById(userName);
			if(optional.isEmpty()) {
				throw new IdNotFoundException("record not found");
			}
			else {
				repository.deleteById(userName);
				return "login record deleted";
			}
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
	}

	@Override
	public String changePassword(String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String changeRole(String userName, Role role) {
		// TODO Auto-generated method stub
		return null;
	}

}