package com.bombo.cheatbot.input;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.awt.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = InputActionKeyboard.class)
})
public abstract class InputAction {

    private ActionSource source;

    public InputAction() {
    }

    public InputAction(ActionSource source) {
        this.source = source;
    }

    public ActionSource getSource() {
        return source;
    }

    public void setSource(ActionSource source) {
        this.source = source;
    }

    public abstract void execute(Robot robot);
}
