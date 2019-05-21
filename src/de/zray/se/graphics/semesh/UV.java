/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.semesh;

/**
 *
 * @author Vortex Acherontic
 */
public class UV {
    public float u = 0, v = 0;
    
    public UV(float u, float v){
        this.u = u;
        this.v = v;
    }
    
    public UV(double u, double v){
        this.u = (float) u;
        this.v = (float) v;
    }
}
