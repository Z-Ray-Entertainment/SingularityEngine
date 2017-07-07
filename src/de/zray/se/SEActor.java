/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import de.zray.se.ai.SEAI;
import de.zray.se.grapics.semesh.SEMaterial;
import de.zray.se.grapics.modelloader.Modelloader;
import de.zray.se.grapics.semesh.SEMesh;
import de.zray.se.grapics.semesh.SEOriantation;
import de.zray.se.physics.SEBulletObject;

/**
 *
 * @author Vortex Acherontic
 */
public class SEActor {
    private SEMesh mesh;
    private SEAI ai;
    private SEBulletObject bullet;
    private boolean isVisible;
    
    public SEActor(SEMesh mesh, SEAI ai, SEBulletObject bulletObj, SEWorld parrent){
        this.ai = ai;
        this.bullet = bulletObj;
        this.mesh = mesh;
    }
    
    public SEActor(String mesh, SEWorld parrent){
        this.mesh = Modelloader.get().loadModel(mesh);
    }
    
    public SEActor(String mesh, SEMaterial mat){
        this.mesh = Modelloader.get().loadModel(mesh);
        this.mesh.setMaterial(mat);
    }
    
    public SEMesh getSEMesh(){
        return mesh;
    }
    
    public SEAI getSEAI(){
        return ai;
    }
    
    public void setAI(SEAI ai){
        this.ai = ai;
    }
    
   public SEBulletObject getBulletObject(){
       return bullet;
   }
   
   public void setOriantation(SEOriantation ori){
       mesh.setOrientation(ori);
   }
   
   public SEOriantation getOrientation(){
       return mesh.getOrientation();
   }
   
   public void setVisible(boolean visible){
       isVisible = visible;
   }
   
   public boolean isVisible(){
       return isVisible;
   }
}
