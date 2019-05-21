/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

import de.zray.se.ai.SEAI;
import de.zray.se.graphics.Camera;
import de.zray.se.graphics.semesh.BoundingBox;
import de.zray.se.graphics.semesh.Mesh;
import de.zray.se.physics.SEBulletObject;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Vortex Acherontic
 */
public class Actor extends Entity {

    private List<Mesh> mesh = new LinkedList<Mesh>();
    private SEAI ai;
    private SEBulletObject bullet;
    private World parrentWorld;
    private WorldID seWorldID;

    public Actor(Mesh mesh, SEAI ai, SEBulletObject bulletObj, World parrentWorld) {
        this.ai = ai;
        this.bullet = bulletObj;
        this.mesh.add(mesh);
        this.parrentWorld = parrentWorld;
        bBox = new BoundingBox(this);
    }

    public List<Mesh> getRendableMeshes() {
        List rMeshes = new LinkedList();
        for(Mesh m : mesh){
            Mesh tmp = m.getMeshOrLOD(parrentWorld.getCurrentCamera());
            if(tmp != null){
                rMeshes.add(tmp);
            }
        }
        return rMeshes;
    }

    public SEAI getSEAI() {
        return ai;
    }

    public void setAI(SEAI ai) {
        this.ai = ai;
    }

    public SEBulletObject getBulletObject() {
        return bullet;
    }

    public void setSEWorldID(WorldID id) {
        this.seWorldID = id;
    }

    public WorldID getSEWorldID() {
        return seWorldID;
    }

    /**
     * Returns the first Mesh in it's mesh List
     * @return 
     */
    public Mesh getRootMesh() {
        return mesh.get(0);
    }
}
