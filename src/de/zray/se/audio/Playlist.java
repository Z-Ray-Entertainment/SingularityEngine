/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.audio;

import de.zray.se.SEUtils;
import de.zray.se.Settings;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Vortex Acherontic
 */
public class Playlist {
    public enum LoopMode{LOOP_ONE, LOOP_ALL, LOOP_NONE};
    public enum PlayMode{RANDOM, CONTINUES};
    
    private boolean isPlaying = false;
    private List<Audio> clips = new ArrayList<>();
    private int currentClip = -1;
    private LoopMode loopMode = LoopMode.LOOP_ALL;
   
    public int addTrack(Audio track){
        int freeSlot = getFreeSlot();
        if(freeSlot == -1){
            clips.add(track);
            return clips.size()-1;
        }
        else{
            clips.set(freeSlot, track);
                return freeSlot;
        }
    }
    
    public int addTrack(String file) throws IOException{
        int freeSlot = getFreeSlot();
        Audio track = AudioLoader.getStreamingAudio(new SEUtils().getSuffix(file).toUpperCase(), ResourceLoader.getResource(file));
        if(freeSlot == -1){
            clips.add(track);
            return clips.size()-1;
        }
        else{
            clips.set(freeSlot, track);
            return freeSlot;
        }
    }
    
    private int getFreeSlot(){
        for(int i = 0; i < clips.size(); i++){
            if(clips.get(i) == null){
                return i;
            }
        }
        return -1;
    }
    
    public boolean isPlaying(){
        return isPlaying && SoundStore.get().isMusicPlaying();
    }
    
    public void stop(){
        isPlaying = false;
    }
    
    public void nextTrack(){
        if(currentClip + 1 >= clips.size()){
            currentClip = 0;
        }
        else{
            currentClip++;
        }
        play();
    }
    
    public void previousTrack(){
        if(currentClip - 1 < 0){
            currentClip = clips.size()-1;
        }
        else{
            currentClip--;
        }
        play();
    }
    
    public Audio getCurrentClip(){
        return clips.get(currentClip);
    }
    
    private void play(){
        isPlaying = true;
        if(loopMode == LoopMode.LOOP_ONE.LOOP_ONE){
            clips.get(currentClip).playAsMusic(Settings.get().sound.volume, Settings.get().sound.pitch, true);
        }
        else{
            clips.get(currentClip).playAsMusic(Settings.get().sound.volume, Settings.get().sound.pitch, false);
        }
    }
}
