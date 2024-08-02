import processing.core.PImage;

import java.util.List;

public abstract class Grow extends ActivityEntity implements Transform {
    private int health;
    private int healthLimit;

    public Grow(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, actionPeriod, animationPeriod, images);
        this.health = health;
        this.healthLimit = healthLimit;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (!this.transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), getActionPeriod());
        }
    }

    public int getHealth() {
        return health;
    }

    public int getHealthLimit() {
        return healthLimit;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
