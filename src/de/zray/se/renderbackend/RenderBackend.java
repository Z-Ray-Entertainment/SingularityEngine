/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend;

import de.zray.se.inputmanager.InputManager;
import de.zray.se.SEWorld;

/**
 *
 * @author vortex
 */
public interface RenderBackend {
    /**
     * This Inferface handles only the communication between any Renderbackend
     * such as OpenGL, Vulkan, GLES or any other.
     * DO NOT, programm any game or other logics such as inputs or actor 
     * behaviour.
     * Only stuff, which kann be changed by the user with in the backend you
     * can handle in here, such as rezising windows or what ever ist important
     * to your Backend.
     */
    
    /**
     * Initialize the Renderbackend and all its context.
     * @return true if init was sucdsessfull
     */
    public abstract boolean init();
    
    /**
     * Refenrece if the Backend was already inited.
     * @return tru if backend is initialized
     */
    public abstract boolean isInited();
    public abstract void renderWorld(double delta);
    public abstract void setCurrentWorld(SEWorld world);
    public abstract void shutdown();
    public abstract void requestClose();
    public abstract boolean closeRequested();
    public abstract boolean isReady();
    public abstract void backendSwitchRequested();
    public abstract int getWidth();
    public abstract int getHeight();
}
