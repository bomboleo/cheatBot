package com.bombo.cheatbot.actions;

import com.bombo.cheatbot.images.ImageAnalyze;
import com.bombo.cheatbot.input.InputAction;
import lombok.Data;

@Data
public class Action {

    private InputAction inputAction;
    private ImageAnalyze trigger;

    public Action(InputAction inputAction, ImageAnalyze trigger) {
        this.inputAction = inputAction;
        this.trigger = trigger;
    }
}
