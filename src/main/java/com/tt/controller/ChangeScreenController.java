package com.tt.controller;
import com.tt.UI.Account;
import com.tt.UI.Home;
import com.tt.UI.Student;
import com.tt.bean.ListBean;
import com.tt.dto.LoginInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChangeScreenController {
    private JPanel root;
    private String kindSelected = "";
    private List<ListBean> listItem = null;
    private LoginInfo loginInfo;

    public ChangeScreenController(JPanel root, LoginInfo loginInfo) {
        this.root = root;
        this.loginInfo = loginInfo;
    }

    public void setView(JButton btnItem){
        btnItem.setBackground(new Color(96, 100, 191));

        JPanel node = new Home().getJpnHome();
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(node);
        root.validate();
        root.repaint();
    }

    public void setEvent(List<ListBean> listItem){
        this.listItem = listItem;
        for (ListBean item : listItem){
            item.getBtn().addActionListener(new btnEvent(item.getKind(), item.getBtn()));
        }
    }

    class btnEvent implements ActionListener{

        private JPanel node;
        private String kind;
        private JButton btnItem;

        public btnEvent(String kind, JButton btnItem) {
            this.kind = kind;
            this.btnItem = btnItem;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (kind) {
                case "Home":
                    node = new Home().getJpnHome();
                    break;
                case "Student":
                    node = new Student(null, loginInfo).getJpnStudent();
                    break;

                case "Account":
                    node = new Account(null, loginInfo).getJpnAccount();
                    break;
                default:
                    break;
            }
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
        }
    }
}
