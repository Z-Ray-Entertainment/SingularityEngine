/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics.shapes;

import de.zray.se.grapics.semesh.SEMaterial;
import de.zray.se.grapics.semesh.SEFace;
import de.zray.se.grapics.semesh.SEMesh;
import de.zray.se.grapics.semesh.SENormal;
import de.zray.se.grapics.semesh.SEUV;
import de.zray.se.grapics.semesh.SEVertex;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vortex
 */
public class Cube implements SEMeshProvider{
    private SEMesh cube;
    
    public Cube(float w, float h, float d, SEMaterial material){
        List<SEVertex> v = new ArrayList<>();
        List<SEUV> uvs = new ArrayList<>();
        List<SENormal> normlas = new ArrayList<>();
        List<SEFace> f = new ArrayList<>();

        v.add(new SEVertex(w, -h, -d));
        v.add(new SEVertex(w, -h, d));
        v.add(new SEVertex(-w, -h, d));
        v.add(new SEVertex(-w, -h, -d));
        v.add(new SEVertex(w, h, -d));
        v.add(new SEVertex(w, h, d));
        v.add(new SEVertex(-w, h, d));
        v.add(new SEVertex(-w, h, -d));
        
        uvs.add(new SEUV(.25f, .3333f));
        uvs.add(new SEUV(.5f, 0f));
        uvs.add(new SEUV(.25f, 0f));
        uvs.add(new SEUV(.5f, 1f));
        uvs.add(new SEUV(.25f, .6666f));
        uvs.add(new SEUV(.25f, 1f));
        uvs.add(new SEUV(0f, .6666f));
        uvs.add(new SEUV(0f, .3333f));
        uvs.add(new SEUV(.5f, .3333f));
        uvs.add(new SEUV(.75f, .6666f));
        uvs.add(new SEUV(.75f, .3333f));
        uvs.add(new SEUV(1f, .3333f));
        uvs.add(new SEUV(1f, .6666f));
        uvs.add(new SEUV(.5f, .6666f));
        
        normlas.add(new SENormal(0, -1, 0));
        normlas.add(new SENormal(0, 1, 0));
        normlas.add(new SENormal(1, 0, 0));
        normlas.add(new SENormal(0, 0, 1));
        normlas.add(new SENormal(-1, 0, 0));
        normlas.add(new SENormal(0, 0, -1));
        
        f.add(new SEFace(1, 3, 0, 0, 1, 2, 0, 0, 0));
        f.add(new SEFace(7, 5, 4, 3, 4, 5, 1, 1, 1));
        f.add(new SEFace(4, 1, 0, 6, 0, 7, 2, 2, 2));
        f.add(new SEFace(5, 2, 1, 4, 8, 3, 3, 3, 3));
        f.add(new SEFace(2, 7, 3, 8, 9, 10, 4, 4, 4));
        f.add(new SEFace(0, 7, 4, 11, 9, 12, 5, 5, 5));
        f.add(new SEFace(1, 2, 3, 0, 8, 1, 0, 0, 0));
        f.add(new SEFace(7, 6, 5, 3, 13, 4, 1, 1, 1));
        f.add(new SEFace(4, 5, 1, 6, 4, 0, 2, 2, 2));
        f.add(new SEFace(5, 6, 2, 4, 13, 8, 3, 3, 3));
        f.add(new SEFace(2, 6, 7, 8, 13, 9, 5, 5, 5));
        
        cube = new SEMesh(v, uvs, normlas, f, null, material);
    }
    
    @Override
    public SEMesh getSEMesh() {
        return cube;
    }
    
}
