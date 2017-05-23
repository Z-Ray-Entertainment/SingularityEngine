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
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.lwjgl.BufferUtils;
import static org.lwjgl.BufferUtils.createByteBuffer;
import org.lwjgl.openal.AL;
import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
import static org.lwjgl.openal.AL10.alBufferData;
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
import sun.nio.ch.IOUtil;

/**
 *
 * @author Vortex Acherontic
 */
public class SEAudioWorld extends SEModule{

    private ByteBuffer resizeBuffer(ByteBuffer buffer, int i) {
        ByteBuffer newbuf = BufferUtils.createByteBuffer(buffer.capacity()*i);
        newbuf.put(buffer);
        return newbuf;
    }
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

        SELogger.get().dispatchMsg(this, SELogger.SELogType.INFO, new String[]{
            "ALC_FREQUENCY: " + alcGetInteger(audioDevice, ALC_FREQUENCY) + "Hz",
            "ALC_REFRESH: " + alcGetInteger(audioDevice, ALC_REFRESH) + "Hz",
            "ALC_SYNC: " + (alcGetInteger(audioDevice, ALC_SYNC) == ALC_TRUE),
            "ALC_MONO_SOURCES: " + alcGetInteger(audioDevice, ALC_MONO_SOURCES),
            "ALC_STEREO_SOURCES: " + alcGetInteger(audioDevice, ALC_STEREO_SOURCES)
        }, false);
    }
    
    public AudioSource loadAudioFile(String file){
        int buffer = alGenBuffers();
        int source = alGenSources();
        ShortBuffer pcm;
        STBVorbisInfo info = STBVorbisInfo.malloc();
        pcm = readVorbis(file, 32 * 1024, info);
        alBufferData(buffer, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
        return new AudioSource(buffer, source, pcm);
    }

    private ShortBuffer readVorbis(String file, int bufferSize, STBVorbisInfo info){
        try {
            ByteBuffer vorbis = ioResourceToByteBuffer(file, bufferSize);
            IntBuffer error   = BufferUtils.createIntBuffer(1);
            long      decoder = stb_vorbis_open_memory(vorbis, error, null);
            if (decoder == NULL) {
                SELogger.get().dispatchMsg(this, SELogger.SELogType.ERROR, new String[]{"Failed to open Ogg Vorbis file. Error: " + error.get(0)}, true);
                return null;
            }

            stb_vorbis_get_info(decoder, info);

            int channels = info.channels();

            int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);

            ShortBuffer pcm = BufferUtils.createShortBuffer(lengthSamples);

            pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
            stb_vorbis_close(decoder);

            return pcm;
        } 
        catch (IOException ex) {
            SELogger.get().dispatchMsg(this, SELogger.SELogType.ERROR, new String[]{"Failed to open Ogg Vorbis file. Error: " + ex.getMessage()}, true);
            return null;
        }
    }
    
    public ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
                while (fc.read(buffer) != -1) {
                    ;
                }
            }
        } else {
            try (
                InputStream source = IOUtil.class.getClassLoader().getResourceAsStream(resource);
                ReadableByteChannel rbc = Channels.newChannel(source)
            ) {
                buffer = createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                    }
                }
            }
        }

        buffer.flip();
        return buffer;
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
}
