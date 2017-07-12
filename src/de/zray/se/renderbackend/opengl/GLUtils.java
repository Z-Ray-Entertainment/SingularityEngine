/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

import de.zray.se.grapics.semesh.SEFace;
import de.zray.se.grapics.semesh.SEMesh;
import de.zray.se.logger.SELogger;
import java.nio.FloatBuffer;
import javax.vecmath.Vector3f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_NORMAL_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glMultMatrixf;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glNormalPointer;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

/**
 *
 * @author vortex
 */
public class GLUtils {
    private static float[] IDENTIY_MATRIX;
    private FloatBuffer matrix = BufferUtils.createFloatBuffer(16);
    
    public GLUtils(){
        IDENTIY_MATRIX = new float[] {
			1.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
                        0.0f, 0.0f, 0.0f, 1.0f };
    }
    
    public void generateVBO(SEMesh mesh, OpenGLRenderData rData){
        rData.setVBOSize(mesh.getFaces().size()*3*8);
        FloatBuffer vboData = BufferUtils.createFloatBuffer(rData.getVBOSize());
        
        for(SEFace face : mesh.getFaces()){
            vboData.put(mesh.getVertecies().get(face.v1).vX);
            vboData.put(mesh.getVertecies().get(face.v1).vY);
            vboData.put(mesh.getVertecies().get(face.v1).vZ);
            vboData.put(mesh.getNormals().get(face.n1).nX);
            vboData.put(mesh.getNormals().get(face.n1).nY);
            vboData.put(mesh.getNormals().get(face.n1).nZ);
            vboData.put(mesh.getUVs().get(face.uv1).u);
            vboData.put(mesh.getUVs().get(face.uv1).v);
            
            vboData.put(mesh.getVertecies().get(face.v2).vX);
            vboData.put(mesh.getVertecies().get(face.v2).vY);
            vboData.put(mesh.getVertecies().get(face.v2).vZ);
            vboData.put(mesh.getNormals().get(face.n2).nX);
            vboData.put(mesh.getNormals().get(face.n2).nY);
            vboData.put(mesh.getNormals().get(face.n2).nZ);
            vboData.put(mesh.getUVs().get(face.uv2).u);
            vboData.put(mesh.getUVs().get(face.uv2).v);
            
            vboData.put(mesh.getVertecies().get(face.v3).vX);
            vboData.put(mesh.getVertecies().get(face.v3).vY);
            vboData.put(mesh.getVertecies().get(face.v3).vZ);
            vboData.put(mesh.getNormals().get(face.n3).nX);
            vboData.put(mesh.getNormals().get(face.n3).nY);
            vboData.put(mesh.getNormals().get(face.n3).nZ);
            vboData.put(mesh.getUVs().get(face.uv3).u);
            vboData.put(mesh.getUVs().get(face.uv3).v);
        }
        vboData.flip();
        rData.setVBOID(glGenBuffers());
       
        glBindBuffer(GL_ARRAY_BUFFER, rData.getVBOID());
        glBufferData(GL_ARRAY_BUFFER, vboData, GL_STATIC_DRAW);

        vboData.clear();
    }
    
