/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.world;

import de.zray.se.Settings;
import de.zray.se.graphics.semesh.SEOriantation;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author vortex
 */
public class DistancePatch {
    private DistancePatch parent;
    private int level;
    private List<DistancePatch> subPatches = new LinkedList<>();
    private long pos[];
    private List<SEActor> actors = new LinkedList<>();
    
    public DistancePatch(int level, double pos[]){
        this(null, level, pos);
    }
    
    public DistancePatch(DistancePatch parent, int level, double pos[]){
        calcPosition(pos);
        this.level = level;
        this.parent = parent;
    }
    
    public boolean addActor(SEActor actor){
        SEOriantation ori = actor.getOrientation();
        if(level == Settings.get().scene.dpSizes.length-1){
            if(isInside(ori.getPosition()[0], ori.getPosition()[1], ori.getPosition()[2])){
                actors.add(actor);
                return true;
            }
        } else {
            if(subPatches.isEmpty()){
                DistancePatch sub = new DistancePatch(this.level+1, ori.getPosition());
                sub.addActor(actor);
                subPatches.add(sub);
            }
        }
        return false;
    }
    
    private void calcPosition(double pos[]){
        int edgeLength = Settings.get().scene.dpSizes[level];
        this.pos[0] = (long) Math.round((pos[0]/edgeLength));
        this.pos[1] = (long) Math.round((pos[1]/edgeLength));
        this.pos[2] = (long) Math.round((pos[2]/edgeLength));
        System.out.println("Distancepatch created at: "+pos[0]+" "+pos[1]+" "+pos[2]);
    }
    
    public boolean isInside(double x, double y, double z){
        int edgeLength = Settings.get().scene.dpSizes[level];
        
        if(!onLine(pos[0], pos[0]+edgeLength/2, x)){
            if(!onLine(pos[0], pos[0]-edgeLength/2, x)){
                return false;
            }
        } else if(!onLine(pos[1], pos[1]+edgeLength/2, y)){
            if(!onLine(pos[1], pos[1]-edgeLength/2, y)){
                return false;
            }
        } else if(!onLine(pos[2], pos[2]+edgeLength/2, z)){
            if(!onLine(pos[2], pos[2]-edgeLength/2, z)){
                return false;
            }
        }
        return true;
    }
    
    private boolean onLine(float start, float end, double num){
        return num <= end && num >= start;
    }
}
