/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.commands;

import de.zray.coretex.AbstractCommand;
import de.zray.coretex.Parameter;
import de.zray.se.MainThread;
import java.util.List;

/**
 *
 * @author Vortex Acherontic
 */
public class Mute extends AbstractCommand{
    private MainThread mainThread;

    public Mute(MainThread mainThread) {
        super("togglemute", null);
        this.mainThread = this.mainThread;
    }

    @Override
    public String action(List<Parameter> params) {
        mainThread.getCurrentWorld().getAudioModule().setBGMMuted(!mainThread.getCurrentWorld().getAudioModule().isBGMMuted());
        return "Music muted";
    }
}
