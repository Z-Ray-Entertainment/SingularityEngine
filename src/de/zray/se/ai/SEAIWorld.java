/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.ai;

import de.zray.se.world.Module;
import de.zray.se.world.World;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vortex
 */
public class SEAIWorld extends Module{
    private List<SEAI> ais = new ArrayList<>();

    public SEAIWorld(World parrent){
        super(parrent);
    }
    
    @Override
    public boolean update(double delta){
        for(SEAI tmp : ais){
            if(tmp != null){
                tmp.act(delta);
            }
        }
        return true;
    } 
   
    public void addAI(SEAI ai){
        this.ais.add(ai);
    }

    @Override
    public boolean shutdown() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean cleanUp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
