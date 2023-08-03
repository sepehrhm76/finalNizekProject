package org.example;


import org.example.view.UiFrame;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(UiFrame::getInstance);
    }
}