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
import org.lwjgl.openal.ALC10;

/**
 *
 * @author vortex
 */
public class AudioSource {
    private int bufferID, alSource;
    private long duration;
    private Vector3f pos = new Vector3f(0, 0, 0);
    
    public AudioSource(int bufferID, int alSource){
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
    
    public void playAsSound(boolean loop){
        alSourcei(alSource, AL_BUFFER, bufferID);
        alSource3f(alSource, AL_POSITION, pos.x, pos.y, pos.z);
        if(loop){
            alSourcei(alSource, AL_LOOPING, AL_TRUE);
        }
        alSourcePlay(alSource);
    }
    
    public void setPosition(Vector3f position){
        this.pos = position;
        alSource3f(alSource, AL_POSITION, pos.x, pos.y, pos.z);
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