    public void generateVBOWired(SEMesh mesh, OpenGLRenderData rData){
        rData.setVBOSizeWired(mesh.getFaces().size()*6*8); //Größe für den FloatBuffer (8 Werte pro Vertex)
        SELogger.get().dispatchMsg("WiredVBO", SELogger.SELogType.INFO, new String[]{"Generate VBO with size: "+rData.getVBOSizeWired()}, false);
        FloatBuffer vboData = BufferUtils.createFloatBuffer(rData.getVBOSizeWired());
        
        for(SEFace face : mesh.getFaces()){
            vboData.put(mesh.getVertecies().get(face.v1).vX);
            vboData.put(mesh.getVertecies().get(face.v1).vY);
            vboData.put(mesh.getVertecies().get(face.v1).vZ);
            vboData.put(mesh.getNormals().get(face.n1).nX);
            vboData.put(mesh.getNormals().get(face.n1).nY);
            vboData.put(mesh.getNormals().get(face.n1).nZ);
            vboData.put(mesh.getUVs().get(face.uv1).u);
            vboData.put(mesh.getUVs().get(face.uv1).v);
            
            vboData.put(mesh.getVertecies().get(face.v2).vX);
            vboData.put(mesh.getVertecies().get(face.v2).vY);
            vboData.put(mesh.getVertecies().get(face.v2).vZ);
            vboData.put(mesh.getNormals().get(face.n2).nX);
            vboData.put(mesh.getNormals().get(face.n2).nY);
            vboData.put(mesh.getNormals().get(face.n2).nZ);
            vboData.put(mesh.getUVs().get(face.uv2).u);
            vboData.put(mesh.getUVs().get(face.uv2).v);
            
            vboData.put(mesh.getVertecies().get(face.v3).vX);
            vboData.put(mesh.getVertecies().get(face.v3).vY);
            vboData.put(mesh.getVertecies().get(face.v3).vZ);
            vboData.put(mesh.getNormals().get(face.n3).nX);
            vboData.put(mesh.getNormals().get(face.n3).nY);
            vboData.put(mesh.getNormals().get(face.n3).nZ);
            vboData.put(mesh.getUVs().get(face.uv3).u);
            vboData.put(mesh.getUVs().get(face.uv3).v);
            
            vboData.put(mesh.getVertecies().get(face.v1).vX);
            vboData.put(mesh.getVertecies().get(face.v1).vY);
            vboData.put(mesh.getVertecies().get(face.v1).vZ);
            vboData.put(mesh.getNormals().get(face.n1).nX);
            vboData.put(mesh.getNormals().get(face.n1).nY);
            vboData.put(mesh.getNormals().get(face.n1).nZ);
            vboData.put(mesh.getUVs().get(face.uv1).u);
            vboData.put(mesh.getUVs().get(face.uv1).v);
            
            vboData.put(mesh.getVertecies().get(face.v3).vX);
            vboData.put(mesh.getVertecies().get(face.v3).vY);
            vboData.put(mesh.getVertecies().get(face.v3).vZ);
            vboData.put(mesh.getNormals().get(face.n3).nX);
            vboData.put(mesh.getNormals().get(face.n3).nY);
            vboData.put(mesh.getNormals().get(face.n3).nZ);
            vboData.put(mesh.getUVs().get(face.uv3).u);
            vboData.put(mesh.getUVs().get(face.uv3).v);
            
            vboData.put(mesh.getVertecies().get(face.v2).vX);
            vboData.put(mesh.getVertecies().get(face.v2).vY);
            vboData.put(mesh.getVertecies().get(face.v2).vZ);
            vboData.put(mesh.getNormals().get(face.n2).nX);
            vboData.put(mesh.getNormals().get(face.n2).nY);
            vboData.put(mesh.getNormals().get(face.n2).nZ);
            vboData.put(mesh.getUVs().get(face.uv2).u);
            vboData.put(mesh.getUVs().get(face.uv2).v);
        }
        vboData.flip();
        rData.setVBOIDWired(glGenBuffers());
        
        glBindBuffer(GL_ARRAY_BUFFER, rData.getVBOIDWired());
        glBufferData(GL_ARRAY_BUFFER, vboData, GL_STATIC_DRAW);
	vboData.clear();
    }
    
