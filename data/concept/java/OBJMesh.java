/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package singularityengine.graphicengine.rendersystem.renderobjects;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import singularityengine.graphicengine.assetsystem.AssetsManager;
import singularityengine.graphicengine.assetsystem.OBJManager;
import singularityengine.graphicengine.rendersystem.Material;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
/**
 *
 * @author vortex
 */
public class OBJMesh extends AbstractLODObj{
    int meshID;
    
    public OBJMesh(Material mat, int model){
        super(mat);
        meshID = model;
        setRenderMode(AbstractLODObj.RENDERMODE_DISPLAY_LIST);
    }
    
    @Override
    public void directCall() {
        OBJManager.OBJRawMesh raw = AssetsManager.getOBJManager().getMesh(meshID);
        glBegin(GL_TRIANGLES);
        for(int i = 0; i < raw.getGroups().size(); i++){
            for(int j = 0; j < raw.getGroups().get(i).getFaces().size(); j++){
                OBJManager.OBJRawMesh.OBJFace tmpFace = raw.getGroups().get(i).getFaces().get(j);
                for(int k = 0; k < tmpFace.getIndecies().size(); k++){
                    OBJManager.OBJRawMesh.OBJVertex v = raw.getVertecies().get(tmpFace.getIndecies().get(k).getVertexID()-1);
                    glVertex3f(v.getX(), v.getY(), v.getZ());
                    try{
                        OBJManager.OBJRawMesh.OBJNormal n = raw.getNormals().get(tmpFace.getIndecies().get(k).getNormalID()-1);
                        glNormal3f(n.getX(), n.getY(), n.getZ());
                    }
                    catch(ArrayIndexOutOfBoundsException e){}
                    
                    try{
                        OBJManager.OBJRawMesh.OBJUV uv = raw.getUVs().get(tmpFace.getIndecies().get(k).getUVID()-1);
                        glTexCoord2f(uv.getU(), uv.getV());
                    }
                    catch(ArrayIndexOutOfBoundsException e){}
                    catch(NullPointerException e){}
                }
            }
        }
        glEnd();
    }

    @Override
    public boolean generateVBO() {
        OBJManager.OBJRawMesh raw = AssetsManager.getOBJManager().getMesh(meshID);
        
	vboSize = 0;
        boolean normals = (raw.getNormals().size() > 0);
        boolean texture = (raw.getUVs().size() > 0);
        int groups = raw.getGroups().size();
        int faces = 0;
        for(int i = 0; i < groups; i++){
            faces += raw.getGroups().get(i).getFaces().size()*3*3;
        }
	vboSize = faces*3*3*2*3;
        System.err.println("VBOSIZE: "+vboSize);
	FloatBuffer vboBuffer = BufferUtils.createFloatBuffer(vboSize);
        
	for(int i = 0; i < raw.getGroups().size(); i++){
            for(int j = 0; j < raw.getGroups().get(i).getFaces().size(); j++){
                OBJManager.OBJRawMesh.OBJFace tmpFace = raw.getGroups().get(i).getFaces().get(j);
                for(int k = 0; k < tmpFace.getIndecies().size(); k++){
                    OBJManager.OBJRawMesh.OBJVertex v = raw.getVertecies().get(tmpFace.getIndecies().get(k).getVertexID()-1);
                    vboBuffer.put(v.getX());
                    vboBuffer.put(v.getY());
                    vboBuffer.put(v.getZ());
                    if(normals){
                        OBJManager.OBJRawMesh.OBJNormal n = raw.getNormals().get(tmpFace.getIndecies().get(k).getNormalID()-1);
                        glNormal3f(n.getX(), n.getY(), n.getZ());
                        vboBuffer.put(n.getX());
                        vboBuffer.put(n.getY());
                        vboBuffer.put(n.getZ());
                    }
                    if(texture){
                        OBJManager.OBJRawMesh.OBJUV uv = raw.getUVs().get(tmpFace.getIndecies().get(k).getUVID()-1);
                        vboBuffer.put(uv.getU());
                        vboBuffer.put(uv.getV());
                    }
                }
            }
        }
	
	vboBuffer.flip();
	
	vboID = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vboBuffer, GL_STATIC_DRAW);
	
	vboBuffer.clear();
        
        return true;
    }

    @Override
    public boolean generateDisplayList() {
        displayList = glGenLists(1);
        glNewList(displayList, GL_COMPILE);
        directCall();
        glEndList();
        return true;
    }

    @Override
    public void drawVBO() {
        glEnableClientState(GL_VERTEX_ARRAY);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glVertexPointer(3, GL_FLOAT, 32, 0);
        
        glEnableClientState(GL_NORMAL_ARRAY);
        glNormalPointer(GL_FLOAT, 32, 12);
        
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glTexCoordPointer(2, GL_FLOAT, 32, 24);
        
	glDrawArrays(GL_TRIANGLES, 0, vboSize);
        
	glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
}
