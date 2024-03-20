package com.tt.UI;

import com.tt.bean.ListBean;
import com.tt.controller.ChangeScreenController;
import com.tt.dto.LoginInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainPage extends JDialog {
    private JPanel jpnRoot;
    private JPanel jpnMenu;
    private JPanel jpnView;
    private JButton btnStudent;
    private JButton btnLogOut;
    private JButton btnHome;
    private JButton btnAccount;

    private LoginInfo loginInfo;


    public MainPage(JFrame parent, LoginInfo loginInfo) {
        super(parent);
        this.loginInfo = loginInfo;
        setTitle("Management");
        setContentPane(jpnRoot);
        setMinimumSize(new Dimension(1200, 600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ChangeScreenController controller = new ChangeScreenController(jpnView, loginInfo);
        controller.setView(btnHome);

        List<ListBean> listItem = new ArrayList<>();
        listItem.add((new ListBean("Home", btnHome)));
        listItem.add((new ListBean("Student", btnStudent)));
        listItem.add((new ListBean("Account", btnAccount)));
        controller.setEvent(listItem);


        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                JDialog loginDialog = new formLogin(null);
                loginDialog.setVisible(true);
            }
        });
        setVisible(true);
    }
}
