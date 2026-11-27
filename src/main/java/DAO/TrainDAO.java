package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DBConnection;
import model.Train;

public class TrainDAO {
	
	public Train getTrainByNumber(int trainNumber) {
		
		String sql = "SELECT * FROM trains WHERE train_number = ?";
	
		try(Connection con = DBConnection.getConnection();
		    PreparedStatement ps = con.prepareStatement(sql);){
			
			ps.setInt(1, trainNumber);
			
			try(ResultSet rs =ps.executeQuery()){
				if(rs.next()) {
					Train train = new Train();
					train.setTrainNumber(rs.getInt("train_number"));
					train.setTrainName(rs.getString("train_name"));
					train.setSource(rs.getString("source"));
					train.setDestination(rs.getString("destination"));
					
					return train;
				}
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return null;
	}

}
