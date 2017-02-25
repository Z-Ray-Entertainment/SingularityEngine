/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics.modelloader;

import de.zray.se.grapics.modelloader.OBJLoader.OBJLoader;
import de.zray.se.grapics.semesh.SEMesh;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vortex
 */
public class Modelloader {
    public List<LoaderModule> loaderModules = new ArrayList<>();
    private static Modelloader mLoader;
    
    public SEMesh loadModel(String file){
        for(LoaderModule module : loaderModules){
            if(module.meshSupported(file)){
                return module.loadModel(file);
            }
        }
        return null;
    }
    
    public void addModule(LoaderModule mod){
        loaderModules.add(mod);
    }
    
    public static final Modelloader get(){
        if(mLoader == null){
            mLoader = new Modelloader();
            mLoader.addModule(new OBJLoader());
        }
        return mLoader;
    }
}
