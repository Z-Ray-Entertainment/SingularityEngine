/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics.semesh.vbo;

import de.zray.se.grapics.semesh.SEFace;
import de.zray.se.grapics.semesh.SENormal;
import de.zray.se.grapics.semesh.SEUV;
import de.zray.se.grapics.semesh.SEVertex;
import de.zray.se.logger.SELogger;
import java.nio.FloatBuffer;
import java.util.List;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

/**
 *
 * @author Vortex Acherontic
 */
public class WiredVBO extends AbstractVBO{

    public WiredVBO(List<SEVertex> v, List<SEUV> uv, List<SENormal> n, List<SEFace> f, VBOMode m) {
        super(v, uv, n, f, m);
    }

    @Override
    public void render() {
        if(id == -1){
            build();
        }
        
        glEnableClientState(GL_VERTEX_ARRAY);
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glVertexPointer(3, GL_FLOAT, 32, 0);
        
        glEnableClientState(GL_NORMAL_ARRAY);
        glNormalPointer(GL_FLOAT, 32, 12); //3*4 = 12 (3 vertexdaten * 4 wegen float)
        
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glTexCoordPointer(2, GL_FLOAT, 32, 24); //6*4 = 24 (3 Vertex und 3 Normals * 4 wegen float
        
	glDrawArrays(GL_LINES, 0, size);
        
	glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void build() {
        
        size = (faces.size()*6*8); //Größe für den FloatBuffer (8 Werte pro Vertex)
        SELogger.get().dispatchMsg("WiredVBO", SELogger.SELogType.INFO, new String[]{"Generate VBO with size: "+size}, false);
        FloatBuffer vboData = BufferUtils.createFloatBuffer(size);
        
        for(SEFace face : faces){
            vboData.put(vertecies.get(face.v1).vX);
            vboData.put(vertecies.get(face.v1).vY);
            vboData.put(vertecies.get(face.v1).vZ);
            vboData.put(normals.get(face.n1).nX);
            vboData.put(normals.get(face.n1).nY);
            vboData.put(normals.get(face.n1).nZ);
            vboData.put(uvs.get(face.uv1).u);
            vboData.put(uvs.get(face.uv1).v);
            
            vboData.put(vertecies.get(face.v2).vX);
            vboData.put(vertecies.get(face.v2).vY);
            vboData.put(vertecies.get(face.v2).vZ);
            vboData.put(normals.get(face.n2).nX);
            vboData.put(normals.get(face.n2).nY);
            vboData.put(normals.get(face.n2).nZ);
            vboData.put(uvs.get(face.uv2).u);
            vboData.put(uvs.get(face.uv2).v);
            
            vboData.put(vertecies.get(face.v3).vX);
            vboData.put(vertecies.get(face.v3).vY);
            vboData.put(vertecies.get(face.v3).vZ);
            vboData.put(normals.get(face.n3).nX);
            vboData.put(normals.get(face.n3).nY);
            vboData.put(normals.get(face.n3).nZ);
            vboData.put(uvs.get(face.uv3).u);
            vboData.put(uvs.get(face.uv3).v);
            
            vboData.put(vertecies.get(face.v1).vX);
            vboData.put(vertecies.get(face.v1).vY);
            vboData.put(vertecies.get(face.v1).vZ);
            vboData.put(normals.get(face.n1).nX);
            vboData.put(normals.get(face.n1).nY);
            vboData.put(normals.get(face.n1).nZ);
            vboData.put(uvs.get(face.uv1).u);
            vboData.put(uvs.get(face.uv1).v);
            
            vboData.put(vertecies.get(face.v3).vX);
            vboData.put(vertecies.get(face.v3).vY);
            vboData.put(vertecies.get(face.v3).vZ);
            vboData.put(normals.get(face.n3).nX);
            vboData.put(normals.get(face.n3).nY);
            vboData.put(normals.get(face.n3).nZ);
            vboData.put(uvs.get(face.uv3).u);
            vboData.put(uvs.get(face.uv3).v);
            
            vboData.put(vertecies.get(face.v2).vX);
            vboData.put(vertecies.get(face.v2).vY);
            vboData.put(vertecies.get(face.v2).vZ);
            vboData.put(normals.get(face.n2).nX);
            vboData.put(normals.get(face.n2).nY);
            vboData.put(normals.get(face.n2).nZ);
            vboData.put(uvs.get(face.uv2).u);
            vboData.put(uvs.get(face.uv2).v);
        }
        vboData.flip();
        id = glGenBuffers();
        
        switch(mode){
            case DYNAMIC :
                glBindBuffer(GL_ARRAY_BUFFER, id);
                glBufferData(GL_ARRAY_BUFFER, vboData, GL_DYNAMIC_DRAW);
                break;
            case STATIC :
                glBindBuffer(GL_ARRAY_BUFFER, id);
                glBufferData(GL_ARRAY_BUFFER, vboData, GL_STATIC_DRAW);
                break;
        }
	vboData.clear();
        vertecies.clear();
        faces.clear();
        normals.clear();
        uvs.clear();
    }

    @Override
    public void destroy() {
        glDeleteBuffers(id);
    }
    
}
