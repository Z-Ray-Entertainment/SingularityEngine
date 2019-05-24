/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import de.zray.se.exceptions.MissingRenderBackendException;
import de.zray.se.inputmanager.InputManager;
import de.zray.se.logger.SELogger;
import de.zray.se.world.World;
import de.zray.se.renderbackend.RenderBackend;
import de.zray.se.storages.DataLibrary;
import de.zray.se.utils.TimeTaken;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallback;

/**
 *
 * @author vortex
 */
public class Engine {
    private static Engine engine;
    
    private double fpsUpdate = 0;
    private double delta = 0;//, timeBeforeAct = System.nanoTime();
    private int fps = 0, countedFrames;
    private boolean firstCycle = true;
    
    private RenderBackend backend;
    private final Stack<RenderBackend> backendStack;
    private World currentWorld;
    private ArrayList<InputManager> inputs;
    
    public Engine(){
      SELogger.get().dispatchMsg(DataLibrary.class, SELogger.SELogType.INFO, new String[]{"New Engine"}, false);
      this.backendStack = new Stack<>();
      this.inputs = new ArrayList<>();
      if(EngineSettings.get().assetDirectory == null || EngineSettings.get().assetDirectory.isEmpty()){
          SELogger.get().dispatchMsg(DataLibrary.class, SELogger.SELogType.ERROR, new String[]{"No asset directory set!"}, false);
      } else {
          File assetDir = new File(EngineSettings.get().assetDirectory);
          SELogger.get().dispatchMsg(DataLibrary.class, SELogger.SELogType.INFO, new String[]{"Scanning Asset Directory: "+assetDir.getAbsolutePath()}, false);
          LinkedList<String> dublicates = DataLibrary.get().scanAssetDirectory(assetDir);
          dublicates.forEach((s) -> {
            SELogger.get().dispatchMsg(Engine.class, SELogger.SELogType.WARNING, new String[]{s}, true);
        });
          SELogger.get().dispatchMsg(DataLibrary.class, SELogger.SELogType.INFO, new String[]{DataLibrary.get().getNumberOfKnownAssets()+" assetes registered"}, false);
      }
    }
    
    public static final Engine get(){
        if(engine == null){
            engine = new Engine();
        }
        return engine;
    }
    
    public final double getDeltaInSec(){
        return delta/1000000000.;
    }
    
    public final double getDeltaInMs(){
        return delta/1000000.;
    }
    
    public final int getFPS(){
        return fps;
    }
    
    public void addInputManager(InputManager manager){
        SELogger.get().dispatchMsg(Engine.class, SELogger.SELogType.INFO, new String[]{"Added Manager"}, false);
        inputs.add(manager);
    }
    
    public void registerRenderBackend(RenderBackend backend){
        backendStack.push(backend);
    }
    
    public void switchWorld(World world){
        currentWorld = world;
        firstCycle = true;
    }
    
    public void loop() throws IOException, MissingRenderBackendException{
        if(backend == null){
            chooseRenderBackend();
        }
        
        Thread loop = new Thread(() -> {
            TimeTaken timeTaken;
            while(!backend.closeRequested()){
                timeTaken = new TimeTaken(true);
                if(!backend.isInited()){
                    backend.init();
                    setGLFWInputCallbacks(backend.getWindow());
                }
                if(currentWorld != null){
                    currentWorld.act(getDeltaInSec());
                    currentWorld.optimizeScene();
                }
                if(backend.isReady()){
                    backend.setCurrentWorld(currentWorld);
                    glfwPollEvents();
                    backend.renderWorld(EngineSettings.get().debug.debugMode);
                }
                if(firstCycle){
                    timeTaken = new TimeTaken(true);
                    firstCycle = false;
                }
                delta = timeTaken.endInNano();
            }
            shutdown();
        });
        loop.start();
    }
    
    public void shutdown(){
        if(currentWorld.getAudioWorld() != null){
            currentWorld.getAudioWorld().shutdown();
        }
        backend.shutdown();
        System.exit(0);
    }
    
    public World getCurrentWorld(){
        return currentWorld;
    }
    
    private void chooseRenderBackend() throws MissingRenderBackendException{
        RenderBackend test = backendStack.pop();
        while(test != null){
            SELogger.get().dispatchMsg(Engine.class, SELogger.SELogType.INFO, new String[]{"Testing renderer: "+test.getClassAsString()}, false);
            if(test.featureTest()){
                SELogger.get().dispatchMsg(Engine.class, SELogger.SELogType.INFO, new String[]{"Use renderer: "+test.getClassAsString()}, false);
                this.backend = test;
                break;
            }
            SELogger.get().dispatchMsg(Engine.class, SELogger.SELogType.INFO, new String[]{"Not supported renderer: "+test.getClassAsString()}, false);
            test = backendStack.pop();
        }
        if(backend == null){
            throw new MissingRenderBackendException();
        } 
    }
    
    private void setGLFWInputCallbacks(long window){
        glfwSetKeyCallback(window, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                int a = action;
                //SELogger.get().dispatchMsg(Engine.class, SELogger.SELogType.INFO, new String[]{"Input!", "Key: "+key, "Action: "+action}, false);
                //SELogger.get().dispatchMsg(Engine.class, SELogger.SELogType.INFO, new String[]{"Managers to serve: "+inputs.size()}, false);
                for(InputManager i : inputs){
                    switch(a){
                        case GLFW_PRESS :
                            SELogger.get().dispatchMsg(Engine.class, SELogger.SELogType.INFO, new String[]{"GLFW_PRESS"}, false);
                            i.keyTiped(key);
                            break;
                        case GLFW_RELEASE :
                            SELogger.get().dispatchMsg(Engine.class, SELogger.SELogType.INFO, new String[]{"GLFW_RELEASE"}, false);
                            i.keyReleased(key);
                            break;
                        case GLFW_REPEAT:
                            SELogger.get().dispatchMsg(Engine.class, SELogger.SELogType.INFO, new String[]{"GLFW_REPEAT"}, false);
                            i.keyPressed(key);
                            break;
                    }
                }
            }
        });
    }
}
