package com.bombo.cheatbot;

import com.bombo.cheatbot.windows.ApplicationWindow;
import com.bombo.cheatbot.windows.ProcessLister;
import org.junit.jupiter.api.Test;

import java.util.List;

class ProcessListerTest {

    @Test
    void listProcess() {
        ProcessLister pl = new ProcessLister();
        List<ApplicationWindow> processList = pl.listProcess();
        processList.forEach(System.out::println);
    }
}