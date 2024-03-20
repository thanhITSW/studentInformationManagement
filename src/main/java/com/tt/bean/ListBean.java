package com.tt.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListBean {
    private String kind;
    private JButton btn;
}
