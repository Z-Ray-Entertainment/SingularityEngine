/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics;

import de.zray.se.SEModule;
import de.zray.se.Settings;
import de.zray.se.env.SkyDome;
import de.zray.se.grapics.semesh.SEMesh;
import de.zray.se.grapics.texturemanager.TextureManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author vortex
 */
public class SEGLModule extends SEModule{
    private List<Camera> cams = new ArrayList<>();
    private List<SkyDome> skyDomes = new ArrayList<>();
    private int cam = 0, skyDome = 0;
    private TextureManager texManager = new TextureManager();
    
    public SEGLModule(){
        cams.add(new Camera());
    }
    
    public int addSkyDome(SkyDome dome){
        skyDomes.add(dome);
        return skyDomes.size()-1;
    }
    
    public void setSkyDome(int id){
        skyDome = id;
    }
    
    public int addSEMesh(SEMesh mesh){
        if(freeSlots.size() > 1){
            int slot = freeSlots.get(0);
            freeSlots.remove(0);
            meshes.set(slot, mesh);
            return slot;
        }
        else{
            meshes.add(mesh);
            return meshes.size()-1;
        }
    }
    
    public void removeSEMesh(int id){
        if(id == meshes.size()-1){
            meshes.remove(id);
        }
        else{
            meshes.set(id, null);
            freeSlots.add(id);
        }
    }
    
    public SEMesh getSEMesh(int id){
        return meshes.get(id);
    }
    
    public Camera getCurrentCamera(){
        return cams.get(cam);
    }
    
    public int addCamera(Camera cam){
        this.cams.add(cam);
        return cams.size()-1;
    }
    
    public void render() throws IOException{
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        if(!skyDomes.isEmpty()){
            skyDomes.get(skyDome).render();
        }
        glEnable(GL_DEPTH_TEST);
        cams.get(cam).applyCamera();
        switch(Settings.get().debugMode){
            case DEBUG_ON :
            case DEBUG_AND_OBJECTS:
                debugSetup();
                renderDebugGrid();
                renderObjects();
                break;
            case DEBUG_OFF:
                renderObjects();
                break;
        }
        
    }
    
    private void debugSetup(){
        glDisable(GL_LIGHTING);
                glDisable(GL_BLEND);
                glDisable(GL_TEXTURE);
                glDisable(GL_TEXTURE_2D);
    }
    
    private void renderDebugGrid(){
        glColor4f(1, 0, 0, 0);
        int linesX = 100, linesY = 100;
        glPushMatrix();
        glTranslatef(0, 0, (float)-linesX/2f);
        glBegin(GL_LINES);
        for(int i = 0; i < linesX+1; i++){
            glVertex3f(-linesX/2, 0, i);
            glVertex3f(linesX/2, 0, i);
            glTranslatef(0, 0, 1);
        }
        glEnd();
        glPopMatrix();
        glPushMatrix();
        glTranslatef((float)-linesY/2f, 0, 0);
        glBegin(GL_LINES);
        for(int i = 0; i < linesY+1; i++){
            glVertex3f(i, 0, -linesY/2);
            glVertex3f(i, 0, linesY/2);
            glTranslatef(1, 0, 0);
        }
        glEnd();
        glPopMatrix();
    }
    
    private void renderObjects() throws IOException{
        for(SEMesh tmp : meshes){
            if(tmp != null){
                tmp.render();
            }
        }
    }
    
    @Override
    public boolean shutdown() {
        return true;
    }

    @Override
    public boolean update(float delta) {
        return true;
    }

    @Override
    public boolean cleanUp() {
        return true;
    }
}
