/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics.modelloader.OBJLoader;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vortex Acherontic
 */
public class OBJGroup {
    
    public class Vertex{
        float x, y, z;
        public Vertex(float x, float y, float z){
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
    public class Normal{
        float x, y, z;
        public Normal(float x, float y, float z){
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
    public class UV{
        float u, v;
        public UV(float u, float v){
            this.u = u;
            this.v = v;
        }
    }
    public class Face{
        int v1, v2, v3;
        int n1, n2, n3;
        int uv1, uv2, uv3;
        
        public Face(int verts[], int uvs[], int normals[]) throws ArrayIndexOutOfBoundsException{
            v1 = verts[0];
            v2 = verts[1];
            v3 = verts[2];
            uv1 = uvs[0];
            uv2 = uvs[1];
            uv3 = uvs[2];
            n1 = normals[0];
            n2 = normals[1];
            n3 = normals[2];
            
        }
    }
    
    String name = "";
    List<Vertex> verts = new ArrayList<>();
    List<Normal> normlas = new ArrayList<>();
    List<UV> uvs = new ArrayList<>();
    List<Face> faces = new ArrayList<>();
    
    public void addVertex(float x, float y, float z){
        verts.add(new Vertex(x, y, z));
    }
    public void addNormal(float x, float y, float z){
        normlas.add(new Normal(x, y, z));
    }
    public void addUV(float u, float v){
        uvs.add(new UV(u, v));
    }
    public void addFace(int v1, int v2, int v3, int uv1, int uv2, int uv3, int n1, int n2, int n3){
        faces.add(new Face(new int[]{v1, v2, v3}, new int[]{uv1, uv2, uv3}, new int[]{n1, n2, n3}));
    }
}
