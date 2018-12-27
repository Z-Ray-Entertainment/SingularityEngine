/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.logger;

import de.zray.se.EngineSettings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author vortex
 */
public class SELogger {
    private static SELogger logger;
    private List<SEDispatcherHook> dispatcherHooks = new LinkedList<>();
    
    public static SELogger get(){
        if(logger == null){
            logger = new SELogger();
        }
        return logger;
    }
    
    public enum SELogType{ ERROR("Error"), INFO("Info"), FATAL_ERROR("Fatal Error"),
        WARNING("Warning");
        String designator;
        private SELogType(String designator){
            this.designator = "["+designator+"]";
        }
    };
    
    private File logFile;
    private List<String> logContent = new ArrayList<>();
    
    public SELogger(){
        logFile = new File(EngineSettings.get().logfile);
        loadLogfile();
    }
    
    public void registerDispatcherHook(SEDispatcherHook hook){
        dispatcherHooks.add(hook);
    }
    
    private void loadLogfile(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(logFile));
            String line;
            
            while((line = reader.readLine()) != null){
                logContent.add(line);
            }
        }
        catch(FileNotFoundException e){
            File path = new File(logFile.getPath());
            if(!path.mkdirs()){
                dispatchMsg("SELogger", SELogType.FATAL_ERROR, new String[]{"Could not create directory "+logFile.toString()}, true);
            }
            else{
                loadLogfile();
            }
        }
        catch(IOException e){
            dispatchMsg("SELogger", SELogType.FATAL_ERROR, new String[]{"Could not load "+logFile.toString()}, true);
        }
    }
    
    public void dispatchMsg(Object dispatcher, Exception e){
        dispatchMsg(dispatcher, SELogType.ERROR, new String[]{e.getMessage()}, true);
    }
    
    public void dispatchMsg(String dispatcher, Exception e){
        dispatchMsg(dispatcher, SELogType.ERROR, new String[]{e.getMessage()}, true);
    }
    
    public void dispatchMsg(Object dispatcher, SELogType logType, String lines[], boolean saveToFile){
        dispatchMsg(dispatcher.toString(), logType, lines, saveToFile);
    }
    
    public void dispatchMsg(String dispatcher, SELogType logType, String lines[], boolean saveToFile){
        for(String tmp : lines){
            String output = new Date().toString()+" : "+dispatcher+" : "+logType.designator+" : "+tmp;
            for(SEDispatcherHook hook : dispatcherHooks){
                hook.dispatchMessage(output);
            }
            System.out.println(output);
        }
        
        if(false){
            try {
            
            
                BufferedReader reader = new BufferedReader(new FileReader(logFile));
            }
            catch(FileNotFoundException e){
                String tmp[] = new String[lines.length+1];
                for(int i = 0; i < lines.length; i++){
                    tmp[i] = lines[i];
                }
                tmp[lines.length] = e.getMessage();
                dispatchMsg("SELogger", logType, tmp , false);
            }
        }
    }
}
