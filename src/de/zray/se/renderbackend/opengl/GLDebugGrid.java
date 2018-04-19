/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

import de.zray.se.Settings;
import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glVertex3d;

/**
 *
 * @author vortex
 */
public class GLDebugGrid {
    private int gridDisplayList = -1;
    
    public void renderGrid(){
        glColor3f(0.5f, 0.5f, 0.5f);
        
        if(gridDisplayList == -1){
            double edge = Settings.get().debug.gridSize;
            double step = Settings.get().debug.gridStep;
            
            gridDisplayList = glGenLists(1);
            glNewList(gridDisplayList, GL_COMPILE);
            glBegin(GL_LINES);
            for(int i = 0; i < (edge+1)/step; i++){
                glVertex3d(-edge/2, 0, (-edge/2)+i*step); glVertex3d(edge/2, 0, (-edge/2)+i*step);
                glVertex3d((-edge/2)+i*step, 0, -edge/2); glVertex3d((-edge/2)+i*step, 0, edge/2);
            }
            glEnd();
            glEndList();
        }
        glCallList(gridDisplayList);
    }
}
