/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics.semesh;

import de.zray.se.grapics.texturemanager.SETexture;
import de.zray.se.grapics.texturemanager.TextureManager;
import java.io.IOException;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author vortex
 */
public class SEMaterial {
    SEColor diffuse, specular, ambient;
    private int textureID;
    private boolean lighted = false, blending = false;

    public SEMaterial(String texture){
        init(TextureManager.get().addTexture(new SETexture(texture)), new SEColor(), new SEColor(), new SEColor(), true);
    }
    
    public SEMaterial() {
        init(-1, new SEColor(), new SEColor(), new SEColor(), true);
    }
    
    public SEMaterial(int textureID, TextureManager tManager){
        init(textureID, new SEColor(), new SEColor(), new SEColor(), true);
    }

    public SEMaterial(SEColor diffuse, SEColor specular, SEColor ambient){
        init(-1, diffuse, specular, ambient, false);
    }
    
    public void setLighted(boolean enabled){
        this.lighted = enabled;
    }
    
    private void init(int texture, SEColor diffuse, SEColor specular, SEColor ambient, boolean lighted){
        this.textureID = texture;
        this.lighted = lighted;
        this.diffuse = diffuse;
        this.specular = specular;
        this.ambient = ambient;
    }
    
    public void apply() throws IOException{
        if(diffuse.colorData.get(3) > 0 && blending){
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        }
        else{
            glDisable(GL_BLEND);
        }
        if(lighted){
            glEnable(GL_LIGHTING);
        }
        else{
            glDisable(GL_LIGHTING);
        }
        if(lighted){
            //glMaterial(GL_FRONT_AND_BACK, GL_DIFFUSE, diffuse.colorData);
            //glMaterial(GL_FRONT_AND_BACK, GL_SPECULAR, specular.colorData);
            //glMaterial(GL_FRONT_AND_BACK, GL_AMBIENT, ambient.colorData);
        }
        else{
            diffuse.getColorData().rewind();
            glColor4f(diffuse.colorData.get(0), diffuse.colorData.get(1), diffuse.colorData.get(2), diffuse.colorData.get(3));
        }
        
        if(textureID != -1){
            glEnable(GL_TEXTURE);
            glEnable(GL_TEXTURE_2D);
            TextureManager.get().bindTexture(textureID);
        }
        else if(textureID == -1){
            glDisable(GL_TEXTURE);
            glDisable(GL_TEXTURE_2D);
        }
    }
}
