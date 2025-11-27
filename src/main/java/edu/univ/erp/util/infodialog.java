package edu.univ.erp.util;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;

public class infodialog extends JOptionPane {

    public infodialog(String message){
        try{
            UIManager.setLookAndFeel(new FlatDarculaLaf());

        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JOptionPane.showOptionDialog(
                null,
                message,
                "System Message", // Dialog title
                JOptionPane.DEFAULT_OPTION, // Option type: no default buttons
                JOptionPane.PLAIN_MESSAGE, // Message type: no icon
                null, // Icon (null for no icon)
                new Object[] {}, // Options array: empty for no buttons
                null // Initial value (not relevant when there are no options)
        );
        this.setVisible(true);
    }

}
