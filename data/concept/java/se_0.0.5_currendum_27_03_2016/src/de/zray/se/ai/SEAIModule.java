/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.ai;

import de.zray.se.SEModule;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author vortex
 */
public class SEAIModule extends SEModule{
    private List<SEAI> ais = new LinkedList<>();

    @Override
    public boolean update(float delta){
        for(SEAI tmp : ais){
            tmp.act();
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
