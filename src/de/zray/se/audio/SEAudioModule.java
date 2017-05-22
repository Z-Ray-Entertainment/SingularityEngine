/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.audio;

import de.zray.se.SEModule;
import de.zray.se.SEWorld;
import de.zray.se.logger.SELogger;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.ALC11.*;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALUtil;
import static org.lwjgl.openal.EXTThreadLocalContext.alcSetThreadContext;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 *
 * @author Vortex Acherontic
 */
public class SEAudioModule extends SEModule{
    private long audioDevice, context;
;

    public SEAudioModule(SEWorld parrent) {
        super(parrent);
        
        audioDevice = ALC10.alcOpenDevice((ByteBuffer) null);
        if(audioDevice == NULL){
            SELogger.get().dispatchMsg(this, SELogger.SELogType.ERROR, new String[]{"Failed to open the default audio device."}, true);
        }
        
        ALCCapabilities deviceCaps = ALC.createCapabilities(audioDevice);
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
