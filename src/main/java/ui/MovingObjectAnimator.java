package com.mycompany.ui;

/**
 * This class represents the moving object animator. 
 * It implements the runnable interface.
 * @author jnesis
 */
public class MovingObjectAnimator implements Runnable{
    
    private MovingObject movingObject;
    private int movePerSec=100;
    
    /**
     * Constructor
     * @param movingObject 
     */
    public MovingObjectAnimator(MovingObject movingObject){
        this.movingObject=movingObject;
    }
    
    /**
     * Determines how the moving object animation looks on the GUI 
     */
    @Override
    public void run() {
        while (true){
            movingObject.move();
            try {
                //move every movePerSec.
                Thread.sleep(1000/movePerSec);
            } catch (InterruptedException ex) {
                System.out.println("Something interrupted me while sleeping...");
            }
        }
    }

    /**
     * Get moves per second
     * @return int movePerSec
     */
    public int getMovePerSec() {
        return movePerSec;
    }

    /**
     * Set moves per second
     * @param movePerSec int
     */
    public void setMovePerSec(int movePerSec) {
        this.movePerSec = movePerSec;
    }
}
