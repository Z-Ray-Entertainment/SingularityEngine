/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.zray.se.renderbackend.opengl;

/**
 *
 * @author vortex
 */
public class RenderDataCacheEntry {
    public int meshDataID = -1, vboID = -1, vboSize = -1, vboIDWired = -1, vboSizeWired = -1;
    public int displayListID = -1, displayListIDWired = -1;
    public boolean isWired = false, isAnimatedVBO = false;
}
