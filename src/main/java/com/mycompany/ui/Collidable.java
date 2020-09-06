package com.mycompany.ui;

import java.awt.Rectangle;

/**
 * This interface class represents any object that is "collidable". i.e. can
 * collide with other "collidables". It has the following methods.
 * @author jnesis.
 */
public interface Collidable {
    Rectangle getBounds();
    void handleCollision(CollisionEvent collisionEvent);   
}
