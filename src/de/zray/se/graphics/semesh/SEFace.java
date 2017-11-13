/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.semesh;

/**
 * A SEFace alway contians indecies of THREE vertecies with defines its shape.
 * @author vortex
 */
public class SEFace {
    public int v1, v2, v3;
    public int n1, n2, n3;
    public int uv1, uv2, uv3;
    
    
    public SEFace(int v1, int v2, int v3, int uv1, int uv2, int uv3, int n1, int n2, int n3){
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.uv1 = uv1;
        this.uv2 = uv2;
        this.uv3 = uv3;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
    }
}
