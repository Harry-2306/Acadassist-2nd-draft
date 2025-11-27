package edu.univ.erp.ui;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;

public class errorinfodialog extends JOptionPane {

    private final Color selection = new Color(0x0B1A3A);

    public errorinfodialog(String msg){

        try{
            UIManager.setLookAndFeel(new FlatDarculaLaf());

        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.setBackground(selection);
        JOptionPane.showMessageDialog(this,msg,"Warning!",JOptionPane.WARNING_MESSAGE );
    }
}
