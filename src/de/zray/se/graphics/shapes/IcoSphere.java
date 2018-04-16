/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.shapes;

import de.zray.se.generators.VoronoiCracle;
import de.zray.se.graphics.semesh.SEFace;
import de.zray.se.graphics.semesh.SEMaterial;
import de.zray.se.graphics.semesh.SEMesh;
import de.zray.se.graphics.semesh.SEMeshData;
import de.zray.se.graphics.semesh.SENormal;
import de.zray.se.graphics.semesh.SEUV;
import de.zray.se.graphics.semesh.SEVertex;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Vector3f;
import de.zray.se.storages.AssetLibrary;

/**
 *
 * @author Vortex Acherontic
 */
public class IcoSphere implements SEMeshProvider{
    private SEMeshData icoSphere;
    private float lengthScale = 1;
    
    public IcoSphere(int subDiv){
        generate(subDiv, false, 0, 0, 0);
    }
    
    public IcoSphere(int subDiv, boolean deform, long seed, float scale, float freq){
        generate(subDiv, deform, seed, scale, freq);
    }
    
    private void generate(int subDiv, boolean deform, long seed, float scale, float freq){
        float tau = (1f+(float)Math.sqrt(5f))/2f;
        List<SEVertex> vertex = new ArrayList<>();
        List<SENormal> normals = new ArrayList<>();
        List<SEUV> uvs = new ArrayList<>();
        List<SEFace> faces = new ArrayList<>();
        
        vertex.add(setOnUnitSphere(new SEVertex(-1, tau, 0)));
        vertex.add(setOnUnitSphere(new SEVertex(1, tau, 0)));
        vertex.add(setOnUnitSphere(new SEVertex(-1, -tau, 0)));
        vertex.add(setOnUnitSphere(new SEVertex(1, -tau, 0)));
        
        vertex.add(setOnUnitSphere(new SEVertex(0, -1, tau)));
        vertex.add(setOnUnitSphere(new SEVertex(0, 1, tau)));
        vertex.add(setOnUnitSphere(new SEVertex(0, -1, -tau)));
        vertex.add(setOnUnitSphere(new SEVertex(0, 1, -tau)));

        vertex.add(setOnUnitSphere(new SEVertex(tau, 0, -1)));
        vertex.add(setOnUnitSphere(new SEVertex(tau, 0, 1)));
        vertex.add(setOnUnitSphere(new SEVertex(-tau, 0, -1)));
        vertex.add(setOnUnitSphere(new SEVertex(-tau, 0, 1)));
        
        //DO TO
        for(SEVertex tmp : vertex){
            normals.add(calcNormal(tmp));
        }
        
        uvs.add(new SEUV(0, 0));
        
        faces.add(new SEFace(0, 11, 5, 0, 0, 0, 0, 11, 5));
        faces.add(new SEFace(0, 5, 1, 0, 0, 0, 0, 5, 1));
        faces.add(new SEFace(0, 1, 7, 0, 0, 0, 0, 1, 7));
        faces.add(new SEFace(0, 7, 10, 0, 0, 0, 0, 7, 10));
        faces.add(new SEFace(0, 10, 11, 0, 0, 0, 0, 10, 11));
        
        faces.add(new SEFace(1, 5, 9, 0, 0, 0, 0, 0, 0));
        faces.add(new SEFace(5, 11, 4, 0, 0, 0, 0, 0, 0));
        faces.add(new SEFace(11, 10, 2, 0, 0, 0, 0, 0, 0));
        faces.add(new SEFace(10, 7, 6, 0, 0, 0, 0, 0, 0));
        faces.add(new SEFace(7, 1, 8, 0, 0, 0, 0, 0, 0));
        
        faces.add(new SEFace(3, 9, 4, 0, 0, 0, 0, 0, 0));
        faces.add(new SEFace(3, 4, 2, 0, 0, 0, 0, 0, 0));
        faces.add(new SEFace(3, 2, 6, 0, 0, 0, 0, 0, 0));
        faces.add(new SEFace(3, 6, 8, 0, 0, 0, 0, 0, 0));
        faces.add(new SEFace(3, 8, 9, 0, 0, 0, 0, 0, 0));
        
        faces.add(new SEFace(4, 9, 5, 0, 0, 0, 0, 0, 0));
        faces.add(new SEFace(2, 4, 11, 0, 0, 0, 0, 0, 0));
        faces.add(new SEFace(6, 2, 10, 0, 0, 0, 0, 0, 0));
        faces.add(new SEFace(8, 6, 7, 0, 0, 0, 0, 0, 0));
        faces.add(new SEFace(9, 8, 1, 0, 0, 0, 0, 0, 0));
        
        SEMeshData mData = new SEMeshData(vertex, uvs, normals, faces, null);
        icoSphere = subdevide(subDiv, mData);
        
        if(deform){
            for(int i = 0; i < mData.getVertecies().size(); i++){
                mData.getVertecies().set(i, deform(mData.getVertecies().get(i), seed, scale, freq));
            }
        }
    }
    
