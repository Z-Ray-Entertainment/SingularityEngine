/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

import de.zray.se.SEActor;
import de.zray.se.SEWorld;
import de.zray.se.Settings;
import de.zray.se.grapics.Camera;
import de.zray.se.grapics.semesh.SEMesh;
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

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ){
                    glfwSetWindowShouldClose(window, true);
                    closeRequested = true;
            }
        });

        glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                aspectRatio = (float) width/ (float) height;
                windowW = width;
                windowH = height;
            }
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
        
        return true;
    }

    @Override
    public boolean isInited() {
        return (window != -1);
    }

       
    @Override
    public void renderWorld(double delta, SEWorld world) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        for(SEActor actor : world.getActors()){
            if(actor.getSEMesh().getRenderData() == -1){
                OpenGLRenderData rData = new OpenGLRenderData();
                
            }
        }
        
        
        glfwSwapBuffers(window);
        glfwPollEvents();
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
        return closeRequested;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void backendSwitchRequested() {
        //Do nothing
    }
    
    private void applyCamera(Camera cam){
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        switch (cam.getCameraMode()) {
            case ORTHOGRAPHIC:
                glOrtho(0, windowW, windowH, 0, cam.getNear(), cam.getFar());
                break;
            case PERSPECTIVE:
                //glPerspective(cam.getFOV(), aspectRatio, cam.getNear(), cam.getFar());
                break;
        }
        if (cam.getViewMode() == Camera.ViewMode.THIRDPERSON) {
            glTranslated(-cam.getPosition().x, -cam.getPosition().y, -cam.getPosition().z);
            applyRotations(cam);
        } else {
            applyRotations(cam);
            glTranslated(-cam.getPosition().x, -cam.getPosition().y, -cam.getPosition().z);
        }
        glMatrixMode(GL_MODELVIEW);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
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
    
    private void renderSEMesh(SEMesh mesh){}

}
