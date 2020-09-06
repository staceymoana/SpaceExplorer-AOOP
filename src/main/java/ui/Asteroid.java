package com.mycompany.ui;

import static com.mycompany.ui.FirstWindow.masterLock;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.locks.Lock;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * This class represents an asteroid within outer space aka the factory.
 * It inherits from the MovingObject class and implements the Collidable interface.
 * There is a maximum of 5 asteroids on the GUI.
 * @author jnesis.
 * adapted by stacey
 */
public class Asteroid extends MovingObject implements Collidable {
    
    private int w = 50; /**width*/
    private int h = 50; /**height*/
    private File asteroidImageFile = new File("src/resources/asteroid.png"); /**image file*/
    private BufferedImage image; /**image holder*/
    private CustomPanel panel; /**GUI panel*/
    private boolean draw; /**Check to draw or not*/
     private Lock lock;
     
    /**
     * Constructor
     * @param masterLock
     * @throws Exception 
     */
    public Asteroid(Lock masterLock) throws Exception {
        image = loadImage(asteroidImageFile);
        this.lock = masterLock;
    } 
    
    /**
     * Paints asteroid to GUI
     * @param g 
     */
    public void paintAsteroid (Graphics g){
        lock.lock();
        try {
            g.drawImage(image, getX(), getY(), w, h, null);
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * Loads the image file
     * @param file This is the image file
     * @return the image as a BufferedImage
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
     * Gets the bounds of the asteroid
     * @return a Rectangle obj with the bounds of the asteroid
     */
    @Override
    public Rectangle getBounds() {
        int x = getX() - 5; //tried to decrease bounds cause of round shape
        int y = getY() - 5;
        return new Rectangle(x, y, w, h);
    }
    
    /**
     * Defines how an asteroid handles any collisions with static or moving
     * objects and the walls in the GUI
     * @param collisionEvent 
     */
    @Override
    public void handleCollision(CollisionEvent collisionEvent) {
        lock.lock();
        try {
            if (collisionEvent.getSource().equals(CollisionEvent.WALLSEVENTSOURCE)) {
                //it hit the wall
                //wall specifics
            } else {
                //the collisionEvent source is the other object
                Collidable eventSource = (Collidable) collisionEvent.getSource();
                //Collifable specifics
                //and possibly a movingobject
                if (eventSource instanceof MovingObject) {
                    //it is a moving object
                    //MovingObject movingObject=(MovingObject)eventSource;
                    //movingObject specifics
                    if (eventSource instanceof Rocket) {
                        //If the asteroid hits the rocket:
                        Rocket rocket=(Rocket)eventSource;
                        if (!rocket.isShielded()) {
                            //If the rocket isn't protected by a shield: destroy rocket!
                            //Set rocket to no longer be on the GUI
                            panel.getRockets().clear();
                            rocket.setDraw(false);
                            rocket.setW(0);
                            rocket.setH(0);
                            rocket.setXDirection(0);
                            rocket.setYDirection(0);
                            CollisionManager.removeCollidable(rocket);
                            panel.repaint();
                        }
                    }
                }
            }
            
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
     * Defines how the asteroid moves
     */
    @Override
    public void move() {
        super.move();
        CollisionManager.handleCollisions(this);
    }

    /**
     * Gets the main panel of the asteroid
     * @return Panel panel - Main GUI Panel
     */
    public CustomPanel getPanel() {
        return panel;
    }

    /**
     * Set the main panel of the asteroid
     * @param panel CustomPanel - Main GUI Panel
     */
    public void setPanel(CustomPanel panel) {
        this.panel = panel;
    }

    /**
     * Check if asteroid is drawable. i.e. less than 5 on GUI
     * @return Boolean draw
     */
    public boolean isDraw() {
        return draw;
    }
    
    /**
     * Set if the asteroid is drawable.
     * @param draw Boolean
     */
    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    /**
     * Get the width of the asteroid
     * @return int width
     */
    public int getW() {
        return w;
    }

    /**
     * Set the width of the asteroid
     * @param w width
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     * Get the height of the asteroid
     * @return int height
     */
    public int getH() {
        return h;
    }

    /**
     * Set the height of the asteroid
     * @param h height
     */
    public void setH(int h) {
        this.h = h;
    } 
}