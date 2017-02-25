/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics;

import java.nio.FloatBuffer;

/**
 *
 * @author Vortex Acherontic
 */
public class Light {
    public static enum Type{SPOT, POINT, SUN};
    private Type lightType = Type.SUN;
    private boolean shadows = false;
    private FloatBuffer diffuse, specular, ambiente;
    
}
