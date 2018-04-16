/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.semesh;

import de.zray.se.logger.SELogger;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author vortex
 */
public class SEMeshData {
    private List<SEVertex> vertecies = new LinkedList<>();
    private List<SEUV> uvs = new LinkedList<>();
    private List<SENormal> normals = new LinkedList<>();
    private List<SEFace> faces = new LinkedList<>();
    private SEAmature amature = new SEAmature();
    private boolean cleared = false;
    private BoundingBox bb;
    
    public SEMeshData(List<SEVertex> vertexList, List<SEUV> uvs, List<SENormal> normals, List<SEFace> faceList, SEAmature amature){
        this.amature = amature;
        this.faces = faceList;
        
        this.vertecies = vertexList;
        this.uvs = uvs;
        this.normals = normals;
        this.bb = new BoundingBox(vertecies);
    }
    
    public void printMeshData(){
        String lines[] = {"SEMESH: ",
            "Vertex: "+vertecies.size(), "UVs: "+uvs.size(), 
            "Normals: "+normals.size(), "Faces: "+faces.size()};
        SELogger.get().dispatchMsg("SEMesh", SELogger.SELogType.INFO, lines, false);
    }
    
    public void clear(){
        vertecies.clear();
        faces.clear();
        normals.clear();
        uvs.clear();
        cleared = true;
    }
    
    public boolean isCleared(){
        return cleared;
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
}
