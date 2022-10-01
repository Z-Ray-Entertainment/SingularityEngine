/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics.semesh;

import de.zray.se.Settings;
import de.zray.se.grapics.shapes.Cube;
import java.io.IOException;
import java.util.List;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Vortex Acherontic
 */
public class SEBoundingBox {
    public float plusX = 0, minusX = 0, plusY = 0, minusY = 0, plusZ = 0, minusZ = 0;
    private SEMesh debugMesh;
    
    public SEBoundingBox(List<SEVertex> vertecies){
        for(SEVertex tmp : vertecies){
            if(tmp.vX > plusX){
                plusX = tmp.vX;
            }
            else if(tmp.vX < minusX){
                minusX = tmp.vX;
            }
            if(tmp.vY > plusY){
                plusY = tmp.vY;
            }
            else if(tmp.vY < minusY){
                minusY = tmp.vY;
            }
            if(tmp.vZ > plusZ){
                plusZ = tmp.vZ;
            }
            else if(tmp.vZ < minusZ){
                minusZ = tmp.vZ;
            }
        }
        
        
    }
    
    public void render() throws IOException{
        switch(Settings.get().debugMode){
            case DEBUG_AND_OBJECTS:
            case DEBUG_ON :
                glPushMatrix();
                debugMesh.render();
                glPopMatrix();
                break;
        }
    }
}
