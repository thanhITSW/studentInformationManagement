package com.tt.controller;

import com.tt.Service.AccountService;
import com.tt.UI.InformationAccount;
import com.tt.dto.LoginInfo;
import com.tt.entity.Account;
import com.tt.utility.ClassTableModelAccount;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ManageAccountController {
    private JPanel jpnView;
    private JButton btnAdd, btnDelete, btnRe;
    private JTextField tvSearch;
    private AccountService service = null;

    private String[] listColumn = {"ID", "Name", "Email", "Age", "Phone Number", "Role", "Status", "History"};

    private TableRowSorter<TableModel> rowSorter = null;

    private LoginInfo loginInfo;


    public ManageAccountController(JPanel jpnView, JButton btnAdd, JButton btnDelete, JButton btnRe,JTextField tvSearch, LoginInfo loginInfo) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.btnDelete = btnDelete;
        this.tvSearch = tvSearch;
        this.btnRe = btnRe;
        this.loginInfo = loginInfo;
        this.service = new AccountService();
    }

    public void setDatetoTable(){
        List<Account> accountList = service.readAll();

        DefaultTableModel model = new ClassTableModelAccount().setTableAccount(accountList, listColumn);

        JTable table = new JTable(model);

        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        tvSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = tvSearch.getText();
                if(text.trim().length() == 0){
                    rowSorter.setRowFilter(null);
                }
                else{
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = tvSearch.getText();
                if(text.trim().length() == 0){
                    rowSorter.setRowFilter(null);
                }
                else{
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        table.getColumnModel().getColumn(0).setMinWidth(30);
        table.getColumnModel().getColumn(0).setMaxWidth(30);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);

        table.getColumnModel().getColumn(1).setMinWidth(150);
        table.getColumnModel().getColumn(1).setMaxWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);

        table.getColumnModel().getColumn(2).setMinWidth(160);
        table.getColumnModel().getColumn(2).setMaxWidth(160);
        table.getColumnModel().getColumn(2).setPreferredWidth(160);

        if(loginInfo.getRole().equals("admin")){
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() == 2 && table.getSelectedRow() != -1){
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        int selectedRowIndex = table.getSelectedRow();
                        selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);

                        Account account = new Account();
                        account.setId((int) model.getValueAt(selectedRowIndex, 0));
                        account.setName(model.getValueAt(selectedRowIndex, 1).toString());
                        account.setEmail(model.getValueAt(selectedRowIndex, 2).toString());
                        account.setAge((int)model.getValueAt(selectedRowIndex, 3));
                        account.setPhoneNumber(model.getValueAt(selectedRowIndex, 4).toString());
                        account.setRole(model.getValueAt(selectedRowIndex, 5).toString());
                        account.setStatus(model.getValueAt(selectedRowIndex, 6).toString());
                        account.setHistory(model.getValueAt(selectedRowIndex, 7).toString());


                        InformationAccount frame = new InformationAccount(null, account);
                        frame.setLocationRelativeTo(null);
                        frame.setTitle("Information");
                        frame.setVisible(true);
                    }

                    if(e.getClickCount() == 1){
                        btnDelete.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                DefaultTableModel model = (DefaultTableModel) table.getModel();
                                int selectedRowIndex = table.getSelectedRow();
                                selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);
                                service.delete((int) model.getValueAt(selectedRowIndex, 0));
                                setDatetoTable();
                            }
                        });
                    }
                }
            });
        }

        table.getTableHeader().setFont(new Font("Arrial", Font.BOLD,14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(table);
        scrollPane.setPreferredSize(new Dimension(1300, 400));

        jpnView.removeAll();
        jpnView.setLayout(new BorderLayout());
        jpnView.add(scrollPane);
        jpnView.validate();
        jpnView.repaint();
    }


    public void setEvent(){
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                InformationAccount frame = new InformationAccount(null,new Account());
                frame.setTitle("Information");
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                setDatetoTable();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnAdd.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAdd.setBackground(new Color(100, 221, 23));
            }
        });


        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnDelete.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnDelete.setBackground(new Color(100, 221, 23));
            }
        });

        btnRe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setDatetoTable();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnRe.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnRe.setBackground(new Color(100, 221, 23));
            }
        });

    }

}
