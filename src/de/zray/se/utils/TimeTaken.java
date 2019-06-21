/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.utils;

import de.zray.se.logger.SELogger;

/**
 *
 * @author vortex
 */
public final class TimeTaken {
    private long start;
    
    public TimeTaken(){}
    
    public  TimeTaken(boolean start){
        if(start){
            start();
        }
    }
    
    public void start(){
        start = System.nanoTime();
    }
    
    public double endInSec(){
        double timeTaken = timeTaken()/1000000000.;
        //SELogger.get().dispatchMsg(TimeTaken.class, SELogger.SELogType.INFO, new String[]{"Time in Sec: "+timeTaken}, false);
        return timeTaken;
    }
    
    public double endInMilli(){
        double timeTaken = timeTaken()/1000000.;
        return timeTaken;
    }
    
    public double endInNano(){
        return timeTaken();
    }
    
    private double timeTaken(){
        double timeTaken = ((double) System.nanoTime()- (double) start);
        //System.out.println("TimeTaken: "+timeTaken/1000000000.+"s");
        return timeTaken;
    }
}