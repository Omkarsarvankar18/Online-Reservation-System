package service;

import DAO.UserDAO;
import model.User;

public class AuthService {
	
	private UserDAO userDAO = new UserDAO();
	
	public User userLogin(String username, String password) {
		if(username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
			return null;
		}
		return userDAO.validateUser(username, password);
	}

}
