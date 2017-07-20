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
        for(int i = 0; i < rCache.size(); i++){
            if(rCache.get(i).meshDataID == meshDataID){
                return i;
            }
        }
        SELogger.get().dispatchMsg("GLRenderDataCache", SELogger.SELogType.INFO, new String[]{"Created new RenderDataCache entry for Mesh:"+meshDataID}, false);
        RenderDataCacheEntry rDataCacheEntry = new RenderDataCacheEntry();
        rDataCacheEntry.meshDataID = meshDataID;
        rCache.add(rDataCacheEntry);
        return rCache.size()-1;
    }
    
    public RenderDataCacheEntry getCacheEntry(int id){
        return rCache.get(id);
    }
}
