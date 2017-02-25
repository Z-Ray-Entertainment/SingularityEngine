/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.exceptions;

/**
 *
 * @author Vortex Acherontic
 */
public class DifferentLengthArrays extends Exception{
    public DifferentLengthArrays(){
        super("One ore more Arrays have not the same lenght, but ths ist required by this Method.");
    }
    
}
