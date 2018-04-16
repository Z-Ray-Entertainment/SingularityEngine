/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.audio;

import javax.vecmath.Vector3f;
import org.lwjgl.openal.AL10;
import static org.lwjgl.openal.AL10.AL_PLAYING;
import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_SOURCE_STATE;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alGetSourcei;
import static org.lwjgl.openal.AL10.alSource3f;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceQueueBuffers;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourceUnqueueBuffers;

/**
 *
 * @author vortex
 */
public class AudioSource {
    private int bufferID, alSource;
    private Vector3f pos = new Vector3f(0, 0, 0);
    private boolean looped = false;
    
    public AudioSource(int bufferID, int alSource){
        this.alSource = alSource;
        this.bufferID = bufferID;
    }
    
    public int getSourceID(){
        return alSource;
    }
    
    public void playAsMusic(boolean loop){
        looped = loop;
        alSourceQueueBuffers(alSource, bufferID);
        SEAudioWorld.handleALError("alSourceQueueBuffer playAsMusic");
        alSourcePlay(alSource);
        SEAudioWorld.handleALError("alSourcePlay Music");
        alSourceUnqueueBuffers(alSource);
        SEAudioWorld.handleALError("alSourceUnqueueBuffers, playAsMusic");
    }
    
    public boolean isLooped(){
        return looped;
    }
    
    public void setLooped(boolean loop){
        looped = loop;
    }
    
    public void playAsSound(boolean loop){
        alSourceQueueBuffers(alSource, bufferID);
        SEAudioWorld.handleALError("alSourceQueueBuffer playAsSound");
        alSource3f(alSource, AL_POSITION, pos.x, pos.y, pos.z);
        SEAudioWorld.handleALError("alSource3f, set Position");
        looped = loop;
        alSourcePlay(alSource);
        SEAudioWorld.handleALError("alSourcePlay Sound");
    }
    
    public void setPosition(Vector3f position){
        this.pos = position;
        alSource3f(alSource, AL_POSITION, pos.x, pos.y, pos.z);
        SEAudioWorld.handleALError("alSource3f, set Position");
    }
    
    public boolean isPlaying(){
        alSourceQueueBuffers(alSource, bufferID);
        SEAudioWorld.handleALError("alSourceQueueBuffer");
        int playing = alGetSourcei(alSource, AL_SOURCE_STATE);
        SEAudioWorld.handleALError("alGetSourcei, AL_SOURCE_STATE");
        alSourceUnqueueBuffers(alSource);
        SEAudioWorld.handleALError("alSourceUnqueueBuffers, isPlaying");
        return (playing == AL_PLAYING);
    }
    
    public void delete(){
        alSourceStop(alSource);
        alDeleteSources(alSource);
        alDeleteBuffers(bufferID);
    }
}
