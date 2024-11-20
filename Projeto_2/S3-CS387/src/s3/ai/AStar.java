package s3.ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import s3.base.S3;
import s3.entities.S3PhysicalEntity;
import s3.util.Pair;

public class AStar {
    private final S3 game;
    private final S3PhysicalEntity entity;
    private final double start_x, start_y, goal_x, goal_y;

    public static int pathDistance(double start_x, double start_y, double goal_x, double goal_y,
                                    S3PhysicalEntity i_entity, S3 the_game) {
        AStar a = new AStar(start_x, start_y, goal_x, goal_y, i_entity, the_game);
        List<Pair<Double, Double>> path = a.computePath();
        if (path != null) return path.size();
        return -1;
    }

    public AStar(double start_x, double start_y, double goal_x, double goal_y,
                 S3PhysicalEntity i_entity, S3 the_game) {
        this.start_x = start_x;
        this.start_y = start_y;
        this.goal_x = goal_x;
        this.goal_y = goal_y;
        this.entity = i_entity;
        this.game = the_game;
    }

    public List<Pair<Double, Double>> computePath() {
        // Inicializa os nós de início e objetivo
        Node startNode = new Node((int) start_x, (int) start_y, 0, heuristica(start_x, start_y), null);
        Node goalNode = new Node((int) goal_x, (int) goal_y, Double.MAX_VALUE, 0, null);

        // Fila de prioridade para A*
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Pair<Integer, Integer>> closedSet = new HashSet<>();

        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            // Obtém o nó com menor fCost
            Node currentNode = openSet.poll();

            // Verifica se atingimos o objetivo
            if (currentNode.getX() == goalNode.getX() && currentNode.getY() == goalNode.getY()) {
                return Node.reconstructPath(currentNode);
            }

            // Marca como visitado
            closedSet.add(new Pair<>(currentNode.getX(), currentNode.getY()));

            // Explorar os vizinhos
            for (Node neighbor : getNeighbors(currentNode)) {
                Pair<Integer, Integer> neighborPos = new Pair<>(neighbor.getX(), neighbor.getY());

                // Ignora se já visitado
                if (closedSet.contains(neighborPos)) continue;

                // Atualiza o custo e adiciona à fila
                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                }
            }
        }

        return null; // Caminho não encontrado
    }

    private double heuristica(double x, double y) {
        // Exemplo: Distância de Manhattan
        return Math.abs(x - goal_x) + Math.abs(y - goal_y);
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        // Exemplo: Gerar nós vizinhos no grid (4-direcional)
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] dir : directions) {
            int newX = node.getX() + dir[0];
            int newY = node.getY() + dir[1];
            if (isValidPosition(newX, newY)) {
                double gCost = node.getG() + 1;
                double hCost = heuristica(newX, newY);
                neighbors.add(new Node(newX, newY, gCost, hCost, node));
            }
        }
        return neighbors;
    }

    private boolean isValidPosition(int x, int y) {
        // Validação específica do jogo
        return true; // Atualize conforme necessário
    }

    // Classe Node como interna estática
    private static class Node implements Comparable<Node> {
        private final int x, y;
        private final double g, h;
        private final Node parent;

        public Node(int x, int y, double g, double h, Node parent) {
            this.x = x;
            this.y = y;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public double getG() {
            return g;
        }

        public double getH() {
            return h;
        }

        public Node getParent() {
            return parent;
        }

        public double fCost() {
            return g + h;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.fCost(), other.fCost());
        }

        public static List<Pair<Double, Double>> reconstructPath(Node endNode) {
            List<Pair<Double, Double>> path = new ArrayList<>();
            Node current = endNode;
            while (current != null) {
                path.add(0, new Pair<>((double) current.getX(), (double) current.getY()));
                current = current.getParent();
            }
            return path;
        }
    }
}
