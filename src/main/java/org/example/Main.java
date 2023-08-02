package org.example;

import org.example.Conroller.ProjectController;
import org.example.Log.Logger;
import org.example.view.UiFrame;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        ProjectController projectController = new ProjectController();

        SwingUtilities.invokeLater(UiFrame::getInstance);

    }
}