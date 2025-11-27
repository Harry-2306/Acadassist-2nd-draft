package edu.univ.erp.ui;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SliderToggle extends JToggleButton{
    public SliderToggle() {
        // Set FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Create a toggle button styled like a slider
        this.setText("OFF");
        this.putClientProperty("JButton.buttonType", "roundRect"); // smoother look
        this.setFocusPainted(false);
        this.setPreferredSize(new Dimension(120, 30));

        // Add a nice custom style
        this.addActionListener((ActionEvent e) -> {
            if (this.isSelected()) {
                this.setText("Dark Mode ON");
                this.setBackground(new Color(76, 175, 80)); // green
                this.setForeground(Color.WHITE);
            } else {
                this.setText("Dark Mode OFF");
                this.setBackground(new Color(189, 189, 189)); // gray
                this.setForeground(Color.BLACK);
            }
        });

        // Initialized style
        this.setBackground(new Color(189, 189, 189));
        this.setFocusPainted(false);

        this.setForeground(Color.BLACK);

    }
}