    private SEMeshData subdevide(int iterations, SEMeshData mesh){
        SEMeshData tmpMeshData = mesh;
        
        for(int i = 0; i < iterations; i++){
            tmpMeshData = iterate(tmpMeshData);
        }
        
        return tmpMeshData;
    }
    
    private SENormal calcNormal(SEVertex v){
        Vector3f n = new Vector3f(v.vX, v.vY, v.vZ);
        n.normalize();
        return new SENormal(n.x, n.y, n.z);
    }
    
    private SEMeshData iterate(SEMeshData mesh){
        List<SEVertex> v = new ArrayList<>();
        List<SEFace> f = new ArrayList<>();
        List<SEUV> uv = new ArrayList<>();
        List<SENormal> n = new ArrayList<>();
        
        for(SEFace tmp : mesh.getFaces()){
            SEVertex a = getMiddle(mesh.getVertecies().get(tmp.v1), mesh.getVertecies().get(tmp.v2));
            SEVertex b = getMiddle(mesh.getVertecies().get(tmp.v2), mesh.getVertecies().get(tmp.v3));
            SEVertex c = getMiddle(mesh.getVertecies().get(tmp.v3), mesh.getVertecies().get(tmp.v1));

            v.add(mesh.getVertecies().get(tmp.v1));
            int iv1 = v.size()-1;
            v.add(mesh.getVertecies().get(tmp.v2));
            int iv2 = v.size()-1;
            v.add(mesh.getVertecies().get(tmp.v3));
            int iv3 = v.size()-1;
            
            v.add(a);
            int ia = v.size()-1;
            v.add(b);
            int ib = v.size()-1;
            v.add(c);
            int ic = v.size()-1;

             //DO To
            uv.add(new SEUV(0, 0));
            n.add(calcNormal(mesh.getVertecies().get(tmp.v1)));
            n.add(calcNormal(mesh.getVertecies().get(tmp.v2)));
            n.add(calcNormal(mesh.getVertecies().get(tmp.v3)));
            n.add(calcNormal(a));
            n.add(calcNormal(a));
            n.add(calcNormal(a));

            f.add(new SEFace(iv1, ia, ic, 0, 0, 0, iv1, ia, ib));
            f.add(new SEFace(iv2, ib, ia, 0, 0, 0, iv2, ib, ia));
            f.add(new SEFace(iv3, ic, ib, 0, 0, 0, iv3, ic, ib));
            f.add(new SEFace(ia, ib, ic, 0, 0, 0, ia, ib, ic));
        }
        return new SEMeshData(v, uv, n, f, null);
    }
    
    private SEVertex getMiddle(SEVertex v1, SEVertex v2){
        return setOnUnitSphere(new SEVertex((v1.vX+v2.vX)/2f, (v1.vY+v2.vY)/2f, (v1.vZ+v2.vZ)/2f));
    }
    
    private SEVertex setOnUnitSphere(SEVertex v){
        float lenght = (float) Math.sqrt(v.vX*v.vX + v.vY*v.vY + v.vZ*v.vZ)*lengthScale;
        return new SEVertex(v.vX/lenght, v.vY/lenght, v.vZ/lenght);
    }
    
    private SEVertex deform(SEVertex v, long seed, float scale, float frequenzy){
        VoronoiCracle cracle = new VoronoiCracle(seed, (short) 1);
        lengthScale = (float) cracle.noise(v.vX, v.vY, v.vZ, frequenzy)*scale;
        return setOnUnitSphere(v);
    }
    
    @Override
    public SEMesh getSEMesh() {
        int mDataID = AssetLibrary.get().addMesh(icoSphere);
        return new SEMesh(new SEMaterial(), mDataID);
    }
    
}