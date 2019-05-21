/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.modelloader;

import de.zray.se.graphics.semesh.Mesh;
import java.io.File;

/**
 *
 * @author vortex
 */
public abstract class LoaderModule {
    private String ext[];
    
    public LoaderModule(String[] supportedExt){
        this.ext = supportedExt;
    }
    
    public boolean meshSupported(File file){
        String meshExt = "";
        String fileName = file.getName();
        int ctc = 0;
        while(!fileName.substring(fileName.length()-(ctc+1), fileName.length()-(ctc)).equals(".")){
            ctc++;
        }
        meshExt = fileName.substring(fileName.length()-ctc);
        for(int i = 0; i < ext.length; i++){
            if(ext[i].toLowerCase().equals(meshExt.toLowerCase())){
                return true;
            }
        }
        return false;
    }
    
    public abstract Mesh loadModel(File file);
}
