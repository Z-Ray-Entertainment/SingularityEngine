/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.audio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_close;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_get_info;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_get_samples_short_interleaved;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_open_memory;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_stream_length_in_samples;
import org.lwjgl.stb.STBVorbisInfo;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 *
 * @author vortex
 */
public class Audio {
    private int bufferID = -1;
    
    public Audio(String file){
        bufferID = alGenBuffers();
        try{
            ShortBuffer pcm = readVorbis(file, 1024, null);
        }
        catch(RuntimeException e){}
    }
    
    private ShortBuffer readVorbis(String file, int bufferSize, STBVorbisInfo info){
        ByteBuffer vorbis = BufferUtils.createByteBuffer(bufferSize);
        IntBuffer error = BufferUtils.createIntBuffer(1);
        long decoder = stb_vorbis_open_memory(vorbis, error, null);
        if(decoder == NULL){
            throw new RuntimeException("Failed to load Ogg Vorbis file. "+error.get(0));
        }
        
        stb_vorbis_get_info(decoder, info);
        int channels = info.channels();
        int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);
        
        ShortBuffer pcm = BufferUtils.createShortBuffer(lengthSamples);
        pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
        stb_vorbis_close(decoder);
        
        return pcm;
    }
}
