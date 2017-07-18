/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storages;

import de.zray.se.grapics.semesh.SEMeshData;

/**
 *
 * @author vortex
 */
public class AssetLibrary {
    private static AssetLibrary lib = new AssetLibrary();
    private MeshLibrary meshLib;
    
    public AssetLibrary(){
        meshLib = new MeshLibrary();
    }
    
    public static AssetLibrary get(){
        return lib;
    }
    
    public int addMesh(SEMeshData mesh){
        return meshLib.addMesh(mesh);
    }
    
    public SEMeshData getMesh(int id){
        return meshLib.getMesh(id);
    }
    
}
