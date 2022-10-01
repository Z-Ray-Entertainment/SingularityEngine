/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

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
    
    public static void loop(SEWorld world) throws IOException{
        if(!Display.isCreated()){
            createDisplay();
        }
        
        while(!closeRequested){
            world.act();
            Display.update();
        }
    }
    
    public static void shutdown(){
        System.exit(0);
    }
    
    private static void createDisplay(){
        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 0);
        
        try {
                Display.setDisplayMode(new DisplayMode(Settings.get().winSettings.resX, Settings.get().winSettings.resY));
                Display.setTitle(Settings.get().title+" "+Settings.get().version);
                Display.setResizable(Settings.get().winSettings.displayRezisable);
                Display.create(pixelFormat, contextAtrributes);
        } catch (LWJGLException e) {
                e.printStackTrace();
                System.exit(-1);
        }
        glClearColor(0.1f, 0.1f, 0.1f, 0f);
    }
}
