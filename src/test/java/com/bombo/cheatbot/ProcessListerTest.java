package com.bombo.cheatbot;

import com.bombo.cheatbot.windows.ApplicationWindow;
import com.bombo.cheatbot.windows.ProcessLister;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
class ProcessListerTest {

    @Test
    void listProcess() {
        ProcessLister pl = new ProcessLister();
        List<ApplicationWindow> processList = pl.listProcess();
        processList.forEach(p -> log.info(p.toString()));
    }
}