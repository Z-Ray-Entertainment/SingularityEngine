/*
 * Copyright by Z-Ray Entertainment 2018
 * Unauthorized copying or distributing of this source is not allowed
 * for further informations contact: support@z-ray.de
 */
package de.zray.se.world;

import de.zray.se.graphics.semesh.SEOriantation;

/**
 *
 * @author vortex
 */
public abstract class SEEntity implements Refreshable{
    private final SEOriantation orientation;
    private DistancePatch parent;
    
    public SEEntity(){
        orientation = new SEOriantation(this);
    }
    
    public SEOriantation getOrientation(){
        return orientation;
    }
    
    public void setParent(DistancePatch parent){
        this.parent = parent;
    }
    
    @Override
    public void setRefreshNeeded(boolean b) {
        if(b){
            if(parent != null){
                parent.setRefreshNeeded(b);
            }
        }
    }
}
