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
public class SEAudioModule {
    private Playlist playlist;
    private List<Audio> sfx = new ArrayList<>();
    private float lastVolume = Settings.get().soundSettings.volume;
    
    public SEAudioModule(){}
    
    public  SEAudioModule(Playlist playlist){
        this.playlist = playlist;
    }
    
    public void setPlaylist(Playlist playlist){
        this.playlist = playlist;
    }
    
    public void playSoundEffect(String sfx) throws IOException{
        Audio sfxFile = AudioLoader.getStreamingAudio(new SEUtils().getSuffix(sfx).toUpperCase(), ResourceLoader.getResource(sfx));
        playSoundEffect(sfxFile);
    }
    
    public void playSoundEffect(Audio sfx){
       this.sfx.add(sfx);
        playSFX(this.sfx.size()-1, 0, 0, 0);
    }
    
    private void playSFX(int sfx, float x, float y, float z){
        this.sfx.get(sfx).playAsSoundEffect(Settings.get().soundSettings.sfxVolume, Settings.get().soundSettings.sfxPitch, false, x, y, z);
    }
    
    public void poll(){
        if(playlist != null){
            if(!playlist.isPlaying()){
                playlist.nextTrack();
            }
        }
        if(sfx.size() > 0){
            for(int i = 0; i < sfx.size(); i++){
                if(sfx.get(i) != null && !sfx.get(i).isPlaying()){
                    sfx.set(i, null);
                    if(i == sfx.size()-1){
                        sfx.remove(i);
                    }
                }
            }
        }
        SoundStore.get().poll(0);
    }
    
    public void playBGM(boolean enabled){
        if(!enabled){
            SoundStore.get().pauseLoop();
        }
        else{
            SoundStore.get().restartLoop();
        }
    }
    
    public void setBGMMuted(boolean muted){
        if(muted){
            Settings.get().soundSettings.volume = 0;
            SoundStore.get().setMusicVolume(Settings.get().soundSettings.volume);
        }
        else{
            Settings.get().soundSettings.volume = lastVolume;
            SoundStore.get().setMusicVolume(Settings.get().soundSettings.volume);
        }
    }
    
    public boolean isBGMMuted(){
        return (Settings.get().soundSettings.volume == 0);
    }   

    
    public Playlist getBGMPlayList(){
        return playlist;
    }
}
