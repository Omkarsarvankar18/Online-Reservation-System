
package com.oasis.reservation;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.formdev.flatlaf.FlatLightLaf;

import view.LoginFrame;

public class Main {

    public static void main(String[] args) {

        // Smooth text rendering
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        // Setup application theme
        setupLookAndFeel();

        // Start Swing application on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {

            try {

                LoginFrame login = new LoginFrame();

                login.setLocationRelativeTo(null);
                login.setVisible(true);

            } catch (Exception e) {

                e.printStackTrace();

                JOptionPane.showMessageDialog(
                        null,
                        "Failed to start application:\n" + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    // ---------- Setup Look and Feel ----------
    private static void setupLookAndFeel() {

        try {

            // Custom UI settings
            UIManager.put("Button.arc", 12);
            UIManager.put("Component.arc", 12);
            UIManager.put("TextComponent.arc", 10);

            UIManager.put("ProgressBar.arc", 12);

            UIManager.put("ScrollBar.thumbArc", 999);
            UIManager.put("ScrollBar.width", 12);

            UIManager.put("Component.focusWidth", 1);

            UIManager.put("Table.showHorizontalLines", true);
            UIManager.put("Table.rowHeight", 28);

            UIManager.put(
                    "TableHeader.font",
                    new Font("Segoe UI", Font.BOLD, 13)
            );

            UIManager.put(
                    "defaultFont",
                    new Font("Segoe UI", Font.PLAIN, 14)
            );

            // Application accent color
            UIManager.put(
                    "@accentColor",
                    new Color(0x2563EB)
            );

            // Apply FlatLaf theme
            FlatLightLaf.setup();

            JFrame.setDefaultLookAndFeelDecorated(false);

        } catch (Throwable t) {

            // Fallback to Nimbus if FlatLaf is unavailable
            try {

                for (LookAndFeelInfo info :
                        UIManager.getInstalledLookAndFeels()) {

                    if ("Nimbus".equals(info.getName())) {

                        UIManager.setLookAndFeel(
                                info.getClassName()
                        );

                        return;
                    }
                }

                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );

            } catch (Exception ignored) {

                ignored.printStackTrace();
            }
        }
    }
}
