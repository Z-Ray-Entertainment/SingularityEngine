/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package singularityengine.graphicengine.assetsystem;

import java.util.Vector;
import singularityengine.core.Logger;
import singularityengine.graphicengine.assetsystem.OBJManager.OBJRawMesh.OBJUV;

/**
 *
 * @author vortex
 */
public class OBJManager {
    Vector<ObjRawLib> library = new Vector<>();
    
    public static class ObjRawLib{
        OBJRawMesh mesh;
        String file;
        int relative;
        public ObjRawLib(int rel, String file, OBJRawMesh mesh){
            this.file = file;
            this.relative = rel;
            this.mesh = mesh;
        }
       
    }
    
    public static class OBJRawMesh{
        Vector<OBJGroup> groups = new Vector<>();
        Vector<OBJVertex> vertecies = new Vector<>();
        Vector<OBJNormal> normals = new Vector<>();
        Vector<OBJUV> uvs = new Vector<>();
        
        public static class OBJGroup{
            Vector<OBJFace> faces = new Vector<>();
            String id;
            
            public String getID(){
                return id;
            }
            
            public Vector<OBJFace> getFaces(){
                return faces;
            }
        }
        public static class OBJVertex{
            float x, y, z;
            
            public float getX(){
                return x;
            }
            public float getY(){
                return y;
            }
            public float getZ(){
                return z;
            }
        }
        public static class OBJNormal{
            float x, y, z;
            public float getX(){
                return x;
            }
            public float getY(){
                return y;
            }
            public float getZ(){
                return z;
            }
        }
        public static class OBJUV{
            float u, v;
            public float getU(){
                return u;
            }
            public float getV(){
                return v;
            }
        }
        public static class OBJFace{
            Vector<Indices> indicies = new Vector<>();
            public static class Indices{
                int vertex, uv, normal, flatGroup;
                
                public int getVertexID(){
                    return vertex;
                }
                public int getFlatGroup(){
                    return flatGroup;
                }
                public int getUVID(){
                    return uv;
                }
                public int getNormalID(){
                    return normal;
                }
            }
            
            public Vector<Indices> getIndecies(){
                return indicies;
            }
        }
        
        public Vector<OBJGroup> getGroups(){
            return groups;
        }
        public Vector<OBJVertex> getVertecies(){
            return vertecies;
        }
        public Vector<OBJNormal> getNormals(){
            return normals;
        }
        public Vector<OBJUV> getUVs(){
            return uvs;
        }
        
    }
    
    public int loadOBJ(int relative, String file){
        Logger.displayMSG(false, new String[]{"Loading obj mesh "+file}, true, Logger.MSG_TYPE_INFO, null);
        //TODO load ctf file (obj.ctf)
        Vector<String> lines = AssetsManager.loadTextfile(relative, file);
        OBJRawMesh raw = new OBJRawMesh();
        OBJRawMesh.OBJGroup group = null;
        for(String tmp : lines){
            String args[] = tmp.split(" ");
            switch(args[0]){
                case "#" :
                    Logger.displayMSG(false, new String[]{"OBJ Comment, ignore"}, true, Logger.MSG_TYPE_INFO, null);
                    break;
                case "v" :
                    OBJRawMesh.OBJVertex vertex = new OBJRawMesh.OBJVertex();
                    vertex.x = Float.parseFloat(args[1]);
                    vertex.y = Float.parseFloat(args[2]);
                    vertex.z = Float.parseFloat(args[3]);
                    raw.vertecies.add(vertex);
                    break;
                case "vn" :
                    OBJRawMesh.OBJNormal normal = new OBJRawMesh.OBJNormal();
                    normal.x = Float.parseFloat(args[1]);
                    normal.y = Float.parseFloat(args[2]);
                    normal.z = Float.parseFloat(args[3]);
                    raw.normals.add(normal);
                    break;
                case "vt" :
                    OBJUV uv = new OBJRawMesh.OBJUV();
                    uv.u = Float.parseFloat(args[1]);
                    uv.v = Float.parseFloat(args[2]);
                    raw.uvs.add(uv);
                    break;
                case "g" :
                case "o" :
                    if(group != null){
                        raw.groups.add(group);
                        group = null;
                    }
                    group = new OBJRawMesh.OBJGroup();
                    group.id = args[1];
                    break;
                case "f" :
                    OBJRawMesh.OBJFace face = new OBJRawMesh.OBJFace();
                    for(int i = 1; i < args.length; i++){
                        OBJRawMesh.OBJFace.Indices indices = new OBJRawMesh.OBJFace.Indices();
                        String sub[] = args[i].split("/");
                        indices.vertex = Integer.parseInt(sub[0]);
                        try{
                            indices.uv = Integer.parseInt(sub[1]);
                        }
                        catch(NumberFormatException e){
                            indices.uv = -1;
                        }
                        try{
                            indices.normal = Integer.parseInt(sub[1]);
                        }
                        catch(NumberFormatException e){
                            indices.normal = -1;
                        }
                        
                        face.indicies.add(indices);
                    }
                    group.faces.add(face);
                    break;
            }
            if(group != null){
                raw.groups.add(group);
            }
        }
        return addOBJ(relative, file, raw);
    }
    
    public OBJRawMesh getMesh(int index){
        return library.get(index).mesh;
    }
    
    private int addOBJ(int rel, String file, OBJRawMesh mesh){
        for(int i = 0; i < library.size(); i++){
            if(library.get(i) != null){
                if(library.get(i).file.equals(file) && library.get(i).relative == rel){
                    return i;
                }
            }
            else{
                library.set(i, new ObjRawLib(rel, file, mesh));
                return i;
            }
        }
        library.add(new ObjRawLib(rel, file, mesh));
        System.err.println("OBJ ID: "+(library.size()-1));
        return library.size()-1;
    }
}
