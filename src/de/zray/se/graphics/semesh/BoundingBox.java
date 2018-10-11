/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.semesh;

import de.zray.se.storages.AssetLibrary;
import de.zray.se.world.Actor;
import de.zray.se.world.Entity;
import javax.vecmath.Vector3d;

/**
 *
 * @author Vortex Acherontic
 */
public class BoundingBox{
    public static final int BOTTOM_BACK_LEFT = 0, BOTTOM_BACK_RIGHT = 1,
            BOTTOM_FRONT_RIGHT = 2, BOTTOM_FRONT_LEFT = 3, TOP_BACK_LEFT = 4,
            TOP_BACK_RIGHT = 5, TOP_FRONT_RIGHT = 6, TOP_FRONT_LEFT = 7;
    float radius = 0;
    
    Orientation ori;
    Vertex vertecies[] = new Vertex[8];
    
    
    public BoundingBox(Entity parent){
        calculateBoundingBox(parent);
    }
    
    private void calculateBoundingBox(Entity parent){
        this.ori = parent.getOrientation();
        if(parent instanceof Actor){
            for(int i = 0; i < 8; i++){
                vertecies[i] = new Vertex(0, 0, 0);
            }
            Actor parentActor = (Actor) parent;
            int mDataID =  parentActor.getRootMesh().getSEMeshData();
            MeshData mData = AssetLibrary.get().getMesh(mDataID);
            if(mData.getVertecies().size() > 0){
                for(Vertex v : mData.getVertecies()){
                    for(int i = 0; i < 8; i++){
                        switch(i){
                            case BOTTOM_BACK_LEFT :
                                if(v.vX < vertecies[i].vX){
                                    vertecies[i].vX = v.vX;
                                }
                                if(v.vY < vertecies[i].vY){
                                    vertecies[i].vY = v.vY;
                                }
                                if(v.vZ < vertecies[i].vZ){
                                    vertecies[i].vZ = v.vZ;
                                }
                                break;
                            case BOTTOM_BACK_RIGHT :
                                if(v.vX > vertecies[i].vX){
                                    vertecies[i].vX = v.vX;
                                }
                                if(v.vY < vertecies[i].vY){
                                    vertecies[i].vY = v.vY;
                                }
                                if(v.vZ < vertecies[i].vZ){
                                    vertecies[i].vZ = v.vZ;
                                }
                                break;
                            case BOTTOM_FRONT_LEFT :
                                if(v.vX < vertecies[i].vX){
                                    vertecies[i].vX = v.vX;
                                }
                                if(v.vY > vertecies[i].vY){
                                    vertecies[i].vY = v.vY;
                                }
                                if(v.vZ < vertecies[i].vZ){
                                    vertecies[i].vZ = v.vZ;
                                }
                                break;
                            case BOTTOM_FRONT_RIGHT :
                                if(v.vX > vertecies[i].vX){
                                    vertecies[i].vX = v.vX;
                                }
                                if(v.vY > vertecies[i].vY){
                                    vertecies[i].vY = v.vY;
                                }
                                if(v.vZ < vertecies[i].vZ){
                                    vertecies[i].vZ = v.vZ;
                                }
                                break;
                            case TOP_BACK_LEFT :
                                if(v.vX < vertecies[i].vX){
                                    vertecies[i].vX = v.vX;
                                }
                                if(v.vY < vertecies[i].vY){
                                    vertecies[i].vY = v.vY;
                                }
                                if(v.vZ > vertecies[i].vZ){
                                    vertecies[i].vZ = v.vZ;
                                }
                                break;
                            case TOP_BACK_RIGHT :
                                if(v.vX > vertecies[i].vX){
                                    vertecies[i].vX = v.vX;
                                }
                                if(v.vY < vertecies[i].vY){
                                    vertecies[i].vY = v.vY;
                                }
                                if(v.vZ > vertecies[i].vZ){
                                    vertecies[i].vZ = v.vZ;
                                }
                                break;
                            case TOP_FRONT_LEFT :
                                if(v.vX < vertecies[i].vX){
                                    vertecies[i].vX = v.vX;
                                }
                                if(v.vY > vertecies[i].vY){
                                    vertecies[i].vY = v.vY;
                                }
                                if(v.vZ > vertecies[i].vZ){
                                    vertecies[i].vZ = v.vZ;
                                }
                                break;
                            case TOP_FRONT_RIGHT :
                                if(v.vX > vertecies[i].vX){
                                    vertecies[i].vX = v.vX;
                                }
                                if(v.vY > vertecies[i].vY){
                                    vertecies[i].vY = v.vY;
                                }
                                if(v.vZ > vertecies[i].vZ){
                                    vertecies[i].vZ = v.vZ;
                                }
                                break;
                        }
                    }
                }
            }
            
        }
    }
    
    public void setOrientation(Orientation ori){
        this.ori = ori;
    }
    
    public boolean intersect(BoundingBox bb){
        
        return false;
    }
  
    public boolean isInside(Vector3d point){
        return false;
    }
    
    private boolean isBetween(double start, double end, double val){
        return val <= end && val >= start;
    }
    
    /**
     * 
     * @return The lagest radius from a sphere containing this box
     */
    public double getRadius(){
        return radius;
    }
       
    public Orientation getOrientation(){
        return ori;
    }
    
    public void reCalc(){
    }
    
    public Vertex[] getBoundingVertecies(){
        return vertecies;
    }
}
