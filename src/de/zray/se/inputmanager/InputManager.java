/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.inputmanager;

import de.zray.se.world.World;
import java.util.HashMap;
import java.util.Set;
import static org.lwjgl.glfw.GLFW.*;

/**
 *
 * @author vortex
 */
public abstract class InputManager {
    private World parrent;
    private HashMap<Integer, Boolean> pressedKeys = new HashMap<>();
    private HashMap<Integer, Boolean> pressedModifiers = new HashMap<>();
    
    
    public InputManager(World world){
        this.parrent = world;
    }
    
    public World getWorld(){
        return parrent;
    }
    
    public void callBack(int action, int key, int mod){
        switch(action){
            case GLFW_PRESS :
                pressedKeys.put(key, true);
                break;
            case GLFW_RELEASE :
                pressedKeys.put(key, false);
                break;
            case GLFW_REPEAT :
                //Maybe
                break;
        }
    }
    
    public void invoke(){
        Set<Integer> keys = pressedKeys.keySet();
        for(Integer i : keys){
            if(pressedKeys.get(i)){
                keyPressed(i);
            } else {
                keyReleased(i);
            }
        }
        
    }
    
    public abstract void mousePressed(int key, int posX, int posY);
    public abstract void mouseClicked(int key, int posX, int posY);
    public abstract void mouseMoved(int posX, int posY);
    public abstract void keyPressed(int key);
    //public abstract void keyTiped(int key);
    public abstract void keyReleased(int i);
}
