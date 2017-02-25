/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics;

import de.zray.se.SEModule;
import de.zray.se.SEWorld;
import de.zray.se.Settings;
import de.zray.se.env.SkyDome;
import de.zray.se.grapics.semesh.SEMesh;
import de.zray.se.grapics.semesh.SEOriantation;
import de.zray.se.logger.SELogger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
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
    private List<DistancePatch> dp = new LinkedList<>();
    private List<Integer> needReOrg = new LinkedList<>();
    
    public SEGLModule(SEWorld parrent){
        super(parrent);
        cams.add(new Camera());
    }
    
    public int addSkyDome(SkyDome dome){
        skyDomes.add(dome);
        return skyDomes.size()-1;
    }
    
    public void setSkyDome(int id){
        skyDome = id;
    }
    
    public void addSEMesh(SEMesh mesh){
        for(DistancePatch tmp : dp){
            if(tmp.addSEMesh(mesh)){
                return;
            }
        }
        createDP(mesh.getOrientation()).addSEMesh(mesh);
    }
    
    private DistancePatch createDP(SEOriantation meshOri){
        int x = (int) (meshOri.getPositionVec().x/Settings.get().scene.dbSize);
        int y = (int) (meshOri.getPositionVec().y/Settings.get().scene.dbSize);
        int z = (int) (meshOri.getPositionVec().z/Settings.get().scene.dbSize);
        SEOriantation dpOri = new SEOriantation(Settings.get().scene.dbSize*x, Settings.get().scene.dbSize*y, Settings.get().scene.dbSize*z);
        dp.add(new DistancePatch(parrent, dpOri));
        if(Settings.get().debug.debugMode == Settings.DebugMode.DEBUG_AND_OBJECTS || Settings.get().debug.debugMode == Settings.DebugMode.DEBUG_ON){
            SELogger.get().dispatchMsg("SEGLModule", SELogger.SELogType.INFO, new String[]{"New DP at: "+dpOri.getPositionVec().x+" "+dpOri.getPositionVec().y+" "+dpOri.getPositionVec().z}, false);
        }
        return dp.get(dp.size()-1);
    }
    
    public void removeSEMesh(UUID uuid){
        for(DistancePatch tmp : dp){
            if(tmp.removeSEMesh(uuid)){
                return;
            }
        }
    }
    
    public SEMesh getSEMesh(UUID uuid){
        for(DistancePatch tmp : dp){
            SEMesh smTMP = tmp.getSEMesh(uuid);
            if(smTMP != null){
                return smTMP;
            }
        }
        return null;
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
        switch(Settings.get().debug.debugMode){
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
        for(int i = 0; i < dp.size(); i++){
            dp.get(i).render();
            if(!dp.get(i).getChagedMeshes().isEmpty()){
                needReOrg.add(i);
            }
        }
        reorganizeChangedMeshes();
    }
    
    private void reorganizeChangedMeshes(){
        for(int i : needReOrg){
            for(SEMesh tmp : dp.get(i).getChagedMeshes()){
                addSEMesh(tmp);
            }
            dp.get(i).clearChangedeshes();
        }
        needReOrg.clear();
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
