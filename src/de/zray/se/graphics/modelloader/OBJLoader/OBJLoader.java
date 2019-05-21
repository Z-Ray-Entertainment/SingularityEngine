/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.graphics.modelloader.OBJLoader;

import de.zray.se.graphics.semesh.Material;
import de.zray.se.graphics.modelloader.LoaderModule;
import de.zray.se.graphics.semesh.Face;
import de.zray.se.graphics.semesh.Mesh;
import de.zray.se.graphics.semesh.MeshData;
import de.zray.se.graphics.semesh.Normal;
import de.zray.se.graphics.semesh.UV;
import de.zray.se.graphics.semesh.Vertex;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import de.zray.se.storages.AssetLibrary;
import java.io.File;

/**
 *
 * @author Vortex Acherontic
 */
public class OBJLoader extends LoaderModule{
    public OBJLoader(){
        super(new String[]{"obj"});
    }
    
    @Override
    public Mesh loadModel(File file) {
        try {
            String objFile = loadTextFile(file);
            ArrayList<OBJGroup> groups = readGroups(objFile);
            Mesh root = objGroupToSEMesh(groups.get(0));
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
    
    private Mesh objGroupToSEMesh(OBJGroup group){
        ArrayList<Vertex> seVerts = new ArrayList<>();
        ArrayList<UV> seUVs = new ArrayList<>();
        ArrayList<Normal> seNormals = new ArrayList<>();
        ArrayList<Face> faces = new ArrayList<>();
        
        for(int i = 0; i < group.verts.size(); i++){
            seVerts.add(new Vertex(group.verts.get(i).x, group.verts.get(i).y, group.verts.get(i).z));
        }
        for(int i = 0; i < group.uvs.size(); i++){
            seUVs.add(new UV(group.uvs.get(i).u, group.uvs.get(i).v));
        }
        for(int i = 0; i < group.normlas.size(); i++){
            seNormals.add(new Normal(group.normlas.get(i).x, group.normlas.get(i).y, group.normlas.get(i).z));
        }
        for(OBJGroup.Face tmp : group.faces){
            faces.add(new Face(tmp.v1-1, tmp.v2-1, tmp.v3-1, tmp.uv1-1, tmp.uv2-1, tmp.uv3-1, tmp.n1-1, tmp.n2-1, tmp.n3-1));//-1 da OBJ Format mit 1 Indiziert
        }
        MeshData mData = new MeshData(seVerts, seUVs, seNormals, faces, null);
        int mDataID = AssetLibrary.get().addMesh(mData);
        return new Mesh(new Material(), mDataID);
    }
    
    private String loadTextFile(File file) throws FileNotFoundException,
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
    
    private ArrayList<OBJGroup> readGroups(String objFile){
        ArrayList<OBJGroup> objGroups = new ArrayList<>();
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
