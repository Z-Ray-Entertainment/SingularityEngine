/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.utils;

import java.util.Random;
import javax.vecmath.Matrix3d;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author Vortex Acherontic
 */
public class SEUtils {
    private static final SEUtils UTILS = new SEUtils();
    public static final double G = 6.67408*Math.pow(10, -11);
    
    public static SEUtils get(){
        return UTILS;
    }
    
    public String getSuffix(String file){
        int lastDot = 0;
        for(int i = 0; i < file.length(); i++){
            if(file.substring(i, i+1).equals(".")){
                lastDot = i;
            }
        }
        
        return file.substring(lastDot+1, file.length());
    }
    
    public Vector3f getVector(Vector3f start, Vector3f end){
        return new Vector3f(end.x-start.x, end.y-start.y, end.z-start.z);
    }
    
    public float getLenght(Vector3f vec){
        return (float) Math.sqrt(((vec.x*vec.x)+(vec.y*vec.y)+(vec.z*vec.z)));
    }
    
    public Vector2f calcCoordinates(float radius, float angle){
        return new Vector2f((float) (radius*Math.cos(angle)), (float) (radius*Math.sin(angle)));
    }
    
    public Vector2d calcCoordinates(double radius, double angle){
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
    public double calcSateliteSpeed(double massOfCenter, double radiusOfCenter, double distanceToCenter){
        return Math.sqrt(G*(massOfCenter/(radiusOfCenter+distanceToCenter)));
    }
    
    public double calcSpeedInAngleSpeed(double speed, double radius){
        return speed/radius;
    }
    
    public int randomInt(int start, int end){
        if(start == 0 && end == 0){
            return 0;
        }
        return (new Random().nextInt(end)+start)%end;
    }

    /**
     * Source: https://vvvv.org/blog/polar-spherical-and-geographic-coordinates
     * @param p
     * @return 
     */
    public SphericalCoordiante sphercialCooridantes(Vector3f p){
        return new SphericalCoordiante(p);
    }
    
    /**
     * Source: https://vvvv.org/blog/polar-spherical-and-geographic-coordinates
     * @param p
     * @return 
     */
    public SphericalCoordiante sphercialCooridantes(Vector3d p){
        return new SphericalCoordiante(p);
    }
    
    /**
     * Source: https://vvvv.org/blog/polar-spherical-and-geographic-coordinates
     * @param radius
     * @param polar
     * @param azimuthal
     * @return 
     */
    public Vector3f sphercialCooridantes(float radius, float polar, float azimuthal){
        Vector3f coords = new Vector3f();
        coords.x = radius * (float) Math.sin(polar) * (float) Math.cos(azimuthal);
        coords.y = radius * (float) Math.sin(polar) * (float) Math.sin(azimuthal);
        coords.z = radius * (float) Math.cos(polar);        
        return coords;
    }
    
    
    public Vector3d matrixMult(Matrix3d matrix, Vector3d vector){
        double x = matrix.m00*vector.x+matrix.m01*vector.y+matrix.m02*vector.z;
        double y = matrix.m10*vector.x+matrix.m11*vector.y+matrix.m12*vector.z;
        double z = matrix.m20*vector.x+matrix.m21*vector.y+matrix.m22*vector.z;
        return new Vector3d(x, y, z);
    }
}
