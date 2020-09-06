package com.mycompany.ui;

import static com.mycompany.ui.FirstWindow.masterLock;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList; 
import java.util.Random;
import java.util.concurrent.locks.Lock;

/**
 * This class represents a black hole within outer space aka the factory.
 * It inherits from the StaticObject class and implements the Collidable interface.
 * There is 1 black hole per round.
 * @author jnesis.
 * adapted by stacey
 */
public class Blackhole extends StaticObject implements Collidable {
    private int w = 150; /**width*/
    private int h = 155; /**height*/
    private File blackholeImageFile = new File("src/resources/blackhole1.png"); /**image file*/
    private BufferedImage image; /**image holder*/
    private CustomPanel panel; /**GUI panel*/
    private boolean draw; /**Check to draw or not*/
    private Lock lock;
    
    /**
     * Constructor
     * @param masterLock
     * @throws Exception 
     */
    public Blackhole(Lock masterLock) throws Exception {
        image = loadImage(blackholeImageFile);
        this.lock = masterLock;
    }
    
    /**
     * Paints black hole to GUI
     * @param g 
     */
    public void paintBlackhole (Graphics g){
        lock.lock();
        try {
            g.drawImage(image, super.getX(), super.getY(), w, h, null);
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
     * Gets the bounds of the black hole
     * @return a Rectangle obj with the bounds of the black hole
     */
    @Override
    public Rectangle getBounds() {
        int w = this.w - 30; //tried to decrease bounds cause of round shape
        int h = this.h - 30;
        return new Rectangle(getX(), getY(), w, h);
    }

    /**
     * Defines how a black hole handles any collisions with moving objects
     * (rocket and asteroid)
     * @param collisionEvent 
     */
    @Override
    public void handleCollision(CollisionEvent collisionEvent) {
        lock.lock();
        try {
            //the collisionEvent source is the other object
            Collidable eventSource = (Collidable) collisionEvent.getSource();
            //Collifable specifics
            if (eventSource instanceof MovingObject) {
                //it is a moving object
                if (eventSource instanceof Asteroid) {
                    //If it is an asteroid: destroy asteroid!
                    
                    Asteroid asteroid=(Asteroid)eventSource;
                    //Remove correct asteroid in asteroids list
                    for (int i = 0; i < panel.getAsteroids().size(); i++) {
                        int pos = panel.getAsteroids().indexOf(asteroid);
                        if (pos == i) {
                            panel.getAsteroids().remove(i);
                        }
                    }
                    //Set asteroid to no longer be on the GUI
                    asteroid.setDraw(false);
                    asteroid.setW(0);
                    asteroid.setH(0);
                    asteroid.setXDirection(0);
                    asteroid.setYDirection(0);
                    CollisionManager.removeCollidable(asteroid);
                }
                if (eventSource instanceof Rocket) {
                    //If it is a rocket: teleport rocket & remove blackhole for
                    //current round.
                    //Set black hole to no longer be on GUI
                    setDraw(false);
                    setW(0);
                    setH(0);
                    CollisionManager.removeCollidable(this);
                    panel.repaint();
                    //Teleport rocket to random location
                    Random ran = new Random();
                    int rX = ran.nextInt(600);
                    int rY = ran.nextInt(500);
                    //Ensure rocket doesn't spawn on shield
                    while (rX >= (panel.getShield().getX() - 70) && rX <= (panel.getShield().getX() + 70)) {
                        rX = ran.nextInt(600);
                    }
                    while (rY >= (panel.getShield().getY() - 90) && rY <= (panel.getShield().getY() + 90)) {
                        rY = ran.nextInt(600);
                    }
                    Rocket rocket=(Rocket)eventSource;
                    //"Teleports" rocket to new co-ordinates
                    if (!rocket.isShielded()) {
                        rocket.setX(rX);
                        rocket.setY(rY);
                        panel.repaint();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Gets the main panel of the asteroid
     * @return CustomPanel panel - Main GUI Panel
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
     * Get the width of the black hole
     * @return int width
     */
    public int getW() {
        return w;
    }

    /**
     * Set the width of the black hole
     * @param w width
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     * Get the height of the black hole
     * @return int height
     */
    public int getH() {
        return h;
    }

    /**
     * Set the height of the black hole
     * @param h height
     */
    public void setH(int h) {
        this.h = h;
    }

    /**
     * Check if black hole is drawable
     * @return Boolean draw
     */
    public boolean isDraw() {
        return draw;
    }

    /**
     * Set if the black hole is drawable.
     * @param draw 
     */
    public void setDraw(boolean draw) {
        this.draw = draw;
    }
}
