package com.bombo.cheatbot.gui;

import com.bombo.cheatbot.CheatApplication;
import com.bombo.cheatbot.windows.ApplicationWindow;
import com.bombo.cheatbot.windows.ProcessLister;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WindowChooser extends JPanel {

    public WindowChooser() {
        super(new BorderLayout());

        buildPanel();
    }

    private void buildPanel() {
        List<ApplicationWindow> applicationWindowList = new ProcessLister().listProcess();
        List<WindowComponent> data = new ArrayList<>();

        applicationWindowList.forEach(aw -> {
            WindowComponent wc = new WindowComponent();
            JLabel titleAndIcon = new JLabel(aw.getTitle(), aw.getIcon(), SwingConstants.LEFT);
            wc.setTitleAndIcon(titleAndIcon);
            wc.setFilePath(new JLabel(aw.getApplicationFilePath()));
            wc.setApplicationWindow(aw);
            data.add(wc);
        });

        JList<WindowComponent> windowList = new JList<>(data.toArray(new WindowComponent[applicationWindowList.size()]));
        windowList.setCellRenderer(WindowComponent.getCellRenderer());
        windowList.getSelectionModel().addListSelectionListener(getListSelectionListener(data));
        this.add(windowList, BorderLayout.CENTER);
    }

    private ListSelectionListener getListSelectionListener(List<WindowComponent> data) {
        return e -> {
            if(e.getValueIsAdjusting()) {
                int selectedIndex = ((ListSelectionModel) e.getSource()).getSelectedIndices()[0];
                final WindowComponent wc = data.get(selectedIndex);
                CheatApplication.getInstance().setSelectedApplication(wc.getApplicationWindow());
            }
        };
    }
}
