package controllers;

import engine.Car;
import engine.Game;
import engine.GameObject;
import engine.Vector2D;

public class ArriveController extends Controller {

    private GameObject target;
    private double slowRadios;
    private double targetRadios;

    public ArriveController (GameObject _target, double _slowRadios, double _targetRadios){
        target = _target;
        slowRadios = _slowRadios;
        targetRadios = _targetRadios;
    }

    public Vector2D Arrive (Car subject, double delta_t){
    /*
     * Declaração de Vetores:
     * ---->targetVelocity ( vetor velocidade  que queremos que o carro atinja)
     * ---->d(desired_direction)(armazena a diferença entre a posição do alvo e a posição atual do carro)
     * ---->a(aceleração) (aceleração necessária para mover o carro em direção ao alvo)
     * ---->character(posição atual do carro)
     * ---->target_position(posição do alvo (target))
     */
        Vector2D targetvelocity = new Vector2D(subject.getX(), subject.getY());
        Vector2D d = new Vector2D(subject.getX(),subject.getY());
        Vector2D a = new Vector2D(subject.getX(), subject.getY());
        Vector2D character_position = new Vector2D(subject.getX(), subject.getY());
        Vector2D targuet_position = new Vector2D(target.getX(),target.getY());

    //Calcula a direção do alvo (diferença entre o alvo e a posição atual do carro)
        d = targuet_position.subtração(character_position);
        
    //Calcular a distancia entre o carro e o alvo
        double length =d.norma();
        double targetSpeed;

    // Se estiver dentro do raio, a função retorna um vetor (0,0), o que indica que o carro deve parar
    // Se a distância é maior que o slowRadius, a velocidade alvo é a máxima.
    // // Caso contrário, ajusta a velocidade alvo proporcionalmente à distância e ao raio de desaceleração.
        if (length < targetRadios){
            return new Vector2D(0, 0);
        }
        else if(length > slowRadios){
            targetSpeed = subject.getMaxSpeed();
        }
        else{
            targetSpeed = subject.getMaxSpeed() * (length / slowRadios);
        }

    // Normaliza o vetor direção e o multiplica pela velocidade alvo.
        targetvelocity = d.normalize();
        targetvelocity = targetvelocity.times(targetSpeed);

    // Calcula a velocidade atual do carro com base no ângulo de orientação.
        Vector2D characterVelocity = new Vector2D(Math.cos(subject.getAngle()), Math.sin(subject.getAngle()));
        characterVelocity = characterVelocity.normalize();
        characterVelocity = characterVelocity.times(subject.getSpeed());
    
    // A = (velocidade alvo - velocidade atual) / delta_t (tempo de atualização).
        a = targetvelocity.subtração(characterVelocity).times((1/delta_t));

     // Se a aceleração for maior que a aceleração máxima permitida, normaliza e ajusta para o valor máximo.
        if(a.norma() > MAX_ACCELERATION){
            a = a.normalize();
            a = a.times(MAX_ACCELERATION);
        }

        return a;
    }

    public void update(Car subject, Game game, double delta_t, double controlVariables[]) {
	    controlVariables[VARIABLE_STEERING] = 0;
	    controlVariables[VARIABLE_THROTTLE] = 0;
	    controlVariables[VARIABLE_BRAKE] = 0;

	    motorControl(subject, Arrive(subject, delta_t), controlVariables);
	}
    
}
