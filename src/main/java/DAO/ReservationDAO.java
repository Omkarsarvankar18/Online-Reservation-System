
package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DBConnection;
import model.Reservation;



public class ReservationDAO {

    // Save reservation
    public boolean saveReservation(Reservation reservation) {

        String sql = "INSERT INTO reservations "
                + "(pnr_number, passenger_name, train_number, "
                + "train_name, class_type, journey_date, source, destination) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reservation.getPnrNumber());
            ps.setString(2, reservation.getPassengerName());
            ps.setInt(3, reservation.getTrainNumber());
            ps.setString(4, reservation.getTrainName());
            ps.setString(5, reservation.getClassType());
            ps.setDate(6, Date.valueOf(reservation.getJourneyDate()));
            ps.setString(7, reservation.getSource());
            ps.setString(8, reservation.getDestination());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Get reservation using PNR
    public Reservation getReservationByPNR(String pnr) {

        String sql = "SELECT * FROM reservations WHERE pnr_number = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pnr);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Reservation reservation = new Reservation();

                    reservation.setReservationId(rs.getInt("reservation_id"));
                    reservation.setPnrNumber(rs.getString("pnr_number"));
                    reservation.setPassengerName(rs.getString("passenger_name"));
                    reservation.setTrainNumber(rs.getInt("train_number"));
                    reservation.setTrainName(rs.getString("train_name"));
                    reservation.setClassType(rs.getString("class_type"));
                    reservation.setJourneyDate(
                            rs.getDate("journey_date").toLocalDate());
                    reservation.setSource(rs.getString("source"));
                    reservation.setDestination(rs.getString("destination"));

                    return reservation;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Cancel reservation using PNR
    public boolean cancelReservation(String pnr) {

        String sql = "DELETE FROM reservations WHERE pnr_number = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pnr);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
