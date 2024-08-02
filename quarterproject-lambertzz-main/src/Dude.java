import processing.core.PImage;

import java.util.List;

public abstract class Dude extends ActivityEntity {
    private int resourceLimit;

    public Dude(String id, Point position, double animationPeriod, double actionPeriod, int resourceLimit, List<PImage> images) {
        super(id, position, animationPeriod, actionPeriod, images);
        this.resourceLimit = resourceLimit;
    }

    public Point nextPosition(WorldModel world, Point destPos) {

        PathingStrategy strat = new AStarPathingStrategy();

        List<Point> path = strat.computePath(getPosition(), destPos,
                pos -> world.withinBounds(pos) && (world.getOccupant(pos).isEmpty() || ! (!world.getOccupant(pos).isEmpty() &&
                        world.getOccupant(getPosition()).get().getClass() == House.class)), Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);

        if (path.size() > 0)
        {
            return path.get(0);
        }
        else {
            return getPosition();
        }
    }
    public int getResourceLimit() {
        return resourceLimit;
    }
}
