package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DBConnection;
import model.User;

public class UserDAO {
	
	public User validateUser(String username, String password) {
		
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		try(Connection con = DBConnection.getConnection();
		    PreparedStatement ps =	con.prepareStatement(sql)) {
		    
		    ps.setString(1, username);
		    ps.setString(2, password);
		    
		   try(ResultSet rs = ps.executeQuery()){
			   if(rs.next()) {
				 User user =  new User();
				 user.setUserId(rs.getInt("user_id"));
				 user.setUsername(rs.getString("username"));
				 user.setPassword(rs.getString("password"));
				 
				 return user;
			   }
			 
			 
		 };
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
		return null;
	}
	

}
