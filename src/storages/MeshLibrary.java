/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storages;

import de.zray.se.grapics.semesh.SEFace;
import de.zray.se.grapics.semesh.SEMesh;
import de.zray.se.grapics.semesh.SEMeshData;
import de.zray.se.grapics.semesh.SENormal;
import de.zray.se.grapics.semesh.SEUV;
import de.zray.se.grapics.semesh.SEVertex;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author vortex
 */
public class MeshLibrary {
    private List<SEMeshData> meshLibrary = new LinkedList<>();
    private List<Integer> freeSlots = new LinkedList<>();
    
    public SEMeshData getMesh(int id){
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
    
    public int addMesh(SEMeshData mesh){
        for(int i = 0; i < meshLibrary.size(); i++){
            if(compareMesh(meshLibrary.get(i), mesh)){
                return i;
            }
        }
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
    
    private boolean compareMesh(SEMeshData original, SEMeshData compare){
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
    
    private boolean compareVertecies(SEMeshData original, SEMeshData compare){
        for(int i = 0; i < original.getVertecies().size(); i++){
            SEVertex v1 = original.getVertecies().get(i);
            SEVertex v2 = compare.getVertecies().get(i);
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
    
    private boolean compareNormals(SEMeshData original, SEMeshData compare){
        for(int i = 0; i < original.getVertecies().size(); i++){
            SENormal n1 = original.getNormals().get(i);
            SENormal n2 = compare.getNormals().get(i);
            if(n1.nX != n2.nY){
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
    
    private boolean compareUVs(SEMeshData original, SEMeshData compare){
        for(int i = 0; i < original.getUVs().size(); i++){
            SEUV uv1 = original.getUVs().get(i);
            SEUV uv2 = compare.getUVs().get(i);
            if(uv1.u != uv2.u){
                return false;
            }
            else if(uv1.v != uv2.v){
                return false;
            }
        }
        return true;
    }
    
    private boolean compareFaces(SEMeshData original, SEMeshData compare){
        for(int i = 0; i < original.getFaces().size(); i++){
            SEFace f1 = original.getFaces().get(i);
            SEFace f2 = compare.getFaces().get(i);
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
