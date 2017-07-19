/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

import de.zray.se.MainThread;
import de.zray.se.SEActor;
import de.zray.se.SEWorld;
import de.zray.se.Settings;
import de.zray.se.grapics.Camera;
import de.zray.se.grapics.semesh.SEMesh;
import de.zray.se.grapics.semesh.SEMeshData;
import de.zray.se.inputmanager.KeyMap;
import de.zray.se.logger.SELogger;
import de.zray.se.renderbackend.RenderBackend;
import org.lwjgl.glfw.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import storages.AssetLibrary;

/**
 *
 * @author vortex
 */
public class GLRenderer implements RenderBackend{
    private long window = -1;
    private float aspectRatio = 1;
    private final String windowTitle = Settings.get().title+" "+Settings.get().version;
    private int windowW = Settings.get().window.resX;
    private int windowH = Settings.get().window.resY;
    private boolean closeRequested = false;
    private List<OpenGLRenderData> oglRenderDatas = new LinkedList<>();
    private GLUtils glUtils = new GLUtils();
    private SEWorld currentWorld;
    private int keyTimes[] = new int[349], threshold = 32;
    private List<RenderDataCacheEntry> rCache = new LinkedList<>();
    
    
    @Override
    public boolean init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if ( !glfwInit() ){
            SELogger.get().dispatchMsg(this, SELogger.SELogType.ERROR, new String[]{"Unable to initialize GLFW"}, true);
            return false;
        }
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        window = glfwCreateWindow(windowW, windowH, windowTitle, NULL, NULL);
        if ( window == NULL ){
            SELogger.get().dispatchMsg(this, SELogger.SELogType.ERROR, new String[]{"Failed to create the GLFW window"}, true);
            return false;
        }
        
        glfwSetMouseButtonCallback(window, (window, key, action, mods) -> {
            
        });

