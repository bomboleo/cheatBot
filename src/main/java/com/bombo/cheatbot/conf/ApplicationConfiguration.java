package com.bombo.cheatbot.conf;

import com.bombo.cheatbot.actions.Action;

import java.util.List;

public class ApplicationConfiguration {

    private String applicationExecutable;
    private List<Action> actionAndImageAnalyzes;

    public ApplicationConfiguration() {
    }

    public ApplicationConfiguration(String applicationExecutable, List<Action> actionAndImageAnalyzes) {
        this.applicationExecutable = applicationExecutable;
        this.actionAndImageAnalyzes = actionAndImageAnalyzes;
    }

    public String getApplicationExecutable() {
        return applicationExecutable;
    }

    public void setApplicationExecutable(String applicationExecutable) {
        this.applicationExecutable = applicationExecutable;
    }

    public List<Action> getActionAndImageAnalyzes() {
        return actionAndImageAnalyzes;
    }

    public void setActionAndImageAnalyzes(List<Action> actionAndImageAnalyzes) {
        this.actionAndImageAnalyzes = actionAndImageAnalyzes;
    }
}
