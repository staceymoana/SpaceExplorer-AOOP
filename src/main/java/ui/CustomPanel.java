package com.mycompany.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * This class represents a custom panel for the GUI which holds all
 * collidable objects. This class inherits from JPanel.
 * @author jnesis.
 * adapted by stacey
 */
public class CustomPanel extends JPanel
{
    private List<Asteroid> asteroids = new ArrayList<>(); /**List of asteroids*/
    private List<Rocket> rockets = new ArrayList<>(); /**List of rockets*/
    private Blackhole blackhole; /**The black hole*/
    private Shield shield; /**The shield*/
    private Image backgroundImage; /**The background image*/
    
    /**
     * Constructor
     * @throws IOException 
     */
    public CustomPanel() throws IOException {
        backgroundImage = ImageIO.read(new File("src/resources/bg.jpg")).getScaledInstance(800, 800, Image.SCALE_SMOOTH);
    }
    
    /**
     * "Paint" all objects to GUI
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
        for (Asteroid asteroid: asteroids) {
            asteroid.paintAsteroid(g);
        }
        for (Rocket rocket: rockets) {
            if (rocket.isDraw())
            rocket.paintRocket(g);
        }
        
        blackhole.paintBlackhole(g);
        shield.paintShield(g);
        
    }
    
    /**
     * Add asteroid to asteroids list
     * @param asteroid Asteroid
     */
    public void addAsteroid(Asteroid asteroid) {
        asteroids.add(asteroid);
    }
    
    /**
     * Add rocket to rockets list
     * @param rocket Rocket
     */
    public void addRocket(Rocket rocket) {
        rockets.add(rocket);
    }

    /**
     * Get list of asteroids
     * @return List of asteroids
     */
    public List<Asteroid> getAsteroids() {
        return asteroids;
    }
    
    /**
     * Get list of rockets
     * @return List of rockets
     */
    public List<Rocket> getRockets() {
        return rockets;
    }

    /**
     * Get the black hole
     * @return Blackhole 
     */
    public Blackhole getBlackhole() {
        return blackhole;
    }

    /**
     * Set the black hole
     * @param blackhole 
     */
    public void setBlackhole(Blackhole blackhole) {
        this.blackhole = blackhole;
    }

    /**
     * Get the shield
     * @return Shield
     */
    public Shield getShield() {
        return shield;
    }

    /**
     * Set the shield
     * @param shield 
     */
    public void setShield(Shield shield) {
        this.shield = shield;
    }
}
