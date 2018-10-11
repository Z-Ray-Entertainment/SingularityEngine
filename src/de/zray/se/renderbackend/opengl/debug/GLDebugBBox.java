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
import static de.zray.se.graphics.semesh.BoundingBox.BOTTOM_BACK_LEFT;
import static de.zray.se.graphics.semesh.BoundingBox.BOTTOM_BACK_RIGHT;
import static de.zray.se.graphics.semesh.BoundingBox.BOTTOM_FRONT_LEFT;
import static de.zray.se.graphics.semesh.BoundingBox.BOTTOM_FRONT_RIGHT;
import static de.zray.se.graphics.semesh.BoundingBox.TOP_BACK_LEFT;
import static de.zray.se.graphics.semesh.BoundingBox.TOP_BACK_RIGHT;
import static de.zray.se.graphics.semesh.BoundingBox.TOP_FRONT_LEFT;
import static de.zray.se.graphics.semesh.BoundingBox.TOP_FRONT_RIGHT;
import javax.vecmath.Vector3d;


/**
 *
 * @author vortex
 */
public class GLDebugBBox {
    
    public void renderBoundingBox(BoundingBox bBox){
        Orientation ori = bBox.getOrientation();
        glColor3f(1, 1, 1);

        glPushMatrix();
            glTranslated(ori.getPosition()[0], ori.getPosition()[1], ori.getPosition()[2]);
            glScaled(ori.getScale()[0], ori.getScale()[1], ori.getScale()[2]);
            drawBox(bBox);
        glPopMatrix();
    }
    
    private void drawBox(BoundingBox bBox){
        Vector3d v[] = bBox.getBoundingVertecies();
        
        glBegin(GL_LINE_STRIP);
            glVertex3d(v[BOTTOM_BACK_LEFT].x, v[BOTTOM_BACK_LEFT].y, v[BOTTOM_BACK_LEFT].z);
            glVertex3d(v[BOTTOM_BACK_RIGHT].x, v[BOTTOM_BACK_RIGHT].y, v[BOTTOM_BACK_RIGHT].z);
            glVertex3d(v[BOTTOM_FRONT_RIGHT].x, v[BOTTOM_FRONT_RIGHT].y, v[BOTTOM_FRONT_RIGHT].z);
            glVertex3d(v[BOTTOM_FRONT_LEFT].x, v[BOTTOM_FRONT_LEFT].y, v[BOTTOM_FRONT_LEFT].z);
            glVertex3d(v[BOTTOM_BACK_LEFT].x, v[BOTTOM_BACK_LEFT].y, v[BOTTOM_BACK_LEFT].z);
            glVertex3d(v[TOP_BACK_LEFT].x, v[TOP_BACK_LEFT].y, v[TOP_BACK_LEFT].z);
            glVertex3d(v[TOP_BACK_RIGHT].x, v[TOP_BACK_RIGHT].y, v[TOP_BACK_RIGHT].z);
            glVertex3d(v[TOP_FRONT_RIGHT].x, v[TOP_FRONT_RIGHT].y, v[TOP_FRONT_RIGHT].z);
            glVertex3d(v[TOP_FRONT_LEFT].x, v[TOP_FRONT_LEFT].y, v[TOP_FRONT_LEFT].z);
            glVertex3d(v[TOP_BACK_LEFT].x, v[TOP_BACK_LEFT].y, v[TOP_BACK_LEFT].z);
        glEnd();
        glBegin(GL_LINES);
            glVertex3d(v[BOTTOM_FRONT_LEFT].x, v[BOTTOM_FRONT_LEFT].y, v[BOTTOM_FRONT_LEFT].z);
            glVertex3d(v[TOP_FRONT_LEFT].x, v[TOP_FRONT_LEFT].y, v[TOP_FRONT_LEFT].z);
            glVertex3d(v[BOTTOM_FRONT_RIGHT].x, v[BOTTOM_FRONT_RIGHT].y, v[BOTTOM_FRONT_RIGHT].z);
            glVertex3d(v[TOP_FRONT_RIGHT].x, v[TOP_FRONT_RIGHT].y, v[TOP_FRONT_RIGHT].z);
            glVertex3d(v[BOTTOM_BACK_RIGHT].x, v[BOTTOM_BACK_RIGHT].y, v[BOTTOM_BACK_RIGHT].z);
            glVertex3d(v[TOP_BACK_RIGHT].x, v[TOP_BACK_RIGHT].y, v[TOP_BACK_RIGHT].z);
        glEnd();
        glEndList();
    }
}
