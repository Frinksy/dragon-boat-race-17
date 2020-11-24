package org.gnocchigames.dragonboat.exceptions;

/**
 * IsNotDrawingException  <br>
 * A simple Exception to be thrown when
 * a sprite is trying to draw itself when the 
 * SpriteBatch has not begun drawing
 * ( is not between batch.begin() and batch.end() )
 */
public class IsNotDrawingException extends Exception{

    private static final long serialVersionUID = 8538240909571855569L;

    public IsNotDrawingException() {
        super();
    }

    public IsNotDrawingException(String message) {
        super(message);
    }
    
}