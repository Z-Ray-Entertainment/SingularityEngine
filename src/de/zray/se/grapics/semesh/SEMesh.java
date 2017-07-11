/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics.semesh;

import de.zray.se.grapics.Camera;
import de.zray.se.logger.SELogger;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.vecmath.Vector3f;

/**
 *
 * @author vortex
 */
public class SEMesh{
    public static enum RenderMode{DIRECT, DISPLAY_LIST, VBO}
    public static enum DisplayMode{SOLID, WIRED, DOTS};
    
    private int renderData = -1;
    private RenderMode renderMode = RenderMode.DIRECT;
    private DisplayMode displayMode = DisplayMode.SOLID;
    private List<SEVertex> vertecies = new LinkedList<>();
    private List<SEUV> uvs = new LinkedList<>();
    private List<SENormal> normals = new LinkedList<>();
    private List<SEFace> faces = new LinkedList<>();
    private SEAmature amature = new SEAmature();
    private SEOriantation orientation = new SEOriantation();
    private SEMaterial material = new SEMaterial();
    private boolean isAnimated = false;
    private SEMesh lod;
    private List<SEMesh> subMeshes = new LinkedList<>();
    public UUID uuid = UUID.randomUUID();
    private BoundingBox bb;
    private Vector3f direction;
    private float renderDist = -1;
    
    public SEMesh(List<SEVertex> vertexList, List<SEUV> uvs, List<SENormal> normals, List<SEFace> faceList, SEAmature amature, SEMaterial material){
        this.amature = amature;
        this.faces = faceList;
        this.material = material;
        this.vertecies = vertexList;
        this.uvs = uvs;
        this.normals = normals;
        orientation = new SEOriantation();
        this.bb = new BoundingBox(vertecies);
        this.direction = new Vector3f(0, 0, -1);
    }
    
    public void setRenderData(int index){
        this.renderData = index;
    }
    
    public int getRenderData(){
        return renderData;
    }
    
    public void printMeshData(){
        String lines[] = {"SEMESH: "+uuid.toString(),
            "Vertex: "+vertecies.size(), "UVs: "+uvs.size(), 
            "Normals: "+normals.size(), "Faces: "+faces.size()};
        SELogger.get().dispatchMsg("SEMesh", SELogger.SELogType.INFO, lines, false);
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
    
    public List<SEFace> getFaces(){
        return faces;
    }
    
    public List<SEVertex> getVertecies(){
        return vertecies;
    }
    
    public List<SEUV> getUVs(){
        return uvs;
    }
    
    public List<SENormal> getNormals(){
        return normals;
    }
    
    public void addSubMesh(SEMesh subMesh){
        subMeshes.add(subMesh);
    }
    
    public void setMaterial(SEMaterial mat){
        this.material = mat;
    }
    
    public SEMaterial getMaterial(){
        return material;
    }
    
    public void setRenderMode(RenderMode rMode){
        this.renderMode = rMode;
    }
    
    public RenderMode getRenderMode(){
        return renderMode;
    }
        
    public void addLOD(SEMesh mesh){
        mesh.setOrientation(orientation);
        SEMesh freeLOD = lod;
        while(freeLOD != null){
            freeLOD = freeLOD.lod;
        }
        freeLOD = mesh;
    }
    
    public void setOrientation(SEOriantation orientation){
        this.orientation = orientation;
    }
    
    public SEOriantation getOrientation(){
        return orientation;
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
        
        float endX = (float) getOrientation().getPositionVec().x;
        float endY = (float) getOrientation().getPositionVec().y;
        float endZ = (float) getOrientation().getPositionVec().z;

        Vector3f distVec = new Vector3f(endX - startX, endY - startY, endZ - startZ);
        float distance = distVec.length();
        
        return (distance < renderDist);
    }
    
    /**
     * Returns only visible SEMEshes and visible LODs and submeshes
     * @param activeCam the cmarera which is used to determine the ditance to the player
     * @return visible Meshes
     */
    public List<SEMesh> getRendableMeshes(Camera activeCam){
        List<SEMesh> rMeshes = new LinkedList<>();
        if(inView(activeCam)){
            rMeshes.add(this);
            System.out.println("Added rendable Mesh");
            for(SEMesh sub : subMeshes){
                List<SEMesh> rendableSubMeshes = sub.getRendableMeshes(activeCam);
                if(rendableSubMeshes != null){
                    for(SEMesh tmp : rendableSubMeshes){
                        if(tmp != null){
                            rMeshes.add(tmp);
                            System.out.println("Added rendable Mesh");
                        }
                    }
                }
            }
            return rMeshes;
        }
        else {
            SEMesh currentdLOD = lod;
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
