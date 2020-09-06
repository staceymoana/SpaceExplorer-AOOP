package com.mycompany.ui;

import java.util.EventObject;

/**
 * This class represents the collision event which inherits from EventObject.
 * @author jnesis.
 */
public class CollisionEvent extends EventObject{
    
    public static final String WALLSEVENTSOURCE="WALLS";
    
    private String impact;
    
    /**
     * Constructor
     * @param source 
     */
    public CollisionEvent(Object source) {
        super(source);
    }

    /**
     * Gets the impact string
     * @return String impact
     */
    public String getImpact() {
        return impact;
    }

    /**
     * Sets the impact 
     * @param impact String
     */
    public void setImpact(String impact) {
        this.impact = impact;
    }
}
