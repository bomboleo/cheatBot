package com.bombo.cheatbot.images;

import com.bombo.cheatbot.CheatApplication;
import com.bombo.cheatbot.ScreenCapture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageAnalyze {

    @Getter@Setter
    private int x, y, width, height;
    private double limitRatio;

    @JsonIgnore@Getter
    private BufferedImage reference;
    @JsonIgnore@Getter
    private BufferedImage currentImage;
    @JsonIgnore
    private long lastImageChanged;
    @JsonIgnore@Getter
    private boolean triggered;

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

    public void saveReference() {
        reference = ScreenCapture.capture(CheatApplication.getInstance().getSelectedApplication().getPointer(), new Rectangle(x, y, width, height));
    }
}
