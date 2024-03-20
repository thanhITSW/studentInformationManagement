package com.tt.UI;

import com.tt.Service.AccountService;
import com.tt.dto.LoginInfo;
import com.tt.entity.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class formLogin extends JDialog{
    private JTextField tvUserName;
    private JPasswordField tvPassWord;
    private JButton btnLogin;
    private JButton btnCancel;
    private JPanel loginPanel;
    private LoginInfo loginInfo;

    AccountService service = new AccountService();

    public formLogin(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void loginUser() {
        String username = tvUserName.getText();
        String password = String.valueOf(tvPassWord.getPassword());


        if(username.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
        else {
            List<Account> accountList = service.readAll();
            boolean loginSuccess = false;

            for (Account ac : accountList){
                if(username.equals(ac.getUsername()) && password.equals(ac.getPassword())){
                    loginInfo = new LoginInfo();
                    loginInfo.setRole(ac.getRole());
                    ac.setHistory(service.getCurrentDayTime());
                    service.update(ac);
                    new MainPage(null, loginInfo);
                    loginSuccess = true;
                    break;
                }
            }

            if (!loginSuccess) {
                JOptionPane.showMessageDialog(this,
                        "User name or password are incorrect",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                dispose();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new formLogin(null));
    }
}
