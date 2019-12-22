package com.bombo.cheatbot.actions;

import com.bombo.cheatbot.images.ImageAnalyze;
import com.bombo.cheatbot.input.InputAction;

public class Action {

    private InputAction inputAction;
    private ImageAnalyze trigger;

    public Action() {
    }

    public Action(InputAction inputAction, ImageAnalyze trigger) {
        this.inputAction = inputAction;
        this.trigger = trigger;
    }

    public InputAction getInputAction() {
        return inputAction;
    }

    public void setInputAction(InputAction inputAction) {
        this.inputAction = inputAction;
    }

    public ImageAnalyze getTrigger() {
        return trigger;
    }

    public void setTrigger(ImageAnalyze trigger) {
        this.trigger = trigger;
    }
}
