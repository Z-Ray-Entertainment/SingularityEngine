/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

import de.zray.se.world.DistancePatch;
import de.zray.se.world.SEWorld;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex3d;

/**
 *
 * @author vortex
 */
public class GLDebugRenderer {
    public void renderDistancePatches(SEWorld world){
        glDisable(GL_LIGHTING);
        glDisable(GL_TEXTURE_2D);
        glCullFace(GL_FALSE);
        glLineWidth(1);
        glColor3f(10, 10, 10);
        for(DistancePatch dp : world.getDistancePatched()){
            double verts[][] = new double[8][3];
            int edge = dp.getEdgeLength();
            verts[0][0] = dp.getPostion()[0]-edge/2;
            verts[0][1] = dp.getPostion()[1]+edge/2;
            verts[0][2] = dp.getPostion()[2]-edge/2;
            
            verts[1][0] = dp.getPostion()[0]+edge/2;
            verts[1][1] = dp.getPostion()[1]+edge/2;
            verts[1][2] = dp.getPostion()[2]-edge/2;
            
            verts[2][0] = dp.getPostion()[0]+edge/2;
            verts[2][1] = dp.getPostion()[1]+edge/2;
            verts[2][2] = dp.getPostion()[2]+edge/2;
            
            verts[3][0] = dp.getPostion()[0]-edge/2;
            verts[3][1] = dp.getPostion()[1]+edge/2;
            verts[3][2] = dp.getPostion()[2]+edge/2;
            
            verts[4][0] = dp.getPostion()[0]-edge/2;
            verts[4][1] = dp.getPostion()[1]-edge/2;
            verts[4][2] = dp.getPostion()[2]-edge/2;
            
            verts[5][0] = dp.getPostion()[0]+edge/2;
            verts[5][1] = dp.getPostion()[1]-edge/2;
            verts[5][2] = dp.getPostion()[2]-edge/2;
            
            verts[6][0] = dp.getPostion()[0]+edge/2;
            verts[6][1] = dp.getPostion()[1]-edge/2;
            verts[6][2] = dp.getPostion()[2]+edge/2;
            
            verts[7][0] = dp.getPostion()[0]-edge/2;
            verts[7][1] = dp.getPostion()[1]-edge/2;
            verts[7][2] = dp.getPostion()[2]+edge/2;
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
}
