/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.audio;

import de.zray.se.SEModule;
import de.zray.se.SEWorld;
import de.zray.se.logger.SELogger;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.ALC.createCapabilities;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.ALC11.*;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALUtil;

import static org.lwjgl.openal.EXTThreadLocalContext.alcSetThreadContext;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_close;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_get_info;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_get_samples_short_interleaved;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_open_memory;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_stream_length_in_samples;
import org.lwjgl.stb.STBVorbisInfo;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 *
 * @author Vortex Acherontic
 */
public class SEAudioWorld extends SEModule{
    private long audioDevice, context;

    public SEAudioWorld(SEWorld parrent) {
        super(parrent);
        
        audioDevice = alcOpenDevice((ByteBuffer) null);
        if(audioDevice == NULL){
            SELogger.get().dispatchMsg(this, SELogger.SELogType.ERROR, new String[]{"Failed to open the default audio device."}, true);
        }
        
        ALCCapabilities deviceCaps = createCapabilities(audioDevice);
        SELogger.get().dispatchMsg(this, SELogger.SELogType.INFO, new String[]{"OpenALC10: " + deviceCaps.OpenALC10, "OpenALC11: " + deviceCaps.OpenALC11, "caps.ALC_EXT_EFX = " + deviceCaps.ALC_EXT_EFX}, false);
        
        if (deviceCaps.OpenALC11) {
            List<String> devices = ALUtil.getStringList(NULL, ALC_ALL_DEVICES_SPECIFIER);
            if (devices == null) {
                SELogger.get().dispatchMsg(this, SELogger.SELogType.ERROR, new String[]{"No AL devices found!"}, true);
            } else {
                for (int i = 0; i < devices.size(); i++) {
                    System.out.println(i + ": " + devices.get(i));
                }
            }
        }

        String defaultDeviceSpecifier = alcGetString(NULL, ALC_DEFAULT_DEVICE_SPECIFIER);
        System.out.println("Default device: " + defaultDeviceSpecifier);

        context = alcCreateContext(audioDevice, (IntBuffer)null);
        alcSetThreadContext(context);
        AL.createCapabilities(deviceCaps);

        System.out.println("ALC_FREQUENCY: " + alcGetInteger(audioDevice, ALC_FREQUENCY) + "Hz");
        System.out.println("ALC_REFRESH: " + alcGetInteger(audioDevice, ALC_REFRESH) + "Hz");
        System.out.println("ALC_SYNC: " + (alcGetInteger(audioDevice, ALC_SYNC) == ALC_TRUE));
        System.out.println("ALC_MONO_SOURCES: " + alcGetInteger(audioDevice, ALC_MONO_SOURCES));
        System.out.println("ALC_STEREO_SOURCES: " + alcGetInteger(audioDevice, ALC_STEREO_SOURCES));
    }
    
    public AudioSource loadAudioFile(String file){
        try{
            int buffer = alGenBuffers();
            int source = alGenSources();
            ShortBuffer pcm;
            STBVorbisInfo info = STBVorbisInfo.malloc();
            pcm = readVorbis(file, 32 * 1024, info);
            alBufferData(buffer, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
            return new AudioSource(buffer, source, pcm, 5000); //Need to be changed, 5000 is default lenght of audio file
        }
        catch(Exception e){
            return null;
        }
    }

    private ShortBuffer readVorbis(String file, int bufferSize, STBVorbisInfo info){
        ByteBuffer vorbis = BufferUtils.createByteBuffer(bufferSize);

        IntBuffer error   = BufferUtils.createIntBuffer(1);
        long      decoder = stb_vorbis_open_memory(vorbis, error, null);
        if (decoder == NULL) {
            throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));
        }

        stb_vorbis_get_info(decoder, info);

        int channels = info.channels();

        int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);

        ShortBuffer pcm = BufferUtils.createShortBuffer(lengthSamples);

        pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
        stb_vorbis_close(decoder);

        return pcm;
    }
    
    @Override
    public boolean shutdown() {
        alcMakeContextCurrent(NULL);
        alcDestroyContext(context);
        alcCloseDevice(audioDevice);
        return true;
    }

    @Override
    public boolean update(float delta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean cleanUp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void alBufferData(int buffer, int par, ShortBuffer pcm, int sample_rate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
