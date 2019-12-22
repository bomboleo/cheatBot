package com.bombo.cheatbot.conf;

import com.bombo.cheatbot.actions.Action;
import com.bombo.cheatbot.images.ImageAnalyze;
import com.bombo.cheatbot.input.InputAction;
import com.bombo.cheatbot.input.InputActionKeyboard;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationManagerTest {

    @Test
    void saveConfiguration() {
        ConfigurationManager configurationManager = new ConfigurationManager();
        ArrayList<Action> actions = new ArrayList<>();
        ImageAnalyze iLeft = new ImageAnalyze(5d, new Rectangle(648, 860, 50, 50));
        ImageAnalyze iRight = new ImageAnalyze(5d, new Rectangle(983, 860, 50, 50));
        ImageAnalyze iUp = new ImageAnalyze(5d, new Rectangle(815, 690, 50, 50));
        InputAction aLeft = new InputActionKeyboard( KeyEvent.VK_LEFT, 200);
        InputAction aRight = new InputActionKeyboard(KeyEvent.VK_RIGHT, 200);
        InputAction aUp = new InputActionKeyboard(KeyEvent.VK_UP, 200);
        actions.add(new Action(aLeft, iLeft));
        actions.add(new Action(aRight, iRight));
        actions.add(new Action(aUp, iUp));
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration("Swords & Souls Neverseen.exe", actions);
        configurationManager.addConfiguration(applicationConfiguration);
        configurationManager.saveConfiguration(applicationConfiguration);
    }
}