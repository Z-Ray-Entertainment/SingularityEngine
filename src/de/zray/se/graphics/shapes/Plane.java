/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.shapes;

import de.zray.se.graphics.semesh.SEMaterial;
import de.zray.se.graphics.semesh.SEFace;
import de.zray.se.graphics.semesh.SEMesh;
import de.zray.se.graphics.semesh.SEMeshData;
import de.zray.se.graphics.semesh.SENormal;
import de.zray.se.graphics.semesh.SEUV;
import de.zray.se.graphics.semesh.SEVertex;
import java.util.ArrayList;
import java.util.List;
import de.zray.se.storages.AssetLibrary;


/**
 *
 * @author vortex
 */
public class Plane implements SEMeshProvider{
    private SEMesh plane;
    
    public Plane(float width, float height, boolean backfaceculling){
        List<SEVertex> vertecies = new ArrayList();
        List<SEUV> uvs = new ArrayList<>();
        List<SENormal> normals = new ArrayList<>();
        List<SEFace> faces = new ArrayList();
        
        vertecies.add(new SEVertex(-width, 0, height));
        vertecies.add(new SEVertex(width, 0, height));
        vertecies.add(new SEVertex(width, 0, -height));
        
        vertecies.add(new SEVertex(-width, 0, height));
        vertecies.add(new SEVertex(width, 0, -height));
        vertecies.add(new SEVertex(-width, 0, -height));
        
        uvs.add(new SEUV(0, 0));
        uvs.add(new SEUV(1, 0));
        uvs.add(new SEUV(1, 1));
        uvs.add(new SEUV(0, 1));
        
        normals.add(new SENormal(0, 1, 0));
        
        faces.add(new SEFace(0, 1, 2, 0, 1, 2, 0, 0, 0));
        faces.add(new SEFace(3, 4, 5, 0, 2, 3, 0, 0, 0));
        
        SEMeshData mData = new SEMeshData(vertecies, uvs, normals, faces, null);
        int mDataID = AssetLibrary.get().addMesh(mData);
        plane = new SEMesh(new SEMaterial(), mDataID);
    }

    @Override
    public SEMesh getSEMesh(){
        return plane;
    }
}
