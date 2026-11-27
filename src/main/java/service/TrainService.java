package service;

import DAO.TrainDAO;
import model.Train;

public class TrainService {
	
	private TrainDAO trainDAO= new TrainDAO();
	
	public Train getTrainDetails(int trainNumber) {
		if(trainNumber <= 0) {
			return null;
		}
		
		return trainDAO.getTrainByNumber(trainNumber);
	}

}
