/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics.semesh;

import java.util.List;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Vortex Acherontic
 */
public class BoundingBox {
    float xmin = 0, xmax = 0, ymin = 0, ymax = 0, zmin = 0, zmax = 0;
    Vector3d v1, v2, v3, v4, v5, v6, v7, v8;
    SEMesh cube;
    SEOriantation ori = new SEOriantation(0, 0, 0);
    
    public BoundingBox(float xmin, float xmax, float ymin, float ymax, float zmin, float zmax){
        this.xmax = xmax;
        this.xmin = xmin;
        this.ymin = ymin;
        this.ymax = ymax;
        this.zmin = zmin;
        this.zmax = zmax;
        buildBB();
    }
    
    public BoundingBox(List<SEVertex> vertecies){
        for(SEVertex tmp : vertecies){
            if(tmp.vX < xmin){
                xmin = tmp.vX;
            }
            else if(tmp.vX > xmax){
                xmax = tmp.vX;
            }
            if(tmp.vY < ymin){
                ymin = tmp.vY;
            }
            else if(tmp.vY > ymax){
                ymax = tmp.vY;
            }
            if(tmp.vZ < zmin){
                zmin = tmp.vZ;
            }
            else if(tmp.vZ > zmax){
                zmax = tmp.vZ;
            }
        }
        buildBB();
    }
    
    private void buildBB(){
        v1 = new Vector3d(xmin, ymin, zmin);
        v2 = new Vector3d(xmax, ymin, zmin);
        v3 = new Vector3d(xmax, ymin, zmax);
        v4 = new Vector3d(xmin, ymin, zmax);
        v5 = new Vector3d(xmin, ymax, zmin);
        v6 = new Vector3d(xmax, ymax, zmin);
        v7 = new Vector3d(xmax, ymax, zmax);
        v8 = new Vector3d(xmin, ymax, zmax);
    }
    
    public void setOrientation(SEOriantation ori){
        this.ori = ori;
    }
    
    public boolean intersect(BoundingBox bb){
        
        return false;
    }
  
    public boolean inside(Vector3d point, SEOriantation vecOri){
        return realInside(point, vecOri);
    }
    
    public boolean inside(Vector3d point){
        return realInside(point, new SEOriantation());
    }
    
    private boolean realInside(Vector3d point, SEOriantation vecOri){
        if(point.x+vecOri.getPositionVec().x >= xmin+ori.getPositionVec().x && point.x < xmax+ori.getPositionVec().x){
            return true;
        }
        if(point.y+vecOri.getPositionVec().y >= ymin+ori.getPositionVec().y && point.y < ymax+ori.getPositionVec().y+vecOri.getPositionVec().y){
            return true;
        }
        if(point.z+vecOri.getPositionVec().z >= zmin+ori.getPositionVec().z && point.z < zmax+ori.getPositionVec().z+vecOri.getPositionVec().z){
            return true;
        }
        return false;
    }
    
    public void debug(){
        /*glDisable(GL_TEXTURE);
        glDisable(GL_TEXTURE_2D);
        glColor4f(0, 1, 0, 0);
        glBegin(GL_LINES);
        glVertex3f(v1.x, v1.y, v1.z);
        glVertex3f(v2.x, v2.y, v2.z);
        glVertex3f(v1.x, v1.y, v1.z);
        glVertex3f(v3.x, v3.y, v3.z);
        glVertex3f(v1.x, v1.y, v1.z);
        glVertex3f(v4.x, v4.y, v4.z);
        glVertex3f(v1.x, v1.y, v1.z);
        glVertex3f(v5.x, v5.y, v5.z);
        glVertex3f(v1.x, v1.y, v1.z);
        glVertex3f(v6.x, v6.y, v6.z);
        glVertex3f(v1.x, v1.y, v1.z);
        glVertex3f(v7.x, v7.y, v7.z);
        glVertex3f(v1.x, v1.y, v1.z);
        glVertex3f(v8.x, v8.y, v8.z);
        
        glVertex3f(v2.x, v2.y, v2.z);
        glVertex3f(v3.x, v3.y, v3.z);
        glVertex3f(v2.x, v2.y, v2.z);
        glVertex3f(v4.x, v4.y, v4.z);
        glVertex3f(v2.x, v2.y, v2.z);
        glVertex3f(v5.x, v5.y, v5.z);
        glVertex3f(v2.x, v2.y, v2.z);
        glVertex3f(v6.x, v6.y, v6.z);
        glVertex3f(v2.x, v2.y, v2.z);
        glVertex3f(v7.x, v7.y, v7.z);
        glVertex3f(v2.x, v2.y, v2.z);
        glVertex3f(v8.x, v8.y, v8.z);
        
        glVertex3f(v3.x, v3.y, v3.z);
        glVertex3f(v4.x, v4.y, v4.z);
        glVertex3f(v3.x, v3.y, v3.z);
        glVertex3f(v5.x, v5.y, v5.z);
        glVertex3f(v3.x, v3.y, v3.z);
        glVertex3f(v6.x, v6.y, v6.z);
        glVertex3f(v3.x, v3.y, v3.z);
        glVertex3f(v7.x, v7.y, v7.z);
        glVertex3f(v3.x, v3.y, v3.z);
        glVertex3f(v8.x, v8.y, v8.z);
        
        glVertex3f(v4.x, v4.y, v4.z);
        glVertex3f(v5.x, v5.y, v5.z);
        glVertex3f(v4.x, v4.y, v4.z);
        glVertex3f(v6.x, v6.y, v6.z);
        glVertex3f(v4.x, v4.y, v4.z);
        glVertex3f(v7.x, v7.y, v7.z);
        glVertex3f(v4.x, v4.y, v4.z);
        glVertex3f(v8.x, v8.y, v8.z);
        
        glVertex3f(v5.x, v5.y, v5.z);
        glVertex3f(v6.x, v6.y, v6.z);
        glVertex3f(v5.x, v5.y, v5.z);
        glVertex3f(v7.x, v7.y, v7.z);
        glVertex3f(v5.x, v5.y, v5.z);
        glVertex3f(v8.x, v8.y, v8.z);
        
        glVertex3f(v6.x, v6.y, v6.z);
        glVertex3f(v7.x, v7.y, v7.z);
        glVertex3f(v6.x, v6.y, v6.z);
        glVertex3f(v8.x, v8.y, v8.z);
        
        glVertex3f(v7.x, v7.y, v7.z);
        glVertex3f(v8.x, v8.y, v8.z);
        glEnd();*/
    }
}
