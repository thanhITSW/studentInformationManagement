package com.tt.UI;

import com.tt.controller.ManageAccountController;
import com.tt.dto.LoginInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.swing.*;


@Data
@AllArgsConstructor
public class Account extends JDialog{
    private JPanel jpnAccount;
    private JTextField tvSearch;
    private JButton btnAdd;
    private JButton btnDelete;
    private JPanel jpnView;
    private JButton btnRe;
    private LoginInfo loginInfo;

    public Account(JFrame parent, LoginInfo loginInfo){
        super(parent);
        this.loginInfo = loginInfo;
        ManageAccountController controller = new ManageAccountController(jpnView, btnAdd,btnDelete, btnRe,tvSearch, loginInfo);
        controller.setDatetoTable();
        controller.setEvent();

        if(loginInfo.getRole().equals("manager") || loginInfo.getRole().equals("employee")){
            btnRe.hide();
            btnAdd.hide();
            btnDelete.hide();
        }
    }
}
