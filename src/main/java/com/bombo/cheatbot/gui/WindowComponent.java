package com.bombo.cheatbot.gui;

import com.bombo.cheatbot.windows.ApplicationWindow;

import javax.swing.*;
import java.awt.*;

public class WindowComponent {

    private JLabel titleAndIcon;
    private JLabel filePath;
    private ApplicationWindow applicationWindow;

    public WindowComponent() {
    }

    public JLabel getTitleAndIcon() {
        return titleAndIcon;
    }

    public void setTitleAndIcon(JLabel titleAndIcon) {
        this.titleAndIcon = titleAndIcon;
    }

    public JLabel getFilePath() {
        return filePath;
    }

    public void setFilePath(JLabel filePath) {
        this.filePath = filePath;
    }

    public ApplicationWindow getApplicationWindow() {
        return applicationWindow;
    }

    public void setApplicationWindow(ApplicationWindow applicationWindow) {
        this.applicationWindow = applicationWindow;
    }

    public static ListCellRenderer<? super WindowComponent> getCellRenderer() {
        return (ListCellRenderer<WindowComponent>) (jList, windowComponent, index, isSelected, cellHasFocus) -> {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            gbc.insets = new Insets(5, 5, 0, 5);
            panel.add(windowComponent.getTitleAndIcon(), gbc);
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            gbc.insets = new Insets(5, 5, 10, 5);
            panel.add(windowComponent.getFilePath(), gbc);
            return panel;
        };
    }
}
