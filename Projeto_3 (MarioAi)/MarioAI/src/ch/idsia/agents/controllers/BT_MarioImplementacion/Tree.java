package ch.idsia.agents.controllers.BT_MarioImplementacion;

import ch.idsia.agents.controllers.BehaviorAgent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Classe que representa a árvore de comportamento principal
public class Tree {
    private List<Task> tasks; // Lista de tarefas (nós principais da árvore)
    private BehaviorAgent b;  // Referência ao agente de comportamento

    // Construtor que inicializa a árvore com uma referência ao agente
    public Tree(BehaviorAgent _b) {
        this.tasks = new ArrayList<>();
        this.b = _b;
    }

    // Executa as tarefas da árvore em ordem de prioridade
    public void runTree() {
        tasks.stream()
            .sorted(Comparator.comparingInt(Task::getPriority).reversed()) // Ordena por prioridade (maior primeiro)
            .forEach(task -> {
                if (task.run(b)) return; // Para a execução se uma tarefa retornar true
            });
    }

    // Adiciona uma nova tarefa à árvore
    public void addTask(Task task) {
        tasks.add(task);
    }
}
