package edu.univ.erp.domain;

import javax.swing.table.AbstractTableModel;

public class SectionTableModel extends AbstractTableModel {

    private final String[] columns = {
            "Section ID",
            "Course Code",
            "Current Cap",
            "Max Cap",
            "Venue",
            "Timings"
    };

    private final java.util.List<sectionlogistics> sectionList = new java.util.ArrayList<>();

    @Override
    public int getRowCount() {
        return sectionList.size();
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
    public Object getValueAt(int row, int col) {
        sectionlogistics s = sectionList.get(row);

        return switch (col) {
            case 0 -> s.sectionid;
            case 1 -> s.coursecode;
            case 2 -> s.currcapacity;
            case 3 -> s.maxcapacity;
            case 4 -> s.venue;
            case 5 -> s.timings;
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        // Editable: maxCap, venue, timings
        return col >= 3;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        sectionlogistics s = sectionList.get(row);

        switch (col) {
            case 3 -> s.maxcapacity=Integer.parseInt(value.toString());
            case 4 -> s.venue=(value.toString());
            case 5 -> s.timings=(value.toString());
        }

        fireTableCellUpdated(row, col);
    }

    public void setData(java.util.List<sectionlogistics> list) {
        sectionList.clear();
        sectionList.addAll(list);
        fireTableDataChanged();
    }

    public sectionlogistics getSectionAt(int row) {
        return sectionList.get(row);
    }
}
