/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import de.zray.se.ai.SEAIModule;
import de.zray.se.audio.SEAudioModule;
import de.zray.se.grapics.SEGLModule;
import de.zray.se.physics.SEBulletModule;
import de.zray.zgui.GUI;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author vortex
 */
public class SEWorld {
    private final SEGLModule glModule;
    private final SEAIModule aiModule;
    private final SEBulletModule bullettModule;
    private final SEAudioModule audioModule;
    private List<SEActor> actors = new LinkedList<>();
    private InputManager inputManager;
    private final List<GUI> guis = new LinkedList<>();
    private final List<Integer> pressedKeysLastTime = new LinkedList<>();
    private final List<Integer> pressedKeysThisTime = new LinkedList<>();
    //private long lastTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
    private float delta = 0;
    private final int currentGUI = 0;
    
    public SEWorld(SEGLModule glModule, SEAIModule aiModule, SEBulletModule bulletModule, SEAudioModule audioModule){
        this.aiModule = aiModule;
        this.glModule = glModule;
        this.bullettModule = bulletModule;
        this.audioModule = audioModule;
    }
    
    public SEWorld(){
        aiModule = new SEAIModule(this);
        glModule = new SEGLModule(this);
        bullettModule = new SEBulletModule(this);
        audioModule = new SEAudioModule(this);
    }
    
    public void act() throws IOException{
        update();
        //render();
        pollInputs();
        pollSounds();
    }
    
    public GUI getCurrentGUI(){
        return guis.get(currentGUI);
    }
    
    public int addGUI(GUI gui){
        guis.add(gui);
        return guis.size()-1;
    }
    
    public void setInputManager(InputManager inputManager){
        this.inputManager = inputManager;
    }
    
    public SEAudioModule getAudioModule(){
        return audioModule;
    }
    
    public SEGLModule getGLModule(){
        return glModule;
    }
    
    public SEAIModule getAIModule(){
        return aiModule;
    }
    
    public SEBulletModule getBulletModule(){
        return bullettModule;
    }
    
    private void pollSounds(){
        if(audioModule != null){
            audioModule.poll();
        }
    }
    
    private void pollInputs(){
        if(inputManager != null){
            pollKeyboardInput();
            pollMouseInput();
        }
        if(guis.size() > 0 && currentGUI <= guis.size()-1){
            //guis.get(currentGUI).pollInputs((long) getDeltaInMS());
        }
    }
    
    private void pollKeyboardInput(){
        /*while(Keyboard.next()){
            if(Keyboard.getEventKeyState()){
                int key = Keyboard.getEventKey();
                inputManager.keyTiped(key);
                
            }
        }
        
        for(int i = 0; i < Keyboard.getKeyCount(); i++){
            if(Keyboard.isKeyDown(i)){
                pressedKeysThisTime.add(i);
                inputManager.keyPressed(i);
            }
            guis.get(currentGUI).keyPressed(i, (long) getDeltaInMS());
        }
        
        for(int i = 0; i < pressedKeysLastTime.size(); i++){
            boolean found = false;
            for(int j = 0; j < pressedKeysThisTime.size(); j++){
                if(Objects.equals(pressedKeysLastTime.get(i), pressedKeysThisTime.get(j))){
                    found = true;
                }
            }
            if(!found){
                inputManager.keyReleased(pressedKeysLastTime.get(i));
            }
        }
        pressedKeysLastTime.clear();
        pressedKeysLastTime.addAll(pressedKeysThisTime);
        pressedKeysThisTime.clear();*/
    }
    
    private void pollMouseInput(){
        /*int x = Mouse.getX();
        int y = Display.getHeight()-Mouse.getY();
        
        inputManager.mouseMoved(x, y);
        
        while(Mouse.next()){
            if(Mouse.getEventButtonState()){
                inputManager.mouseClicked(Mouse.getEventButton(), x, y);
                guis.get(currentGUI).mouseClicked(Mouse.getEventButton());
            }
        }
        for(int i = 0; i < Mouse.getButtonCount(); i++){
            if(Mouse.isButtonDown(i)){
                inputManager.mousePressed(Mouse.getEventButton(), x, y);
            }
            guis.get(currentGUI).mousePressed(Mouse.getEventButton(), (long) getDeltaInMS());
        }
        
        guis.get(currentGUI).mouseHovered();
        guis.get(currentGUI).mouseReleased();
    }
    
    private void render() throws IOException{
        glModule.render();
        if(guis.size() > 0 && currentGUI <= guis.size()-1){
            guis.get(currentGUI).render();
        }*/
    }
    
    private void update(){
        glModule.update(getDeltaInS());
        aiModule.update(getDeltaInS());
        bullettModule.update(getDeltaInS());
        if(guis.size() > 0 && currentGUI <= guis.size()-1){
            guis.get(currentGUI).update(getDeltaInS());
        }
        updateDelta();
    }
    
    public int addActor(SEActor actor){
        for(int i = 0; i < actors.size(); i++){
            if(actors.get(i) == null){
                actors.set(i, actor);
                return i;
            }
        }
        this.actors.add(actor);
        glModule.addSEMesh(actor.getSEMesh());
        aiModule.addAI(actor.getSEAI());
        
        return this.actors.size()-1;
    }
    
    public void removeActor(int id){
        if(id == actors.size()-1){
            actors.remove(id);
        }
        else{
            actors.set(id, null);
        }
    }
    
    public float getDeltaInMS(){
        return delta;
    }
    
    public float getDeltaInS(){
        return delta/1000;
    }
    
    private void updateDelta(){
        /*long currentTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        delta = currentTime - lastTime; //Delta in Milliseconds
        lastTime = currentTime;*/
    }
}
