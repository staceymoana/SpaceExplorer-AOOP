package com.mycompany.ui;

import static com.mycompany.ui.FirstWindow.masterLock;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.locks.Lock;
import javax.imageio.ImageIO;

/**
 * This class represents a rocket within outer space aka the factory.
 * It inherits from the MovingObject class and implements the Collidable interface.
 * There is 1 rocket per round.
 * @author jnesis.
 * adapted by stacey
 */
public class Rocket extends MovingObject implements Collidable {
    
    private int w = 70; /**width*/
    private int h = 90; /**height*/
    private File rocketImageFile = new File("src/resources/rocket2.png"); /**rocket image file*/
    private File shieldImageFile = new File("src/resources/prot_rocket.png"); /**shielded rocket image file*/
    private BufferedImage image; /**image holder*/
    private boolean draw; /**Check to draw or not*/
    private CustomPanel panel; /**GUI panel*/
    private boolean shielded; /**Check if rocket is shielded or not*/
    private Lock lock;
    /**
     * Constructor
     * @throws Exception 
     */
    public Rocket(Lock masterLock) throws Exception {
        if (shielded) {
            image = loadImage(shieldImageFile);
        } else {
            image = loadImage(rocketImageFile);
        }
        this.lock = masterLock;
    } 
    
    /**
     * Paints rocket to GUI
     * @param g 
     */
    public void paintRocket (Graphics g){
        lock.lock();
        try {
            if (shielded) {
                image = loadImage(shieldImageFile);
            } else {
                image = loadImage(rocketImageFile);
            }
            g.drawImage(image, getX(), getY(), w, h, null);
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * Loads the image file
     * @param file This is the image file
     * @return rocket image
     */
    public BufferedImage loadImage(File file) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (Exception e) {
            System.out.println("Error loading Image: " + e);
        } 
        return image;
    }

    /**
     * Gets the bounds of the rocket
     * @return a Rectangle with the bounds of the rocket
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), w, h);
    }

    /**
     * Determines how the rocket collides with other collidables
     * Rocket collision events are determined in the other collidable classes 
     * @param collisionEvent 
     */
    @Override
    public void handleCollision(CollisionEvent collisionEvent) {
        lock.lock();
        try {
            //Checks direction of collison. "Bouncing effect". Makes it go in
            //opposite direction
            switch (collisionEvent.getImpact()) {
                case "left":
                    if (getXDirection()<0) setXDirection(-getXDirection());
                    break;
                case "right":
                    if (getXDirection()>0) setXDirection(-getXDirection());
                    break;
                case "top":
                    if (getYDirection()<0) setYDirection(-getYDirection());
                    break;
                case "bottom":
                    if (getYDirection()>0) setYDirection(-getYDirection());
            }
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * Defines how the rocket moves
     */
    @Override
    public void move() {
        super.move();
        CollisionManager.handleCollisions(this);
    }

    /**
     * Check if rocket is drawable.
     * @return Boolean draw
     */
    public boolean isDraw() {
        return draw;
    }

    /**
     * Set if the rocket is drawable.
     * @param draw Boolean
     */
    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    /**
     * Gets the main panel of the rocket
     * @return Panel panel - Main GUI Panel
     */
    public CustomPanel getPanel() {
        return panel;
    }

    /**
     * Set the main panel of the rocket
     * @param panel CustomPanel - Main GUI Panel
     */
    public void setPanel(CustomPanel panel) {
        this.panel = panel;
    }

    /**
     * Get the width of the rocket
     * @return int width
     */
    public int getW() {
        return w;
    }

    /**
     * Set the width of the rocket
     * @param w int
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     * Get the height of the rocket
     * @return int height
     */
    public int getH() {
        return h;
    }

    /**
     * Set the height of the rocket
     * @param h 
     */
    public void setH(int h) {
        this.h = h;
    }

    /**
     * Check if the rocket is shielded or not
     * @return Boolean shielded
     */
    public boolean isShielded() {
        return shielded;
    }

    /**
     * Set if the rocket is shielded or not
     * @param shielded Boolean
     */
    public void setShielded(boolean shielded) {
        this.shielded = shielded;
    }
    
}