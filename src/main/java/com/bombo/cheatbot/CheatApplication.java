package com.bombo.cheatbot;

import com.bombo.cheatbot.actions.Action;
import com.bombo.cheatbot.actions.ActionManager;
import com.bombo.cheatbot.conf.ApplicationConfiguration;
import com.bombo.cheatbot.conf.ConfigurationManager;
import com.bombo.cheatbot.gui.ImageAnalyzePanel;
import com.bombo.cheatbot.gui.OverlayFrame;
import com.bombo.cheatbot.gui.WindowChooser;
import com.bombo.cheatbot.images.ImageAnalyze;
import com.bombo.cheatbot.input.InputAction;
import com.bombo.cheatbot.windows.ApplicationWindow;
import com.bombo.cheatbot.windows.WindowTracker;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.regex.Pattern;

@Slf4j
public class CheatApplication implements PropertyChangeListener {

    private static CheatApplication instance;

    private WindowChooser applicationChooser;
    private WindowTracker windowTracker;

    @Getter
    private ApplicationWindow selectedApplication;

    private OverlayFrame overlayFrame;

    private Robot robot;
    @Getter
    private ActionManager actionManager;

    private ConfigurationManager configurationManager;
    private ImageAnalyzePanel imageAnalyzePanel;

    private CheatApplication() {
        init();
    }

    private void init() {
        initRobot();
        actionManager = new ActionManager(robot);
        configurationManager = new ConfigurationManager();
        overlayFrame = new OverlayFrame(new Insets(30, 8, 8, 8));
        windowTracker = new WindowTracker();
        windowTracker.addPropertyChangeListener(this);
        applicationChooser = new WindowChooser();

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(applicationChooser, BorderLayout.CENTER);

        JButton reloadConf = new JButton("reload");
        reloadConf.addActionListener(e -> {
            loadConfiguration(selectedApplication.getApplicationFilePath());
        });

        contentPane.add(reloadConf, BorderLayout.SOUTH);

        imageAnalyzePanel = new ImageAnalyzePanel();
        contentPane.add(imageAnalyzePanel, BorderLayout.NORTH);

        new GlobalShortcut();

        JFrame frame = new JFrame("Choose an application");
        frame.setContentPane(contentPane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void initRobot() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static CheatApplication getInstance() {
        if(instance == null) instance = new CheatApplication();
        return instance;
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        this.selectedApplication = (ApplicationWindow) propertyChangeEvent.getNewValue();
        SwingUtilities.invokeLater(() -> {
            overlayFrame.setPosition(selectedApplication.getX(), selectedApplication.getY(),
                    selectedApplication.getWidth(),selectedApplication.getHeight());

            overlayFrame.setVisible(true);
        });
    }

    public void setSelectedApplication(ApplicationWindow selectedApplication) {
        this.selectedApplication = selectedApplication;
        loadConfiguration(selectedApplication.getApplicationFilePath());
        this.windowTracker.setApplicationWindowToTrack(selectedApplication);
        this.actionManager.setToAnalyze(selectedApplication);
    }

    private boolean loadConfiguration(String applicationFilePath) {
        actionManager.clearActions();
        String exeName = cleanFileName(new File(applicationFilePath).getName());
        ApplicationConfiguration ap = configurationManager.loadConfiguration(exeName);
        if(ap == null) {
            //TODO No configuration available
            log.warn("No configuration found for application " + exeName + ", unable to load a bot.");
            return false;
        } else {
            ap.getActionAndImageAnalyzes().forEach(a -> registerAction(a.getInputAction(), a.getTrigger()));
            return true;
        }
    }

    public void registerAction(InputAction action, ImageAnalyze trigger) {
        this.actionManager.registerAction(new Action(action, trigger));
    }

    public static String cleanFileName(String name) {
        int i = name.lastIndexOf(".");
        if(i != -1) name = name.substring(0, i);

        return Pattern.compile("[^A-Za-z0-9]").matcher(name).replaceAll("");
    }
}
