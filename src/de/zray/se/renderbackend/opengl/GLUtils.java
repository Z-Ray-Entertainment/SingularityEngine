/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

import de.zray.se.grapics.semesh.SEFace;
import de.zray.se.grapics.semesh.SEMaterial;
import de.zray.se.grapics.semesh.SEMesh;
import de.zray.se.grapics.semesh.SEMeshData;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

/**
 *
 * @author vortex
 */
public class GLUtils {
    public void generateVBO(SEMeshData mesh, OpenGLRenderData rData){
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
        vboData = null;
    }
    
    public void generateVBOWired(SEMeshData mesh, OpenGLRenderData rData){
        rData.setVBOSizeWired(mesh.getFaces().size()*6*8); //Größe für den FloatBuffer (8 Werte pro Vertex)
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
	vboData = null;
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
    
    public void drawObject(SEMeshData mesh){
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
    
    public void drawObjectWired(SEMeshData mesh){
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
    
    public void generateDisplayListWired(SEMeshData mesh, OpenGLRenderData rData){
        rData.setDisplayList(glGenLists(1));
        glNewList(rData.getDisplayList(), GL_COMPILE);
        drawObjectWired(mesh);
        glEndList();
    }
    
    private void loadTexture(String file, OpenGLRenderData rData){
        rData.setDiffuseTextureID(glGenTextures());
        glBindTexture(GL_TEXTURE_2D, rData.getDiffuseTextureID());
        
        GLTexture diffuse = new GLTexture();
        diffuse.loadTexture(file);
        
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, diffuse.getImageWidth(), diffuse.getImageHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, diffuse.getImageBuffer());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    }
    
    private void applyTextures(SEMaterial mat, OpenGLRenderData rData){
        if(mat.getTexture() != null && !mat.getTexture().isEmpty()){
            if(rData.getDiffuseTextureID() == -1){
                loadTexture(mat.getTexture(), rData);
            }
            glBindTexture(GL_TEXTURE_2D, rData.getDiffuseTextureID());
            glEnable(GL_TEXTURE_2D);
        }
        else{
            glDisable(GL_TEXTURE_2D);
        }
    }
    
    public void applyMaterial(SEMaterial mat, OpenGLRenderData rData){
        if(mat.cullBackfaces() && !glIsEnabled(GL_CULL_FACE)){
            glEnable(GL_CULL_FACE);
        }
        else if(glIsEnabled(GL_CULL_FACE)){
            glDisable(GL_CULL_FACE);
        }
        if(mat.isShadeless()){
            glDisable(GL_LIGHTING);
            if(mat.getTransparency() > 0){
                glEnable(GL_BLEND);
                glBlendFunc(GL_ONE, GL_SRC_COLOR);
            }
            glColor4f(mat.getDiffiseColor().x, mat.getDiffiseColor().y, mat.getDiffiseColor().z, mat.getTransparency());
        }
        else{
            glEnable(GL_LIGHTING);
            if(mat.getTransparency() > 0){
                glEnable(GL_BLEND);
                glBlendFunc(GL_ONE, GL_SRC_COLOR);
            }
        }
        float[] diffuse = new float[]{mat.getDiffiseColor().x, mat.getDiffiseColor().y, mat.getDiffiseColor().z, mat.getTransparency()};
        float[] spec = new float[]{mat.getSpecularColor().x, mat.getSpecularColor().y, mat.getSpecularColor().z, mat.getTransparency()};
        
        glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE, diffuse);
        glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, spec);
        applyTextures(mat, rData);
    }
    
    public void generateDisplayList(SEMeshData mesh, OpenGLRenderData rData){
        rData.setDisplayList(glGenLists(1));
        glNewList(rData.getDisplayList(), GL_COMPILE);
        drawObject(mesh);
        glEndList();
    }
}