    public void renderVBO(OpenGLRenderData rData){
        glEnableClientState(GL_VERTEX_ARRAY);
        glBindBuffer(GL_ARRAY_BUFFER, rData.getVBOID());
        glVertexPointer(3, GL_FLOAT, 32, 0);
        
        glEnableClientState(GL_NORMAL_ARRAY);
        glNormalPointer(GL_FLOAT, 32, 12);
        
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glTexCoordPointer(2, GL_FLOAT, 32, 24);
        
	glDrawArrays(GL_TRIANGLES, 0, rData.getVBOSize());
        
	glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
    public void renderVBOWired(OpenGLRenderData rData){
        glEnableClientState(GL_VERTEX_ARRAY);
        glBindBuffer(GL_ARRAY_BUFFER, rData.getVBOIDWired());
        glVertexPointer(3, GL_FLOAT, 32, 0);
        
        glEnableClientState(GL_NORMAL_ARRAY);
        glNormalPointer(GL_FLOAT, 32, 12); //3*4 = 12 (3 vertexdaten * 4 wegen float)
        
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glTexCoordPointer(2, GL_FLOAT, 32, 24); //6*4 = 24 (3 Vertex und 3 Normals * 4 wegen float
        
	glDrawArrays(GL_LINES, 0, rData.getVBOSizeWired());
        
	glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
    public void drawObject(SEMesh mesh){
        glBegin(GL_TRIANGLES);
        for(SEFace face : mesh.getFaces()){
            glNormal3f(mesh.getNormals().get(face.n1).nX, mesh.getNormals().get(face.n1).nY, mesh.getNormals().get(face.n1).nZ);
            glTexCoord2f(mesh.getUVs().get(face.uv1).u, mesh.getUVs().get(face.uv1).v);
            glVertex3f(mesh.getVertecies().get(face.v1).vX, mesh.getVertecies().get(face.v1).vY, mesh.getVertecies().get(face.v1).vZ);

            glNormal3f(mesh.getNormals().get(face.n2).nX, mesh.getNormals().get(face.n2).nY, mesh.getNormals().get(face.n2).nZ);
            glTexCoord2f(mesh.getUVs().get(face.uv2).u, mesh.getUVs().get(face.uv2).v);
            glVertex3f(mesh.getVertecies().get(face.v2).vX, mesh.getVertecies().get(face.v2).vY, mesh.getVertecies().get(face.v2).vZ);

            glNormal3f(mesh.getNormals().get(face.n3).nX, mesh.getNormals().get(face.n3).nY, mesh.getNormals().get(face.n3).nZ);
            glTexCoord2f(mesh.getUVs().get(face.uv3).u, mesh.getUVs().get(face.uv3).v);
            glVertex3f(mesh.getVertecies().get(face.v3).vX, mesh.getVertecies().get(face.v3).vY, mesh.getVertecies().get(face.v3).vZ);
        }
        glEnd();
    }
    
    public void drawObjectWired(SEMesh mesh){
        glBegin(GL_LINES);
        for(SEFace face : mesh.getFaces()){
            glTexCoord2f(mesh.getUVs().get(face.uv1).u, mesh.getUVs().get(face.uv1).v);
            glVertex3f(mesh.getVertecies().get(face.v1).vX, mesh.getVertecies().get(face.v1).vY, mesh.getVertecies().get(face.v1).vZ);
            glTexCoord2f(mesh.getUVs().get(face.uv2).u, mesh.getUVs().get(face.uv2).v);
            glVertex3f(mesh.getVertecies().get(face.v2).vX, mesh.getVertecies().get(face.v2).vY, mesh.getVertecies().get(face.v2).vZ);
            glTexCoord2f(mesh.getUVs().get(face.uv1).u, mesh.getUVs().get(face.uv1).v);
            glVertex3f(mesh.getVertecies().get(face.v1).vX, mesh.getVertecies().get(face.v1).vY, mesh.getVertecies().get(face.v1).vZ);
            glTexCoord2f(mesh.getUVs().get(face.uv3).u, mesh.getUVs().get(face.uv3).v);
            glVertex3f(mesh.getVertecies().get(face.v3).vX, mesh.getVertecies().get(face.v3).vY, mesh.getVertecies().get(face.v3).vZ);
            glTexCoord2f(mesh.getUVs().get(face.uv2).u, mesh.getUVs().get(face.uv2).v);
            glVertex3f(mesh.getVertecies().get(face.v2).vX, mesh.getVertecies().get(face.v2).vY, mesh.getVertecies().get(face.v2).vZ);
            glTexCoord2f(mesh.getUVs().get(face.uv3).u, mesh.getUVs().get(face.uv3).v);
            glVertex3f(mesh.getVertecies().get(face.v3).vX, mesh.getVertecies().get(face.v3).vY, mesh.getVertecies().get(face.v3).vZ);
        }
        glEnd();
    }
    
    public void generateDisplayListWired(SEMesh mesh, OpenGLRenderData rData){
        rData.setDisplayList(glGenLists(1));
        glNewList(rData.getDisplayList(), GL_COMPILE);
        drawObjectWired(mesh);
        glEndList();
    }
    
    public void generateDisplayList(SEMesh mesh, OpenGLRenderData rData){
        rData.setDisplayList(glGenLists(1));
        glNewList(rData.getDisplayList(), GL_COMPILE);
        drawObject(mesh);
        glEndList();
    }
    
    public void gluPerspective(float fovy, float aspect, float zNear, float zFar) {
       
        float sine, cotangent, deltaZ;
        float radians = fovy / 2 * (float) Math.PI / 180;

        deltaZ = zFar - zNear;
        sine = (float) Math.sin(radians);

        if ((deltaZ == 0) || (sine == 0) || (aspect == 0)) {
                return;
        }

        cotangent = (float) Math.cos(radians) / sine;

        gluMakeIdentityf(matrix);

        matrix.put(0 * 4 + 0, cotangent / aspect);
        matrix.put(1 * 4 + 1, cotangent);
        matrix.put(2 * 4 + 2, - (zFar + zNear) / deltaZ);
        matrix.put(2 * 4 + 3, -1);
        matrix.put(3 * 4 + 2, -2 * zNear * zFar / deltaZ);
        matrix.put(3 * 4 + 3, 0);

        glMultMatrixf(matrix);
    }
    
    private void gluMakeIdentityf(FloatBuffer m) {
        int oldPos = m.position();
        m.put(IDENTIY_MATRIX);
        m.position(oldPos);
    }
    
    public void gluLookAt(Vector3f eye, Vector3f center, Vector3f up){
        Vector3f forward = new Vector3f();
        Vector3f side = new Vector3f();
        Vector3f upv = new Vector3f();

        forward.x = center.x - eye.x;
        forward.y = center.y - eye.y;
        forward.z = center.z - eye.z;

        upv.x = up.x;
        upv.y = up.y;
        upv.z = up.z;

        forward.normalize();
        forward.cross(up, side);
        side.normalize();
        side.cross(forward, up);

        gluMakeIdentityf(matrix);
        matrix.put(0 * 4 + 0, side.x);
        matrix.put(1 * 4 + 0, side.y);
        matrix.put(2 * 4 + 0, side.z);

        matrix.put(0 * 4 + 1, up.x);
        matrix.put(1 * 4 + 1, up.y);
        matrix.put(2 * 4 + 1, up.z);

        matrix.put(0 * 4 + 2, -forward.x);
        matrix.put(1 * 4 + 2, -forward.y);
        matrix.put(2 * 4 + 2, -forward.z);

        glMultMatrixf(matrix);
        glTranslatef(-eye.x, -eye.y, -eye.z);
    }
}
