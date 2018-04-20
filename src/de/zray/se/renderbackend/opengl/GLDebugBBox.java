/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

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
        
        glColor3f(1, 1, 1);
        Vertex v[] = bBox.getVertecies();
        glPushMatrix();
        glTranslated(ori.getPosition()[0], ori.getPosition()[1], ori.getPosition()[2]);
        glBegin(GL_LINE_STRIP);
            glVertex3f(v[0].vX, v[0].vY, v[0].vZ);
            glVertex3f(v[1].vX, v[1].vY, v[1].vZ);
            glVertex3f(v[3].vX, v[3].vY, v[3].vZ);
            glVertex3f(v[7].vX, v[7].vY, v[7].vZ);
            glVertex3f(v[5].vX, v[5].vY, v[5].vZ);
            glVertex3f(v[4].vX, v[4].vY, v[4].vZ);
            glVertex3f(v[6].vX, v[6].vY, v[6].vZ);
            glVertex3f(v[2].vX, v[2].vY, v[2].vZ);
            glVertex3f(v[3].vX, v[3].vY, v[3].vZ);
        glEnd();
        glBegin(GL_LINE_STRIP);
            glVertex3f(v[2].vX, v[2].vY, v[2].vZ);
            glVertex3f(v[0].vX, v[0].vY, v[0].vZ);
            glVertex3f(v[4].vX, v[4].vY, v[4].vZ);
        glEnd();
        glBegin(GL_LINES);
            glVertex3f(v[1].vX, v[1].vY, v[1].vZ);
            glVertex3f(v[5].vX, v[5].vY, v[5].vZ);
            glVertex3f(v[6].vX, v[6].vY, v[6].vZ);
            glVertex3f(v[7].vX, v[7].vY, v[7].vZ);
        glEnd();
        glPopMatrix();
    }
}
