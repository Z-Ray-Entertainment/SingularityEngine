/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics;

import de.zray.se.SEWorld;
import de.zray.se.Settings;
import de.zray.se.grapics.semesh.BoundingBox;
import de.zray.se.grapics.semesh.SEMesh;
import de.zray.se.grapics.semesh.SEOriantation;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.vecmath.Vector3d;

/**
 *
 * @author Vortex Acherontic
 */
public class DistancePatch {
    private SEWorld parrentWorld;
    private List<SEMesh> meshes = new LinkedList<>();
    private List<Integer> freeSlots = new LinkedList<>();
    private BoundingBox distanceBB;
    private List<SEMesh> changedMeshes = new LinkedList<>();
    
    public DistancePatch(SEWorld parrent, SEOriantation ori){
        this.parrentWorld = parrent;
        float width = Settings.get().scene.dbSize;
        float height = Settings.get().scene.dbSize;
        float depth = Settings.get().scene.dbSize;
        distanceBB = new BoundingBox(-width, width, -height, height, -depth, depth);
        distanceBB.setOrientation(ori);
    }
    
    public void render() throws IOException{
        switch(Settings.get().debug.debugMode){
            case DEBUG_AND_OBJECTS:
                renderMeshes();
            case DEBUG_ON:
                //distanceBB.debug();
                break;
            case DEBUG_OFF:
                renderMeshes();
                break;
        }
    }
    
    public List<SEMesh> getChagedMeshes(){
        return changedMeshes;
    }
    
    public void clearChangedeshes(){
        changedMeshes.clear();
    }
    
    private void renderMeshes() throws IOException{
        for(SEMesh tmp : meshes){
            if(tmp != null){
                //tmp.render();
                if(!fits(tmp.getOrientation())){
                    System.out.println("Mesh left DistancePatch!!!");
                    tmp.getOrientation().wasChanged();
                    changedMeshes.add(tmp);  
                }
            }
        }
        for(SEMesh tmp : changedMeshes){
            meshes.remove(tmp);
        }
    }
    
    public boolean fits(SEOriantation objOri){
        Vector3d pivot = objOri.getPositionVec();
        return distanceBB.inside(pivot, objOri);
    }
    
    public boolean addSEMesh(SEMesh mesh){
        if(fits(mesh.getOrientation())){
            if(freeSlots.size() > 1){
                int slot = freeSlots.get(0);
                freeSlots.remove(0);
                meshes.set(slot, mesh);
                return true;
            }
            else{
                meshes.add(mesh);
                return true;
            }
        }
        return false;
    }
}
