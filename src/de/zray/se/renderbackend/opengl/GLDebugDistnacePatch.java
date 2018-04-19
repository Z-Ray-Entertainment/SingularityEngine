/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

import de.zray.se.Settings;
import de.zray.se.world.DistancePatch;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex3d;

/**
 *
 * @author vortex
 */
public class GLDebugDistnacePatch {
    int dpLists[];
    
    public GLDebugDistnacePatch(){
        dpLists = new int[Settings.get().scene.dpSizes.length];
        for(int i = 0; i < dpLists.length; i++){
            dpLists[i] = -1;
        }
    }
    
    public void renderDistnacePatch(DistancePatch dp){
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
                if(dpLists[dp.getLevel()] == -1){
                    createDisplayList(verts);
                } else {
                    glCallList(dpLists[dp.getLevel()]);
                }
            glPopMatrix();
    }
    
    private void createDisplayList(double verts[][]){
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
    }
}
