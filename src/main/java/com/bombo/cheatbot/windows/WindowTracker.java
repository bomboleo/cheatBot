package com.bombo.cheatbot.windows;

import com.sun.jna.platform.win32.Win32Exception;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class WindowTracker {

    private TrackerThread trackerThread ;

    private PropertyChangeSupport support;

    public WindowTracker() {
        support = new PropertyChangeSupport(this);
        trackerThread = new TrackerThread();
        Thread tracker = new Thread(trackerThread);
        tracker.start();
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setApplicationWindowToTrack(ApplicationWindow aw) {
        trackerThread.setToTrack(aw);
        support.firePropertyChange("bounds", new ApplicationWindow(aw.getApplicationFilePath(), aw.getTitle(), aw.getPointer(), new Rectangle(aw.getX(), aw.getY(), aw.getWidth(), aw.getHeight())), aw);
    }

    private class TrackerThread implements Runnable {

        private ApplicationWindow toTrack;

        @Override
        public void run() {
            ProcessLister pl = new ProcessLister();
            while (true) {
                if(toTrack != null) {
                    int x = toTrack.getX();
                    int y = toTrack.getY();
                    int w = toTrack.getWidth();
                    int h = toTrack.getHeight();
                    try {
                        pl.refreshApplicationWindow(toTrack);
                    } catch (Win32Exception e) {

                    }

                    if(x != toTrack.getX() || y != toTrack.getY()
                    || w != toTrack.getWidth() || h != toTrack.getHeight()) {
                        support.firePropertyChange("bounds", new ApplicationWindow(toTrack.getApplicationFilePath(), toTrack.getTitle(), toTrack.getPointer(), new Rectangle(x, y, w, h)), toTrack);
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public ApplicationWindow getToTrack() {
            return toTrack;
        }

        public void setToTrack(ApplicationWindow toTrack) {
            this.toTrack = toTrack;
        }
    }
}
