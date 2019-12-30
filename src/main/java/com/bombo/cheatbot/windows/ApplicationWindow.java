package com.bombo.cheatbot.windows;

import com.sun.jna.platform.win32.WinDef;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

@Data
public class ApplicationWindow {

    private String applicationFilePath;
    private String title;
    private int x, y, width, height;
    private Icon icon;

    private final WinDef.HWND pointer;

    public ApplicationWindow(String applicationFilePath, String title, WinDef.HWND pointer, Rectangle rect) {
        this.applicationFilePath = applicationFilePath;
        this.title = title;
        this.x = rect.x;
        this.y = rect.y;
        this.width = rect.width;
        this.height = rect.height;
        this.icon = FileSystemView.getFileSystemView().getSystemIcon(new File(applicationFilePath));
        this.pointer = pointer;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Window " + title + ": \n");
        sb.append("\tfilePath: " + applicationFilePath + "\n");
        sb.append("\tposition: " + x + ", " + y + "\n");
        sb.append("\tsize: " + width + ", " + height + "\n");
        return sb.toString();
    }
}
