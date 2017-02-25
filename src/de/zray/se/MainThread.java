/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import de.zray.se.commands.Mute;
import de.zray.se.logger.SELogger;
import de.zray.se.script.Console;
import de.zray.se.script.exceptions.DublicateCommandException;
import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.glClearColor;
import org.lwjgl.opengl.PixelFormat;

/**
 *
 * @author vortex
 */
public class MainThread {
    private static boolean closeRequested = false;
    private static SEWorld currentWorld;
    private static Console console;
    
    public static void loop(SEWorld world) throws IOException{
        currentWorld = world;
        if(console == null){
            createConsole();
        }
        if(!Display.isCreated()){
            createDisplay();
        }
        
        while(!closeRequested){
            world.act();
            Display.update();
        }
    }
    
    public static SEWorld getCurrentWorld(){
        return currentWorld;
    }
    
    public static Console getConsole(){
        if(console == null){
            createConsole();
        }
        return console;
    }
    
    public static void shutdown(){
        System.exit(0);
    }
    
    private static void createConsole(){
        try {
            console = new Console();
            console.addCommand(new Mute());
        } catch (DublicateCommandException ex) {
            SELogger.get().dispatchMsg("MainThread", SELogger.SELogType.ERROR, new String[]{ex.getMessage()}, false);
        }
    }
    
    private static void createDisplay(){
        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 0);
        
        try {
                Display.setDisplayMode(new DisplayMode(Settings.get().window.resX, Settings.get().window.resY));
                Display.setTitle(Settings.get().title+" "+Settings.get().version);
                Display.setResizable(Settings.get().window.displayRezisable);
                Display.create(pixelFormat, contextAtrributes);
        } catch (LWJGLException e) {
                e.printStackTrace();
                System.exit(-1);
        }
        glClearColor(0.1f, 0.1f, 0.1f, 0f);
    }
}
