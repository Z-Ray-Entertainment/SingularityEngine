/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.shapes;

import de.zray.se.graphics.semesh.Material;
import de.zray.se.graphics.semesh.Face;
import de.zray.se.graphics.semesh.Mesh;
import de.zray.se.graphics.semesh.MeshData;
import de.zray.se.graphics.semesh.Normal;
import de.zray.se.graphics.semesh.UV;
import de.zray.se.graphics.semesh.Vertex;
import java.util.ArrayList;
import de.zray.se.storages.AssetLibrary;


/**
 *
 * @author vortex
 */
public class Plane implements SEMeshProvider{
    private Mesh plane;
    
    public Plane(float width, float height, boolean backfaceculling){
        ArrayList<Vertex> vertecies = new ArrayList();
        ArrayList<UV> uvs = new ArrayList<>();
        ArrayList<Normal> normals = new ArrayList<>();
        ArrayList<Face> faces = new ArrayList();
        
        vertecies.add(new Vertex(-width, 0, height));
        vertecies.add(new Vertex(width, 0, height));
        vertecies.add(new Vertex(width, 0, -height));
        
        vertecies.add(new Vertex(-width, 0, height));
        vertecies.add(new Vertex(width, 0, -height));
        vertecies.add(new Vertex(-width, 0, -height));
        
        uvs.add(new UV(0, 0));
        uvs.add(new UV(1, 0));
        uvs.add(new UV(1, 1));
        uvs.add(new UV(0, 1));
        
        normals.add(new Normal(0, 1, 0));
        
        faces.add(new Face(0, 1, 2, 0, 1, 2, 0, 0, 0));
        faces.add(new Face(3, 4, 5, 0, 2, 3, 0, 0, 0));
        
        MeshData mData = new MeshData(vertecies, uvs, normals, faces, null);
        int mDataID = AssetLibrary.get().addMesh(mData);
        plane = new Mesh(new Material(), mDataID);
    }

    @Override
    public Mesh getSEMesh(){
        return plane;
    }
}
