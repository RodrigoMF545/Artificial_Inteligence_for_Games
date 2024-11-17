package controllers;

import engine.Car;
import engine.Game;
import engine.GameObject;
import engine.Vector2D;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;


public class SeekController extends Controller{
    
    private GameObject target;

    public SeekController(GameObject _target){
        this.target = _target;
    }

    public Vector2D seek(Car subject) {
       Vector2D n = new Vector2D(subject.getX(), subject.getY());

       Vector2D car = new Vector2D(subject.getX(), subject.getY());
       Vector2D targetCar = new Vector2D(target.getX(), target.getY());
       n = targetCar.subtração(car);
       n = n.normalize();
       n = n.times(MAX_ACCELERATION);

       return n;
    }
    public void update(Car subject, Game game, double delta_t, double controlVariables[]) {
	    controlVariables[VARIABLE_STEERING] = 0;
	    controlVariables[VARIABLE_THROTTLE] = 0;
	    controlVariables[VARIABLE_BRAKE] = 0;

	    motorControl(subject, seek(subject), controlVariables);
	}
}