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
public class Cube implements SEMeshProvider{
    private Mesh cube;
    
    public Cube(float w, float h, float d, Material material){
        ArrayList<Vertex> v = new ArrayList<>();
        ArrayList<UV> uvs = new ArrayList<>();
        ArrayList<Normal> normlas = new ArrayList<>();
        ArrayList<Face> f = new ArrayList<>();

        v.add(new Vertex(w, -h, -d));
        v.add(new Vertex(w, -h, d));
        v.add(new Vertex(-w, -h, d));
        v.add(new Vertex(-w, -h, -d));
        v.add(new Vertex(w, h, -d));
        v.add(new Vertex(w, h, d));
        v.add(new Vertex(-w, h, d));
        v.add(new Vertex(-w, h, -d));
        
        uvs.add(new UV(.25f, .3333f));
        uvs.add(new UV(.5f, 0f));
        uvs.add(new UV(.25f, 0f));
        uvs.add(new UV(.5f, 1f));
        uvs.add(new UV(.25f, .6666f));
        uvs.add(new UV(.25f, 1f));
        uvs.add(new UV(0f, .6666f));
        uvs.add(new UV(0f, .3333f));
        uvs.add(new UV(.5f, .3333f));
        uvs.add(new UV(.75f, .6666f));
        uvs.add(new UV(.75f, .3333f));
        uvs.add(new UV(1f, .3333f));
        uvs.add(new UV(1f, .6666f));
        uvs.add(new UV(.5f, .6666f));
        
        normlas.add(new Normal(0, -1, 0));
        normlas.add(new Normal(0, 1, 0));
        normlas.add(new Normal(1, 0, 0));
        normlas.add(new Normal(0, 0, 1));
        normlas.add(new Normal(-1, 0, 0));
        normlas.add(new Normal(0, 0, -1));
        
        f.add(new Face(1, 3, 0, 0, 1, 2, 0, 0, 0));
        f.add(new Face(7, 5, 4, 3, 4, 5, 1, 1, 1));
        f.add(new Face(4, 1, 0, 6, 0, 7, 2, 2, 2));
        f.add(new Face(5, 2, 1, 4, 8, 3, 3, 3, 3));
        f.add(new Face(2, 7, 3, 8, 9, 10, 4, 4, 4));
        f.add(new Face(0, 7, 4, 11, 9, 12, 5, 5, 5));
        f.add(new Face(1, 2, 3, 0, 8, 1, 0, 0, 0));
        f.add(new Face(7, 6, 5, 3, 13, 4, 1, 1, 1));
        f.add(new Face(4, 5, 1, 6, 4, 0, 2, 2, 2));
        f.add(new Face(5, 6, 2, 4, 13, 8, 3, 3, 3));
        f.add(new Face(2, 6, 7, 8, 13, 9, 5, 5, 5));
        
        MeshData mData = new MeshData(v, uvs, normlas, f, null);
        int mDataID = AssetLibrary.get().addMesh(mData);
        cube = new Mesh(material, mDataID);
    }
    
    @Override
    public Mesh getSEMesh() {
        return cube;
    }
    
}
