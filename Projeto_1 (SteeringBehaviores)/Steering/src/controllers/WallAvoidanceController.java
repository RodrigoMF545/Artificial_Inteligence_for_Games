package controllers;

import engine.Car;
import engine.Game;
import engine.GameObject;
import engine.RotatedRectangle;
import engine.Vector2D;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;


public class WallAvoidanceController extends Controller{
   
    private GameObject target;

    //Distancia máxima dos raios
    private static final double Ray_Distance = 25;

    public WallAvoidanceController (GameObject _target){
        target = _target;
    }

    //função de raycasting que verifica se existe um ostaculo á frente de um angulo especifico
    public boolean isObstacleAhead(Car subject, Game game, double angle){
        double rayEndX= subject.getX() + Math.cos(angle) * Ray_Distance;
        double rayEndY= subject.getY() + Math.sin(angle) * Ray_Distance;

        RotatedRectangle rayBox = new RotatedRectangle(rayEndX, rayEndY, subject.getImg().getWidth(), subject.getImg().getHeight(), angle);

        for (GameObject obj : game.getObjects()){
            if(obj.isObstacle() && RotatedRectangle.RotRectsCollision(rayBox, obj.getCollisionBox())){
                return true; //obstaculo detectado
            }
        }
        return false; //nenhum obstáculo na direção do raio
    }

    public Vector2D calculateSteering(Car subject, double targetX, double targetY) {
        Vector2D carPos = new Vector2D(subject.getX(), subject.getY());
        Vector2D targetPos = new Vector2D(targetX, targetY);
        
        Vector2D desiredDirection = targetPos.subtração(carPos).normalize().times(MAX_ACCELERATION);
        return desiredDirection;
    }

    public void update(Car subject, Game game, double delta_t, double[] controlVariables) {
        controlVariables[VARIABLE_STEERING] = 0;
        controlVariables[VARIABLE_THROTTLE] = 0;
        controlVariables[VARIABLE_BRAKE] = 0;
    
        double carAngle = subject.getAngle();
    
        // Verificar se há obstáculos em três direções: esquerda, frente e direita
        boolean leftObstacle = isObstacleAhead(subject, game, carAngle - Math.PI / 4);   // Raio à esquerda
        boolean frontObstacle = isObstacleAhead(subject, game, carAngle);                // Raio à frente
        boolean rightObstacle = isObstacleAhead(subject, game, carAngle + Math.PI / 4);  // Raio à direita
    
        if (leftObstacle && frontObstacle && rightObstacle) {
            // Todos os raios estão bloqueados - retroceder
            System.out.println("All directions blocked -> Moving Backward");
            double newX = subject.getX() - Math.cos(carAngle) * Ray_Distance;
            double newY = subject.getY() - Math.sin(carAngle) * Ray_Distance;
            motorControl(subject, calculateSteering(subject, newX, newY), controlVariables);
    
        } else if (frontObstacle) {
            // Se apenas o caminho à frente está bloqueado, tente contornar
            if (!leftObstacle) {
                System.out.println("Obstacle in Front -> Steering Left");
                double newX = subject.getX() + Math.cos(carAngle - Math.PI / 4) * Ray_Distance;
                double newY = subject.getY() + Math.sin(carAngle - Math.PI / 4) * Ray_Distance;
                motorControl(subject, calculateSteering(subject, newX, newY), controlVariables);
    
            } else if (!rightObstacle) {
                System.out.println("Obstacle in Front -> Steering Right");
                double newX = subject.getX() + Math.cos(carAngle + Math.PI / 4) * Ray_Distance;
                double newY = subject.getY() + Math.sin(carAngle + Math.PI / 4) * Ray_Distance;
                motorControl(subject, calculateSteering(subject, newX, newY), controlVariables);
            } else {
                // Se todos os caminhos laterais também estão bloqueados, retrocede
                System.out.println("Front and Sides blocked -> Moving Backward");
                double newX = subject.getX() - Math.cos(carAngle) * Ray_Distance;
                double newY = subject.getY() - Math.sin(carAngle) * Ray_Distance;
                motorControl(subject, calculateSteering(subject, newX, newY), controlVariables);
            }
    
        } else if (leftObstacle) {
            // Se só o lado esquerdo está bloqueado, desvia para a direita
            System.out.println("Obstacle on Left -> Steering Right");
            double newX = subject.getX() + Math.cos(carAngle + Math.PI / 4) * Ray_Distance;
            double newY = subject.getY() + Math.sin(carAngle + Math.PI / 4) * Ray_Distance;
            motorControl(subject, calculateSteering(subject, newX, newY), controlVariables);
    
        } else if (rightObstacle) {
            // Se só o lado direito está bloqueado, desvia para a esquerda
            System.out.println("Obstacle on Right -> Steering Left");
            double newX = subject.getX() + Math.cos(carAngle - Math.PI / 4) * Ray_Distance;
            double newY = subject.getY() + Math.sin(carAngle - Math.PI / 4) * Ray_Distance;
            motorControl(subject, calculateSteering(subject, newX, newY), controlVariables);
    
        } else {
            // Nenhum obstáculo detectado, segue para o alvo
            motorControl(subject, calculateSteering(subject, target.getX(), target.getY()), controlVariables);
        }
    }
    
}
