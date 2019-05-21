/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.exceptions;

/**
 *
 * @author vortex
 */
public class MissingRenderBackendException extends Exception{

    public MissingRenderBackendException() {
        super("No rnederbackend is supported or was set for your system.");
    }
    
}
