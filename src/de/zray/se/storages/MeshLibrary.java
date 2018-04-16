/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.storages;

import de.zray.se.graphics.semesh.Face;
import de.zray.se.graphics.semesh.MeshData;
import de.zray.se.graphics.semesh.Normal;
import de.zray.se.graphics.semesh.UV;
import de.zray.se.graphics.semesh.Vertex;
import de.zray.se.logger.SELogger;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author vortex
 */
public class MeshLibrary {
    private List<MeshData> meshLibrary = new LinkedList<>();
    private List<Integer> freeSlots = new LinkedList<>();
    
    public MeshData getMesh(int id){
        if(id >= meshLibrary.size()){
            return null;
        }
        else{
            return meshLibrary.get(id);
        }
    }
    
    public void removeMesh(int id){
        if(id == meshLibrary.size()-1){
            meshLibrary.remove(id);
        }
        else{
            meshLibrary.set(id, null);
            freeSlots.add(id);
        }
    }
    
    public int addMesh(MeshData mesh){
        int meshID = getMesh(mesh);
        if(meshID == -1){
            if(!freeSlots.isEmpty()){
                int slot = freeSlots.get(0);
                freeSlots.remove(0);
                meshLibrary.set(slot, mesh);
                return slot;
            }
            else{
                meshLibrary.add(mesh);
                return meshLibrary.size()-1;
            }
        }
        else{
            return meshID;
        }
    }
    
    public int getMesh(MeshData mesh){
        for(int i = 0; i < meshLibrary.size(); i++){
            if(compareMesh(meshLibrary.get(i), mesh)){
                return i;
            }
        }
        SELogger.get().dispatchMsg("MeshLibrary", SELogger.SELogType.INFO, new String[]{"Registering new mesh data"}, false);
        return -1;
    }
    
    private boolean compareMesh(MeshData original, MeshData compare){
        if(original != null && compare != null){
            if(compareVertecies(original, compare)){
                if(compareNormals(original, compare)){
                    if(compareNormals(original, compare)){
                        if(compareUVs(original, compare)){
                            if(compareFaces(original, compare)){
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }
    
    private boolean compareVertecies(MeshData original, MeshData compare){
        for(int i = 0; i < original.getVertecies().size(); i++){
            Vertex v1 = original.getVertecies().get(i);
            Vertex v2 = compare.getVertecies().get(i);
            if(v1.vX != v2.vX){
                return false;
            }
            else if(v1.vY != v2.vY){
                return false;
            }
            else if(v1.vZ != v2.vZ){
                return false;
            }
        }
        return true;
    }
    
    private boolean compareNormals(MeshData original, MeshData compare){
        for(int i = 0; i < original.getNormals().size(); i++){
            Normal n1 = original.getNormals().get(i);
            Normal n2 = compare.getNormals().get(i);
            if(n1.nX != n2.nX){
                return false;
            }
            else if(n1.nY != n2.nY){
                return false;
            }
            else if(n1.nZ != n2.nZ){
                return false;
            }
        }
        return true;
    }
    
    private boolean compareUVs(MeshData original, MeshData compare){
        for(int i = 0; i < original.getUVs().size(); i++){
            UV uv1 = original.getUVs().get(i);
            UV uv2 = compare.getUVs().get(i);
            if(uv1.u != uv2.u){
                return false;
            }
            else if(uv1.v != uv2.v){
                return false;
            }
        }
        return true;
    }
    
    private boolean compareFaces(MeshData original, MeshData compare){
        for(int i = 0; i < original.getFaces().size(); i++){
            Face f1 = original.getFaces().get(i);
            Face f2 = compare.getFaces().get(i);
            if(f1.v1 != f2.v1){
                return false;
            }
            if(f1.v2 != f2.v2){
                return false;
            }
            if(f1.v3 != f2.v3){
                return false;
            }
            if(f1.n1 != f2.n1){
                return false;
            }
            if(f1.n2 != f2.n2){
                return false;
            }
            if(f1.n3 != f2.n3){
                return false;
            }
            if(f1.uv1 != f2.uv1){
                return false;
            }
            if(f1.uv2 != f2.uv2){
                return false;
            }
            if(f1.uv3 != f2.uv3){
                return false;
            }
        }
        return true;
    }
}