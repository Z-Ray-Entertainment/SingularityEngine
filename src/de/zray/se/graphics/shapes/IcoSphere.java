/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.shapes;

import de.zray.se.generators.VoronoiCracle;
import de.zray.se.graphics.semesh.Face;
import de.zray.se.graphics.semesh.Material;
import de.zray.se.graphics.semesh.Mesh;
import de.zray.se.graphics.semesh.MeshData;
import de.zray.se.graphics.semesh.Normal;
import de.zray.se.graphics.semesh.UV;
import de.zray.se.graphics.semesh.Vertex;
import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Vector3f;
import de.zray.se.storages.AssetLibrary;

/**
 *
 * @author Vortex Acherontic
 */
public class IcoSphere implements SEMeshProvider{
    private MeshData icoSphere;
    private float lengthScale = 1;
    
    public IcoSphere(int subDiv){
        generate(subDiv, false, 0, 0, 0);
    }
    
    public IcoSphere(int subDiv, boolean deform, long seed, float scale, float freq){
        generate(subDiv, deform, seed, scale, freq);
    }
    
    private void generate(int subDiv, boolean deform, long seed, float scale, float freq){
        float tau = (1f+(float)Math.sqrt(5f))/2f;
        List<Vertex> vertex = new ArrayList<>();
        List<Normal> normals = new ArrayList<>();
        List<UV> uvs = new ArrayList<>();
        List<Face> faces = new ArrayList<>();
        
        vertex.add(setOnUnitSphere(new Vertex(-1, tau, 0)));
        vertex.add(setOnUnitSphere(new Vertex(1, tau, 0)));
        vertex.add(setOnUnitSphere(new Vertex(-1, -tau, 0)));
        vertex.add(setOnUnitSphere(new Vertex(1, -tau, 0)));
        
        vertex.add(setOnUnitSphere(new Vertex(0, -1, tau)));
        vertex.add(setOnUnitSphere(new Vertex(0, 1, tau)));
        vertex.add(setOnUnitSphere(new Vertex(0, -1, -tau)));
        vertex.add(setOnUnitSphere(new Vertex(0, 1, -tau)));

        vertex.add(setOnUnitSphere(new Vertex(tau, 0, -1)));
        vertex.add(setOnUnitSphere(new Vertex(tau, 0, 1)));
        vertex.add(setOnUnitSphere(new Vertex(-tau, 0, -1)));
        vertex.add(setOnUnitSphere(new Vertex(-tau, 0, 1)));
        
        //DO TO
        for(Vertex tmp : vertex){
            normals.add(calcNormal(tmp));
        }
        
        uvs.add(new UV(0, 0));
        
        faces.add(new Face(0, 11, 5, 0, 0, 0, 0, 11, 5));
        faces.add(new Face(0, 5, 1, 0, 0, 0, 0, 5, 1));
        faces.add(new Face(0, 1, 7, 0, 0, 0, 0, 1, 7));
        faces.add(new Face(0, 7, 10, 0, 0, 0, 0, 7, 10));
        faces.add(new Face(0, 10, 11, 0, 0, 0, 0, 10, 11));
        
        faces.add(new Face(1, 5, 9, 0, 0, 0, 0, 0, 0));
        faces.add(new Face(5, 11, 4, 0, 0, 0, 0, 0, 0));
        faces.add(new Face(11, 10, 2, 0, 0, 0, 0, 0, 0));
        faces.add(new Face(10, 7, 6, 0, 0, 0, 0, 0, 0));
        faces.add(new Face(7, 1, 8, 0, 0, 0, 0, 0, 0));
        
        faces.add(new Face(3, 9, 4, 0, 0, 0, 0, 0, 0));
        faces.add(new Face(3, 4, 2, 0, 0, 0, 0, 0, 0));
        faces.add(new Face(3, 2, 6, 0, 0, 0, 0, 0, 0));
        faces.add(new Face(3, 6, 8, 0, 0, 0, 0, 0, 0));
        faces.add(new Face(3, 8, 9, 0, 0, 0, 0, 0, 0));
        
        faces.add(new Face(4, 9, 5, 0, 0, 0, 0, 0, 0));
        faces.add(new Face(2, 4, 11, 0, 0, 0, 0, 0, 0));
        faces.add(new Face(6, 2, 10, 0, 0, 0, 0, 0, 0));
        faces.add(new Face(8, 6, 7, 0, 0, 0, 0, 0, 0));
        faces.add(new Face(9, 8, 1, 0, 0, 0, 0, 0, 0));
        
        MeshData mData = new MeshData(vertex, uvs, normals, faces, null);
        icoSphere = subdevide(subDiv, mData);
        
        if(deform){
            for(int i = 0; i < mData.getVertecies().size(); i++){
                mData.getVertecies().set(i, deform(mData.getVertecies().get(i), seed, scale, freq));
            }
        }
    }
    
