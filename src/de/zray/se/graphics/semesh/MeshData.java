/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.semesh;

import de.zray.se.logger.SELogger;
import java.util.ArrayList;

/**
 *
 * @author vortex
 */
public class MeshData {
    private ArrayList<Vertex> vertecies = new ArrayList<>();
    private ArrayList<UV> uvs = new ArrayList<>();
    private ArrayList<Normal> normals = new ArrayList<>();
    private ArrayList<Face> faces = new ArrayList<>();
    private Amature amature = new Amature();
    private boolean cleared = false;
    
    public MeshData(ArrayList<Vertex> vertexList, ArrayList<UV> uvs, ArrayList<Normal> normals, ArrayList<Face> faceList, Amature amature){
        this.amature = amature;
        this.faces = faceList;
        
        this.vertecies = vertexList;
        this.uvs = uvs;
        this.normals = normals;
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
    
     public ArrayList<Face> getFaces(){
        return faces;
    }
    
    public ArrayList<Vertex> getVertecies(){
        return vertecies;
    }
    
    public ArrayList<UV> getUVs(){
        return uvs;
    }
    
    public ArrayList<Normal> getNormals(){
        return normals;
    }
    
    public Amature getAmature(){
        return amature;
    }
}
