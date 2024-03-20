package com.tt.UI;

import com.tt.controller.InformationStudentController;
import com.tt.entity.Student;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.text.SimpleDateFormat;

public class InformationStudent extends JDialog{
    private JPanel jpnRoot;
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnSave;
    private JTextField tvId;
    private JTextField tvName;
    private JFormattedTextField tvDOB;
    private JRadioButton rdMale;
    private JRadioButton rdFemale;
    private JTextField tvPhoneNumber;
    private JTextField tvAddress;
    private JPanel jpnView;
    private JButton btnImport;
    private JButton btnExport;
    private JButton btnRe;

    public InformationStudent(JFrame parent, Student student){
        super(parent);

        setContentPane(jpnRoot);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(800,500));
        InformationStudentController controller = new InformationStudentController(jpnView, btnSave, btnDelete, btnAdd, btnImport, btnExport, btnRe, tvId, tvName, tvPhoneNumber, tvAddress, tvDOB, rdMale, rdFemale);
        controller.setView(student);


    }
}
