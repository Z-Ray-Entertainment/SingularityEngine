/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.inputmanager;

/**
 *
 * @author vortex
 */
public class KeyMap {
    public static enum KeyBoardLayout {GERMAN, ENGLISH};
    public static enum MODE {RELEASED, PRESSED, TIPED};
    public static final int KEY_A = 65, KEY_B = 66, KEY_C = 67, KEY_D = 68,
            KEY_E = 69, KEY_F = 70, KEY_G = 71, KEY_H = 72, KEY_I = 73,
            KEY_J = 74, KEY_K = 75, KEY_L = 76, KEY_M = 77, KEY_N = 78,
            KEY_O = 79, KEY_P = 80, KEY_Q = 81, KEY_R = 82, KEY_S = 83,
            KEY_T = 84, KEY_U = 85, KEY_V = 86, KEY_W = 87, KEY_X = 88,
            KEY_Y = 89, KEY_Z = 90, KEY_ARROW_UP = 265, KEY_ARROW_DOWN = 264,
            KEY_ARROW_LEFT = 263, KEY_ARROW_RIGHT = 262, KEY_ESCAPE = 256,
            KEY_PAGE_UP = 266, KEY_PAGE_DOWN = 267, KEY_LEFT_SHIFT = 340;
    
    public KeyMap(KeyBoardLayout layout){
        
    }
}
