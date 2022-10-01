/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se;

/**
 *
 * @author Vortex Acherontic
 */
public class SEUtils {
    public String getSuffix(String file){
        int lastDot = 0;
        for(int i = 0; i < file.length(); i++){
            if(file.substring(i, i+1).equals(".")){
                lastDot = i;
            }
        }
        
        return file.substring(lastDot+1, file.length());
    }
}
