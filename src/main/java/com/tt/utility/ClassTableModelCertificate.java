package com.tt.utility;

import com.tt.entity.Certificate;
import com.tt.entity.Student;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ClassTableModelCertificate {
    public DefaultTableModel setTableCertificate(List<Certificate> listItem, String[] listColumn){
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
                Certificate certificate = listItem.get(i);
                obj = new Object[columns];
                obj[0] = certificate.getId();
                obj[1] = certificate.getStudentId();
                obj[2] = certificate.getTitle();
                obj[3] = certificate.getIssuer();
                obj[4] = certificate.getIssueDate();
                dtm.addRow(obj);
            }
        }
        return dtm;
    }
}
