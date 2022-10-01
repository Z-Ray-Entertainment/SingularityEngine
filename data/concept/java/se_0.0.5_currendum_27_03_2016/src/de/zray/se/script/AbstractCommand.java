/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.script;

/**
 *
 * @author Vortex Acherontic
 */
public abstract class AbstractCommand {
    private String cmd = "DEFAULT", validArguments[];
    
    public AbstractCommand(String cmd, String validArguments[]){
        this.cmd = cmd;
        this.validArguments = validArguments;
    }
    
    public String getRootCMD(){
        return cmd;
    }
    
    public String autoComplete(String currendInput){
        return "null";
    }
    
    public abstract void execute(String input[]);
}
