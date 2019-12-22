package com.bombo.cheatbot.windows;

import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.Win32Exception;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProcessLister {

    public List<ApplicationWindow> listProcess() {
        List<ApplicationWindow> processes = new ArrayList<>();
        WindowUtils.getAllWindows(true).stream().filter(dw -> dw.getTitle() != null && !dw.getTitle().isEmpty())
                .forEach(dw -> processes.add(new ApplicationWindow(dw.getFilePath(), dw.getTitle(), dw.getHWND(), dw.getLocAndSize())));

        return processes;
    }

    public void refreshApplicationWindow(ApplicationWindow applicationWindow) throws Win32Exception {
        try {
            DesktopWindow desktopWindow = WindowUtils.getAllWindows(true).stream().filter(dw -> dw.getHWND().equals(applicationWindow.getPointer())).findFirst().orElse(null);
            if (desktopWindow != null) {
                Rectangle locAndSize = desktopWindow.getLocAndSize();
                applicationWindow.setX(locAndSize.x);
                applicationWindow.setY(locAndSize.y);
                applicationWindow.setWidth(locAndSize.width);
                applicationWindow.setHeight(locAndSize.height);
            }
        } catch (Win32Exception e) {}
    }
}
