/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import de.zray.se.ai.SEAI;
import de.zray.se.grapics.semesh.SEMesh;
import de.zray.se.physics.SEBulletObject;

/**
 *
 * @author Vortex Acherontic
 */
public class SEActor {
    private SEMesh mesh;
    private SEAI ai;
    private SEBulletObject bullet;
    
    public SEActor(SEMesh mesh, SEAI ai, SEBulletObject bulletObj){
        this.ai = ai;
        this.bullet = bulletObj;
        this.mesh = mesh;
    }
    
    public SEActor(String mesh){}
    
    public SEMesh getSEMesh(){
        return mesh;
    }
    
    public SEAI getSEAI(){
        return ai;
    }
    
   public SEBulletObject getBulletObject(){
       return bullet;
   } 
}
