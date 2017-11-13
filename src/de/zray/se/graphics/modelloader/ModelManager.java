/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.modelloader;

import de.zray.se.graphics.semesh.SEMesh;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Vortex Acherontic
 */
public class ModelManager {
    private List<SEMesh> models = new LinkedList<>();
    private List<String> fileNames = new LinkedList<>();
    private Modelloader loader = new Modelloader();
    
    public int loadModel(String file){
        for(int i = 0; i < models.size(); i++){
            if(fileNames.get(i).equals(file)){
                return i;
            }
        }
        fileNames.add(file);
        models.add(loader.loadModel(file));
        return models.size()-1;
    }
    
    public void removeModel(String file){
        for(int i = 0; i < models.size(); i++){
            if(fileNames.get(i) != null && fileNames.get(i).equals(file)){
                if(i == models.size()-1){
                    models.remove(i);
                    fileNames.remove(i);
                }
                else{
                    models.set(i, null);
                    fileNames.set(i, null);
                }
            }
        }
    }
    
    public SEMesh getModel(int id){
        return models.get(id);
    }
}
