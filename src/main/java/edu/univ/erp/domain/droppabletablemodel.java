package edu.univ.erp.domain;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class droppabletablemodel extends AbstractTableModel {

    private final String[] columns = {
            "Select", "Course Code", "Course Title", "Instructor", "Venue"
    };

    private ArrayList<droppablecourseinfo> rows;

    public droppabletablemodel(ArrayList<droppablecourseinfo> rows) {
        this.rows = rows;
    }

    public void setRows(ArrayList<droppablecourseinfo> newRows) {
        this.rows = newRows;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return rows == null ? 0 : rows.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public void removeRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= rows.size()) return;

        rows.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) return Boolean.class; // checkbox column
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 0; // only checkbox editable
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        droppablecourseinfo row = rows.get(rowIndex);

        switch (columnIndex) {
            case 0: return row.selected;
            case 1: return row.courseCode;
            case 2: return row.courseTitle;
            case 3: return row.instructor;
            case 4: return row.venue;
            default: return "";
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (col == 0) {
            rows.get(row).selected = (Boolean) value;
            fireTableCellUpdated(row, col);
        }
    }

    public ArrayList<droppablecourseinfo> getRows() {
        return rows;
    }
}
