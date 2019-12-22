package com.bombo.cheatbot.images;

import com.bombo.cheatbot.CheatApplication;
import com.bombo.cheatbot.ScreenCapture;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageAnalyze {

    private int x, y, width, height;
    private double limitRatio;

    @JsonIgnore
    private BufferedImage reference;
    @JsonIgnore
    private BufferedImage currentImage;
    @JsonIgnore
    private long lastImageChanged;
    @JsonIgnore
    private boolean triggered;

    public ImageAnalyze() {
    }

    public ImageAnalyze(double limitRatio, Rectangle rect) {
        this.limitRatio = limitRatio;
        this.x = rect.x;
        this.y = rect.y;
        this.width = rect.width;
        this.height = rect.height;
        this.triggered = false;
    }

    public boolean computeTriggered(BufferedImage image) {
        triggered = false;
        currentImage = image;
        if (reference != null) {
            double diff = ImageComparator.getDifferencePercent(reference, currentImage);
            if (diff > limitRatio) triggered = true;
        }
        return triggered;
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

    public double getLimitRatio() {
        return limitRatio;
    }

    public void setLimitRatio(double limitRatio) {
        this.limitRatio = limitRatio;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public BufferedImage getReference() {
        return reference;
    }

    public BufferedImage getCurrentImage() {
        return currentImage;
    }

    public void saveReference() {
        reference = ScreenCapture.capture(CheatApplication.getInstance().getSelectedApplication().getPointer(), new Rectangle(x, y, width, height));
    }
}
