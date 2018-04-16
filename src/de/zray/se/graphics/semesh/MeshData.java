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
public class MeshData {
    private List<Vertex> vertecies = new LinkedList<>();
    private List<UV> uvs = new LinkedList<>();
    private List<Normal> normals = new LinkedList<>();
    private List<Face> faces = new LinkedList<>();
    private Amature amature = new Amature();
    private boolean cleared = false;
    private BoundingBox bb;
    
    public MeshData(List<Vertex> vertexList, List<UV> uvs, List<Normal> normals, List<Face> faceList, Amature amature){
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
    
     public List<Face> getFaces(){
        return faces;
    }
    
    public List<Vertex> getVertecies(){
        return vertecies;
    }
    
    public List<UV> getUVs(){
        return uvs;
    }
    
    public List<Normal> getNormals(){
        return normals;
    }
}
