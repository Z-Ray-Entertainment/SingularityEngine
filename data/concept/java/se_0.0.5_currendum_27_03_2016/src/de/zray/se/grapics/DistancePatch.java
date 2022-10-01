/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics;

import de.zray.se.SEWorld;
import de.zray.se.Settings;
import de.zray.se.grapics.semesh.SEMesh;
import de.zray.se.grapics.semesh.SEOriantation;
import java.util.LinkedList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Vortex Acherontic
 */
public class DistancePatch {
    private float width = 10, height = 10, depth = 10, posX = 0, posY = 0, posZ = 0;
    private SEWorld parrentWorld;
    private List<SEMesh> meshes = new LinkedList<>();
    private List<Integer> freeSlots = new LinkedList<>();
    
    public DistancePatch(SEWorld parrent){
        this.parrentWorld = parrent;
    }
    
    public void render(){
        switch(Settings.get().debugMode){
            case DEBUG_AND_OBJECTS:
            case DEBUG_ON:
                break;
            case DEBUG_OFF:
                break;
        }
    }
    
    public boolean fits(SEOriantation objOri, ){
        Vector3f pos = objOri.getPositionVec();
        
        if(){}
        return false;
    }
    
    public int addSEMesh(SEMesh mesh){
        if(freeSlots.size() > 1){
            int slot = freeSlots.get(0);
            freeSlots.remove(0);
            meshes.set(slot, mesh);
            return slot;
        }
        else{
            meshes.add(mesh);
            return meshes.size()-1;
        }
    }
    
    public void removeSEMesh(int id){
        if(id == meshes.size()-1){
            meshes.remove(id);
        }
        else{
            meshes.set(id, null);
            freeSlots.add(id);
        }
    }
    
    public SEMesh getSEMesh(int id){
        return meshes.get(id);
    }
}
