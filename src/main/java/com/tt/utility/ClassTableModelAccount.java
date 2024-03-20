package com.tt.utility;

import com.tt.entity.Account;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ClassTableModelAccount {
    public DefaultTableModel setTableAccount(List<Account> listItem, String[] listColumn){
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
                Account account = listItem.get(i);
                obj = new Object[columns];
                obj[0] = account.getId();
                obj[1] = account.getName();
                obj[2] = account.getEmail();
                obj[3] = account.getAge();
                obj[4] = account.getPhoneNumber();
                obj[5] = account.getRole();
                obj[6] = account.getStatus();
                obj[7] = account.getHistory();

                dtm.addRow(obj);
            }
        }
        return dtm;
    }
}