/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.modelloader;

import de.zray.se.graphics.modelloader.OBJLoader.OBJLoader;
import de.zray.se.graphics.semesh.SEMaterial;
import de.zray.se.graphics.semesh.SEMesh;
import de.zray.se.logger.SELogger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author vortex
 */
public class Modelloader {
    public List<LoaderModule> loaderModules = new ArrayList<>();
    private List<ModelCacheEntry> modelCache = new LinkedList<>();
    private static Modelloader mLoader;
    
    public SEMesh loadModel(String file){
        SEMesh tmp = checkForExisitingModels(file);
        if(tmp == null){
            for(LoaderModule module : loaderModules){
                if(module.meshSupported(file)){
                    tmp = module.loadModel(file);
                    modelCache.add(new ModelCacheEntry(file, tmp.getSEMeshData()));
                    return module.loadModel(file);
                }
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
    
    private SEMesh checkForExisitingModels(String file){
        for(ModelCacheEntry entry : modelCache){
            int meshDataID = entry.compareFile(file);
            if(meshDataID > -1){
                SELogger.get().dispatchMsg("ModelLoader", SELogger.SELogType.INFO, new String[]{"MeshData for "+file+" already cached!"}, false);
                return new SEMesh(new SEMaterial(), meshDataID);
            }
        }
        return null;
    }
}
