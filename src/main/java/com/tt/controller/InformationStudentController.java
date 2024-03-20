package com.tt.controller;

import com.tt.Service.CertificateService;
import com.tt.Service.StudentService;
import com.tt.UI.CertificateEdit;
import com.tt.entity.Certificate;
import com.tt.entity.Student;
import com.tt.utility.ClassTableModelCertificate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

public class InformationStudentController {
    private JPanel jpnView;
    private JButton btnSave, btnDelete, btnAdd, btnImport, btnExport, btnRe;
    private JTextField tvId, tvName, tvPhoneNumber, tvAddress;
    private JFormattedTextField tvDOB;
    private JRadioButton rdMale, rdFemale;

    Student student = null;
    StudentService service = null;
    CertificateService certificateService = null;

    private String[] listColumn = {"ID", "Student ID", "Title", "Issuer", "Issue Date"};
    private TableRowSorter<TableModel> rowSorter = null;

    public InformationStudentController(JPanel jpnView, JButton btnSave, JButton btnDelete, JButton btnAdd, JButton btnImport, JButton btnExport, JButton btnRe, JTextField tvId, JTextField tvName, JTextField tvPhoneNumber, JTextField tvAddress, JFormattedTextField tvDOB, JRadioButton rdMale, JRadioButton rdFemale) {
        this.jpnView = jpnView;
        this.btnSave = btnSave;
        this.btnDelete = btnDelete;
        this.btnAdd = btnAdd;
        this.btnImport = btnImport;
        this.btnExport = btnExport;
        this.btnRe = btnRe;
        this.tvId = tvId;
        this.tvName = tvName;
        this.tvPhoneNumber = tvPhoneNumber;
        this.tvAddress = tvAddress;
        this.tvDOB = tvDOB;
        this.rdMale = rdMale;
        this.rdFemale = rdFemale;
        this.service = new StudentService();
        this.certificateService = new CertificateService();
    }



    public void setDatetoTable() {
        List<Certificate> certificateList = certificateService.readByStudentID(student.getId());

        DefaultTableModel model = new ClassTableModelCertificate().setTableCertificate(certificateList, listColumn);

        JTable table = new JTable(model);

        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);


        table.getColumnModel().getColumn(0).setMinWidth(30);
        table.getColumnModel().getColumn(0).setMaxWidth(30);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);

        table.getColumnModel().getColumn(1).setMinWidth(150);
        table.getColumnModel().getColumn(1).setMaxWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);

        table.getColumnModel().getColumn(2).setMinWidth(160);
        table.getColumnModel().getColumn(2).setMaxWidth(160);
        table.getColumnModel().getColumn(2).setPreferredWidth(160);


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2 && table.getSelectedRow() != -1){
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int selectedRowIndex = table.getSelectedRow();
                    selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);

                    Certificate certificate = new Certificate();
                    certificate.setId((int) model.getValueAt(selectedRowIndex, 0));
                    certificate.setStudentId((int) model.getValueAt(selectedRowIndex, 1));
                    certificate.setTitle(model.getValueAt(selectedRowIndex, 2).toString());
                    certificate.setIssuer(model.getValueAt(selectedRowIndex, 3).toString());
                    certificate.setIssueDate((Date) model.getValueAt(selectedRowIndex, 4));

                    CertificateEdit frame = new CertificateEdit(null, certificate);
                    frame.setLocationRelativeTo(null);
                    frame.setTitle("Information Certificate");
                    frame.setVisible(true);
                }

                if(e.getClickCount() == 1){
                    btnDelete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            int selectedRowIndex = table.getSelectedRow();
                            selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);
                            certificateService.delete((int) model.getValueAt(selectedRowIndex, 0));
                            setDatetoTable();
                        }
                    });
                }
            }
        });

        table.getTableHeader().setFont(new Font("Arrial", Font.BOLD,14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(table);
        scrollPane.setPreferredSize(new Dimension(400, 400));

        jpnView.removeAll();
        jpnView.setLayout(new BorderLayout());
        jpnView.add(scrollPane);
        jpnView.validate();
        jpnView.repaint();
    }

    public void setView(Student student){
        this.student = student;
        tvId.setText(String.valueOf(student.getId()));
        tvName.setText(student.getName());
        tvDOB.setValue(student.getDOB());
        if(student.getGender().equals("Male")){
            rdMale.setSelected(true);
            rdFemale.setSelected(false);
        }
        else {
            rdMale.setSelected(false);
            rdFemale.setSelected(true);
        }
        tvPhoneNumber.setText(student.getPhoneNumber());
        tvAddress.setText(student.getAddress());
        setEvent();
        setDatetoTable();
    }

    private void setEvent() {
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (!checkNotNull()) {
                        return;
                    }
                    student.setName(tvName.getText().trim());
                    student.setDOB(tvDOB.getText());
                    if(rdMale.isSelected() == true || rdFemale.isSelected() == false){
                        student.setGender("Male");
                    }
                    else {
                        student.setGender("Female");
                    }
                    student.setPhoneNumber(tvPhoneNumber.getText());
                    student.setAddress(tvAddress.getText());


                    if (showDialog()) {
                        if(tvId.getText().equals("0")){
                            int lastId = service.add(student);
                            if (lastId != 0) {
                                student.setId(lastId);
                                tvId.setText(String.valueOf(student.getId()));
                            } else {
                            }
                        }
                        else {
                            service.update(student);
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

        btnRe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setDatetoTable();
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

        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Certificate certificate = new Certificate();
                certificate.setStudentId(student.getId());
                CertificateEdit frame = new CertificateEdit(null, certificate);
                frame.setLocationRelativeTo(null);
                frame.setTitle("Information Certificate");
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

        btnExport.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                certificateService.ExportCsv(student.getId());
                JOptionPane.showMessageDialog(null, "Export file successfully!");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnExport.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnExport.setBackground(new Color(100, 221, 23));
            }
        });

        btnImport.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                certificateService.importExel(student.getId());
                JOptionPane.showMessageDialog(null, "Import file successfully!");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnImport.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnImport.setBackground(new Color(100, 221, 23));
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
