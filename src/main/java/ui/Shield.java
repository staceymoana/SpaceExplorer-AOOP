package com.mycompany.ui;

import static com.mycompany.ui.FirstWindow.masterLock;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import javax.imageio.ImageIO;

/**
 * This class represents a shield within outer space aka the factory.
 * It inherits from the StaticObject class and implements the Collidable interface.
 * There is 1 shield per round.
 * @author jnesis.
 * adapted by stacey
 */
public class Shield extends StaticObject implements Collidable {
    
    private int w = 150; /**width*/
    private int h = 155; /**height*/
    private File shieldImageFile = new File("src/resources/shield.png"); /**image file*/
    private BufferedImage image; /**image holder*/
    private CustomPanel panel; /**GUI panel*/
    private boolean draw; /**Check to draw or not*/
     private Lock lock;
    /**
     * Constructor
     * @param masterLock
     * @throws Exception 
     */
    public Shield(Lock masterLock) throws Exception {
        image = loadImage(shieldImageFile);
        this.lock = masterLock;
    }
    
    /**
     * Paints shield to GUI
     * @param g 
     */
    public void paintShield (Graphics g) {
        lock.lock();
        try {
            if(isDraw()) {
                g.drawImage(image, super.getX(), super.getY(), w, h, null);
            }
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * Loads the image file
     * @param file This is the image file
     * @return shield image
     */
    private BufferedImage loadImage(File file) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (Exception e) {
            System.out.println("Error loading Image: " + e);
        }
        return image;
        
    }
    
    /**
     * Gets the bounds of the shield
     * @return a Rectangle with the bounds of the shield
     */
    @Override
    public Rectangle getBounds() {
        int w = this.w - 15; //tried to decrease bounds cause of round shape
        int h = this.h - 15;
        return new Rectangle(getX(), getY(), w, h);
    }

    /**
     * Defines how a shield handles any collisions with a rocket
     * @param collisionEvent 
     */
    @Override
    public void handleCollision(CollisionEvent collisionEvent) {
        lock.lock();
        try {
            if (collisionEvent.getSource().equals(CollisionEvent.WALLSEVENTSOURCE)) {
                //it the the wall
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
                        //it is a Ball
                        //Ball ball=(Ball)eventSource;
                        //Ball specifics
                        Rocket rocket =(Rocket)eventSource;
                        rocket.setShielded(true);
                        //Remove shield from GUI for 10s
                        setDraw(false);
                        setW(0);
                        setH(0);
                        CollisionManager.removeCollidable(this);
                        panel.repaint();
                        /*Set a timer to ensure rocket is only protected for 10
                        seconds and respawn the shield again after the 10s is over*/
                        Timer timer = new Timer();
                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                rocket.setShielded(false);
                                setDraw(true);
                                setW(150);
                                setH(155);
                                timer.cancel();
                            }
                        }, 5000, 5000);
                        CollisionManager.addCollidable(this);
                        panel.repaint();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * Gets the main panel of the shield
     * @return Panel panel - Main GUI Panel
     */
    public CustomPanel getPanel() {
        return panel;
    }

    /**
     * Set the main panel of the shield
     * @param panel CustomPanel - Main GUI Panel
     */
    public void setPanel(CustomPanel panel) {
        this.panel = panel;
    }

    /**
     * Get the width of the shield
     * @return int width
     */
    public int getW() {
        return w;
    }

    /**
     * Set the width of the shield
     * @param w int
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     * Get the height of the shield
     * @return int height
     */
    public int getH() {
        return h;
    }

    /**
     * Set the height of the shield
     * @param h int
     */
    public void setH(int h) {
        this.h = h;
    }

    /**
     * Check if shield is drawable
     * @return Boolean draw
     */
    public boolean isDraw() {
        return draw;
    }

    /**
     * Set if the shield is drawable.
     * @param draw Boolean
     */
    public void setDraw(boolean draw) {
        this.draw = draw;
    }
}
