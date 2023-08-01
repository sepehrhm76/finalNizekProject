package org.example.view;

import org.example.Model.User;
import org.example.Model.UserRole;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;

public class Projects extends JPanel implements TableModel {
    private static Projects instance = null;
    
    public Projects(){
        setLayout(null);
        setVisible(false);
        setBounds(300,0,1140,1040);
        setBackground(Color.WHITE);
    }


    public static Projects getInstance() {
        if (instance == null)
            instance = new Projects();
        return instance;
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
