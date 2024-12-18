package controllers;

import java.util.Vector;

import engine.Car;
import engine.Game;
import engine.Vector2D;

/**
 *
 * @author santi
 */
public abstract class Controller {
    /*
        commands is an array with three components:
        - the desired "STEER" (-1 to +1)
        - the desired "THROTTLE" (0 to +1)
        - the desired "BRAKE" (0 to +1)
    */
    public static final int VARIABLE_STEERING = 0;
    public static final int VARIABLE_THROTTLE = 1;
    public static final int VARIABLE_BRAKE = 2;
    
    public static final int MAX_ACCELERATION = 5;

    public abstract void update(Car subject, Game game, double delta_t, double controlVariables[]);

    public void motorControl(Car subject, Vector2D target, double controlVariables[]) {   	
    	Vector2D cur = new Vector2D(Math.cos(subject.getAngle()), Math.sin(subject.getAngle()));

    	Vector2D right = new Vector2D((cur.y * -1), cur.x);
    	
    	//Calculates the throttle
    	if(cur.inner_product(target) > 0) {
    		controlVariables[VARIABLE_THROTTLE] = 1;
    	} else if(cur.inner_product(target) < -1) {
    		controlVariables[VARIABLE_BRAKE] = 1;
    	}
    	
    	//Calculate steering 
    	if(right.inner_product(target) > 0) {
    		controlVariables[VARIABLE_STEERING] = right.inner_product(target)/2;
    	} else if(right.inner_product(target) < 0) {
    		controlVariables[VARIABLE_STEERING] = right.inner_product(target)/2;
    	}
    }
}
