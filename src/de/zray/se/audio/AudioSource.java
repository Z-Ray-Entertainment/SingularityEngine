/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.audio;

import java.nio.ShortBuffer;
import javax.vecmath.Vector3f;
import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_LOOPING;
import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_TRUE;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alSource3f;
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
    
    public AudioSource(int bufferID, int alSource, ShortBuffer pcm){
        this.alSource = alSource;
        this.bufferID = bufferID;
    }
    
    public int getSource(){
        return alSource;
    }
    
    public void playAsMusic(boolean loop){
        alSourcei(alSource, AL_BUFFER, bufferID);

        if(loop){
            alSourcei(alSource, AL_LOOPING, AL_TRUE);
        }
        alSourcePlay(alSource);
    }
    
    public void playAsSound(Vector3f pos){
        alSourcei(alSource, AL_BUFFER, bufferID);
        alSource3f(alSource, AL_POSITION, pos.x, pos.y, pos.z);
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
