package edu.univ.erp.ui;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;

public class ArrayListTableModel extends AbstractTableModel {
    private ArrayList<ArrayList<String>> data;
    private String[] columnNames;

    public ArrayListTableModel(ArrayList<ArrayList<String>> data, String[] columnNames) {
        this.data = data;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ArrayList<String> row = data.get(rowIndex);
        if (columnIndex < row.size()) {
            return row.get(columnIndex);
        }
        return null;
    }

    public void setData(ArrayList<ArrayList<String>> newData) {
        this.data = newData;
        fireTableDataChanged();
    }
}
