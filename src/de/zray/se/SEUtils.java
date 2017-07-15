/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

import java.util.Random;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

/**
 *
 * @author Vortex Acherontic
 */
public class SEUtils {
    public static final double G = 6.67408*Math.pow(10, -11);
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
    
    public static Vector2d calcCoordinates(double radius, double angle){
        return new Vector2d(radius*Math.cos(angle), radius*Math.sin(angle));
    }
    
    /**
     * Calculates the speed required by an object to cycle any orb on a stable
     * orbit
     * @param massOfCenter the mass of the orb which the satelite orbits
     * @param radiusOfCenter the size of the orb which the satelite orbits
     * @param distanceToCenter the height above the orb which the satelite centers
     * @return 
     */
    public static double calcSateliteSpeed(double massOfCenter, double radiusOfCenter, double distanceToCenter){
        return Math.sqrt(G*(massOfCenter/(radiusOfCenter+distanceToCenter)));
    }
    
    public static double calcSpeedInAngleSpeed(double speed, double radius){
        return speed/radius;
    }
    
    public static int randomInt(int start, int end){
        return (new Random().nextInt(end)+start)%end;
    }
}
