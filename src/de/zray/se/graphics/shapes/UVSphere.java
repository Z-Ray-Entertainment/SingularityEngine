/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.shapes;

import de.zray.se.graphics.semesh.SEMesh;

/**
 *
 * @author vortex
 */
public class UVSphere implements SEMeshProvider{
    private SEMesh mesh;
        
    public UVSphere(float radius, int slices, int stacks){
        float angleBetweenSlices = 360f/slices;
        //float angleBetweenStacks = 
    }
    
    @Override
    public SEMesh getSEMesh() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
