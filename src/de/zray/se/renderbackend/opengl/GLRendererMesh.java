/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

import de.zray.se.graphics.semesh.Mesh;
import de.zray.se.graphics.semesh.MeshData;
import de.zray.se.storages.AssetLibrary;
import de.zray.se.world.Actor;
import de.zray.se.world.World;
import java.util.LinkedList;
import java.util.List;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL11.glTranslated;

/**
 *
 * @author vortex
 */
public class GLRendererMesh {
    private List<OpenGLRenderData> oglRenderDatas = new LinkedList<>();
    private GLUtils glUtils = new GLUtils();
    private GLRenderDataCache glCache = new GLRenderDataCache();
    
    public void renderActors(World world){
        for(Actor actor : world.getVisibleActors()){
            if(actor != null){
                List<Mesh> rendables = actor.getRendableSEMeshes();
                if(rendables != null){
                    for(int i = 0; i < rendables.size(); i++){
                        Mesh mesh = rendables.get(i);
                        if(mesh != null){
                            glPushMatrix();
                            renderMesh(mesh, actor);
                            glPopMatrix();
                        }
                    }
                }
            }
        }
    }
    
    private void renderMesh(Mesh mesh, Actor parent){
        double posX = parent.getOrientation().getPositionVec().x+mesh.getOffset().getPositionVec().x;
        double posY = parent.getOrientation().getPositionVec().y+mesh.getOffset().getPositionVec().y;
        double posZ = parent.getOrientation().getPositionVec().z+mesh.getOffset().getPositionVec().z;
        double rotX = parent.getOrientation().getRotationVec().x+mesh.getOffset().getRotationVec().x;
        double rotY = parent.getOrientation().getRotationVec().y+mesh.getOffset().getRotationVec().y;
        double rotZ = parent.getOrientation().getRotationVec().z+mesh.getOffset().getRotationVec().z;
        double scaleX = parent.getOrientation().getScaleVec().x+mesh.getOffset().getScaleVec().x;
        double scaleY = parent.getOrientation().getScaleVec().y+mesh.getOffset().getScaleVec().y;
        double scaleZ = parent.getOrientation().getScaleVec().z+mesh.getOffset().getScaleVec().z;
        
        /*System.out.println("[GLRenderer]: Rendering Mesh");
        System.out.println("[GLRenderer]: -> Position: "+posX+" "+posY+" "+posZ);
        System.out.println("[GLRenderer]: -> Rotation: "+rotX+" "+rotY+" "+rotZ);
        System.out.println("[GLRenderer]: -> Scale: "+scaleX+" "+scaleY+" "+scaleZ);*/
        
        glTranslated(posX, posY, posZ);
        glRotated(rotX, 1, 0, 0);
        glRotated(rotY, 0, 1, 0);
        glRotated(rotZ, 0, 0, 1);
        glScaled(scaleX, scaleY, scaleZ);
        
        if(mesh.getRenderData() == -1){
            OpenGLRenderData rData = new OpenGLRenderData();
            rData.setRenderDataCacheID(glCache.lookUpCache(mesh.getSEMeshData()));
            oglRenderDatas.add(rData);
            mesh.setRenderData(oglRenderDatas.size()-1);
        }
        
        OpenGLRenderData rData = oglRenderDatas.get(mesh.getRenderData());
        glUtils.applyMaterial(mesh.getMaterial(), rData);
        
        MeshData mData = AssetLibrary.get().getMesh(mesh.getSEMeshData());
        RenderDataCacheEntry rDataCache = glCache.getCacheEntry(rData.getRenderDataCacheID());
        switch(mesh.getRenderMode()){
            case DIRECT :
                glUtils.drawObject(mData);
                break;
            case DISPLAY_LIST :
                switch(mesh.getDisplayMode()){
                    case SOLID :
                        if(rDataCache.displayListID == -1){
                            glUtils.generateDisplayList(mData, rDataCache);
                        }
                        glCallList(rDataCache.displayListID);
                        break;
                    case WIRED :
                        if(rDataCache.displayListIDWired== -1){
                            glUtils.generateDisplayListWired(mData, rDataCache);
                        }
                        glCallList(rDataCache.displayListIDWired);
                        break;
                }
                break;
            case VBO :
                switch(mesh.getDisplayMode()){
                    case SOLID :
                        if(rDataCache.vboID == -1 || rDataCache.vboSize == -1){
                            glUtils.generateVBO(mData, rDataCache);
                        }
                        glUtils.renderVBO(rDataCache);
                        break;
                    case WIRED :
                        if(rDataCache.vboIDWired == -1 || rDataCache.vboSizeWired == -1){
                            glUtils.generateVBOWired(mData, rDataCache);
                        }
                        glUtils.renderVBOWired(rDataCache);
                        break;
                }
                break;
        }
        glDisable(GL_TEXTURE_2D);
        //glUtils.applyNullifyMaterial();
    }
    
    public void cleanUp(){
        oglRenderDatas.forEach((rData) -> {
            rData.destroy(glCache.getCacheEntry(rData.getRenderDataCacheID()));
        });
    }
}
