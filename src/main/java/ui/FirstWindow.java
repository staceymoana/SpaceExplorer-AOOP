package com.mycompany.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
/**
 * This class represents the window of the GUI. 
 * @author jnesis. adapted by stacey
 */
public class FirstWindow {
        public final static int CANVAS_WIDTH=800;
        public final static int CANVAS_HEIGHT=800;
        public final static Random ran = new Random();
        static Lock masterLock = new ReentrantLock();
	/**
         * Main method.
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		JFrame window = new JFrame();
		window.setTitle("Space Explorer");
                //Don't give a size to the window but to the content pane (see further)
		//window.setSize(400, 400);
		// Locate window content in the center
		window.setLocationRelativeTo(null);
                window.setResizable(false);
		// Kill the Java process on click on the close (X) icon
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
		CustomPanel panel = new CustomPanel();
		window.add(panel, BorderLayout.CENTER);                
                
                //Stop button section panel
                JPanel stopPanel = new JPanel(new BorderLayout());
                JButton stopButton = new JButton("STOP");
                stopButton(stopButton, panel);
                stopButton.setFocusable(false);
                stopButton.setContentAreaFilled(false);
                stopButton.setForeground(Color.WHITE);
                stopPanel.add(stopButton, BorderLayout.CENTER);
                
                //Panel for object additions
                JPanel objPanel = new JPanel(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                window.add(objPanel, BorderLayout.EAST);
                objPanel.setBackground(Color.black);
                stopPanel.setBackground(Color.black);
                
                //Panel for rocket additions
                JPanel rocketPanel = new JPanel(new BorderLayout());
                rocketPanel.setBorder(new EmptyBorder(30,30,30,30));
                rocketPanel.setBackground(Color.black);
                //Panel for asteroid additions
                JPanel asteroidPanel = new JPanel(new BorderLayout());
                asteroidPanel.setBorder(new EmptyBorder(30,30,30,30));
                asteroidPanel.setBackground(Color.black);
                
                //Holder for rocket and asteroid addtions to allow for
                //center allignment in objPanel
                JPanel holder = new JPanel(new BorderLayout());
                
                //Set all properties for rocket addition panel
                JLabel rocketLabel = new JLabel("  ROCKET");
                rocketLabel.setForeground(Color.white);
                ImageIcon rImage = new ImageIcon("src/resources/rocket2.png");
                Image image = rImage.getImage();
                Image newImage = image.getScaledInstance(40, 50, Image.SCALE_SMOOTH);
                rImage = new ImageIcon(newImage);
                JLabel rImageLabel = new JLabel(rImage);
                rImageLabel.setBorder(new EmptyBorder(20,0,20,0));
                JButton rAddButton = new JButton("ADD");
                rAddButton.setFocusable(false);
                rAddButton.setContentAreaFilled(false);
                rAddButton.setForeground(Color.WHITE);
                
                addRocketButton(rAddButton, panel);
                rocketPanel.add(rocketLabel, BorderLayout.NORTH);
                rocketPanel.add(rImageLabel, BorderLayout.CENTER);
                rocketPanel.add(rAddButton, BorderLayout.SOUTH);
                
                //Set all properties for asteroid addition panel
                JLabel asteroidLabel = new JLabel("ASTEROID");
                asteroidLabel.setForeground(Color.white);
                ImageIcon aImage = new ImageIcon("src/resources/asteroid.png");
                Image image2 = aImage.getImage();
                Image newImage2 = image2.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                aImage = new ImageIcon(newImage2);
                JLabel aImageLabel = new JLabel(aImage);
                aImageLabel.setBorder(new EmptyBorder(20,0,20,0));
                JButton aAddButton = new JButton("ADD");
                aAddButton.setFocusable(false);
                aAddButton.setContentAreaFilled(false);
                aAddButton.setForeground(Color.WHITE);
                
                addAsteroidButton(aAddButton, panel);
                asteroidPanel.add(asteroidLabel, BorderLayout.NORTH);
                asteroidPanel.add(aImageLabel, BorderLayout.CENTER);
                asteroidPanel.add(aAddButton, BorderLayout.SOUTH);                
                
                //Add rocket, asteroid and stop panels to holder panel
                holder.add(rocketPanel, BorderLayout.NORTH);
                holder.add(asteroidPanel, BorderLayout.CENTER);
                holder.add(stopPanel, BorderLayout.SOUTH);
                stopPanel.setBorder(new EmptyBorder(50, 10, 0, 10));
                
                //Add holder to obj panel
                objPanel.add(holder, c);

                //A window has insets (borders and title bar). To give a size to the panel
                //where we draw (so we can make sure we know the size) use setPreferedSize.
                panel.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
                CollisionManager.height=CANVAS_HEIGHT;
                CollisionManager.width=CANVAS_WIDTH;
                
                //Get first shield and blackhole on screen
                /*Have to first shield this way to ensure blackhole spawns in 
                different location*/
                Shield firstShield = new Shield(masterLock);
                firstShield.setDraw(true);
                firstShield.setX(ran.nextInt(650));
                firstShield.setY(ran.nextInt(645));
                firstShield.setW(150);
                firstShield.setH(155);
                firstShield.setPanel(panel);
                panel.setShield(firstShield);
                
                
                //Add original blackhole and shield to collision manager
                CollisionManager.addCollidable(getNewBlackhole(panel));
                CollisionManager.addCollidable(firstShield);
                
