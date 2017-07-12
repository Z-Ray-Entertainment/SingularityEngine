/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.grapics.modelloader.OBJLoader;

import de.zray.se.grapics.semesh.SEMaterial;
import de.zray.se.grapics.modelloader.LoaderModule;
import de.zray.se.grapics.semesh.SEFace;
import de.zray.se.grapics.semesh.SEMesh;
import de.zray.se.grapics.semesh.SENormal;
import de.zray.se.grapics.semesh.SEUV;
import de.zray.se.grapics.semesh.SEVertex;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vortex Acherontic
 */
public class OBJLoader extends LoaderModule{
    public OBJLoader(){
        super(new String[]{"obj"});
    }
    
    @Override
    public SEMesh loadModel(String file) {
        try {
            String objFile = loadTextFile(file);
            List<OBJGroup> groups = readGroups(objFile);
            SEMesh root = objGroupToSEMesh(groups.get(0));
            for(int i = 1; i < groups.size(); i++){
                root.addSubMesh(objGroupToSEMesh(groups.get(i)));
            }
            return root;
        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    private SEMesh objGroupToSEMesh(OBJGroup group){
        List<SEVertex> seVerts = new ArrayList<>();
        List<SEUV> seUVs = new ArrayList<>();
        List<SENormal> seNormals = new ArrayList<>();
        List<SEFace> faces = new ArrayList<>();
        
        for(int i = 0; i < group.verts.size(); i++){
            seVerts.add(new SEVertex(group.verts.get(i).x, group.verts.get(i).y, group.verts.get(i).z));
        }
        for(int i = 0; i < group.uvs.size(); i++){
            seUVs.add(new SEUV(group.uvs.get(i).u, group.uvs.get(i).v));
        }
        for(int i = 0; i < group.normlas.size(); i++){
            seNormals.add(new SENormal(group.normlas.get(i).x, group.normlas.get(i).y, group.normlas.get(i).z));
        }
        for(OBJGroup.Face tmp : group.faces){
            faces.add(new SEFace(tmp.v1-1, tmp.v2-1, tmp.v3-1, tmp.uv1-1, tmp.uv2-1, tmp.uv3-1, tmp.n1-1, tmp.n2-1, tmp.n3-1));//-1 da OBJ Format mit 1 Indiziert
        }
        return new SEMesh(seVerts, seUVs, seNormals, faces, null, new SEMaterial());
    }
    
    private String loadTextFile(String file) throws FileNotFoundException,
            IOException{
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file)));
        String line = "", textFile = "";
        while((line = reader.readLine()) != null){
            textFile += line.toLowerCase()+"\n";
        }
        return textFile;
    }
    
    private String getMTLFromOBJ(String objFile){
        String lines[] = objFile.split("\n");
        for(String str : lines){
            if(str.startsWith("mtllib")){
                return str.split(" ")[1];
            }
        }
        return null;
    }
    
    private List<OBJGroup> readGroups(String objFile){
        List<OBJGroup> objGroups = new ArrayList<>();
        OBJGroup currentGroup = null;
        String lines[] = objFile.split("\n");
        for(String tmp : lines){
            String args[] = tmp.split(" ");
            for(int i = 0; i < args.length; i++){
                switch(args[i]){
                    case "o" :
                    case "g" :
                        if(currentGroup != null){
                            objGroups.add(currentGroup);
                        }
                        currentGroup = new OBJGroup();
                        break;
                    case "v" :
                        currentGroup.addVertex(
                                (float) Double.parseDouble(args[1]), 
                                (float) Double.parseDouble(args[2]), 
                                (float) Double.parseDouble(args[3]));
                        break;
                    case "vt" :
                        currentGroup.addUV(
                                (float) Double.parseDouble(args[1]),
                                (float) Double.parseDouble(args[2]));
                        break;
                    case "vn" :
                        currentGroup.addNormal(
                                (float) Double.parseDouble(args[1]),
                                (float) Double.parseDouble(args[2]),
                                (float) Double.parseDouble(args[3]));
                        break;
                    case "usemtl" :
                        break;
                    case "s" :
                        break;
                    case "f" :
                        String ids1[] = args[1].split("/");
                        String ids2[] = args[2].split("/");
                        String ids3[] = args[3].split("/");
                        currentGroup.addFace(
                                Integer.parseInt(ids1[0]),
                                Integer.parseInt(ids2[0]),
                                Integer.parseInt(ids3[0]),
                                Integer.parseInt(ids1[1]),
                                Integer.parseInt(ids2[1]),
                                Integer.parseInt(ids3[1]),
                                Integer.parseInt(ids1[2]),
                                Integer.parseInt(ids2[2]),
                                Integer.parseInt(ids3[2]));
                        break;
                }
            }
        }
        objGroups.add(currentGroup);
        return objGroups;
    }
}
