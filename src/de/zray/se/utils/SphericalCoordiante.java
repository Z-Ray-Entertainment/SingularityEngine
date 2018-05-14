/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.utils;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author vortex
 */
public class SphericalCoordiante {
    double radius, polar, azimuthal;
    
    public SphericalCoordiante(Vector3d p){
        radius = Math.sqrt(Math.pow(p.x, 2)+Math.pow(p.y, 2)+Math.pow(p.z, 2));
        polar = Math.acos(p.z/radius);
        azimuthal = Math.atan2(p.y, p.z);
    }
    
    public SphericalCoordiante(Vector3f p){
        radius = Math.sqrt(Math.pow(p.x, 2)+Math.pow(p.y, 2)+Math.pow(p.z, 2));
        polar = Math.acos(p.z/radius);
        azimuthal = Math.atan2(p.y, p.z);
    }
    
    public double getRadius(){
        return radius;
    }
    
    public double getPolar(){
        return polar;
    }
    
    public double getAzimuthal(){
        return azimuthal;
    }
}
