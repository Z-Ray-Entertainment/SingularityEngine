/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.semesh;

import de.zray.se.storages.AssetLibrary;
import de.zray.se.utils.constrains.Link;
import de.zray.se.utils.constrains.Reference;
import de.zray.se.world.Actor;
import de.zray.se.world.Entity;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

/**
 *
 * @author Vortex Acherontic
 */
public class BoundingBox implements Link{
    public static final int BOTTOM_BACK_LEFT = 0, BOTTOM_BACK_RIGHT = 1,
            BOTTOM_FRONT_RIGHT = 2, BOTTOM_FRONT_LEFT = 3, TOP_BACK_LEFT = 4,
            TOP_BACK_RIGHT = 5, TOP_FRONT_RIGHT = 6, TOP_FRONT_LEFT = 7;
    float radius = 0;
    
    Orientation ori;
    Vector3d vertecies[] = new Vector3d[8];
    MeshData mData;
    
    
    public BoundingBox(Entity parent){
        this.ori = parent.getOrientation();
        this.ori.addLink(this);
        calculateBoundingBox(parent);
    }
    
    private void calculateBoundingBox(Entity parent){
        if(parent instanceof Actor){
            for(int i = 0; i < 8; i++){
                vertecies[i] = new Vector3d(0, 0, 0);
            }
            Actor parentActor = (Actor) parent;
            int mDataID =  parentActor.getRootMesh().getSEMeshData();
            mData = AssetLibrary.get().getMesh(mDataID);
            if(mData.getVertecies().size() > 0){
                updateShape(mData.getVertecies());
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
    
    public Vector3d[] getBoundingVertecies(){
        return vertecies;
    }

    @Override
    public void forceRefresh(Reference ref) {
        applyRotations(mData.getVertecies());
        applyScales();
    }
    
    private void applyRotations(List<Vertex> meshVertecies){
        double degX = ori.getRotationVec().x;
        double degY = ori.getRotationVec().z;
        double degZ = ori.getRotationVec().y;
        
        Matrix3d rotX = new Matrix3d(
                0, 0, 0,
                0, Math.cos(degX), -Math.sin(degX),
                0, Math.sin(degX), Math.cos(degX)
        );
        Matrix3d rotY = new Matrix3d(
                Math.cos(degY), 0, Math.sin(degY),
                0, 0, 0,
                -Math.sin(degY), 0, Math.cos(degY)
        );
        Matrix3d rotZ = new Matrix3d(
                Math.cos(degZ), -Math.sin(degZ), 0,
                Math.sin(degZ), Math.cos(degZ), 0,
                0, 0, 0
        );
        List<Vertex> rotatedVertecies = new ArrayList<>(vertecies.length);
        for(Vertex vertex : meshVertecies){
            Vector3d vecVert = new Vector3d(vertex.vX, vertex.vY, vertex.vZ);
            rotX.transform(vecVert);
            rotY.transform(vecVert);
            rotZ.transform(vecVert);
            Vertex rotVert = new Vertex((float) vecVert.x, (float)  vecVert.y, (float)  vecVert.z);
            rotatedVertecies.add(rotVert);
        }
        updateShape(rotatedVertecies);
    }
    
    private void applyScales(){
        double x = ori.getScaleVec().x;
        double y = ori.getScaleVec().y;
        double z = ori.getScaleVec().z;
        Matrix3d scale = new Matrix3d(
                x, 0, 0, 
                0, y, 0, 
                0, 0, z);
        for(Vector3d v : vertecies){
            scale.transform(v);
        }
    }
    
    private void updateShape(List<Vertex> verts){
        for(Vertex v :verts){
            for(int i = 0; i < 8; i++){
                switch(i){
                    case BOTTOM_BACK_LEFT :
                        if(v.vX < vertecies[i].x){
                            vertecies[i].x = v.vX;
                        }
                        if(v.vY < vertecies[i].y){
                            vertecies[i].y = v.vY;
                        }
                        if(v.vZ < vertecies[i].z){
                            vertecies[i].z = v.vZ;
                        }
                        break;
                    case BOTTOM_BACK_RIGHT :
                        if(v.vX > vertecies[i].x){
                            vertecies[i].x = v.vX;
                        }
                        if(v.vY < vertecies[i].y){
                            vertecies[i].y = v.vY;
                        }
                        if(v.vZ < vertecies[i].z){
                            vertecies[i].z = v.vZ;
                        }
                        break;
                    case BOTTOM_FRONT_LEFT :
                        if(v.vX < vertecies[i].x){
                            vertecies[i].x = v.vX;
                        }
                        if(v.vY > vertecies[i].y){
                            vertecies[i].y = v.vY;
                        }
                        if(v.vZ < vertecies[i].z){
                            vertecies[i].z = v.vZ;
                        }
                        break;
                    case BOTTOM_FRONT_RIGHT :
                        if(v.vX > vertecies[i].x){
                            vertecies[i].x = v.vX;
                        }
                        if(v.vY > vertecies[i].y){
                            vertecies[i].y = v.vY;
                        }
                        if(v.vZ < vertecies[i].z){
                            vertecies[i].z = v.vZ;
                        }
                        break;
                    case TOP_BACK_LEFT :
                        if(v.vX < vertecies[i].x){
                            vertecies[i].x = v.vX;
                        }
                        if(v.vY < vertecies[i].y){
                            vertecies[i].y = v.vY;
                        }
                        if(v.vZ > vertecies[i].z){
                            vertecies[i].z = v.vZ;
                        }
                        break;
                    case TOP_BACK_RIGHT :
                        if(v.vX > vertecies[i].x){
                            vertecies[i].x = v.vX;
                        }
                        if(v.vY < vertecies[i].y){
                            vertecies[i].y = v.vY;
                        }
                        if(v.vZ > vertecies[i].z){
                            vertecies[i].z = v.vZ;
                        }
                        break;
                    case TOP_FRONT_LEFT :
                        if(v.vX < vertecies[i].x){
                            vertecies[i].x = v.vX;
                        }
                        if(v.vY > vertecies[i].y){
                            vertecies[i].y = v.vY;
                        }
                        if(v.vZ > vertecies[i].z){
                            vertecies[i].z = v.vZ;
                        }
                        break;
                    case TOP_FRONT_RIGHT :
                        if(v.vX > vertecies[i].x){
                            vertecies[i].x = v.vX;
                        }
                        if(v.vY > vertecies[i].y){
                            vertecies[i].y = v.vY;
                        }
                        if(v.vZ > vertecies[i].z){
                            vertecies[i].z = v.vZ;
                        }
                        break;
                }
            }
        }
    }
}
