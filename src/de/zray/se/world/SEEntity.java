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
    
    public SEEntity(){
        orientation = new SEOriantation(this);
    }
    
    public SEOriantation getOrientation(){
        return orientation;
    }
}
