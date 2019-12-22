package com.bombo.cheatbot.gui;

import com.bombo.cheatbot.CheatApplication;
import com.bombo.cheatbot.images.ImageAnalyze;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class OverlayFrame extends JFrame {

    private Insets insets;

    private DrawPanel drawPanel;

    public OverlayFrame(Insets insets) {
        setUndecorated(true);
        setLocationRelativeTo(null);
        setBackground(new Color(0, 0, 0, 0));
        setAlwaysOnTop(true);
        this.insets = insets;
    }

    public void setPosition(int x, int y, int width, int height) {
        setSize(width - insets.left - insets.right, height - insets.top - insets.bottom);
        setLocation(x + insets.left, y + insets.top);

        drawPanel = new DrawPanel();
        setContentPane(drawPanel);
    }

    private class DrawPanel extends JPanel {

        private Graphics2D bufferGraphics;
        private BufferedImage offscreen;

        private long lastRepaint = 1;

        public DrawPanel() {
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
            if(bufferGraphics == null) {
                offscreen = new BufferedImage(OverlayFrame.this.getWidth(), OverlayFrame.this.getHeight(),java.awt.image.BufferedImage.TYPE_INT_ARGB);
                bufferGraphics = offscreen.createGraphics();
            }

            ((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0f));
            g.fillRect(0, 0, OverlayFrame.this.getWidth(), OverlayFrame.this.getHeight());
            ((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1f));

            bufferGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0f));
            bufferGraphics.fillRect(0, 0, OverlayFrame.this.getWidth(), OverlayFrame.this.getHeight());

            bufferGraphics.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC, 1));
            CheatApplication.getInstance().getActionManager().getActionList().forEach(v -> {
                bufferGraphics.setColor(v.getTrigger().isTriggered() ? Color.red : Color.green);
                ImageAnalyze t = v.getTrigger();
                bufferGraphics.drawRect(t.getX(), t.getY(), t.getWidth(), t.getHeight());
            });
            g.drawImage(offscreen,0,0,this);

            lastRepaint = System.currentTimeMillis();
        }
    }
}
