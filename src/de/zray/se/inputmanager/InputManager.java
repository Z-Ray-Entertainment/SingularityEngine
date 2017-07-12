/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.inputmanager;

import de.zray.se.SEWorld;

/**
 *
 * @author vortex
 */
public abstract class InputManager {
    public static final KeyMap KEY_MAP = new KeyMap(KeyMap.KeyBoardLayout.ENGLSIH);
    private SEWorld parrent;
    
    public InputManager(SEWorld world){
        this.parrent = world;
    }
    
    public SEWorld getWorld(){
        return parrent;
    }
    
    public abstract void mousePressed(int key, int posX, int posY);
    public abstract void mouseClicked(int key, int posX, int posY);
    public abstract void mouseMoved(int posX, int posY);
    public abstract void keyPressed(int key);
    public abstract void keyTiped(int key);
    public abstract void keyReleased(int key);
}
