package s3.ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import s3.base.S3;
import s3.entities.S3PhysicalEntity;
import s3.util.Pair;

public class AStar {

    private Node start;
    private Node goal;
    private S3 game;
    private S3PhysicalEntity entity;
    private List<Pair<Integer, Integer>> directions;

    public static int pathDistance(double start_x, double start_y, double goal_x, double goal_y,
                                   S3PhysicalEntity i_entity, S3 the_game) {
        AStar a = new AStar(start_x, start_y, goal_x, goal_y, i_entity, the_game);
        List<Pair<Double, Double>> path = a.computePath();
        if (path != null) return path.size();
        return -1;
    }

    public AStar(double start_x, double start_y, double goal_x, double goal_y,
                 S3PhysicalEntity i_entity, S3 the_game) {
        // Initialize start and goal nodes
        this.start = new Node(start_x, start_y);
        this.goal = new Node(goal_x, goal_y);
        this.game = the_game;
        this.entity = i_entity;

        // Define movement directions (4-directional grid)
        this.directions = new ArrayList<>();
        this.directions.add(new Pair<>(-1, 0)); // Left
        this.directions.add(new Pair<>(1, 0));  // Right
        this.directions.add(new Pair<>(0, -1)); // Up
        this.directions.add(new Pair<>(0, 1));  // Down
    }

    public List<Pair<Double, Double>> computePath() {
        // Initialize the open list and closed set
        start.g = 0;
        start.h = heuristic(start);

        ArrayList<Node> open = new ArrayList<>();
        open.add(start);

        HashSet<Node> closed = new HashSet<>();

        while (!open.isEmpty()) {
            // Get the node with the lowest fCost
            Node current = removeLowestF(open);

            // Check if we reached the goal
            if (isGoal(current)) {
                return getPath(current);
            }

            // Mark the current node as visited
            closed.add(current);

            // Explore neighbors
            for (Pair<Integer, Integer> direction : directions) {
                double newX = current.x + direction.m_a;
                double newY = current.y + direction.m_b;

                Node neighbor = new Node(newX, newY);

                if (isSpaceValid(newX, newY) && !open.contains(neighbor) && !closed.contains(neighbor)) {
                    neighbor.parent = current;
                    neighbor.g = current.g + 1; // Move cost
                    neighbor.h = heuristic(neighbor);
                    open.add(neighbor);
                }
            }
        }

        // Return null if no path is found
        return null;
    }

    private boolean isSpaceValid(double x, double y) {
        // Check boundaries and collisions
        if (x < 0 || y < 0 || x >= game.getMap().getWidth() || y >= game.getMap().getHeight()) {
            return false;
        }

        // Create a temporary clone of the entity to check for collisions
        S3PhysicalEntity tempEntity = (S3PhysicalEntity) entity.clone();
        tempEntity.setX((int) x);
        tempEntity.setY((int) y);

        return game.anyLevelCollision(tempEntity) == null;
    }

    private List<Pair<Double, Double>> getPath(Node node) {
        // Reconstruct the path from the goal to the start
        List<Pair<Double, Double>> path = new ArrayList<>();
        while (node != start) {
            path.add(0, new Pair<>(node.x, node.y));
            node = node.parent;
        }
        return path;
    }

    private boolean isGoal(Node node) {
        // Check if the node matches the goal coordinates
        return node.x == goal.x && node.y == goal.y;
    }

    private Node removeLowestF(ArrayList<Node> open) {
        // Find and remove the node with the lowest fCost
        Node lowest = null;
        double lowestF = Double.MAX_VALUE;

        for (Node node : open) {
            double f = node.g + node.h;
            if (f < lowestF) {
                lowest = node;
                lowestF = f;
            }
        }

        open.remove(lowest);
        return lowest;
    }

    private double heuristic(Node node) {
        // Use Manhattan distance as the heuristic
        return Math.abs(goal.x - node.x) + Math.abs(goal.y - node.y);
    }

    // Inner class for A* nodes
    private class Node {
        Node parent;
        double x, y;
        double g, h; // Cost from start and heuristic estimate

        private Node(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node other = (Node) obj;
            return Double.compare(x, other.x) == 0 && Double.compare(y, other.y) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(x) + Double.hashCode(y) * 31;
        }
    }
}
