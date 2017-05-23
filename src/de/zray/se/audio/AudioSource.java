/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.audio;

import java.nio.ShortBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_LOOPING;
import static org.lwjgl.openal.AL10.AL_TRUE;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourcei;

/**
 *
 * @author vortex
 */
public class AudioSource {
    private int bufferID, alSource;
    private long duration;
    private ShortBuffer pcm;
    
    public AudioSource(int bufferID, int alSource, ShortBuffer pcm, long diration){
        this.alSource = alSource;
        this.bufferID = bufferID;
        this.pcm = pcm;
        this.duration = duration;
    }
    
    public int getSource(){
        return alSource;
    }
    
    public void play(boolean loop){
        alSourcei(alSource, AL_BUFFER, bufferID);

        if(loop){
            alSourcei(alSource, AL_LOOPING, AL_TRUE);
        }
        alSourcePlay(alSource);
    }
    
    public boolean isPlaying(){
        return false;
    }
    
    public void delete(){
        alSourceStop(alSource);
        alDeleteSources(alSource);
        alDeleteBuffers(bufferID);
    }
}
