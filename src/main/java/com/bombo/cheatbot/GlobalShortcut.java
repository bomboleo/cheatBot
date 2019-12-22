package com.bombo.cheatbot;

import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;

import javax.swing.*;

public class GlobalShortcut implements HotKeyListener {

    public GlobalShortcut() {
        Provider provider = Provider.getCurrentProvider(true);
        provider.register(KeyStroke.getKeyStroke("control F4"), this);
    }

    @Override
    public void onHotKey(HotKey hotKey) {
        CheatApplication.getInstance().getActionManager().getActionList().forEach(a -> {
            a.getTrigger().saveReference();
        });
    }
}
