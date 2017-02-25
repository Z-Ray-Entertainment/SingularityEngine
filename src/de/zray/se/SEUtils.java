/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Vortex Acherontic
 */
public class SEUtils {
    public static String getSuffix(String file){
        int lastDot = 0;
        for(int i = 0; i < file.length(); i++){
            if(file.substring(i, i+1).equals(".")){
                lastDot = i;
            }
        }
        
        return file.substring(lastDot+1, file.length());
    }
    
    public static Vector3f getVector(Vector3f start, Vector3f end){
        return new Vector3f(end.x-start.x, end.y-start.y, end.z-start.z);
    }
    
    public static float getLenght(Vector3f vec){
        return (float) Math.sqrt(((vec.x*vec.x)+(vec.y*vec.y)+(vec.z*vec.z)));
    }
    
    public static Vector2f calcCoordinates(float radius, float angle){
        return new Vector2f((float) (radius*Math.cos(angle)), (float) (radius*Math.sin(angle)));
    }
}
