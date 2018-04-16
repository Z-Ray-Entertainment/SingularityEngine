/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

import de.zray.se.logger.SELogger;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author vortex
 */
public class GLRenderDataCache {
    private List<RenderDataCacheEntry> rCache = new LinkedList<>();
    
    public int lookUpCache(int meshDataID){
        return lookUpCacheFast(meshDataID);
    }
    
    private int lookUpCacheFast(int meshDataID){
        if(rCache.size()-1 < meshDataID || rCache.get(meshDataID) == null){
            alloc(meshDataID-rCache.size()+1);
            RenderDataCacheEntry entry = new RenderDataCacheEntry();
            rCache.set(meshDataID, entry);
            SELogger.get().dispatchMsg("GLRenderDataCache", SELogger.SELogType.INFO, new String[]{"Created new RenderDataCache entry for Mesh:"+meshDataID}, false);
            return meshDataID;
        }
        return meshDataID;
    }
    
    private void alloc(int amount){
        for(int i = 0; i < amount; i++){
            rCache.add(null);
        }
    }
    
    public RenderDataCacheEntry getCacheEntry(int id){
        return rCache.get(id);
    }
}
