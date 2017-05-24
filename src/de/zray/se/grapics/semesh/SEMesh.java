/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics.semesh;

import de.zray.se.MainThread;
import de.zray.se.SEUtils;
import de.zray.se.Settings;
import de.zray.se.grapics.Camera;
import de.zray.se.grapics.semesh.vbo.AbstractVBO;
import de.zray.se.grapics.semesh.vbo.SolidVBO;
import de.zray.se.grapics.semesh.vbo.WiredVBO;
import de.zray.se.logger.SELogger;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.vecmath.Vector3f;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author vortex
 */
public class SEMesh{
    public static enum RenderMode{DIRECT, DISPLAY_LIST, VBO}
    public static enum DisplayMode{SOLID, WIRED, DOTS};
    
    private int renderData = -1;
    private RenderMode renderMode = RenderMode.DIRECT;
    private DisplayMode displayMode = DisplayMode.SOLID;
    private List<SEVertex> vertecies = new LinkedList<>();
    private List<SEUV> uvs = new LinkedList<>();
    private List<SENormal> normals = new LinkedList<>();
    private List<SEFace> faces = new LinkedList<>();
    private SEAmature amature = new SEAmature();
    private SEOriantation orientation = new SEOriantation();
    private SEMaterial material = new SEMaterial(), debugMat;
    private boolean backfaceCulling = false, vboRendered = false;
    private List <SEMesh> lods = new LinkedList<>();
    private List<SEMesh> subMeshes = new LinkedList<>();
    public UUID uuid = UUID.randomUUID();
    private BoundingBox bb;
    private Vector3f direction;
    private float renderDist = -1;
    
    public SEMesh(List<SEVertex> vertexList, List<SEUV> uvs, List<SENormal> normals, List<SEFace> faceList, SEAmature amature, SEMaterial material){
        this.amature = amature;
        this.faces = faceList;
        this.material = material;
        this.vertecies = vertexList;
        this.uvs = uvs;
        this.normals = normals;
        orientation = new SEOriantation();
        this.bb = new BoundingBox(vertecies);
        this.direction = new Vector3f(0, 0, -1);
    }
    
    public void setRenderData(int index){
        this.renderData = index;
    }
    
    public int getRenderData(){
        return renderData;
    }
    
    public void printMeshData(){
        String lines[] = {"SEMESH: "+uuid.toString(),
            "Vertex: "+vertecies.size(), "UVs: "+uvs.size(), 
            "Normals: "+normals.size(), "Faces: "+faces.size()};
        SELogger.get().dispatchMsg("SEMesh", SELogger.SELogType.INFO, lines, false);
    }
    
    public float getRenderDistance(){
        return renderDist;
    }
    
    public void setRenderDist(float renderDist){
        this.renderDist = renderDist;
    }
    
    public void setDirection(Vector3f direction){
        this.direction = direction;
    }
    
    public Vector3f getDirection(){
        return direction;
    }
    
    public List<SEFace> getFaces(){
        return faces;
    }
    
    public List<SEVertex> getVertecies(){
        return vertecies;
    }
    
    public List<SEUV> getUVs(){
        return uvs;
    }
    
    public List<SENormal> getNormals(){
        return normals;
    }
    
    public void addSubMesh(SEMesh subMesh){
        subMeshes.add(subMesh);
    }
    
    public void setMaterial(SEMaterial mat){
        this.material = mat;
    }
    
    public void setRenderMode(RenderMode rMode){
        this.renderMode = rMode;
    }
    
    public void addLOD(SEMesh mesh, float distance){
        mesh.setRenderDist(distance);
        mesh.setOrientation(orientation);
        lods.add(mesh);
    }
    
    public void setBackfaceCulling(boolean enabled){
        backfaceCulling = enabled;
    }
    
    public void setOrientation(SEOriantation orientation){
        this.orientation = orientation;
    }
    
    public SEOriantation getOrientation(){
        return orientation;
    }
    
    public void setDisplayMpode(DisplayMode displayMode){
        this.displayMode = displayMode;
    }
    
