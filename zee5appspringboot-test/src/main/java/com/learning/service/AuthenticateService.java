package com.learning.service;

import com.learning.dto.Authenticate;
import com.learning.dto.Role;

public interface AuthenticateService {
	public String addCredentials(Authenticate authenticate);
	public String deleteCredentials(String userName);
	public String changePassword(String userName, String password);
	public String changeRole(String userName, Role role);
}