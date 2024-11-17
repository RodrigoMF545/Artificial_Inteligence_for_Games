package controllers;

import java.awt.event.KeyEvent;

import engine.Car;
import engine.Game;
import engine.GameObject;
import engine.RotatedRectangle;
import engine.Vector2D;

public class WallAvoidanceController_2 extends Controller {
    
    private GameObject target;
    private static final double RAY_DISTANCE = 25; // Distância máxima dos raios

    public WallAvoidanceController_2(GameObject _target) {
        target = _target;    
    }

    // Função de raycasting que verifica se existe um obstáculo à frente de um ângulo específico
    public boolean isObstacleAhead(Car subject, Game game, double angle) {
        double rayEndX = subject.getX() + Math.cos(angle) * RAY_DISTANCE;
        double rayEndY = subject.getY() + Math.sin(angle) * RAY_DISTANCE;
        
        RotatedRectangle rayBox = new RotatedRectangle(
            rayEndX, 
            rayEndY, 
            subject.getImg().getWidth(), 
            subject.getImg().getHeight(), 
            angle
        );

        for (GameObject obj : game.getObjects()) {
            if (obj.isObstacle() && RotatedRectangle.RotRectsCollision(rayBox, obj.getCollisionBox())) {
                return true; // Obstáculo detectado
            }
        }
        return false; // Nenhum obstáculo na direção do raio
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

        // Checar raios em três direções: esquerda, frente e direita
        boolean leftObstacle = isObstacleAhead(subject, game, carAngle - Math.PI / 4);  // Raio à esquerda
        boolean frontObstacle = isObstacleAhead(subject, game, carAngle);              // Raio à frente
        boolean rightObstacle = isObstacleAhead(subject, game, carAngle + Math.PI / 4); // Raio à direita

        // Tomar decisões de desvio com base nos obstáculos detectados
        if (leftObstacle) {
            System.out.println("Obstacle on Left -> Steering Right");
            // Ajusta a direção para a direita
            double newX = subject.getX() + Math.cos(carAngle + Math.PI / 4) * RAY_DISTANCE;
            double newY = subject.getY() + Math.sin(carAngle + Math.PI / 4) * RAY_DISTANCE;
            motorControl(subject, calculateSteering(subject, newX, newY), controlVariables);
        } else if (frontObstacle) {
            System.out.println("Obstacle in Front -> Moving Backward");
            // Ajusta a direção para trás
            double newX = subject.getX() - Math.cos(carAngle) * RAY_DISTANCE;
            double newY = subject.getY() - Math.sin(carAngle) * RAY_DISTANCE;
            motorControl(subject, calculateSteering(subject, newX, newY), controlVariables);
        } else if (rightObstacle) {
            System.out.println("Obstacle on Right -> Steering Left");
            // Ajusta a direção para a esquerda
            double newX = subject.getX() + Math.cos(carAngle - Math.PI / 4) * RAY_DISTANCE;
            double newY = subject.getY() + Math.sin(carAngle - Math.PI / 4) * RAY_DISTANCE;
            motorControl(subject, calculateSteering(subject, newX, newY), controlVariables);
        } else {
            // Se não houver obstáculos, continue perseguindo o alvo
            motorControl(subject, calculateSteering(subject, target.getX(), target.getY()), controlVariables);
        }
    }
}
