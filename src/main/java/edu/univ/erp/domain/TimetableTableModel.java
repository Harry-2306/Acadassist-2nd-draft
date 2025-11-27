package edu.univ.erp.domain;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TimetableTableModel extends AbstractTableModel {

    private final String[] columns = { "Course Title", "Venue", "Timings" };

    private ArrayList<TimeTableRow> rows;

    public TimetableTableModel(ArrayList<TimeTableRow> rows) {
        this.rows = rows;
    }

    public void setRows(ArrayList<TimeTableRow> newRows) {
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

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TimeTableRow row = rows.get(rowIndex);
        switch (columnIndex) {
            case 0: return row.title;
            case 1: return row.venue;
            case 2: return row.timings;
            default: return "";
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
