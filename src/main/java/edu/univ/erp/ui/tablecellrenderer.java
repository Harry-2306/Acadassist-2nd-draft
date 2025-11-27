package edu.univ.erp.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class tablecellrenderer extends DefaultTableCellRenderer {

    Color base = new Color(0x050D1F);        // your background color
    Color tintA = new Color(0x131F4C);       // row tint 1
    Color tintB = new Color(0x4C5873);       // row tint 2
    Color selection = new Color(0x0B1A3A);
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        if (!isSelected) {
            // Alternating rows
            if (row % 2 == 0) {
                c.setBackground(tintA);
            } else {
                c.setBackground(selection);
            }
            c.setForeground(Color.WHITE);
        } else {
            // Selection color
            c.setBackground(selection);
            c.setForeground(Color.WHITE);
        }

        return c;

    }
}
