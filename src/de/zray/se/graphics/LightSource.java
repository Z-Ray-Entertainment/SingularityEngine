/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics;

import de.zray.se.graphics.semesh.Oriantation;
import de.zray.se.world.Entity;

/**
 *
 * @author Vortex Acherontic
 */
public class LightSource extends Entity {
    public static enum Type{SPOT, POINT, SUN, VOLUME};
    public static enum FallOf{LINEAR, CUBIC, INVERSE_CUBIC, INFINITY}
    public static final int DIFFUSE = 0, SPECULAR = 1, AMBIENT = 2;
    public static final int RED = 0, GREEN = 1, BLUE = 2;
    
    private Type lightType = Type.SUN;
    private boolean castShadows = false;
    private float[][] colors = new float[3][3];
    private FallOf fallOf = FallOf.CUBIC;
    
    public void setColor(int type, float red, float green, float blue) throws ArrayIndexOutOfBoundsException{
        colors[type][RED] = red;
        colors[type][GREEN] = green;
        colors[type][BLUE] = blue;
    }
    
    public float[] getColor(int type) throws ArrayIndexOutOfBoundsException{
        return colors[type];
    }
    
    public boolean castShadows(){
        return castShadows;
    }
    
    public void setCastShadows(boolean enabled){
        castShadows = enabled;
    }
    
    public Type getLightType(){
        return lightType;
    }
    
    public void setLightType(Type t){
        lightType = t;
        if(lightType == Type.SUN){
            fallOf = FallOf.INFINITY;
        }
    }
    
    public FallOf getFallof(){
        return fallOf;
    }
}
