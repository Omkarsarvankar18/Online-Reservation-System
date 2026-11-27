
package service;

import java.security.SecureRandom;

import DAO.ReservationDAO;
import model.Reservation;

public class ReservationService {

    private ReservationDAO reservationDAO = new ReservationDAO();

    private static final SecureRandom RANDOM = new SecureRandom();

    // Generate unique PNR
    private String generatePNR() {

        return String.valueOf(
                1000000000L + RANDOM.nextInt(900000000)
        );
    }

    // Book ticket
    public String bookTicket(Reservation reservation) {

        if (reservation == null
                || reservation.getPassengerName() == null
                || reservation.getPassengerName().trim().isEmpty()
                || reservation.getJourneyDate() == null
                || reservation.getClassType() == null
                || reservation.getClassType().trim().isEmpty()
                || reservation.getSource() == null
                || reservation.getSource().trim().isEmpty()
                || reservation.getDestination() == null
                || reservation.getDestination().trim().isEmpty()) {

            return null;
        }

        if (reservation.getJourneyDate().isBefore(
                java.time.LocalDate.now())) {
            return null;
        }

        String pnr = generatePNR();

        reservation.setPnrNumber(pnr);

        boolean saved = reservationDAO.saveReservation(reservation);

        if (saved) {
            return pnr;
        }

        return null;
    }

    // Fetch ticket
    public Reservation getReservation(String pnr) {

        if (pnr == null || pnr.trim().isEmpty()) {
            return null;
        }

        return reservationDAO.getReservationByPNR(pnr.trim());
    }

    // Cancel ticket
    public boolean cancelTicket(String pnr) {

        if (pnr == null || pnr.trim().isEmpty()) {
            return false;
        }

        return reservationDAO.cancelReservation(pnr.trim());
    }
}
