package com.learning.service.Impl;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.repository.UserRepository;
import com.learning.service.UserService;
import com.learning.dto.Login;
import com.learning.repository.LoginRepository;
import com.learning.service.LoginService;

import com.learning.dto.User;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private LoginService service;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Override
	@org.springframework.transaction.annotation.Transactional(rollbackFor = AlreadyExistsException.class)
	public User addUser(User user) throws AlreadyExistsException {
		// TODO Auto-generated method stub
		boolean status = repository.existsByEmail(user.getEmail()) ;
		if(status) {
			throw new AlreadyExistsException("this record already exists");
		}
		User register2 = repository.save(user);
		if (register2 != null) {
			Login login = new Login(user.getEmail(), user.getPassword(), register2);
			if(loginRepository.existsByUserName(user.getEmail())) {
				throw new AlreadyExistsException("this record already exists");
			}
			String result = service.addCredentials(login);
			if(result == "success") {
					//return "record added in user and login";
				return register2;
			}
			else {
				return null;
			}
		}	
		else {
			return null;
		}
		
		}	
	
	@Override
	public String authenticateUser(User user)  {
		boolean result = repository.existsByNameAndPassword(user.getName(),user.getPassword()) ;
		if(result) {
			return "user exists";
		}
		else
		{
			return "user does not exist";
		}
		
	}

	@Override
	public User updateUser(int id, User user) throws IdNotFoundException {
		
		// TODO Auto-generated method stub
		
		if(!this.repository.existsById(id))
			throw new IdNotFoundException( "id not found");
		
		return repository.save(user);
		}

	@Override
	public User getUserById(int id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> optional= repository.findById(id);
		if(optional.isEmpty()) {
			throw new IdNotFoundException("id not found");
		}
		else
		{
			return optional.get();
		}
	}

	@Override
	public User[] getAllUsers() {
		// TODO Auto-generated method stub
		List<User> list = repository.findAll();
		User[] array = new User[list.size()];
		return list.toArray(array);
	}

	@Override
	public String deleteUserById(int id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		User optional;
		User user = new User();
		try {
			optional = this.getUserById(id);
			if(optional==null) {
				throw new IdNotFoundException("id not found");
			}
			else {
				repository.deleteById(id);
				return "User deleted successfully";
			}
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IdNotFoundException(e.getMessage());
		}
	}

	@Override
	public Optional<List<User>> getAllUserDetails() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(repository.findAll());
	}


}