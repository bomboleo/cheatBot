package com.bombo.cheatbot.windows;

import com.sun.jna.platform.win32.WinDef;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class ApplicationWindow {

    private String applicationFilePath;
    private String title;
    private int x;
    private int y;
    private int width;
    private int height;
    private Icon icon;

    private WinDef.HWND pointer;

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

    public String getApplicationFilePath() {
        return applicationFilePath;
    }

    public void setApplicationFilePath(String applicationFilePath) {
        this.applicationFilePath = applicationFilePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public WinDef.HWND getPointer() {
        return pointer;
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