        glfwSetWindowSizeCallback(window, (window, w, h) -> {
            calcWindowProps(w, h);
        });
        
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(0);
        glfwShowWindow(window);
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        calcWindowProps(windowW, windowH);
        return true;
    }

    private void calcWindowProps(int w, int h){
        aspectRatio = (float)w/(float)h;
        windowW = w;
        windowH = h;
        glViewport(0, 0, windowW, windowH);
    }
    
    @Override
    public boolean isInited() {
        return (window != -1);
    }

       
    @Override
    public void renderWorld(double delta) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        for(SEActor actor : currentWorld.getActors()){
            List<SEMesh> rendables = actor.getRendableSEMeshes();
            if(rendables != null){
                for(int i = 0; i < rendables.size(); i++){
                    SEMesh mesh = rendables.get(i);
                    if(mesh != null){
                        glPushMatrix();
                        renderMesh(mesh);
                        glPopMatrix();
                    }
                }
            }
        }
        
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_FRONT_AND_BACK, 0);
        glBegin(GL_QUADS);
            glVertex2d(0, 0);
            glTexCoord2d(0, 0);
            glVertex2d(0, 1);
            glTexCoord2d(0, 1);
            glVertex2d(1, 1);
            glTexCoord2d(1, 1);
            glVertex2d(1, 0);
            glTexCoord2d(1, 0);
        glEnd();
        applyCamera(currentWorld.getCurrentCamera());
        glfwSwapBuffers(window);
        glfwPollEvents();
        pollInputs();
    }

    @Override
    public void shutdown() {
        oglRenderDatas.forEach((rData) -> {
            rData.destroy(rCache.get(rData.getRenderDataCacheID()));
        });
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    @Override
    public boolean closeRequested() {
        return (closeRequested || glfwWindowShouldClose(window));
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void backendSwitchRequested(){
        //Do nothing
    }
    
    @Override
    public void setCurrentWorld(SEWorld world){
        this.currentWorld = world;
    }
    
    private void applyCamera(Camera cam){
        if(cam == null){
            applayEmptyCamera();
            return;
        }
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        switch (cam.getCameraMode()) {
            case ORTHOGRAPHIC:
                glOrtho(0, windowW, windowH, 0, cam.getNear(), cam.getFar());
                break;
            case PERSPECTIVE:
                GLUProject.gluPerspective(cam.getFOV(), aspectRatio, cam.getNear(), cam.getFar());
                //applyRotations(cam);
                glTranslated(-cam.getPosition().x, -cam.getPosition().y, -cam.getPosition().z);
                break;
        }
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }
    
    private void applayEmptyCamera(){
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, windowW, windowH, 0, 1, 100);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }
    
    private void applyRotations(Camera cam) {
        if (!cam.getRotationLocks()[0]) {
            glRotated(cam.getRotation().x, 1, 0, 0);
        }
        if (!cam.getRotationLocks()[1]) {
            glRotated(cam.getRotation().y, 0, 1, 0);
        }
        if (!cam.getRotationLocks()[2]) {
            glRotated(cam.getRotation().z, 0, 0, 1);
        }

    }

    @Override
    public int getWidth() {
        return windowW;
    }

    @Override
    public int getHeight() {
        return windowH;
    }
    
    @Override
    public void requestClose() {
        closeRequested = true;
        glfwSetWindowShouldClose(window, true);
    }
    
    private void renderMesh(SEMesh mesh){
        double posX = mesh.getOrientation().getPositionVec().x;
        double posY = mesh.getOrientation().getPositionVec().y;
        double posZ = mesh.getOrientation().getPositionVec().z;
        double rotX = mesh.getOrientation().getRotationVec().x;
        double rotY = mesh.getOrientation().getRotationVec().y;
        double rotZ = mesh.getOrientation().getRotationVec().z;
        double scaleX = mesh.getOrientation().getScaleVec().x;
        double scaleY = mesh.getOrientation().getScaleVec().y;
        double scaleZ = mesh.getOrientation().getScaleVec().z;
        
        glTranslated(posX, posY, posZ);
        glRotated(rotX, 1, 0, 0);
        glRotated(rotY, 0, 1, 0);
        glRotated(rotZ, 0, 0, 1);
        glScaled(scaleX, scaleY, scaleZ);
        
        if(mesh.getRenderData() == -1){
            OpenGLRenderData rData = new OpenGLRenderData();
            rData.setRenderDataCacheID(lookUpRenderDataCache(mesh.getSEMeshData()));
            oglRenderDatas.add(rData);
            mesh.setRenderData(oglRenderDatas.size()-1);
        }
        
        OpenGLRenderData rData = oglRenderDatas.get(mesh.getRenderData());
        glUtils.applyMaterial(mesh.getMaterial(), rData);
        
        SEMeshData mData = AssetLibrary.get().getMesh(mesh.getSEMeshData());
        RenderDataCacheEntry rDataCache = rCache.get(rData.getRenderDataCacheID());
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
    }
    
    private int lookUpRenderDataCache(int meshDataID){
        for(int i = 0; i < rCache.size(); i++){
            if(rCache.get(i).meshDataID == meshDataID){
                return i;
            }
        }
        SELogger.get().dispatchMsg("GLRenderer", SELogger.SELogType.INFO, new String[]{"Created new RenderDataCache entry for Mesh:"+meshDataID}, false);
        RenderDataCacheEntry rDataCacheEntry = new RenderDataCacheEntry();
        rDataCacheEntry.meshDataID = meshDataID;
        rCache.add(rDataCacheEntry);
        return rCache.size()-1;
    }
    
    private void pollInputs(){
        for(int i = 32; i < keyTimes.length; i++){
            /*if(i == GLFW_KEY_LEFT_SHIFT){
                System.out.println(GLFW_KEY_LEFT_SHIFT);
            }*/
            if(glfwGetKey(window, i) == 1){
                if(keyTimes[i] == 0){
                    currentWorld.hanldeKeyInputs(i, KeyMap.MODE.TIPED);
                }
                else if(keyTimes[i] >= threshold){
                    currentWorld.hanldeKeyInputs(i, KeyMap.MODE.PRESSED);
                }
                keyTimes[i] += MainThread.getDeltaInMs();
            }
            else{
                if(keyTimes[i] > 0){
                    currentWorld.hanldeKeyInputs(i, KeyMap.MODE.RELEASED);
                }
                keyTimes[i] = 0;
            }
        }
    }
}
