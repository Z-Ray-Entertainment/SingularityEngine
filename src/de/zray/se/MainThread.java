/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import de.zray.se.exceptions.MissingRenderBackendException;
import de.zray.se.logger.SELogger;
import de.zray.se.world.World;
import de.zray.se.renderbackend.RenderBackend;
import de.zray.se.storages.DataLibrary;
import de.zray.se.utils.TimeTaken;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

/**
 *
 * @author vortex
 */
public class MainThread {
    private static double fpsUpdate = 0;
    private static double delta = 0;//, timeBeforeAct = System.nanoTime();
    private static int fps = 0, countedFrames;
    private boolean firstCycle = true;
    
    private RenderBackend backend;
    private Stack<RenderBackend> backendStack = new Stack<>();
    private World currentWorld;
    
    public MainThread(){
        if(EngineSettings.get().assetDirectory == null || EngineSettings.get().assetDirectory.isEmpty()){
            SELogger.get().dispatchMsg(DataLibrary.class, SELogger.SELogType.ERROR, new String[]{"No asset directory set!"}, false);
        } else {
            File assetDir = new File(EngineSettings.get().assetDirectory);
            SELogger.get().dispatchMsg(DataLibrary.class, SELogger.SELogType.INFO, new String[]{"Scanning Asset Directory:\n"+assetDir.getAbsolutePath()}, false);
            DataLibrary.get().scanAssetDirectory(assetDir);
            SELogger.get().dispatchMsg(DataLibrary.class, SELogger.SELogType.INFO, new String[]{DataLibrary.get().getNumberOfKnownAssets()+" assetes registered"}, false);
        }
    }
    
    public static final double getDeltaInSec(){
        return delta/1000000000.;
    }
    
    public static final double getDeltaInMs(){
        return delta/1000000.;
    }
    
    public static final int getFPS(){
        return fps;
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
                }
                if(currentWorld != null){
                    currentWorld.act(getDeltaInSec());
                    currentWorld.optimizeScene();
                }
                if(backend.isReady()){
                    backend.setCurrentWorld(currentWorld);
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
            SELogger.get().dispatchMsg(MainThread.class, SELogger.SELogType.INFO, new String[]{"Testing renderer: "+test.getClassAsString()}, false);
            if(test.featureTest()){
                SELogger.get().dispatchMsg(MainThread.class, SELogger.SELogType.INFO, new String[]{"Use renderer: "+test.getClassAsString()}, false);
                this.backend = test;
                break;
            }
            SELogger.get().dispatchMsg(MainThread.class, SELogger.SELogType.INFO, new String[]{"Not supported renderer: "+test.getClassAsString()}, false);
            test = backendStack.pop();
        }
        if(backend == null){
            throw new MissingRenderBackendException();
        }
    }
}
