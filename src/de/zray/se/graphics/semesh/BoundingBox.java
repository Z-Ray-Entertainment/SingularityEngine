/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.semesh;

import de.zray.se.storages.AssetLibrary;
import de.zray.se.utils.SEUtils;
import de.zray.se.world.Actor;
import de.zray.se.world.Entity;
import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author Vortex Acherontic
 */
public class BoundingBox{
    float radius = 0;
    Vertex verts[] = new Vertex[8], defaultVerts[] = new Vertex[8];
    Orientation ori;
    
    public BoundingBox(Entity parent){
        calculateBoindingBox(parent);
    }
    
    private void calculateBoindingBox(Entity parent){
        this.ori = parent.getOrientation();
        if(parent instanceof Actor){
            Actor parentActor = (Actor) parent;
            int mDataID =  parentActor.getRootMesh().getSEMeshData();
            MeshData mData = AssetLibrary.get().getMesh(mDataID);
            float minX = 0, maxX = 0, minY = 0, maxY = 0, minZ = 0, maxZ = 0;
            Vector3f center = new Vector3f(0, 0, 0);
            for(Vertex v : mData.getVertecies()){
                if(v.vX < minX){
                    minX = v.vX;
                }
                if(v.vX > maxX){
                    maxX = v.vX;
                }
                if(v.vY < minY){
                    minY = v.vY;
                }
                if(v.vY > maxY){
                    maxY = v.vY;
                }
                if(v.vZ < minZ){
                    minZ = v.vZ;
                }
                if(v.vZ > maxZ){
                    maxZ = v.vZ;
                }
                Vector3f vertex = new Vector3f(v.vX, v.vY, v.vZ);
                Vector3f path = SEUtils.getVector(center, vertex);
                float rad = SEUtils.getLenght(path);
                if(rad > radius){
                    radius = rad;
                }
            }
            verts[0] = new Vertex(minX, minY, minZ);
            verts[1] = new Vertex(maxX, minY, minZ);
            verts[2] = new Vertex(minX, maxY, minZ);
            verts[3] = new Vertex(maxX, maxY, minZ);
            verts[4] = new Vertex(minX, minY, maxZ);
            verts[5] = new Vertex(maxX, minY, maxZ);
            verts[6] = new Vertex(minX, maxY, maxZ);
            verts[7] = new Vertex(maxX, maxY, maxZ);
            
            defaultVerts[0] = new Vertex(minX, minY, minZ);
            defaultVerts[1] = new Vertex(maxX, minY, minZ);
            defaultVerts[2] = new Vertex(minX, maxY, minZ);
            defaultVerts[3] = new Vertex(maxX, maxY, minZ);
            defaultVerts[4] = new Vertex(minX, minY, maxZ);
            defaultVerts[5] = new Vertex(maxX, minY, maxZ);
            defaultVerts[6] = new Vertex(minX, maxY, maxZ);
            defaultVerts[7] = new Vertex(maxX, maxY, maxZ);
            /*System.out.println("[BoundingBox]: Builded BBox");
            System.out.println("=> radius: "+radius);
            System.out.println("=> Vertecies:");
            for(Vertex v : verts){
            System.out.println("=> => "+v.vX+" "+v.vY+" "+v.vZ);
            System.out.println("=> => <= <= ");
            }*/
        }
    }
    
    public void setOrientation(Orientation ori){
        this.ori = ori;
    }
    
    public boolean intersect(BoundingBox bb){
        
        return false;
    }
  
    public boolean inside(Vector3d point, Orientation vecOri){
        return realInside(point, vecOri);
    }
    
    public boolean inside(Vector3d point){
        return realInside(point, new Orientation(null));
    }
    
    private boolean realInside(Vector3d point, Orientation vecOri){
        /*if(point.x+vecOri.getPositionVec().x >= xmin+ori.getPositionVec().x && point.x < xmax+ori.getPositionVec().x){
        return true;
        }
        if(point.y+vecOri.getPositionVec().y >= ymin+ori.getPositionVec().y && point.y < ymax+ori.getPositionVec().y+vecOri.getPositionVec().y){
        return true;
        }
        if(point.z+vecOri.getPositionVec().z >= zmin+ori.getPositionVec().z && point.z < zmax+ori.getPositionVec().z+vecOri.getPositionVec().z){
        return true;
        }*/
        return false;
    }
    
    /**
     * 
     * @return The lagest radius from a sphere containing this box
     */
    public double getRadius(){
        return radius;
    }
    
    public Vertex[] getVertecies(){
        return verts;
    }
    
    public Orientation getOrientation(){
        return ori;
    }
    
    public void reCalc(){
        double scale[] = ori.getScale();
        for(int i = 0; i < verts.length; i++){
            Vector3f vertex = new Vector3f(defaultVerts[i].vX, defaultVerts[i].vY, defaultVerts[i].vZ);
            Vector3f scaleVec = new Vector3f((float) scale[0], (float) scale[1], (float) scale[2]);
            vertex.scale(radius, scaleVec);
        }
    }
}
