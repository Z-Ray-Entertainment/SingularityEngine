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
        
        vertex.forEach((tmp) -> {
            Normal norm = calcNormal(tmp);
            uvs.add(calcUV(norm));
            normals.add(calcNormal(tmp));
        });
        
        faces.add(new Face(0, 11, 5, 0, 11, 5, 0, 11, 5));
        faces.add(new Face(0, 5, 1, 0, 5, 1, 0, 5, 1));
        faces.add(new Face(0, 1, 7, 0, 1, 7, 0, 1, 7));
        faces.add(new Face(0, 7, 10, 0, 7, 10, 0, 7, 10));
        faces.add(new Face(0, 10, 11, 0, 10, 11, 0, 10, 11));
        
        faces.add(new Face(1, 5, 9, 1, 5, 9, 1, 5, 9));
        faces.add(new Face(5, 11, 4, 5, 11, 4, 5, 11, 4));
        faces.add(new Face(11, 10, 2, 11, 10, 2, 11, 10, 2));
        faces.add(new Face(10, 7, 6, 10, 7, 6, 10, 7, 6));
        faces.add(new Face(7, 1, 8, 7, 1, 8, 7, 1, 8));
        
        faces.add(new Face(3, 9, 4, 3, 9, 4, 3, 9, 4));
        faces.add(new Face(3, 4, 2, 3, 4, 2, 3, 4, 2));
        faces.add(new Face(3, 2, 6, 3, 2, 6, 3, 2, 6));
        faces.add(new Face(3, 6, 8, 3, 6, 8, 3, 6, 8));
        faces.add(new Face(3, 8, 9, 3, 8, 9, 3, 8, 9));
        
        faces.add(new Face(4, 9, 5, 4, 9, 5, 4, 9, 5));
        faces.add(new Face(2, 4, 11, 2, 4, 11, 2, 4, 11));
        faces.add(new Face(6, 2, 10, 6, 2, 10, 6, 2, 10));
        faces.add(new Face(8, 6, 7, 8, 6, 7, 8, 6, 7));
        faces.add(new Face(9, 8, 1, 9, 8, 1, 9, 8, 1));
        
        MeshData mData = new MeshData(vertex, uvs, normals, faces, null);
        icoSphere = subdivide(subDiv, mData);
        
        if(deform){
            for(int i = 0; i < mData.getVertecies().size(); i++){
                mData.getVertecies().set(i, deform(mData.getVertecies().get(i), seed, scale, freq));
            }
        }
    }
    
    private MeshData subdivide(int iterations, MeshData mesh){
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
        
        mesh.getFaces().forEach((tmp) -> {
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
            Normal n1 = calcNormal(mesh.getVertecies().get(tmp.v1));
            uv.add(calcUV(n1));
            n.add(n1);
            
            Normal n2 = calcNormal(mesh.getVertecies().get(tmp.v2));
            uv.add(calcUV(n2));
            n.add(n2);
            
            Normal n3 = calcNormal(mesh.getVertecies().get(tmp.v3));
            uv.add(calcUV(n3));
            n.add(n3);
            
            Normal na = calcNormal(a);
            uv.add(calcUV(na));
            n.add(na);
            
            Normal nb = calcNormal(b);
            uv.add(calcUV(nb));
            n.add(nb);
            
            Normal nc = calcNormal(c);
            uv.add(calcUV(nc));
            n.add(nc);

            f.add(new Face(iv1, ia, ic, iv1, ia, ic, iv1, ia, ic));
            f.add(new Face(iv2, ib, ia, iv2, ib, ia, iv2, ib, ia));
            f.add(new Face(iv3, ic, ib, iv3, ic, ib, iv3, ic, ib));
            f.add(new Face(ia, ib, ic, ia, ib, ic, ia, ib, ic));
        });
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
    
    private UV calcUV(Normal n){
        double u = Math.asin(n.nX)/Math.PI + 0.5;
        double v = Math.asin(n.nY)/Math.PI + 0.5;
        return new UV(u, v);
    }
    
    @Override
    public Mesh getSEMesh() {
        int mDataID = AssetLibrary.get().addMesh(icoSphere);
        return new Mesh(new Material(), mDataID);
    }
    
}
