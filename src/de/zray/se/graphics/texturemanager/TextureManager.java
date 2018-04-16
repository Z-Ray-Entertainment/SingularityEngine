/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.texturemanager;

import de.zray.se.world.Module;
import de.zray.se.world.World;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vortex
 */
public class TextureManager {
    public static final int UNKNOWN_TEXTURE = -1;
    private static TextureManager tm;
    
    public static TextureManager get(){
        if(tm == null){
            tm = new TextureManager();
        }
        return tm;
    }
    
    List<SETexture> textures = new ArrayList<>();
    
    public int addTexture(SETexture texture){
        int textureID = textureExist(texture);
        if(textureID == UNKNOWN_TEXTURE){
            textures.add(texture);
            return textures.size()-1;
        }
        else{
            return textureID;
        }
    }
  
    public void bindTexture(int id) throws IOException{
        if(id <= textures.size()-1 && id >= 0){
            textures.get(id).bind();
        }
    }
    
    public void unuseTextures(){
        for(SETexture tmp : textures){
            tmp.unuse();
        }
    }
    
    public int textureExist(SETexture texture){
        for(int i = 0; i < textures.size(); i++){
            if(textures.get(i).getFile().equals(texture.getFile())){
                return i;
            }
        }
        return UNKNOWN_TEXTURE;
    }

    public boolean shutdown(){
        for(SETexture tmp : textures){
            tmp.release();
        }
        textures.clear();
        textures = null;
        return true;
    }

    public boolean cleanUp() {
        for(SETexture tmp : textures){
            if(!tmp.isUsed()){
                tmp.release();
            }
        }
        return true;
    }
}
