package com.tt.UI;

import com.tt.controller.ManageStudentController;
import com.tt.dto.LoginInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Data
@AllArgsConstructor
public class Student extends JDialog{
    private JPanel jpnStudent;
    private JTextField tvSearch;
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnRe;
    private JButton btnImport;
    private JButton btnExport;
    private JPanel jpnView;
    private LoginInfo loginInfo;

    public Student(JFrame parent, LoginInfo loginInfo){
        super(parent);
        this.loginInfo = loginInfo;
        ManageStudentController controller = new ManageStudentController(jpnView,btnAdd, btnDelete, btnRe, btnImport, btnExport, tvSearch , loginInfo);
        controller.setDatetoTable();
        controller.setEvent();

        if(loginInfo.getRole().equals("employee")){
            btnAdd.hide();
            btnDelete.hide();
            btnExport.hide();
            btnImport.hide();
            btnRe.hide();
        }
    }
}