    private MeshData subdevide(int iterations, MeshData mesh){
        MeshData tmpMeshData = mesh;
        
        for(int i = 0; i < iterations; i++){
            tmpMeshData = iterate(tmpMeshData);
        }
        
        return tmpMeshData;
    }
    
    private Normal calcNormal(Vertex v){
        Vector3f n = new Vector3f(v.vX, v.vY, v.vZ);
        n.normalize();
        return new Normal(n.x, n.y, n.z);
    }
    
    private Normal smoothNormals(Normal[] normals){
        int amount = normals.length;
        float smoothX = 0, smoothY = 0, smoothZ = 0;
        for(Normal n : normals){
            smoothX += n.nX;
            smoothY += n.nY;
            smoothZ += n.nZ;
        }
        
        smoothX /= amount;
        smoothY /= amount;
        smoothZ /= amount;
        
        Vector3f smoothNormal = new Vector3f(smoothX, smoothY, smoothZ);
        smoothNormal.normalize();
        
        return new Normal(smoothNormal.x, smoothNormal.y, smoothNormal.z);
    }
    
    private MeshData iterate(MeshData mesh){
        List<Vertex> v = new ArrayList<>();
        List<Face> f = new ArrayList<>();
        List<UV> uv = new ArrayList<>();
        List<Normal> n = new ArrayList<>();
        
        for(Face tmp : mesh.getFaces()){
            Vertex a = getMiddle(mesh.getVertecies().get(tmp.v1), mesh.getVertecies().get(tmp.v2));
            Vertex b = getMiddle(mesh.getVertecies().get(tmp.v2), mesh.getVertecies().get(tmp.v3));
            Vertex c = getMiddle(mesh.getVertecies().get(tmp.v3), mesh.getVertecies().get(tmp.v1));

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
            uv.add(new UV(0, 0));
            n.add(calcNormal(mesh.getVertecies().get(tmp.v1)));
            n.add(calcNormal(mesh.getVertecies().get(tmp.v2)));
            n.add(calcNormal(mesh.getVertecies().get(tmp.v3)));
            n.add(calcNormal(a));
            n.add(calcNormal(b));
            n.add(calcNormal(c));

            f.add(new Face(iv1, ia, ic, 0, 0, 0, iv1, ia, ic));
            f.add(new Face(iv2, ib, ia, 0, 0, 0, iv2, ib, ia));
            f.add(new Face(iv3, ic, ib, 0, 0, 0, iv3, ic, ib));
            f.add(new Face(ia, ib, ic, 0, 0, 0, ia, ib, ic));
        }
        return new MeshData(v, uv, n, f, null);
    }
    
    private Vertex getMiddle(Vertex v1, Vertex v2){
        return setOnUnitSphere(new Vertex((v1.vX+v2.vX)/2f, (v1.vY+v2.vY)/2f, (v1.vZ+v2.vZ)/2f));
    }
    
    private Vertex setOnUnitSphere(Vertex v){
        float lenght = (float) Math.sqrt(v.vX*v.vX + v.vY*v.vY + v.vZ*v.vZ)*lengthScale;
        return new Vertex(v.vX/lenght, v.vY/lenght, v.vZ/lenght);
    }
    
    private Vertex deform(Vertex v, long seed, float scale, float frequenzy){
        VoronoiCracle cracle = new VoronoiCracle(seed, (short) 1);
        lengthScale = (float) cracle.noise(v.vX, v.vY, v.vZ, frequenzy)*scale;
        return setOnUnitSphere(v);
    }
    
    @Override
    public Mesh getSEMesh() {
        int mDataID = AssetLibrary.get().addMesh(icoSphere);
        return new Mesh(new Material(), mDataID);
    }
    
}
