package com.bombo.cheatbot;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.win32.W32APIOptions;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenCapture {

    public static BufferedImage capture(WinDef.HWND hWnd, Rectangle r) {

        WinDef.HDC hdcWindow = User32.INSTANCE.GetDC(hWnd);
        WinDef.HDC hdcMemDC = GDI32.INSTANCE.CreateCompatibleDC(hdcWindow);

        WinDef.RECT bounds = new WinDef.RECT();
        bounds.top = r.y;
        bounds.left = r.x;
        bounds.right = r.x + r.width;
        bounds.bottom = r.y + r.height;
        //User32Extra.INSTANCE.GetClientRect(hWnd, bounds);

        int width = r.width;//bounds.right - bounds.left;
        int height = r.height;//bounds.bottom - bounds.top;

        WinDef.HBITMAP hBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(hdcWindow, width, height);

        HANDLE hOld = GDI32.INSTANCE.SelectObject(hdcMemDC, hBitmap);
        GDI32Extra.INSTANCE.BitBlt(hdcMemDC, 0, 0, width, height, hdcWindow, r.x, r.y, WinGDIExtra.SRCCOPY);

        GDI32.INSTANCE.SelectObject(hdcMemDC, hOld);
        GDI32.INSTANCE.DeleteDC(hdcMemDC);

        WinGDI.BITMAPINFO bmi = new WinGDI.BITMAPINFO();
        bmi.bmiHeader.biWidth = width;
        bmi.bmiHeader.biHeight = -height;
        bmi.bmiHeader.biPlanes = 1;
        bmi.bmiHeader.biBitCount = 32;
        bmi.bmiHeader.biCompression = WinGDI.BI_RGB;

        Memory buffer = new Memory(width * height * 4);
        GDI32.INSTANCE.GetDIBits(hdcWindow, hBitmap, 0, height, buffer, bmi, WinGDI.DIB_RGB_COLORS);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, buffer.getIntArray(0, width * height), 0, width);

        GDI32.INSTANCE.DeleteObject(hBitmap);
        User32.INSTANCE.ReleaseDC(hWnd, hdcWindow);

        return image;

    }

    public interface GDI32Extra extends GDI32 {

        GDI32Extra INSTANCE = Native.load("gdi32", GDI32Extra.class, W32APIOptions.DEFAULT_OPTIONS);

        boolean BitBlt(WinDef.HDC hObject, int nXDest, int nYDest, int nWidth, int nHeight, WinDef.HDC hObjectSource, int nXSrc, int nYSrc, WinDef.DWORD dwRop);
    }


    public interface User32Extra extends User32 {

        User32Extra INSTANCE = Native.load("user32", User32Extra.class, W32APIOptions.DEFAULT_OPTIONS);

        HDC GetWindowDC(HWND hWnd);

        boolean GetClientRect(HWND hWnd, RECT rect);

    }


    public interface WinGDIExtra extends WinGDI {

        WinDef.DWORD SRCCOPY = new WinDef.DWORD(0x00CC0020);
    }



}