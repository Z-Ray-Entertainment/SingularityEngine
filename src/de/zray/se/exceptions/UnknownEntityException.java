/*
 * Copyright by Z-Ray Entertainment 2018
 * Unauthorized copying or distributing of this source is not allowed
 * for further informations contact: support@z-ray.de
 */
package de.zray.se.exceptions;

import de.zray.se.world.SEEntity;

/**
 *
 * @author vortex
 */
public class UnknownEntityException extends Exception{
    public UnknownEntityException(SEEntity ent){
        super(ent.toString()+" is an UnknownEntityType!");
    }
}
