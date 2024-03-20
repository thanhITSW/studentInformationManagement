package com.tt.UI;

import com.tt.controller.CertificateController;
import com.tt.entity.Certificate;

import javax.swing.*;
import java.awt.*;

public class CertificateEdit extends JDialog{
    private JPanel jpnRoot;
    private JButton btnCSave;
    private JTextField tvCId;
    private JTextField tvCStudentId;
    private JTextField tvCTitle;
    private JTextField tvCIssuer;
    private JTextField tvCIssueDate;

    public CertificateEdit(JFrame parent, Certificate certificate){
        super(parent);
        setContentPane(jpnRoot);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(400,600));

        CertificateController controller = new CertificateController(btnCSave, tvCId, tvCStudentId, tvCTitle, tvCIssuer, tvCIssueDate);
        controller.setView(certificate);
    }
}
