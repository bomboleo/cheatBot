package com.bombo.cheatbot.conf;

import com.bombo.cheatbot.actions.Action;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class ApplicationConfiguration {

    private String applicationExecutable;
    private List<Action> actionAndImageAnalyzes;

    public ApplicationConfiguration(String applicationExecutable, List<Action> actionAndImageAnalyzes) {
        this.applicationExecutable = applicationExecutable;
        this.actionAndImageAnalyzes = actionAndImageAnalyzes;
    }

}
