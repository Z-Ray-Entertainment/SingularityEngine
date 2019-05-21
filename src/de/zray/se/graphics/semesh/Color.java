/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.semesh;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

/**
 *
 * @author vortex
 */
public class Color {
    FloatBuffer colorData;
    
    public Color(){
        colorData = BufferUtils.createFloatBuffer(4);
        colorData.put(255); //r
        colorData.put(255); //g
        colorData.put(255); //b
        colorData.put(255); //a
        colorData.flip();
    }
    
    public Color(float red, float green, float blue, float alpha){
        colorData = BufferUtils.createFloatBuffer(4);
        colorData.put(red);
        colorData.put(green);
        colorData.put(blue);
        colorData.put(alpha);
        colorData.flip();
    }
    
    public FloatBuffer getColorData(){
        return colorData;
    }
    
    public float red(){
        return colorData.get(0);
    }
    
    public float green(){
        return colorData.get(1);
    }
    
    public float blue(){
        return colorData.get(2);
    }
    
    public float alpha(){
        return colorData.get(3);
    }
}
