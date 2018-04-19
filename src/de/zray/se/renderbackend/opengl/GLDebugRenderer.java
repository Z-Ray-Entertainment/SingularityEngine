/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

import de.zray.se.Settings;
import de.zray.se.world.DistancePatch;
import de.zray.se.world.World;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author vortex
 */
public class GLDebugRenderer {
    private GLDebugGrid gridRenderer = new GLDebugGrid();
    private GLDebugDistnacePatch dpRenderer = new GLDebugDistnacePatch();
    
    public void render(World world){
        glDisable(GL_LIGHTING);
        glDisable(GL_TEXTURE_2D);
        glCullFace(GL_FALSE);
        if(Settings.get().debug.renderOnTop){
            glDisable(GL_DEPTH_TEST);
        }        
        glLineWidth(1);
        
        if(Settings.get().debug.showGrid){
            renderGrid();
        }
        if(Settings.get().debug.showDistancePatches){
            renderDistancePatches(world);
        }
    }
    
    private void renderGrid(){
        gridRenderer.renderGrid();
        
    }
    
    private void renderDistancePatches(World world){
        for(DistancePatch dp : world.getDistancePatched()){
            dpRenderer.renderDistnacePatch(dp);
        }
    }
}
