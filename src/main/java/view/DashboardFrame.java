
package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import DAO.ReservationDAO;
import model.Reservation;

public class DashboardFrame extends JFrame {

    // Color theme
    private static final Color PRIMARY = new Color(37, 99, 235);
    private static final Color PRIMARY_DARK = new Color(29, 78, 216);
    private static final Color BACKGROUND = new Color(245, 247, 250);
    private static final Color TEXT = new Color(31, 41, 55);
    private static final Color MUTED = new Color(107, 114, 128);
    private static final Color WHITE = Color.WHITE;

    // DAO object
    private final ReservationDAO reservationDAO = new ReservationDAO();

    public DashboardFrame(String username) {

        setTitle("Online Reservation System");

        setSize(600, 550);
        setMinimumSize(new Dimension(500, 500));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);

        // ---------- Header ----------

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(WHITE);
        header.setBorder(new EmptyBorder(30, 40, 25, 40));

        JLabel title = new JLabel("Welcome, " + username);
        title.setFont(new Font("Segoe UI", Font.BOLD, 25));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Manage your ticket reservations easily."
        );

        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);

        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitle);

        // ---------- Content ----------

        JPanel content = new JPanel(new GridLayout(3, 1, 0, 15));
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(25, 40, 25, 40));

        JButton bookButton = createActionButton(
                "Book Ticket",
                "Reserve your train ticket now",
                PRIMARY
        );

        JButton findButton = createActionButton(
                "Find Reservation",
                "View reservation using your PNR number",
                new Color(22, 163, 74)
        );

        JButton cancelButton = createActionButton(
                "Cancel Ticket",
                "Cancel an existing reservation",
                PRIMARY_DARK
        );

        content.add(bookButton);
        content.add(findButton);
        content.add(cancelButton);

        // ---------- Footer ----------

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(BACKGROUND);
        footer.setBorder(new EmptyBorder(0, 40, 25, 40));

        JLabel footerText = new JLabel(
                "Online Reservation System"
        );

        footerText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerText.setForeground(MUTED);

        JButton logoutButton = new JButton("Logout");

        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        logoutButton.setForeground(new Color(220, 38, 38));
        logoutButton.setBackground(WHITE);
        logoutButton.setFocusPainted(false);

        logoutButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(229, 231, 235)
                ),
                new EmptyBorder(8, 18, 8, 18)
        ));

        logoutButton.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        footer.add(footerText, BorderLayout.WEST);
        footer.add(logoutButton, BorderLayout.EAST);

        // ---------- Add Components ----------

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(content, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel);

        // ---------- Button Actions ----------

        // Book Ticket
        bookButton.addActionListener(e -> {
            new ReservationFrame().setVisible(true);
            dispose();
        });

        // Find Reservation
        findButton.addActionListener(e -> findReservation());

        // Cancel Ticket
        cancelButton.addActionListener(e -> {
            new CancellationFrame().setVisible(true);
            dispose();
        });

        // Logout
        logoutButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });

        setVisible(true);
    }

    // ---------- Find Reservation Method ----------

    private void findReservation() {

        String pnrNumber = JOptionPane.showInputDialog(
                this,
                "Enter your PNR Number:",
                "Find Reservation",
                JOptionPane.QUESTION_MESSAGE
        );

        // Check if user cancelled or entered nothing
        if (pnrNumber == null || pnrNumber.trim().isEmpty()) {
            return;
        }

        pnrNumber = pnrNumber.trim();

        // Fetch reservation using DAO
        Reservation reservation =
                reservationDAO.getReservationByPNR(pnrNumber);

        if (reservation != null) {

            String details =
                    "RESERVATION DETAILS\n\n"
                    + "PNR Number: " + reservation.getPnrNumber() + "\n"
                    + "Passenger Name: " + reservation.getPassengerName() + "\n"
                    + "Train Number: " + reservation.getTrainNumber() + "\n"
                    + "Train Name: " + reservation.getTrainName() + "\n"
                    + "Class: " + reservation.getClassType() + "\n"
                    + "Journey Date: " + reservation.getJourneyDate() + "\n"
                    + "Source: " + reservation.getSource() + "\n"
                    + "Destination: " + reservation.getDestination();

            JOptionPane.showMessageDialog(
                    this,
                    details,
                    "Reservation Found",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No reservation found for PNR: " + pnrNumber,
                    "Reservation Not Found",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ---------- Reusable Action Button ----------

    private JButton createActionButton(
            String title,
            String description,
            Color color) {

        JButton button = new JButton(
                "<html><div style='text-align:left;'>"
                + "<b style='font-size:16px;'>" + title + "</b>"
                + "<br><span style='font-size:11px;'>"
                + description + "</span>"
                + "</div></html>"
        );

        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMargin(new Insets(15, 25, 15, 25));

        button.setForeground(WHITE);
        button.setBackground(color);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(15, 25, 15, 25));

        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }
}
