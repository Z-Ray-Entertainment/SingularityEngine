/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.semesh;

import javax.vecmath.Color3f;

/**
 *
 * @author vortex
 */
public class Material {
    public static enum ReflectionMode {BLEND_INTO_MATERIAL, BLEND_INTO_SKY, NO_BLEND};
    
    private String texture, specMap, bumpMap, displaceMap, parallaxMap;
    private float specFac = 0, bumpFac = 0, displaceFac = 0, parallaxFac = 0, emission = 0, reflection = 0, transparency = 0f,
            maxReflectionDistance = 0;
    private Color3f diffuseColor = new Color3f(1, 1, 1),
            specularColor = new Color3f(1, 1, 1), reflectionColor = new Color3f(1, 1, 1);
    private boolean cullBackFaces = false, shadeless = false;
    private ReflectionMode refMode = ReflectionMode.NO_BLEND;
    
    public Material(){}
    
    public Material(Color3f diffuseColor){
        this.diffuseColor = diffuseColor;
    }
    
    public Material(Color3f diffuseColor, Color3f specColor){
        this.diffuseColor = diffuseColor;
        this.specularColor = specColor;
    }
    
    public Material(Color3f diffuseColor, Color3f specColor, Color3f reflectionColor){
        this.diffuseColor = diffuseColor;
        this.specularColor = specColor;
        this.reflectionColor = reflectionColor;
    }
    
    public Material(String diffuseTexture){
        setTextures(diffuseTexture, "", "", "", "");
    }
    
    public Material(String diffuseTexture, String bumpMap){
        setTextures(diffuseTexture, "", bumpMap, "", "");
    }
    
    public Material(String diffuseTexture, String bumpMap, String specMap){
        setTextures(diffuseTexture, specMap, bumpMap, "", "");
    }
    
    public Material(String diffuseTexture, String bumpMap, String specMap, String paralax){
        setTextures(diffuseTexture, specMap, bumpMap, "", paralax);
    }
    
    public Material(String diffuseTexture, String bumpMap, String specMap, String paralax, String displaceMap){
        setTextures(diffuseTexture, specMap, bumpMap, displaceMap, paralax);
    }
    
    private void setTextures(String diff, String spec, String bump, String disp, String para){
        this.texture = diff;
        this.specMap = spec;
        this.bumpMap = bump;
        this.parallaxMap = para;
        this.displaceMap = disp;
    }
    
    public void setBackfaceCulling(boolean enabled){
        this.cullBackFaces = enabled;
    }
    
    public boolean cullBackfaces(){
        return cullBackFaces;
    }
    
    public void setReflectionColor(float red, float green, float blue, float alpha){
        reflectionColor.x = red;
        reflectionColor.y = green;
        reflectionColor.z = blue;
    }
    
    public Color3f getReflectionColor(){
        return reflectionColor;
    }
    
    public void setSpecularColor(float red, float green, float blue, float alpha){
        specularColor.x = red;
        specularColor.y = green;
        specularColor.z = blue;
    }
    
    public Color3f getSpecularColor(){
        return specularColor;
    }
    
    public void setDiffuseColor(float red, float green, float blue, float alpha){
        diffuseColor.x = red;
        diffuseColor.y = green;
        diffuseColor.z = blue;
        transparency = alpha;
    }
      
    public Color3f getDiffiseColor(){
        return diffuseColor;
    }
    
    public void setReflectionMode(ReflectionMode mode){
        this.refMode = mode;
    }
    
    public ReflectionMode getReflectionMode(){
        return refMode;
    }
    
    public void setShadeless(boolean enabled){
        this.shadeless = enabled;
    }
    
    public boolean isShadeless(){
        return shadeless;
    }
    
    public void setTransparency(float fac){
        this.transparency = fac%1;
    }
    
    public float getTransparency(){
        return transparency;
    }
    
    public void setReflectivity(float fac){
        reflection = fac%1;
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
}
