/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

import de.zray.se.Settings;
import de.zray.se.world.DistancePatch;
import de.zray.se.world.World;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author vortex
 */
public class GLDebugRenderer {
    private int gridDisplayList = -1;
    
    public void render(World world){
        glDisable(GL_LIGHTING);
        glDisable(GL_TEXTURE_2D);
        glCullFace(GL_FALSE);
        if(Settings.get().debug.renderOnTop){
            glDisable(GL_DEPTH_TEST);
        }        
        glLineWidth(1);
        
        if(Settings.get().debug.showGrid){
            renderGrid();
        }
        if(Settings.get().debug.showDistancePatches){
            renderDistancePatches(world);
        }
    }
    
    private void renderGrid(){
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
    
    private void renderDistancePatches(World world){
        glColor3f(10, 0, 0);
        for(DistancePatch dp : world.getDistancePatched()){
            glPushMatrix();
            double verts[][] = new double[8][3];
            int edge = dp.getEdgeLength();
            verts[0][0] = -edge/2f;
            verts[0][1] = +edge/2f;
            verts[0][2] = -edge/2f;
            
            verts[1][0] = +edge/2f;
            verts[1][1] = +edge/2f;
            verts[1][2] = -edge/2f;
            
            verts[2][0] = +edge/2f;
            verts[2][1] = +edge/2f;
            verts[2][2] = +edge/2f;
            
            verts[3][0] = -edge/2f;
            verts[3][1] = +edge/2f;
            verts[3][2] = +edge/2f;
            
            verts[4][0] = -edge/2f;
            verts[4][1] = -edge/2f;
            verts[4][2] = -edge/2f;
            
            verts[5][0] = +edge/2f;
            verts[5][1] = -edge/2f;
            verts[5][2] = -edge/2f;
            
            verts[6][0] = +edge/2f;
            verts[6][1] = -edge/2f;
            verts[6][2] = +edge/2f;
            
            verts[7][0] = -edge/2f;
            verts[7][1] = -edge/2f;
            verts[7][2] = +edge/2f;
            
            switch(dp.getLevel()){
                case 0 :
                    glColor3f(1, 0, 0);
                    break;
                case 1 :
                    glColor3f(0, 1, 0);
                    break;
                case 2 :
                    glColor3f(0, 0, 1);
                    break;
                case 3 :
                    glColor3f(1, 0, 1);
                    break;
            }
            
            glTranslated(dp.getPostion()[0], dp.getPostion()[1], dp.getPostion()[2]);
            glBegin(GL_LINES);
                glVertex3d(verts[0][0], verts[0][1], verts[0][2]); glVertex3d(verts[1][0], verts[1][1], verts[1][2]);
                glVertex3d(verts[0][0], verts[0][1], verts[0][2]); glVertex3d(verts[3][0], verts[3][1], verts[3][2]);
                glVertex3d(verts[0][0], verts[0][1], verts[0][2]); glVertex3d(verts[4][0], verts[4][1], verts[4][2]);
                
                glVertex3d(verts[1][0], verts[1][1], verts[1][2]); glVertex3d(verts[2][0], verts[2][1], verts[2][2]);
                glVertex3d(verts[1][0], verts[1][1], verts[1][2]); glVertex3d(verts[5][0], verts[5][1], verts[5][2]);
                
                glVertex3d(verts[2][0], verts[2][1], verts[2][2]); glVertex3d(verts[6][0], verts[6][1], verts[6][2]);
                glVertex3d(verts[2][0], verts[2][1], verts[2][2]); glVertex3d(verts[3][0], verts[3][1], verts[3][2]);
                
                glVertex3d(verts[3][0], verts[3][1], verts[3][2]); glVertex3d(verts[7][0], verts[7][1], verts[7][2]);
                
                glVertex3d(verts[7][0], verts[7][1], verts[7][2]); glVertex3d(verts[4][0], verts[4][1], verts[4][2]);
                glVertex3d(verts[7][0], verts[7][1], verts[7][2]); glVertex3d(verts[6][0], verts[6][1], verts[6][2]);
                
                glVertex3d(verts[5][0], verts[5][1], verts[5][2]); glVertex3d(verts[4][0], verts[4][1], verts[4][2]);
                glVertex3d(verts[5][0], verts[5][1], verts[5][2]); glVertex3d(verts[6][0], verts[6][1], verts[6][2]);
            glEnd();
            glPopMatrix();
        }
    }
}
