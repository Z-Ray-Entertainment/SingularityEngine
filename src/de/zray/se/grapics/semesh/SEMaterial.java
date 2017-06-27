/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics.semesh;

/**
 *
 * @author vortex
 */
public class SEMaterial {
    public static final int RED = 0, GREEN = 1, BLUE = 2, ALPHA = 3;
    
    private String texture, specMap, bumpMap, displaceMap, parallaxMap;
    private int texID, specMapID, bumbMapID, displaceMapID, parallaxMapID;
    private float specFac, bumpFac, displaceFac, parallaxFac, emission, reflection;
    private float diffuseColor[] = {1, 1, 1, 1}, specColor[] = {1, 1, 1, 1};
    
    public void setSpecularColor(float red, float green, float blue, float alpha){
        specColor[RED] = red;
        specColor[GREEN] = green;
        specColor[BLUE] = blue;
        specColor[ALPHA] = alpha;
    }
    
    public float[] getSpecularColor(){
        return specColor;
    }
    
    public void setDiffuseColor(float red, float green, float blue, float alpha){
        diffuseColor[RED] = red;
        diffuseColor[GREEN] = green;
        diffuseColor[BLUE] = blue;
        diffuseColor[ALPHA] = alpha;
    }
    
    public float[] getDiffiseColo(){
        return diffuseColor;
    }
    
    public void setReflectivity(float fac){
        reflection = fac;
    }
    
    public float getReflectivity(){
        return reflection;
    }
    
    public void setEmission(float fac){
        emission = fac;
    }
    
    public float getEmission(){
        return emission;
    }
    
    public void setParallaxiness(float fac){
        parallaxFac = fac;
    }
    
    public float getParallaxiness(){
        return parallaxFac;
    }
    
    public void setSpecularity(float fac){
        specFac = fac;
    }
    
    public float getSpecularity(){
        return specFac;
    }
    
    public void setBumpiness(float fac){
        bumpFac = fac;
    }
    
    public float getBumpiness(){
        return bumpFac;
    }
    
    public void setDisplacement(float fac){
        displaceFac = fac;
    }
    
    public float getDisplacement(){
        return displaceFac;
    }
    
    public void setTexture(String file){
        texture = file;
    }
    
    public String getTexture(){
        return texture;
    }
    
    public void setSpecMap(String file){
        specMap = file;
    }
    
    public String getSpecMap(){
        return specMap;
    }
    
    public void setBumpMap(String file){
        bumpMap = file;
    }
    
    public String getBumpMap(){
        return bumpMap;
    }
    
    public void setDisplaceMap(String file){
        displaceMap = file;
    }
    
    public String getDisplaceMap(){
        return displaceMap;
    }
    
    public void setParallaxMap(String file){
        parallaxMap = file;
    }
    
    public String getParallaxMap(){
        return parallaxMap;
    }
    
    public void setTextureID(int id){
        texID = id;
    }
    
    public int getTextureID(){
        return texID;
    }
    
    public void setBumbMapID(int id){
        bumbMapID = id;
    }
    
    public int getBumbMapID(){
        return bumbMapID;
    }
    
    public void setSpecMapID(int id){
        specMapID = id;
    }
    
    public int getSpecMapID(){
        return specMapID;
    }
    
    public void setParallaxMapID(int id){
        parallaxMapID = id;
    }
    
    public int getParallaxID(){
        return parallaxMapID;
    }
    
    public void setDisplaceMapID(int id){
        displaceMapID = id;
    }
    
    public int getDisplaceMapID(){
        return displaceMapID;
    }
}
