/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

import de.zray.se.MainThread;
import de.zray.se.world.Actor;
import de.zray.se.world.World;
import de.zray.se.Settings;
import de.zray.se.graphics.Camera;
import de.zray.se.graphics.semesh.Mesh;
import de.zray.se.inputmanager.KeyMap;
import de.zray.se.logger.SELogger;
import de.zray.se.renderbackend.RenderBackend;
import org.lwjgl.glfw.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import javax.vecmath.Vector3d;

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
    private World currentWorld;
    private int keyTimes[] = new int[349], threshold = 32;
    
    private GLDebugRenderer dRenderer = new GLDebugRenderer();
    private GLRenderLight lightRender = new GLRenderLight();
    private GLRendererMesh meshRender = new GLRendererMesh();
    
    
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
    public void renderWorld(Settings.DebugMode dMode) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_DEPTH_TEST);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        applyCameraPositioning(currentWorld.getCurrentCamera());
        lightRender.renderLightSources(currentWorld);
        meshRender.renderActors(currentWorld);
        if(dMode == Settings.DebugMode.DEBUG_AND_OBJECTS){
            renderDebug();
        }
        applyCamera(currentWorld.getCurrentCamera());
        glfwSwapBuffers(window);
        glfwPollEvents();
        pollInputs();
    }

    @Override
    public void shutdown() {
        
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
    public void setCurrentWorld(World world){
        this.currentWorld = world;
    }
    
    private void applyCameraPositioning(Camera cam){
        switch(cam.getViewMode()){
            case EGO :
                glRotated(cam.getRotation().x, 1, 0, 0);
                glRotated(cam.getRotation().y, 0, 1, 0);
                glRotated(cam.getRotation().z, 0, 0, 1);
                glTranslatef(-cam.getPosition().x, -cam.getPosition().y, -cam.getPosition().z);
                break;
            case THIRDPERSON :
                break;
        }
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
    
    
    
    
    
    private final void pollInputs(){
        for(int i = 32; i < keyTimes.length; i++){ //32 because of invalid keys < 32
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

    @Override
    public void renderDebug() {
        dRenderer.render(currentWorld);
    }

    @Override
    public boolean pick(int pointerX, int pointerY) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean rayPick(Vector3d ray) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
