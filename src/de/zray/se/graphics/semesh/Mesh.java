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
    private List<Mesh> subMeshes = new LinkedList<>();
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
    
    public void setRenderDist(float renderDist){
        this.renderDist = renderDist;
    }
    
    public void setDirection(Vector3f direction){
        this.direction = direction;
    }
    
    public Vector3f getDirection(){
        return direction;
    }
    
    public void addSubMesh(Mesh subMesh){
        subMeshes.add(subMesh);
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
        
    public void addLOD(Mesh mesh){
        mesh.setOrientation(offset);
        Mesh freeLOD = lod;
        while(freeLOD != null){
            freeLOD = freeLOD.lod;
        }
        freeLOD = mesh;
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
    
    /*
    Determine if the currentd Mesh is in the view cone of the active camera and
    within the render distance.
    */
    public boolean inView(Camera activeCam){
        /*Camera cam = MainThread.getCurrentWorld().getGLModule().getCurrentCamera();
        Vector3f posCam = new Vector3f(cam.getPosition().x, cam.getPosition().y, cam.getPosition().z);
        Vector3f posMesh = orientation.getPositionVec();
        float dist = SEUtils.getLenght(SEUtils.getVector(posMesh, posCam));
        return (renderDist == -1 || dist < renderDist);*/
        float startX = 0;
        float startY = 0;
        float startZ = 0;
        if(activeCam != null){
            startX = activeCam.getPosition().x;
            startY = activeCam.getPosition().y;
            startZ = activeCam.getPosition().z;
        }
        
        float endX = (float) getOffset().getPositionVec().x;
        float endY = (float) getOffset().getPositionVec().y;
        float endZ = (float) getOffset().getPositionVec().z;

        Vector3f distVec = new Vector3f(endX - startX, endY - startY, endZ - startZ);
        float distance = distVec.length();
        
        return (distance < renderDist) || (renderDist == -1);
    }
    
    /**
     * Returns only visible SEMEshes and visible LODs and submeshes
     * @param activeCam the cmarera which is used to determine the ditance to the player
     * @return visible Meshes
     */
    public List<Mesh> getRendableMeshes(Camera activeCam){
        List<Mesh> rMeshes = new LinkedList<>();
        if(inView(activeCam)){
            rMeshes.add(this);
            for(Mesh sub : subMeshes){
                List<Mesh> rendableSubMeshes = sub.getRendableMeshes(activeCam);
                if(rendableSubMeshes != null){
                    for(Mesh tmp : rendableSubMeshes){
                        if(tmp != null){
                            rMeshes.add(tmp);
                        }
                    }
                }
            }
            return rMeshes;
        }
        else {
            Mesh currentdLOD = lod;
            while(currentdLOD != null && !currentdLOD.inView(activeCam)){
                currentdLOD = currentdLOD.lod;
            }
            if(currentdLOD != null && currentdLOD.inView(activeCam)){
                rMeshes.add(currentdLOD);
                return rMeshes;
            }
        }
        return null;
    }
}
