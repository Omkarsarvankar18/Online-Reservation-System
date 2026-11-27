
package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import model.Reservation;
import model.Train;
import service.ReservationService;
import service.TrainService;

public class ReservationFrame extends JFrame {

    // ---------- Colors ----------
    private static final Color PRIMARY = new Color(0x2563EB);
    private static final Color SUCCESS = new Color(0x16A34A);
    private static final Color BACKGROUND = new Color(0xF3F4F6);
    private static final Color CARD = Color.WHITE;
    private static final Color LABEL_COLOR = new Color(0x374151);
    private static final Color TEXT_MUTED = new Color(0xDBEAFE);

    // ---------- Components ----------
    private JTextField passengerField;
    private JTextField trainNumberField;
    private JTextField trainNameField;
    private JTextField journeyDateField;

    private JComboBox<String> classCombo;

    private JLabel sourceLabel;
    private JLabel destinationLabel;

    private JButton bookButton;

    // ---------- Services ----------
    private TrainService trainService = new TrainService();
    private ReservationService reservationService =
            new ReservationService();

    private Train selectedTrain;

    // ---------- Constructor ----------
    public ReservationFrame() {

        setTitle("Online Reservation System - Book Ticket");
        setSize(540, 680);
        setMinimumSize(new Dimension(480, 600));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---------- Header ----------
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(PRIMARY);
        header.setBorder(new EmptyBorder(22, 30, 22, 30));

        JLabel heading = new JLabel("Ticket Reservation");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(Color.WHITE);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subHeading = new JLabel(
                "Search a train and book your seat"
        );
        subHeading.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subHeading.setForeground(TEXT_MUTED);
        subHeading.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(heading);
        header.add(Box.createVerticalStrut(5));
        header.add(subHeading);

        // ---------- Form Fields ----------
        passengerField = createTextField();
        trainNumberField = createTextField();

        trainNameField = createTextField();
        trainNameField.setEditable(false);
        trainNameField.setBackground(new Color(0xF9FAFB));

        journeyDateField = createTextField();

        classCombo = new JComboBox<>(
                new String[]{
                        "Sleeper",
                        "AC",
                        "General",
                        "First Class"
                }
        );

        classCombo.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        classCombo.setPreferredSize(new Dimension(200, 38));

        sourceLabel = createValueLabel("-");
        destinationLabel = createValueLabel("-");

        // ---------- Buttons ----------
        JButton searchButton = createButton(
                "Search Train", PRIMARY
        );

        bookButton = createButton(
                "Book Ticket", SUCCESS
        );

        // Initially disabled until train is searched
        bookButton.setEnabled(false);

        // ---------- Form Card ----------
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(CARD);

        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(0xE5E7EB)
                ),
                new EmptyBorder(22, 25, 22, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        int row = 0;

        row = addRow(
                form, gbc, row,
                "Passenger Name", passengerField
        );

        row = addRow(
                form, gbc, row,
                "Train Number", trainNumberField
        );

        // ---------- Search Button ----------
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.weightx = 1;

        form.add(searchButton, gbc);

        gbc.gridwidth = 1;

        // ---------- Remaining Fields ----------
        row = addRow(
                form, gbc, row,
                "Train Name", trainNameField
        );

        row = addRow(
                form, gbc, row,
                "Class Type", classCombo
        );

        row = addRow(
                form, gbc, row,
                "Journey Date (YYYY-MM-DD)", journeyDateField
        );

        row = addRow(
                form, gbc, row,
                "Source", sourceLabel
        );

        row = addRow(
                form, gbc, row,
                "Destination", destinationLabel
        );

        // ---------- Book Button ----------
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(18, 5, 5, 5);

        form.add(bookButton, gbc);

        // ---------- Wrapper ----------
        JPanel wrapper = new JPanel(new BorderLayout());

        wrapper.setBackground(BACKGROUND);
        wrapper.setBorder(
                new EmptyBorder(20, 30, 20, 30)
        );

        wrapper.add(form, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        // ---------- Add Components ----------
        add(header, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // ---------- Button Actions ----------
        searchButton.addActionListener(e -> searchTrain());

        bookButton.addActionListener(e -> bookTicket());

        // Press Enter to search train
        trainNumberField.addActionListener(
                e -> searchTrain()
        );

        setVisible(true);
    }

    // ---------- Add Form Row ----------
    private int addRow(
            JPanel form,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JComponent field
    ) {

        gbc.insets = new Insets(8, 5, 8, 5);

        JLabel label = new JLabel(labelText);

        label.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        label.setForeground(LABEL_COLOR);

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
    private JTextField createTextField() {

        JTextField field = new JTextField();

        field.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        field.setPreferredSize(
                new Dimension(200, 38)
        );

        return field;
    }

    // ---------- Create Value Label ----------
    private JLabel createValueLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        label.setForeground(new Color(0x111827));

        return label;
    }

    // ---------- Create Button ----------
    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        button.setForeground(Color.WHITE);
        button.setBackground(color);

        button.setFocusPainted(false);

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(200, 42)
        );

        button.setBorderPainted(false);

        return button;
    }

    // ---------- Search Train ----------
    private void searchTrain() {

        // Reset previous search result
        selectedTrain = null;

        trainNameField.setText("");
        sourceLabel.setText("-");
        destinationLabel.setText("-");

        bookButton.setEnabled(false);

        try {

            String input = trainNumberField.getText().trim();

            if (input.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter train number!"
                );

                return;
            }

            int trainNumber = Integer.parseInt(input);

            selectedTrain =
                    trainService.getTrainDetails(trainNumber);

            if (selectedTrain != null) {

                trainNameField.setText(
                        selectedTrain.getTrainName()
                );

                sourceLabel.setText(
                        selectedTrain.getSource()
                );

                destinationLabel.setText(
                        selectedTrain.getDestination()
                );

                bookButton.setEnabled(true);

                JOptionPane.showMessageDialog(
                        this,
                        "Train found successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Train not found!"
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid numeric train number!"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage()
            );
        }
    }

    // ---------- Book Ticket ----------
    private void bookTicket() {

        try {

            if (selectedTrain == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please search for a valid train first!"
                );

                return;
            }

            // Get passenger name
            String passengerName =
                    passengerField.getText().trim();

            if (passengerName.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter passenger name!"
                );

                return;
            }

            // Get journey date
            LocalDate journeyDate = LocalDate.parse(
                    journeyDateField.getText().trim()
            );

            // Create Reservation object
            Reservation reservation = new Reservation();

            reservation.setPassengerName(passengerName);

            reservation.setTrainNumber(
                    selectedTrain.getTrainNumber()
            );

            reservation.setTrainName(
                    selectedTrain.getTrainName()
            );

            reservation.setClassType(
                    (String) classCombo.getSelectedItem()
            );

            reservation.setJourneyDate(journeyDate);

            reservation.setSource(
                    selectedTrain.getSource()
            );

            reservation.setDestination(
                    selectedTrain.getDestination()
            );

            // Save reservation using service
            String pnr =
                    reservationService.bookTicket(reservation);

            if (pnr != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ticket Booked Successfully!\n\n"
                                + "Passenger: " + passengerName
                                + "\nTrain: "
                                + selectedTrain.getTrainName()
                                + "\nPNR Number: " + pnr
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking failed! Please check your details."
                );
            }

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date! Enter date as YYYY-MM-DD."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage()
            );
        }
    }
}
