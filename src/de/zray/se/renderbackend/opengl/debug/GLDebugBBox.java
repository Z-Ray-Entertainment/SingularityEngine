/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl.debug;

import de.zray.se.graphics.semesh.BoundingBox;
import de.zray.se.graphics.semesh.Orientation;
import de.zray.se.graphics.semesh.Vertex;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author vortex
 */
public class GLDebugBBox {
    public void renderBoundingBox(BoundingBox bBox){
        Orientation ori = bBox.getOrientation();
        Vertex v[] = bBox.getBoundingVertecies();
        
        glColor3f(1, 1, 1);
        
        glPushMatrix();
        glTranslated(ori.getPosition()[0], ori.getPosition()[1], ori.getPosition()[2]);
        glScaled(ori.getScale()[0], ori.getScale()[1], ori.getScale()[2]);
        glBegin(GL_LINE_STRIP);
            glVertex3d(v[0].vX, v[0].vY, v[0].vZ);
            glVertex3d(v[1].vX, v[1].vY, v[1].vZ);
            
            glVertex3d(v[0].vX, v[0].vY, v[0].vZ);
            glVertex3d(v[2].vX, v[2].vY, v[2].vZ);
            
            glVertex3d(v[0].vX, v[0].vY, v[0].vZ);
            glVertex3d(v[4].vX, v[4].vY, v[4].vZ);
        glEnd();
        glPopMatrix();
    }
}
