/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics.modelloader;

import de.zray.se.grapics.semesh.SEMesh;

/**
 *
 * @author vortex
 */
public abstract class LoaderModule {
    private String ext[];
    
    public LoaderModule(String[] supportedExt){
        this.ext = supportedExt;
    }
    
    public boolean meshSupported(String file){
        String meshExt = "";
        int ctc = 0;
        while(!file.substring(file.length()-(ctc+1), file.length()-(ctc)).equals(".")){
            ctc++;
        }
        meshExt = file.substring(file.length()-ctc);
        for(int i = 0; i < ext.length; i++){
            if(ext[i].toLowerCase().equals(meshExt.toLowerCase())){
                return true;
            }
        }
        return false;
    }
    
    public abstract SEMesh loadModel(String file);
}
