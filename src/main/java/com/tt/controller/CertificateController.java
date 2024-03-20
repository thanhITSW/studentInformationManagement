package com.tt.controller;

import com.tt.Service.CertificateService;
import com.tt.entity.Certificate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CertificateController {
    private JButton btnCSave;
    private JTextField tvCId, tvCStudentId, tvCTitle, tvCIssuer, tvCIssueDate;
    Certificate certificate = null;
    CertificateService service = null;

    public CertificateController(JButton btnCSave, JTextField tvCId, JTextField tvCStudentId, JTextField tvCTitle, JTextField tvCIssuer, JTextField tvCIssueDate) {
        this.btnCSave = btnCSave;
        this.tvCId = tvCId;
        this.tvCStudentId = tvCStudentId;
        this.tvCTitle = tvCTitle;
        this.tvCIssuer = tvCIssuer;
        this.tvCIssueDate = tvCIssueDate;
        this.service = new CertificateService();
    }

    public void setView(Certificate certificate){
        this.certificate = certificate;
        tvCId.setText(String.valueOf(certificate.getId()));
        tvCStudentId.setText(String.valueOf(certificate.getStudentId()));
        tvCTitle.setText(certificate.getTitle());
        tvCIssuer.setText(certificate.getIssuer());
        tvCIssueDate.setText(String.valueOf(certificate.getIssueDate()));
        setEvent();
    }

    public void setEvent(){
        btnCSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (!checkNotNull()) {
                        return;
                    }
                    certificate.setStudentId(Integer.parseInt(tvCStudentId.getText()));
                    certificate.setTitle(tvCTitle.getText().trim());
                    certificate.setIssuer(tvCIssuer.getText());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = dateFormat.parse(tvCIssueDate.getText());
                    certificate.setIssueDate(date);

                    if (showDialog()) {
                        if(tvCId.getText().equals("0")){
                            int lastId = service.add(certificate);
                            if (lastId != 0) {
                                certificate.setId(lastId);
                                tvCId.setText(String.valueOf(certificate.getId()));
                            } else {
                            }
                        }
                        else {
                            service.update(certificate);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnCSave.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnCSave.setBackground(new Color(100, 221, 23));
            }
        });
    }

    private boolean checkNotNull() {
        return tvCStudentId.getText() != null && !tvCStudentId.getText().equalsIgnoreCase("");
    }

    private boolean showDialog() {
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Do you want update data?", "notification", JOptionPane.YES_NO_OPTION);
        return dialogResult == JOptionPane.YES_OPTION;
    }
}
