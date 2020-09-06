package com.mycompany.ui;

import java.util.concurrent.locks.Lock;

/**
 * This class represents any moving object within outer space.
 * @author jnesis.
 * adapted by stacey
 */
public class MovingObject {
    
    private int x = 20; /**x co-ordinate*/
    private int y = 20; /**y co-ordinate*/
    private int xDirection = 1; /**x direction*/
    private int yDirection = 1;/**y direction*/
    
    /**
     * Determines how the moving object moves
     */
    public void move(){
        x+=xDirection;
        y+=yDirection;
    }

    /**
     * Get x direction
     * @return x direction int
     */
    public int getXDirection() {
        return xDirection;
    }

    /**
     * Set x direction
     * @param xDirection int
     */
    public void setXDirection(int xDirection) {
        this.xDirection = xDirection;
    }

    /**
     * Get y direction
     * @return y direction int
     */
    public int getYDirection() {
        return yDirection;
    }

    /**
     * Set y direction
     * @param yDirection int
     */
    public void setYDirection(int yDirection) {
        this.yDirection = yDirection;
    }

    /**
     * Get x co-ordinate
     * @return x co-ordinate int
     */
    public int getX() {
        return x;
    }

    /**
     * Set x co-ordinate
     * @param x co-ordinate int
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get y co-ordinate
     * @return y co-ordinate int
     */
    public int getY() {
        return y;
    }

    /**
     * Set y co-ordinate
     * @param y co-ordinate int
     */
    public void setY(int y) {
        this.y = y;
    }
}
