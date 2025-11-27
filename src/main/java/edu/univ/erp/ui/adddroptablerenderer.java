package edu.univ.erp.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class adddroptablerenderer extends DefaultTableCellRenderer {
    Color base = new Color(0x050D1F);
    Color tintA = new Color(0x131F4C);
    Color tintB = new Color(0x4C5873);
    Color selection = new Color(0x0B1A3A);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        if (column == 0) {
            return table.getDefaultRenderer(Boolean.class)
                    .getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (!isSelected) {
            c.setBackground(row % 2 == 0 ? tintA : selection);
        } else {
            c.setBackground(selection);
        }
        c.setForeground(Color.WHITE);

        return c;
    }
}
