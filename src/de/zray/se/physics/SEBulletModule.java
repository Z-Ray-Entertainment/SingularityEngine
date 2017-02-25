/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.physics;

import de.zray.se.SEModule;
import de.zray.se.SEWorld;

/**
 *
 * @author vortex
 */
public class SEBulletModule extends SEModule{

    public SEBulletModule(SEWorld parrent){
        super(parrent);
    }
    
    @Override
    public boolean shutdown() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(float delta) {
        return true;
    }

    @Override
    public boolean cleanUp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
