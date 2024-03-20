package com.tt.controller;

import com.tt.Service.AccountService;
import com.tt.entity.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InformationAccountController {
    private JButton btnSave;
    private JTextField tvId, tvName, tvEmail, tvAge, tvPhone, tvStatus, tvRole, tvHistory;
    Account account = null;
    AccountService service = null;

    public InformationAccountController(JButton btnSave, JTextField tvId, JTextField tvName, JTextField tvEmail, JTextField tvAge, JTextField tvPhone, JTextField tvStatus, JTextField tvRole, JTextField tvHistory) {
        this.btnSave = btnSave;
        this.tvId = tvId;
        this.tvName = tvName;
        this.tvEmail = tvEmail;
        this.tvAge = tvAge;
        this.tvPhone = tvPhone;
        this.tvStatus = tvStatus;
        this.tvRole = tvRole;
        this.tvHistory = tvHistory;
        this.service = new AccountService();
    }

    public void setView(Account account){
        this.account = account;
        tvId.setText(String.valueOf(account.getId()));
        tvName.setText(account.getName());
        tvEmail.setText(account.getEmail());
        tvAge.setText(String.valueOf(account.getAge()));
        tvPhone.setText(account.getPhoneNumber());
        tvStatus.setText(account.getStatus());
        tvRole.setText(account.getRole());
        tvHistory.setText(account.getHistory());
        setEvent();
    }

    public void setEvent(){
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (!checkNotNull()) {
                        return;
                    }
                    account.setName(tvName.getText().trim());
                    account.setEmail(tvEmail.getText());
                    account.setAge(Integer.valueOf(tvAge.getText()));
                    account.setPhoneNumber(tvPhone.getText());
                    account.setRole(tvRole.getText());
                    account.setStatus(tvStatus.getText());
                    account.setHistory(tvHistory.getText());

                    String username = tvEmail.getText().split("@")[0];
                    account.setUsername(username);
                    account.setPassword(username);

                    if (showDialog()) {
                        if(tvId.getText().equals("0")){
                            int lastId = service.add(account);
                            if (lastId != 0) {
                                account.setId(lastId);
                                tvId.setText(String.valueOf(account.getId()));
                            } else {
                            }
                        }
                        else {
                            service.update(account);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnSave.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSave.setBackground(new Color(100, 221, 23));
            }
        });
    }

    private boolean checkNotNull() {
        return tvName.getText() != null && !tvName.getText().equalsIgnoreCase("");
    }

    private boolean showDialog() {
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Do you want update data?", "notification", JOptionPane.YES_NO_OPTION);
        return dialogResult == JOptionPane.YES_OPTION;
    }
}
