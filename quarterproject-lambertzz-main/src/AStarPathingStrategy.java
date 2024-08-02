import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{

    private class Node
    {
        public double h;
        public double g;
        public double f;
        public Point pos;
        public Node prior;


        Node( Point pos, Node prior, double g, double h)
        {
            this.pos = pos;
            this.prior = prior;
            this.g = g;
            this.h = h; this.f = g + h;
        }

        double getF()
        {
            return f;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Node)) return false;
            Node n = (Node) obj;
            return pos.equals(n.pos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos);
        }

    }

    private List<Point> pathHelper(Node curNode, List<Point> path) {
        while (curNode != null && curNode.prior != null) {
            path.add(0, curNode.pos);
            curNode = curNode.prior;
        }
        return path;
    }


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {

        List<Point> path = new LinkedList<>();
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparing(Node::getF));  //openSet
        Set<Point> closed = new HashSet<>();
        Map<Point, Node> gValue = new HashMap<>(); //gvalue


        Node currentNode = new Node(start, null, 0, Point.distanceSquared(start, end));
        openSet.add(currentNode);
        gValue.put(start, currentNode);

        Predicate<Point> closedPoint = p -> !closed.contains(p);

        while(openSet.size() > 0 && !withinReach.test(currentNode.pos, end))
        {

            currentNode = openSet.remove();
            gValue.remove(currentNode.pos);
//                                           p ->  withinBounds(p, grid) && grid[p.y][p.x] != GridValues.OBSTACLE,
            List<Point> pNeighbors =
                    potentialNeighbors
                            .apply(currentNode.pos)
                            .filter(canPassThrough)
                            .filter(closedPoint)
                            .toList();


            for (Point neighbor : pNeighbors) {
                if (neighbor.equals(end)) {
                    return pathHelper(currentNode, new LinkedList<>());
                }
                if (closed.contains(neighbor) || !withinReach.test(currentNode.pos, neighbor)) {
                    continue;
                }

                double tentativeG = currentNode.g + 1;
                double tentativeF = tentativeG + Point.manhattanDistance(neighbor, end);
                Node neighborNode = gValue.get(neighbor);

                if (neighborNode == null) {
                    neighborNode = new Node(neighbor, currentNode, tentativeG, Point.manhattanDistance(neighbor, end));
                    openSet.add(neighborNode);
                    gValue.put(neighbor, neighborNode);
                } else if (tentativeG < neighborNode.g) {
                    neighborNode.g = tentativeG;
                    neighborNode.f = tentativeF;
                    neighborNode.prior = currentNode;
                }
            }
            closed.add(currentNode.pos);
        }
        return path;
    }
}
