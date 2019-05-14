/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.semesh;

import de.zray.se.graphics.Camera;
import java.util.LinkedList;
import java.util.List;
import javax.vecmath.Vector3f;

/**
 *
 * @author vortex
 */
public class Mesh{
    public static enum RenderMode{DIRECT, DISPLAY_LIST, VBO}
    public static enum DisplayMode{SOLID, WIRED, DOTS};
    
    private int renderData = -1;
    private RenderMode renderMode = RenderMode.DIRECT;
    private DisplayMode displayMode = DisplayMode.SOLID;
    private Orientation offset = new Orientation(null);
    private Material material = new Material();
    private boolean isAnimated = false;
    private Mesh lod;
    private int meshData = -1;
    private Vector3f direction;
    private float renderDist = -1;
    private BoundingBox bb;
    
    public Mesh(Material material, int seMeshData){
        this.material = material;
        offset = new Orientation(null);
        meshData = seMeshData;
        this.direction = new Vector3f(0, 0, -1);
    }
    
    public int getSEMeshData(){
        return meshData;
    }
    
    public void setMeshData(int mData){
        this.meshData = mData;
    }
    
    public void setRenderData(int index){
        this.renderData = index;
    }
    
    public int getRenderData(){
        return renderData;
    }
    
    /**
     * The distance until when this mesh is visible.
     * @param renderDist 
     */
    public void setRenderDist(float renderDist){
        this.renderDist = renderDist;
    }
    
    public void setDirection(Vector3f direction){
        this.direction = direction;
    }
    
    public Vector3f getDirection(){
        return direction;
    }
    
    public void setMaterial(Material mat){
        this.material = mat;
    }
    
    public Material getMaterial(){
        return material;
    }
    
    public void setRenderMode(RenderMode rMode){
        this.renderMode = rMode;
    }
    
    public RenderMode getRenderMode(){
        return renderMode;
    }
        
    public void setLOD(Mesh mesh){
        lod = mesh;
    }
    
    public void setOrientation(Orientation offset){
        this.offset = offset;
    }
    
    public Orientation getOffset(){
        return offset;
    }
    
    public void setDisplayMode(DisplayMode displayMode){
        this.displayMode = displayMode;
    }
    
    public DisplayMode getDisplayMode(){
        return displayMode;
    }
    
    /**
     * Returns only visible SEMEshes and visible LODs and submeshes
     * @param activeCam the cmarera which is used to determine the ditance to the player
     * @return visible Meshes
     */
    public Mesh getMeshOrLOD(Camera activeCam){
        if(activeCam.isVisable(offset.getPositionVec())){
            double distanceToCamera = activeCam.getDistance(offset.getPositionVec());
            if(distanceToCamera < renderDist && renderDist > 0){
                return this;
            } else if (renderDist > 0) {
                if(lod != null){
                    return lod.getMeshOrLOD(activeCam);
                }
            } else {
                return this;
            }
        }
        return null;
    }
}
