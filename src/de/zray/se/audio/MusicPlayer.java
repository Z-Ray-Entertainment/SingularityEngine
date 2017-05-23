/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.audio;

import java.util.List;

/**
 *
 * @author vortex
 */
public class MusicPlayer {
    private static int NO_TRACK_PLAYING = -1;
    private List<AudioSource> bgm;
    private int runningTrack = -1;
    
    public void addTrack(AudioSource track){
        bgm.add(track);
    }
    
    public void playRandomTrack(){
        if(runningTrack == NO_TRACK_PLAYING){
            
        }
    }
}
