package com.bombo.cheatbot.input;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.*;

public class InputActionKeyboard extends InputAction {

    private int keyCode;
    private long delayBetweenActivation;

    @JsonIgnore
    private long lastExecutedAction;

    public InputActionKeyboard() {
        super(ActionSource.KEYBOARD);
    }

    public InputActionKeyboard(int keyCode) {
        super(ActionSource.KEYBOARD);
        this.keyCode = keyCode;
    }

    public InputActionKeyboard(int keyCode, long delayBetweenActivation) {
        super(ActionSource.KEYBOARD);
        this.keyCode = keyCode;
        this.delayBetweenActivation = delayBetweenActivation;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public long getDelayBetweenActivation() {
        return delayBetweenActivation;
    }

    public void setDelayBetweenActivation(long delayBetweenActivation) {
        this.delayBetweenActivation = delayBetweenActivation;
    }

    @Override
    public void execute(Robot robot) {
        if(System.currentTimeMillis() - lastExecutedAction >= delayBetweenActivation) {
            robot.keyPress(keyCode);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            robot.keyRelease(keyCode);
            lastExecutedAction = System.currentTimeMillis();
        }
    }
}
