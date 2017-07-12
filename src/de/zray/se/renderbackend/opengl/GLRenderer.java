/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

import de.zray.se.inputmanager.InputManager;
import de.zray.se.SEActor;
import de.zray.se.SEWorld;
import de.zray.se.Settings;
import de.zray.se.grapics.Camera;
import de.zray.se.grapics.semesh.SEMaterial;
import de.zray.se.grapics.semesh.SEMesh;
import de.zray.se.logger.SELogger;
import de.zray.se.renderbackend.RenderBackend;
import java.awt.event.KeyEvent;
import org.lwjgl.glfw.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.LinkedList;
import java.util.List;
import javax.vecmath.Vector3f;

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
    GLUtils glUtils = new GLUtils();

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
        
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            translateInputs(key);
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
        glEnable(GL_DEPTH_TEST);
        return true;
    }

    @Override
    public boolean isInited() {
        return (window != -1);
    }

       
    @Override
    public void renderWorld(double delta, SEWorld world) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        applyCamera(world.getCurrentCamera());
        glPushMatrix();
        glTranslated(0, 0, -10);
        glBegin(GL_TRIANGLES);
            glVertex3f(0, 1, 0);
            glVertex3f(1, 0, 0);
            glVertex3f(-1, 0, 0);
        glEnd();
        glPopMatrix();
        
        for(SEActor actor : world.getActors()){
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
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    @Override
    public void shutdown() {
        oglRenderDatas.forEach((rData) -> {
            rData.destroy();
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
    public void handleKeyInput(int keyCode, int mode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void handleMouseInput(int keyCode, int mode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                glViewport(0, 0, windowW, windowH);
                glUtils.gluPerspective(cam.getFOV(), (float) windowW / (float) windowH, cam.getNear(), cam.getFar());
                break;
        }
        if (cam.getViewMode() == Camera.ViewMode.EGO) {
            glTranslated(-cam.getPosition().x, -cam.getPosition().y, -cam.getPosition().z);
            applyRotations(cam);
        }
        else {
            Vector3f lookAt = new Vector3f(cam.getLookAt());
            glUtils.gluLookAt(cam.getPosition(), lookAt, new Vector3f(0, 1, 1));
            //System.out.println("LookAt: "+lookAt.x+" "+lookAt.y+" "+lookAt.z);
            //applyRotations(cam);
            //glTranslated(-cam.getPosition().x, -cam.getPosition().y, -cam.getPosition().z);
        }
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }
    
    private void applayEmptyCamera(){
        glMatrixMode(GL_PROJECTION);
        glOrtho(0, windowW, windowH, 0, 1, 100);
        glRotated(90, 1, 0, 0);
        glMatrixMode(GL_MODELVIEW);
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
    
    
    public void translateInputs(int key){
        System.out.println("Key: "+KeyEvent.getKeyText(key)+" Code: "+key);
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
            oglRenderDatas.add(rData);
            mesh.setRenderData(oglRenderDatas.size()-1);
        }
        
        applyMaterial(mesh.getMaterial());
        OpenGLRenderData rData = oglRenderDatas.get(mesh.getRenderData());
        switch(mesh.getRenderMode()){
            case DIRECT :
                glUtils.drawObject(mesh);
                break;
            case DISPLAY_LIST :
                switch(mesh.getDisplayMode()){
                    case SOLID :
                        if(rData.getDisplayList() == -1){
                            glUtils.generateDisplayList(mesh, rData);
                        }
                        glCallList(rData.getDisplayList());
                        break;
                    case WIRED :
                        if(rData.getDisplayListWired()== -1){
                            glUtils.generateDisplayListWired(mesh, rData);
                        }
                        glCallList(rData.getDisplayListWired());
                        break;
                }
                break;
            case VBO :
                switch(mesh.getDisplayMode()){
                    case SOLID :
                        if(rData.getVBOID() == -1 || rData.getVBOSize() == -1){
                            glUtils.generateVBO(mesh, rData);
                        }
                        glUtils.renderVBO(oglRenderDatas.get(mesh.getRenderData()));
                        break;
                    case WIRED :
                        if(rData.getVBOIDWired()== -1 || rData.getVBOSizeWired()== -1){
                            glUtils.generateVBOWired(mesh, rData);
                        }
                        glUtils.renderVBOWired(oglRenderDatas.get(mesh.getRenderData()));
                        break;
                }
                break;
        }
    }
    
    private void applyMaterial(SEMaterial mat){
        if(mat.cullBackfaces() && !glIsEnabled(GL_CULL_FACE)){
            glEnable(GL_CULL_FACE);
        }
        else if(glIsEnabled(GL_CULL_FACE)){
            glDisable(GL_CULL_FACE);
        }
    }
}