    private void call(){
        switch(displayMode){
            case SOLID:
                glBegin(GL_TRIANGLES);
                for(SEFace face : faces){
                    glNormal3f(normals.get(face.n1).nX, normals.get(face.n1).nY, normals.get(face.n1).nZ);
                    glTexCoord2f(uvs.get(face.uv1).u, uvs.get(face.uv1).v);
                    glVertex3f(vertecies.get(face.v1).vX, vertecies.get(face.v1).vY, vertecies.get(face.v1).vZ);
                    
                    glNormal3f(normals.get(face.n2).nX, normals.get(face.n2).nY, normals.get(face.n2).nZ);
                    glTexCoord2f(uvs.get(face.uv2).u, uvs.get(face.uv2).v);
                    glVertex3f(vertecies.get(face.v2).vX, vertecies.get(face.v2).vY, vertecies.get(face.v2).vZ);
                    
                    glNormal3f(normals.get(face.n3).nX, normals.get(face.n3).nY, normals.get(face.n3).nZ);
                    glTexCoord2f(uvs.get(face.uv3).u, uvs.get(face.uv3).v);
                    glVertex3f(vertecies.get(face.v3).vX, vertecies.get(face.v3).vY, vertecies.get(face.v3).vZ);
                }
                glEnd();
                break;
            case WIRED :
                glBegin(GL_LINES);
                for(SEFace face : faces){
                    glTexCoord2f(uvs.get(face.uv1).u, uvs.get(face.uv1).v);
                    glVertex3f(vertecies.get(face.v1).vX, vertecies.get(face.v1).vY, vertecies.get(face.v1).vZ);
                    glTexCoord2f(uvs.get(face.uv2).u, uvs.get(face.uv2).v);
                    glVertex3f(vertecies.get(face.v2).vX, vertecies.get(face.v2).vY, vertecies.get(face.v2).vZ);
                    glTexCoord2f(uvs.get(face.uv1).u, uvs.get(face.uv1).v);
                    glVertex3f(vertecies.get(face.v1).vX, vertecies.get(face.v1).vY, vertecies.get(face.v1).vZ);
                    glTexCoord2f(uvs.get(face.uv3).u, uvs.get(face.uv3).v);
                    glVertex3f(vertecies.get(face.v3).vX, vertecies.get(face.v3).vY, vertecies.get(face.v3).vZ);
                    glTexCoord2f(uvs.get(face.uv2).u, uvs.get(face.uv2).v);
                    glVertex3f(vertecies.get(face.v2).vX, vertecies.get(face.v2).vY, vertecies.get(face.v2).vZ);
                    glTexCoord2f(uvs.get(face.uv3).u, uvs.get(face.uv3).v);
                    glVertex3f(vertecies.get(face.v3).vX, vertecies.get(face.v3).vY, vertecies.get(face.v3).vZ);
                }
                glEnd();
                break;
            case DOTS :
                break;
        }
        
    }
    
    private void createList(){
        /*displayList = glGenLists(1);
        glNewList(displayList, GL_COMPILE);
        call();
        glEndList();*/
    }
    
    private void renderDebug(){
        glDisable(GL_LIGHTING);
        glLineWidth(1);
        glBegin(GL_LINES);
            glColor4f(0, 1, 0, 1);
            glVertex3f(0, -.5f, 0);
            glVertex3f(0, .5f, 0);
            glColor4f(1, 0, 0, 1);
            glVertex3f(-.5f, 0, 0);
            glVertex3f(.5f, 0, 0);
            glColor4f(0, 0, 1, 1);
            glVertex3f(0, 0, -.5f);
            glVertex3f(0, 0, .5f);
        glEnd();
        glColor4f(1, 1, 1, 1);
    }
    
    private void renderSEMesh() throws IOException{
        if(inView()){
            material.apply();
            if(backfaceCulling && !glIsEnabled(GL_CULL_FACE)){
                glEnable(GL_CULL_FACE);
            }
            else if(glIsEnabled(GL_CULL_FACE)){
                glDisable(GL_CULL_FACE);
            }

            switch(renderMode){
                case DIRECT :
                    call();
                    break;
                case DISPLAY_LIST :
                    /*if(displayList == -1){
                        createList();
                    }
                    glCallList(displayList);*/
                    break;
                case VBO :
                    /*vbo.render();
                    if(faces != null){
                        vertecies.clear();
                        faces.clear();
                        uvs.clear();
                        normals.clear();
                    }*/
                    break;
            }
            if(subMeshes != null && !subMeshes.isEmpty()){
                for(SEMesh sub : subMeshes){
                    sub.render();
                }
            }
        }
        else{
            for(SEMesh lod : lods){
                if(lod.inView()){
                    lod.renderSEMesh();
                    return;
                }
            }
        }
    }
    
    public boolean inView(){
        /*Camera cam = MainThread.getCurrentWorld().getGLModule().getCurrentCamera();
        Vector3f posCam = new Vector3f(cam.getPosition().x, cam.getPosition().y, cam.getPosition().z);
        Vector3f posMesh = orientation.getPositionVec();
        float dist = SEUtils.getLenght(SEUtils.getVector(posMesh, posCam));
        return (renderDist == -1 || dist < renderDist);*/
        return true;
    }
    
    public void render() throws IOException{
        glPushMatrix();
        orientation.apply();
        switch(Settings.get().debug.debugMode){
            case DEBUG_AND_OBJECTS:
                renderDebug();
                renderSEMesh();
                bb.debug();
                break;
            case DEBUG_ON :
                renderDebug();
                bb.debug();
                break;
            case DEBUG_OFF :
                renderSEMesh();
                break;
        }
        glPopMatrix();
    }
}