                //calculate the window size base on its content
                window.pack();
                //Show the window
		window.setVisible(true);

                while (true){
                    panel.repaint();
                    try {
                        //frame rate, refresh screen every 20ms
                        Thread.sleep(20);
                    } catch (InterruptedException ex) {
                        System.out.println("Error: " + ex);
                    }
                }
	}
        
        /**
         * Determines what happens when the add button for an asteroid is clicked.
         * @param button
         * @param panel 
         */
        public static void addAsteroidButton(JButton button, CustomPanel panel) {
            button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (panel.getAsteroids().size() < 5) {
                            Asteroid asteroid;
                            try {
                                asteroid = new Asteroid(masterLock);
                                asteroid.setX(ran.nextInt(750));
                                asteroid.setY(ran.nextInt(750));
                                asteroid.setXDirection(-1);
                                asteroid.setYDirection(3);
                                asteroid.setPanel(panel);
                                panel.addAsteroid(asteroid);
                                CollisionManager.addCollidable(asteroid);
                                MovingObjectAnimator mAsteroid = new MovingObjectAnimator(asteroid);
                                mAsteroid.setMovePerSec(60);
                                Thread thread=new Thread(mAsteroid);
                                thread.start();
                            } catch (Exception ex) {
                                System.out.println("Error " + ex);
                            }
                        }
                    }
                });
        }
        
        /**
         * Determines what happens when the add button for a rocket is clicked.
         * @param button
         * @param panel 
         */
        public static void addRocketButton(JButton button, CustomPanel panel) {
            button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (panel.getRockets().isEmpty()) {
                            Rocket rocket;
                            try {
                                rocket = new Rocket(masterLock);
                                //tried making sure rocket didnt spawn on shield or blackhole but wasnt quite working
//                                int rX = 70;
//                                int rY = 304;
//                                //Ensure new rocket doesn't spawn directly on blackhole or shield.
//                                if (!panel.getBlackhole().isDraw() && !panel.getShield().isDraw()) {
//                                    // if no blackhole or shield on GUI set co ordinates to random
//                                    rX = ran.nextInt(730);
//                                    rY = ran.nextInt(710);
//                                } else if (!panel.getBlackhole().isDraw()) {
//                                    // if just no blackhole
//                                    if (rX >= (panel.getShield().getX() - 150) && rX <= (panel.getShield().getX() + 150)) {
//                                        rX = ran.nextInt(650);
//                                    }
//                                    if (rY >= (panel.getShield().getY() - 155) && rY <= (panel.getShield().getY() + 155)) {
//                                        rY = ran.nextInt(700);
//                                    }
//                                } else {
//                                    // if both black hole and shield on GUI
//                                    if ((rX >= (panel.getBlackhole().getX() - 70) && rX <= (panel.getBlackhole().getX() + 70)) || 
//                                        (rX >= (panel.getShield().getX() - 70) && rX <= (panel.getShield().getX() + 70))) {
//                                        rX = ran.nextInt(730);
//                                }
//                                    if (rY >= (panel.getBlackhole().getY() - 90) && rY <= (panel.getBlackhole().getY() + 90) || 
//                                        (rX >= (panel.getShield().getY() - 90) && rX <= (panel.getShield().getY() + 90))) {
//                                        rY = ran.nextInt(710);
//                                    }
//                                }
//                                rocket.setX(rX);
//                                rocket.setY(rY);
                                rocket.setX(ran.nextInt(730));
                                rocket.setY(ran.nextInt(710));
                                rocket.setXDirection(2);
                                rocket.setYDirection(-1);
                                rocket.setPanel(panel);
                                rocket.setDraw(true);
                                panel.addRocket(rocket);
                                CollisionManager.addCollidable(rocket);
                                MovingObjectAnimator mRocket= new MovingObjectAnimator(rocket);
                                mRocket.setMovePerSec(60);
                                Thread thread=new Thread(mRocket);
                                thread.start();
                            } catch (Exception ex) {
                                System.out.println("Error " + ex);
                            }
                        }
                    }
                });
        }
        
        /**
         * Determines what happens when the stop button is clicked.
         * @param button
         * @param panel 
         */
        public static void stopButton(JButton button, CustomPanel panel) {
            button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        removeCollidables(panel);
                        panel.getAsteroids().clear();
                        panel.getRockets().clear();
                        try {
                            panel.getShield().setH(0);
                            panel.getShield().setW(0);
                            panel.getShield().setDraw(false);
                            CollisionManager.removeCollidable(panel.getShield());
                            getNewShield(panel);
                            panel.getBlackhole().setH(0);
                            panel.getBlackhole().setW(0);
                            panel.getBlackhole().setDraw(false);
                            CollisionManager.removeCollidable(panel.getBlackhole());
                            getNewBlackhole(panel);
                            panel.repaint();
                        } catch (Exception ex) {
                            System.out.println("Error: " + ex);
                        }
                        panel.repaint();
                    }
                });
        }
        
        /**
         * Creates a new shield for new rounds (i.e. when stop button is clicked)
         * @param panel
         * @return new Shield 
         * @throws Exception 
         */
        public static Shield getNewShield(CustomPanel panel) throws Exception {
            Shield shield = new Shield(masterLock);
            panel.setShield(shield);
            shield.setDraw(true);
            shield.setX(ran.nextInt(650));
            shield.setY(ran.nextInt(645));
            shield.setW(150);
            shield.setH(155);
            shield.setPanel(panel);
            panel.setShield(shield); 
            CollisionManager.addCollidable(shield);
            return shield;
        }
        
        /**
         * Creates new black hole for new rounds (i.e. when stop button is clicked)
         * @param panel
         * @return new Blackhole
         * @throws Exception 
         */
        public static Blackhole getNewBlackhole(CustomPanel panel) throws Exception {
            Blackhole blackhole = new Blackhole(masterLock);
            panel.setBlackhole(blackhole);
            blackhole.setDraw(true);
            int rX = ran.nextInt(650);
            int rY = ran.nextInt(645);
            while (rX >= (panel.getShield().getX() - 150) && rX <= (panel.getShield().getX() + 150)) {
                rX = ran.nextInt(650);
            }
            while (rY >= (panel.getShield().getY() - 155) && rY <= (panel.getShield().getY() + 155)) {
                rY = ran.nextInt(700);
            }
            blackhole.setX(rX);
            blackhole.setY(rY);
            blackhole.setW(150);
            blackhole.setH(155);
            blackhole.setPanel(panel);
            panel.setBlackhole(blackhole); 
            CollisionManager.addCollidable(blackhole);
            return blackhole;
        }
        
        /**
         * Remove all collidables when stop button pressed
         * @param panel 
         */
        public static void removeCollidables(CustomPanel panel) {
            if (panel.getRockets().size() > 0) {
                panel.getRockets().get(0).setH(0);
                panel.getRockets().get(0).setW(0);
                panel.getRockets().get(0).setXDirection(0);
                panel.getRockets().get(0).setYDirection(0);
                panel.getRockets().get(0).setDraw(false);
                CollisionManager.removeCollidable(panel.getRockets().get(0));
            }
            if (panel.getAsteroids().size() > 1) {
                for (int i = 0; i < panel.getAsteroids().size(); i++) {
                panel.getAsteroids().get(i).setH(0);
                panel.getAsteroids().get(i).setW(0);
                panel.getAsteroids().get(i).setXDirection(0);
                panel.getAsteroids().get(i).setYDirection(0);
                panel.getAsteroids().get(i).setDraw(false);
                CollisionManager.removeCollidable(panel.getAsteroids().get(i));
                }
            }
            panel.repaint();
        }
}

