package ch.idsia.agents.controllers;

import ch.idsia.agents.controllers.BT_MarioImplementacion.*;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.engine.sprites.Sprite;
import ch.idsia.benchmark.mario.environments.Environment;
import java.util.Set;

// Agente que controla o comportamento do Mario usando uma árvore de decisão
public class BehaviorAgent extends BasicMarioAIAgent {
    private Tree tree; // Instância da árvore de decisão

    // Construtor que configura o agente
    public BehaviorAgent() {
        super("BehaviorAgent");
        reset();        // Inicializa as ações do agente
        configureTree(); // Configura a árvore de comportamento
    }

    // Configura a árvore de decisão com suas sequências e tarefas
    private void configureTree() {
        tree = new Tree(this);

        // Adiciona tarefas e sequências à árvore
        Sequence s1 = new Sequence();
        s1.addChild(new IsCoinNear());
        s1.addChild(new CoinAction());

        Sequence s2 = new Sequence();
        s2.addChild(new IsEnemyNear());
        s2.addChild(new ShootAction());

        Sequence s3 = new Sequence();
        s3.addChild(new CanGoForward());
        s3.addChild(new ForwardAction());

        Sequence s4 = new Sequence();
        s4.addChild(new CanGoForwardJump());
        s4.addChild(new ForwardJumpAction());

        Sequence s5 = new Sequence();
        s5.addChild(new CanGoBackward());
        s5.addChild(new BackwardAction());

        Sequence s6 = new Sequence();
        s6.addChild(new CanGoBackwardJump());
        s6.addChild(new BackwardJumpAction());

        // Adiciona as sequências como tarefas principais na árvore
        tree.addTask(s1);
        tree.addTask(s2);
        tree.addTask(s3);
        tree.addTask(s4);
        tree.addTask(s5);
        tree.addTask(s6);
    }

    // Retorna as ações a serem executadas pelo agente
    public boolean[] getAction() {
        reset();       // Limpa as ações anteriores
        tree.runTree(); // Executa a árvore de decisão
        return action;
    }

    // Reseta todas as ações para false
    public void reset() {
        action = new boolean[Environment.numberOfKeys];
    }

    // Verifica se o código do objeto é de uma criatura (inimigo)
    public boolean isCreature(int c) {
        return CREATURES.contains(c);
    }

    // Verifica se o código do objeto é uma moeda
    public boolean isCoin(int c) {
        return c == -24;
    }

    // Verifica se o caminho à frente está livre
    public boolean clearFront() {
        return isPathClear(1, 0) && isPathClear(2, 0);
    }

    // Verifica se é possível realizar um salto à frente
    public boolean clearForwardJump() {
        return (isMarioAbleToJump || !isMarioOnGround) && !isCreature(enemies[getMarioEgoRow() + 3][getMarioEgoCol() + 3]);
    }

    // Verifica se o caminho atrás está livre
    public boolean clearBack() {
        return isPathClear(-1, 0);
    }

    // Método utilitário para verificar se um caminho está livre
    private boolean isPathClear(int deltaX, int deltaY) {
        int x = getMarioEgoRow();
        int y = getMarioEgoCol();
        return !isCreature(enemies[x + deltaX][y + deltaY]) && levelScene[x + deltaX][y + deltaY] == 0;
    }

    // Métodos de ação
    public void jumpKey() {
        action[Mario.KEY_JUMP] = true; // Pressiona a tecla de pular
    }

    public void shootKey() {
        action[Mario.KEY_SPEED] = true; // Pressiona a tecla de disparo
    }

    public void leftKey() {
        action[Mario.KEY_LEFT] = true; // Pressiona a tecla para ir à esquerda
    }

    public void rightKey() {
        action[Mario.KEY_RIGHT] = true; // Pressiona a tecla para ir à direita
    }

    // Lista de códigos de inimigos para referência
    private static final Set<Integer> CREATURES = Set.of(
        Sprite.KIND_GOOMBA,
        Sprite.KIND_RED_KOOPA,
        Sprite.KIND_RED_KOOPA_WINGED,
        Sprite.KIND_GREEN_KOOPA_WINGED,
        Sprite.KIND_GREEN_KOOPA
    );
}
