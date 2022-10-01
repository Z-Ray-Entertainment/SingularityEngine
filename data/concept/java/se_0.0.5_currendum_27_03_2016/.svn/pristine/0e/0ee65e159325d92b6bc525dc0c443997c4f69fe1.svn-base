/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics.semesh.vbo;

import de.zray.se.grapics.semesh.SEFace;
import de.zray.se.grapics.semesh.SENormal;
import de.zray.se.grapics.semesh.SEUV;
import de.zray.se.grapics.semesh.SEVertex;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author vortex
 */
public abstract class AbstractVBO {
    public static enum VBOMode{STATIC, DYNAMIC};
    
    public VBOMode mode = VBOMode.STATIC;
    public List<SEVertex> vertecies = new LinkedList<>();
    public List<SEFace> faces = new LinkedList<>();
    public List<SENormal> normals = new LinkedList<>();
    public List<SEUV> uvs = new LinkedList<>();
    public int id = -1, size = 0;
    
    public AbstractVBO(List<SEVertex> v, List<SEUV> uv, List<SENormal> n, List<SEFace> f, VBOMode m){
        this.faces = f;
        this.normals = n;
        this.uvs = uv;
        this.vertecies = v;
        this.mode = m;
    }
    
    public abstract void render();
    public abstract void build();
    public abstract void destroy();
}
