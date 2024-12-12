package ch.idsia.agents.controllers.BT_MarioImplementacion;

import ch.idsia.agents.controllers.BehaviorAgent;
import java.util.ArrayList;
import java.util.List;

// Representa uma sequência de tarefas (todas devem ser concluídas com sucesso para retornar true)
public class Sequence implements Task {
    private List<Task> children; // Lista de tarefas filhas

    // Construtor que inicializa a sequência
    public Sequence() {
        this.children = new ArrayList<>();
    }

    // Executa cada tarefa na sequência; retorna false se alguma falhar
    @Override
    public boolean run(BehaviorAgent b) {
        for (Task child : children) {
            if (!child.run(b)) {
                return false; // Retorna false se alguma tarefa falhar
            }
        }
        return true; // Retorna true se todas as tarefas forem concluídas
    }

    // Adiciona uma nova tarefa à sequência
    public void addChild(Task child) {
        children.add(child);
    }

    // Retorna a lista de tarefas filhas
    public List<Task> getChildren() {
        return children;
    }
}
