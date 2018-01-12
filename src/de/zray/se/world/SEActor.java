/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

import de.zray.se.ai.SEAI;
import de.zray.se.graphics.semesh.SEMesh;
import de.zray.se.physics.SEBulletObject;
import java.util.List;

/**
 *
 * @author Vortex Acherontic
 */
public class SEActor extends SEEntity {

    private SEMesh mesh;
    private SEAI ai;
    private SEBulletObject bullet;
    private SEWorld parrentWorld;
    private SEWorldID seWorldID;

    public SEActor(SEMesh mesh, SEAI ai, SEBulletObject bulletObj, SEWorld parrentWorld) {
        this.ai = ai;
        this.bullet = bulletObj;
        this.mesh = mesh;
        this.parrentWorld = parrentWorld;
    }

    public List<SEMesh> getRendableSEMeshes() {
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

    public SEMesh getRootMesh() {
        return mesh;
    }

    public void setSEWorldID(SEWorldID id) {
        this.seWorldID = id;
    }

    public SEWorldID getSEWorldID() {
        return seWorldID;
    }
}
