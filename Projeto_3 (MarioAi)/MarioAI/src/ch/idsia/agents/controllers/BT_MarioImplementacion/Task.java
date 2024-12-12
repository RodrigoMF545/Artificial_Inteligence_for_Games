package ch.idsia.agents.controllers.BT_MarioImplementacion;

import ch.idsia.agents.controllers.BehaviorAgent;

// Interface base para todos os nós da árvore de decisão
// Cada nó (tarefa) implementará a lógica de execução no método run()
public interface Task {
    boolean run(BehaviorAgent b); // Executa a lógica da tarefa

    // Método padrão para definir prioridade das tarefas (opcional)
    default int getPriority() {
        return 0; // Prioridade padrão é 0
    }
}
