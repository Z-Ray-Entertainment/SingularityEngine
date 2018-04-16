/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.texturemanager;

import java.io.IOException;

/**
 *
 * @author vortex
 */
public class SETexture {
    private String file = "", ext = ".file";
    //private Texture texture;
    private boolean used = false;
    
    public SETexture(String file){
        this.file = file;
        int ctc = file.length();
        while(!file.substring(ctc-1, ctc).equals(".")){
            ctc--;
        }
        ext = file.substring(ctc+1, file.length());
    }
    
    public String getFile(){
        return file;
    }
    
    public void release(){
        //texture.release();
        //texture = null;
    }
    
    public void unuse(){
        used = false;
    }
    
    public boolean isUsed(){
        return used;
    }
    
    public void bind() throws IOException{
        /*if(texture == null){
            load();
        }
        used = true;
        texture.bind();*/
    }
}
