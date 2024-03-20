package com.tt.UI;

import com.tt.controller.InformationAccountController;
import com.tt.entity.Account;

import javax.swing.*;
import java.awt.*;

public class InformationAccount extends JDialog{
    private JButton btnSave;
    private JTextField tvId;
    private JTextField tvName;
    private JTextField tvEmail;
    private JTextField tvAge;
    private JTextField tvPhone;
    private JTextField tvStatus;
    private JTextField tvRole;
    private JTextField tvHistory;
    private JPanel jpnRoot;

    public InformationAccount(JFrame parent, Account account){
        super(parent);
        setContentPane(jpnRoot);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(400,400));
        InformationAccountController controller = new InformationAccountController(btnSave, tvId, tvName,tvEmail, tvAge, tvPhone, tvStatus, tvRole, tvHistory);

        controller.setView(account);

    }
}
