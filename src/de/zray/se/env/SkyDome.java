/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.env;

import de.zray.se.grapics.material.SEMaterial;
import java.io.IOException;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.Sphere;

/**
 *
 * @author Vortex Acherontic
 */
public class SkyDome {
    private SEMaterial material;
    private float radius = 1;
    private int stacks = 32, slices = 16;
    private boolean disabledDepth = false, disabledLightting = false;
    
    public SkyDome(SEMaterial material, float radius, int stacks, int slices){
        this.material = material;
        this.radius = radius;
        this.stacks = stacks;
        this.slices = slices;
    }
    
    public void render() throws IOException{
        glPushMatrix();
        if(glIsEnabled(GL_DEPTH_TEST)){
            glDisable(GL_DEPTH_TEST);
            disabledDepth = true;
        }
        if(glIsEnabled(GL_LIGHTING)){
            glDisable(GL_LIGHTING);
            disabledLightting = true;
        }
        material.apply();
        Sphere sphere = new Sphere();
        sphere.setTextureFlag(true);
        sphere.setNormals(GL_SMOOTH);
        sphere.draw(radius, slices, stacks);
        if(disabledDepth){
            glEnable(GL_DEPTH_TEST);
        }
        if(disabledLightting){
            glEnable(GL_LIGHTING);
        }
        glPopMatrix();
    }
    
    public void update(float delta){
        
    }
}
