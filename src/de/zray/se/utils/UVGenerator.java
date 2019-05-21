/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.utils;

import de.zray.se.graphics.semesh.Face;
import de.zray.se.graphics.semesh.MeshData;
import de.zray.se.graphics.semesh.Normal;
import de.zray.se.graphics.semesh.UV;
import de.zray.se.graphics.semesh.Vertex;
import java.util.ArrayList;
import org.joml.Vector3d;

/**
 *
 * @author vortex
 */
public class UVGenerator {
    public static enum Mapping{SPHERE, CYLINDER}
    
    public MeshData generateUVs(MeshData mData, Mapping mapping){
        ArrayList<Face> faces = mData.getFaces();
        ArrayList<Vertex> vertecies = mData.getVertecies();
        ArrayList<Normal> normals = mData.getNormals();
        ArrayList<UV> uvs = new ArrayList<>();
        
        for(Vertex v : vertecies){
            switch(mapping){
                case CYLINDER :
                        uvs.add(cylindircMapping(v));
                    break;
                case SPHERE :
                        uvs.add(sphereMapping(v));
                    break;
            }
        }
        
        for(Face f : faces){
            f.uv1 = f.v1;
            f.uv2 = f.v2;
            f.uv3 = f.v3;
        }
        
        return new MeshData(vertecies, uvs, normals, faces, mData.getAmature());
    }
    
    private UV cylindircMapping(Vertex vertex){
        Vector3d n = new Vector3d(vertex.vX, vertex.vY, vertex.vZ);
        n.normalize();
        double u = Math.atan2(n.x, n.z) / 2*Math.PI + .5;
        double v = n.y * .5 +.5;
        return new UV(v, u);
    }
    
    private UV sphereMapping(Vertex vertex){
        Vector3d n = new Vector3d(vertex.vX, vertex.vY, vertex.vZ);
        n.normalize();
        double u = Math.asin(n.x)/Math.PI + 0.5;
        double v = Math.asin(n.y)/Math.PI + 0.5;
        
        return new UV(v, u);
    }
}
