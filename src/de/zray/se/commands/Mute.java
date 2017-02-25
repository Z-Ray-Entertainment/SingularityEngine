/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.commands;

import de.zray.se.MainThread;
import de.zray.se.script.AbstractCommand;
import de.zray.se.script.Parameter;
import java.util.List;

/**
 *
 * @author Vortex Acherontic
 */
public class Mute extends AbstractCommand{

    public Mute() {
        super("togglemute", null);
    }

    @Override
    public String action(List<Parameter> params) {
        MainThread.getCurrentWorld().getAudioModule().setBGMMuted(!MainThread.getCurrentWorld().getAudioModule().isBGMMuted());
        return "Music muted";
    }
    
}
