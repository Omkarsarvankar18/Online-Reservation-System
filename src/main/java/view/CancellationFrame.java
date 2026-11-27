
package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.Reservation;
import service.ReservationService;

import java.awt.*;

public class CancellationFrame extends JFrame {

    // ---------- Color Theme ----------

    private static final Color PRIMARY = new Color(37, 99, 235);
    private static final Color DANGER = new Color(220, 38, 38);
    private static final Color BACKGROUND = new Color(245, 247, 250);
    private static final Color TEXT = new Color(31, 41, 55);
    private static final Color MUTED = new Color(107, 114, 128);
    private static final Color BORDER = new Color(229, 231, 235);
    private static final Color WHITE = Color.WHITE;

    // ---------- Fields ----------

    private JTextField pnrField;
    private JTextField passengerField;
    private JTextField trainField;
    private JTextField classField;
    private JTextField dateField;
    private JTextField sourceField;
    private JTextField destinationField;

    private JButton cancelButton;

    private ReservationService reservationService =
            new ReservationService();

    private Reservation selectedReservation;

    // ---------- Constructor ----------

    public CancellationFrame() {

        setTitle("Online Reservation System - Cancel Ticket");
        setSize(600, 680);
        setMinimumSize(new Dimension(500, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---------- Header ----------

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(WHITE);
        header.setBorder(new EmptyBorder(25, 40, 22, 40));

        JLabel heading = new JLabel("Cancel Reservation");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 25));
        heading.setForeground(TEXT);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subHeading = new JLabel(
                "Enter your PNR number to view and cancel your ticket."
        );
        subHeading.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subHeading.setForeground(MUTED);
        subHeading.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(heading);
        header.add(Box.createVerticalStrut(7));
        header.add(subHeading);

        // ---------- Fields ----------

        pnrField = createTextField(true);

        pnrField.putClientProperty(
                "JTextField.placeholderText",
                "Enter PNR number"
        );

        passengerField = createTextField(false);
        trainField = createTextField(false);
        classField = createTextField(false);
        dateField = createTextField(false);
        sourceField = createTextField(false);
        destinationField = createTextField(false);

        JButton fetchButton = createButton(
                "Fetch Details",
                PRIMARY
        );

        cancelButton = createButton(
                "Cancel Ticket",
                DANGER
        );

        cancelButton.setEnabled(false);

        // ---------- Form Card ----------

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(WHITE);

        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(22, 25, 22, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        int row = 0;

        // PNR Field
        row = addRow(
                form, gbc, row,
                "PNR Number", pnrField
        );

        // Fetch Button
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        form.add(fetchButton, gbc);

        gbc.gridwidth = 1;

        // Divider
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 15, 5);

        form.add(new JSeparator(), gbc);

        gbc.gridwidth = 1;

        // Reservation Details
        row = addRow(form, gbc, row,
                "Passenger Name", passengerField);

        row = addRow(form, gbc, row,
                "Train Name", trainField);

        row = addRow(form, gbc, row,
                "Class Type", classField);

        row = addRow(form, gbc, row,
                "Journey Date", dateField);

        row = addRow(form, gbc, row,
                "Source", sourceField);

        row = addRow(form, gbc, row,
                "Destination", destinationField);

        // Cancel Button
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);

        form.add(cancelButton, gbc);

        // ---------- Wrapper ----------

        JPanel wrapper = new JPanel(new BorderLayout());

        wrapper.setBackground(BACKGROUND);
        wrapper.setBorder(new EmptyBorder(25, 35, 25, 35));

        wrapper.add(form, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(wrapper);

        scroll.setBorder(null);
        scroll.setBackground(BACKGROUND);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(header, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // ---------- Actions ----------

        fetchButton.addActionListener(e -> fetchReservation());

        cancelButton.addActionListener(e -> cancelReservation());

        pnrField.addActionListener(e -> fetchReservation());

        setVisible(true);
    }

    // ---------- Add Form Row ----------

    private int addRow(
            JPanel form,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JComponent field) {

        gbc.insets = new Insets(9, 5, 9, 5);

        JLabel label = new JLabel(labelText);

        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.35;

        form.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;

        form.add(field, gbc);

        return row + 1;
    }

    // ---------- Create Text Field ----------

    private JTextField createTextField(boolean editable) {

        JTextField field = new JTextField();

        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(220, 36));
        field.setEditable(editable);

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(5, 10, 5, 10)
        ));

        if (!editable) {

            field.setBackground(new Color(249, 250, 251));
            field.setForeground(TEXT);
        }

        return field;
    }

    // ---------- Create Button ----------

    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(WHITE);
        button.setBackground(color);

        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(11, 20, 11, 20));

        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        button.setPreferredSize(new Dimension(200, 42));

        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {

                if (button.isEnabled()) {
                    button.setBackground(color.darker());
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {

                button.setBackground(
                        button.isEnabled()
                                ? color
                                : new Color(156, 163, 175)
                );
            }
        });

        return button;
    }

    // ---------- Fetch Reservation Details ----------

    private void fetchReservation() {

        String pnr = pnrField.getText().trim();

        if (pnr.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter PNR Number!"
            );

            return;
        }

        selectedReservation =
                reservationService.getReservation(pnr);

        if (selectedReservation != null) {

            passengerField.setText(
                    selectedReservation.getPassengerName()
            );

            trainField.setText(
                    selectedReservation.getTrainName()
            );

            classField.setText(
                    selectedReservation.getClassType()
            );

            dateField.setText(
                    selectedReservation.getJourneyDate().toString()
            );

            sourceField.setText(
                    selectedReservation.getSource()
            );

            destinationField.setText(
                    selectedReservation.getDestination()
            );

            cancelButton.setEnabled(true);

        } else {

            selectedReservation = null;

            clearFields();

            JOptionPane.showMessageDialog(
                    this,
                    "No reservation found for this PNR!"
            );
        }
    }

    // ---------- Cancel Reservation ----------

    private void cancelReservation() {

        if (selectedReservation == null) {
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel this ticket?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {

            boolean cancelled =
                    reservationService.cancelTicket(
                            selectedReservation.getPnrNumber()
                    );

            if (cancelled) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ticket Cancelled Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();

                pnrField.setText("");

                selectedReservation = null;

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Ticket cancellation failed!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // ---------- Clear Reservation Details ----------

    private void clearFields() {

        passengerField.setText("");
        trainField.setText("");
        classField.setText("");
        dateField.setText("");
        sourceField.setText("");
        destinationField.setText("");

        cancelButton.setEnabled(false);
    }
}
