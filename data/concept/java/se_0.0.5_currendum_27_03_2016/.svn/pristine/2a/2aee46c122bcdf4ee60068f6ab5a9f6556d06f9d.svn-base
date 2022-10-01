/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.logger;

import de.zray.se.Settings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vortex
 */
public class SELogger {
    private static SELogger logger;
    
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
        logFile = new File(Settings.get().logfile);
        loadLogfile();
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
            if(!logFile.mkdirs()){
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
    
    public void dispatchMsg(String dispatcher, SELogType logType, String lines[], boolean saveToFile){
        for(String tmp : lines){
            System.out.println(new Date().toString()+" : "+dispatcher+" : "+logType.designator+" : "+tmp);
        }
        
        if(saveToFile){
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
