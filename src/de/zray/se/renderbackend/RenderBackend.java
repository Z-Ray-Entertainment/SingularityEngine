/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend;

import de.zray.se.SEWorld;

/**
 *
 * @author vortex
 */
public interface RenderBackend {
    public abstract void init();
    public abstract boolean isInited();
    public abstract void renderWorld(SEWorld world);
    public abstract void shutdown();
    public abstract boolean closeRequested();
}
