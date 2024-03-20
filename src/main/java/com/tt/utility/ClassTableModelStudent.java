package com.tt.utility;

import com.tt.entity.Student;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ClassTableModelStudent {
    public DefaultTableModel setTableStudent(List<Student> listItem, String[] listColumn){
        DefaultTableModel dtm = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dtm.setColumnIdentifiers(listColumn);
        int columns = listColumn.length;
        Object[] obj = null;
        int rows = listItem.size();
        if(rows > 0){
            for(int i = 0; i < rows; i++){
                Student student = listItem.get(i);
                obj = new Object[columns];
                obj[0] = student.getId();
                obj[1] = student.getName();
                obj[2] = student.getDOB();
                obj[3] = student.getGender();
                obj[4] = student.getPhoneNumber();
                obj[5] = student.getAddress();
                dtm.addRow(obj);
            }
        }
        return dtm;
    }
}
