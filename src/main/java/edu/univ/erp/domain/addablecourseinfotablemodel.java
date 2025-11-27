package edu.univ.erp.domain;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class addablecourseinfotablemodel extends AbstractTableModel {

    private final String[] columns = {"Select", "Course Code", "Course Title", "Prerequisite", "Credits", "Current Cap", "Max Cap", "Venue", "Instructor"
    };

    private  ArrayList<addablecourseinfo> data;

    public addablecourseinfotablemodel(ArrayList<addablecourseinfo> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public Class<?> getColumnClass(int col) {
        if (col == 0) return Boolean.class;   // checkbox column
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return (col == 0 );
    }

    @Override
    public Object getValueAt(int row, int col) {
        addablecourseinfo info = data.get(row);

        return switch (col) {
            case 0 -> info.isSelected();
            case 1 -> info.getCourseCode();
            case 2 -> info.getCourseTitle();
            case 3 -> info.getPrerequisite();
            case 4 -> info.getCredits();
            case 5 -> info.getCurrentCapacity();
            case 6 -> info.getMaxCapacity();
            case 7 -> info.getVenue();
            case 8 -> info.getInstructor();
            default -> null;
        };
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (col == 0) {
            data.get(row).setSelected((boolean) value);
            fireTableCellUpdated(row, col);
        }
    }

    public addablecourseinfo getRow(int row) {
        return data.get(row);
    }

    public void setData(ArrayList<addablecourseinfo> newData) {
        this.data = newData;
        fireTableDataChanged();
    }
}
