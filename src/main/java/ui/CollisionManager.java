package com.mycompany.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Collision Manager which "manages" any collidable objects.
 * It manages them by either adding, removing, determines how collisions are 
 * handled between collidable objects and how collidable objects 
 * interact with the GUI wall boundaries.
 * @author jnesis.
 */
public class CollisionManager {

    public static int width;
    public static int height;
    private final static List<Collidable> COLLIDABLES = new ArrayList<>();

    /**
     * Adds a collidable to the list of collidables
     * @param collidable Collidable object
     */
    public static void addCollidable(Collidable collidable) {
        COLLIDABLES.add(collidable);
    }
    
    /**
     * Removes a collidable form the list of collidables
     * @param collidable Collidable object
     */
    public static void removeCollidable(Collidable collidable) {
        COLLIDABLES.remove(collidable);
    }

    public static void handleCollisions(Collidable collidable) {
        handleBoundaryCollision(collidable);
        List<Collidable> others = new ArrayList<>(COLLIDABLES);
        others.remove(collidable);
        handleOtherShapesCollisions(collidable, others);
    }

    protected static void handleBoundaryCollision(Collidable collidable) {
        //Detect the horizontal borders
        CollisionEvent event = null;
        double depth = 0;
        if (collidable.getBounds().getY() < 0) {
            //Top border
            event = new CollisionEvent(CollisionEvent.WALLSEVENTSOURCE);
            depth = -collidable.getBounds().getY();
            event.setImpact("top");
        } else if (collidable.getBounds().getY() + collidable.getBounds().getHeight() > height) {
            //Bottom border
            event = new CollisionEvent(CollisionEvent.WALLSEVENTSOURCE);
            double newDepth = collidable.getBounds().getY() + collidable.getBounds().getHeight() - height;
            if (event.getImpact() == null || newDepth < depth) {
                event.setImpact("bottom");
                depth = newDepth;
            }
        }

        if (collidable.getBounds().getX() < 0) {
            //Left border
            event = new CollisionEvent(CollisionEvent.WALLSEVENTSOURCE);
            double newDepth = -collidable.getBounds().getX();
            if (event.getImpact() == null || newDepth < depth) {
                event.setImpact("left");
                depth = newDepth;
            }
        } else if (collidable.getBounds().getX() + collidable.getBounds().getWidth() > width) {
            //Right border
            event = new CollisionEvent(CollisionEvent.WALLSEVENTSOURCE);
            double newDepth = collidable.getBounds().getX() + collidable.getBounds().getWidth() - width;
            if (event.getImpact() == null || newDepth < depth) {
                event.setImpact("right");
                depth = newDepth;
            }
        }
        if (event != null) {
            collidable.handleCollision(event);
        }
    }

    protected static void handleOtherShapesCollisions(Collidable collidable, List<Collidable> others) {
        for (Collidable otherCollidable : others) {
            if (otherCollidable.getBounds().intersects(collidable.getBounds())) {
                CollisionEvent event = new CollisionEvent(otherCollidable);
                double depth = 0;
                if (collidable.getBounds().getY() < otherCollidable.getBounds().getY() + otherCollidable.getBounds().height) {
                    //Top border
                    event.setImpact("top");
                    depth = otherCollidable.getBounds().getY() + otherCollidable.getBounds().height - collidable.getBounds().getY();
                }
                if (collidable.getBounds().getY() + collidable.getBounds().getHeight() > otherCollidable.getBounds().getY()) {
                    //Bottom border
                    double newDepth = collidable.getBounds().getY() + collidable.getBounds().getHeight() - otherCollidable.getBounds().getY();
                    if (event.getImpact() == null || newDepth < depth) {
                        event.setImpact("bottom");
                        depth = newDepth;
                    }
                }
                //Detect the vertical borders
                if (collidable.getBounds().getX() < otherCollidable.getBounds().getX() + otherCollidable.getBounds().width) {
                    //Left border
                    double newDepth = otherCollidable.getBounds().getX() + otherCollidable.getBounds().width - collidable.getBounds().getX();
                    if (event.getImpact() == null || newDepth < depth) {
                        event.setImpact("left");
                        depth = newDepth;
                    }
                }
                if (collidable.getBounds().getX() + collidable.getBounds().getWidth() > otherCollidable.getBounds().getX()) {
                    //Right border
                    double newDepth = collidable.getBounds().getX() + collidable.getBounds().getWidth() - otherCollidable.getBounds().getX();
                    if (event.getImpact() == null || newDepth < depth) {
                        event.setImpact("right");
                    }
                }
                collidable.handleCollision(event);
                CollisionEvent oppositeEvent=new CollisionEvent(collidable);
                switch (event.getImpact()){
                    case "left":
                        oppositeEvent.setImpact("right");
                        break;
                    case "right":
                        oppositeEvent.setImpact("left");
                        break;
                    case "top":
                        oppositeEvent.setImpact("bottom");
                        break;
                    case "bottom":
                        oppositeEvent.setImpact("top");
                }
                otherCollidable.handleCollision(oppositeEvent);
            }
        }
    }

}
