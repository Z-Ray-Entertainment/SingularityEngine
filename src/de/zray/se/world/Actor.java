/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

import de.zray.se.ai.SEAI;
import de.zray.se.graphics.semesh.Mesh;
import de.zray.se.graphics.semesh.Oriantation;
import de.zray.se.physics.SEBulletObject;
import java.util.List;

/**
 *
 * @author Vortex Acherontic
 */
public class Actor extends Entity {

    private Mesh mesh;
    private SEAI ai;
    private SEBulletObject bullet;
    private World parrentWorld;
    private WorldID seWorldID;
    private Oriantation ori;

    public Actor(Mesh mesh, SEAI ai, SEBulletObject bulletObj, World parrentWorld) {
        this.ai = ai;
        this.bullet = bulletObj;
        this.mesh = mesh;
        this.parrentWorld = parrentWorld;
        this.ori = new Oriantation(this);
    }

    public List<Mesh> getRendableSEMeshes() {
        return mesh.getRendableMeshes(parrentWorld.getCurrentCamera());
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

    public Mesh getRootMesh() {
        return mesh;
    }

    public void setOriantation(Oriantation ori) {
        this.ori = ori;
        this.ori.forceNewParent(this);
    }

    public Oriantation getOrientation() {
        return this.ori;
    }

    public void setSEWorldID(WorldID id) {
        this.seWorldID = id;
    }

    public WorldID getSEWorldID() {
        return seWorldID;
    }
}
