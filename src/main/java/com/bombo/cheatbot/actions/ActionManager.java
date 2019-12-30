package com.bombo.cheatbot.actions;

import com.bombo.cheatbot.ScreenCapture;
import com.bombo.cheatbot.images.ImageAnalyze;
import com.bombo.cheatbot.windows.ApplicationWindow;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class ActionManager {

    private Robot robot;
    private Collection<Action> actionList;
    private AnalyzerThread analyzerThread;

    private ApplicationWindow toAnalyze;

    private Rectangle regionToCapture;

    private BufferedImage screenshot;

    public ActionManager(Robot robot) {
        actionList = new ConcurrentLinkedQueue<>();
        this.robot = robot;

        this.analyzerThread = new AnalyzerThread();
        new Thread(this.analyzerThread).start();
    }

    public Collection<Action> getActionList() {
        return actionList;
    }

    public void registerAction(Action action) {
        actionList.add(action);
        ImageAnalyze t = action.getTrigger();
        Rectangle r = new Rectangle(t.getX(), t.getY(), t.getWidth(), t.getHeight());
        if(regionToCapture == null) {
            regionToCapture = r;
        } else {
            Rectangle.union(regionToCapture, r, regionToCapture);
        }
    }

    public void setToAnalyze(ApplicationWindow toAnalyze) {
        this.toAnalyze = toAnalyze;
    }

    public void clearActions() {
        synchronized (actionList) {
            actionList.clear();
        }
    }

    public BufferedImage getScreenshot() {
        return screenshot;
    }

    private class AnalyzerThread implements Runnable {

        @Override
        public void run() {
            int i = 0;
            long last = 0;
            while (true) {
                if(toAnalyze != null && regionToCapture != null) {
                    actionList.forEach(v -> {
                        ImageAnalyze t = v.getTrigger();
                        if (t.computeTriggered(ScreenCapture.capture(toAnalyze.getPointer(), new Rectangle(t.getX(), t.getY(), t.getWidth(), t.getHeight())))) {
                            v.getInputAction().execute(robot);
                        }
                    });

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i % 100 == 0) {
                        log.info(System.currentTimeMillis() - last + "ms");
                        last = System.currentTimeMillis();
                    }
                    i++;
                }
            }
        }
    }
}
