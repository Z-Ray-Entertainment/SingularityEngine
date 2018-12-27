/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.commands;

import de.zray.coretext.command.AbstractCommand;
import de.zray.coretext.command.CommandDefinition;
import de.zray.coretext.command.Parameter;
import java.util.List;

/**
 *
 * @author Vortex Acherontic
 */
public class Mute extends AbstractCommand{

    public Mute() {
        super(new CommandDefinition("togglemute", "Mutes and unmutes all sounds"){});
    }

    @Override
    public String action(List<Parameter> params) {
        //MainThread.getCurrentWorld().getAudioModule().setBGMMuted(!MainThread.getCurrentWorld().getAudioModule().isBGMMuted());
        return "Music muted";
    }
    
}
