package com.bombo.cheatbot.gui;

import com.bombo.cheatbot.CheatApplication;
import com.bombo.cheatbot.actions.Action;
import com.bombo.cheatbot.images.ImageAnalyze;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;

public class ImageAnalyzePanel extends JPanel {

    private int w, h;

    public ImageAnalyzePanel() {
        this.w = 500;
        this.h = 70;
        this.setPreferredSize(new Dimension(w, h));
        new Thread(() -> {
            while(true) {
                repaint();
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());

        Collection<Action> actionList = CheatApplication.getInstance().getActionManager().getActionList();
        int i = 0;
        for (Action action : actionList) {
            ImageAnalyze t = action.getTrigger();
            BufferedImage last = t.getReference();
            BufferedImage current = t.getCurrentImage();

            if(last != null) {
                g2d.drawImage(last, i * 120 + 10, 10, 50, 50, this);
            }
            if(current != null) {
                g2d.drawImage(current, i * 120 + 70, 10, 50, 50, this);
            }
            i++;
        }
    }
}
