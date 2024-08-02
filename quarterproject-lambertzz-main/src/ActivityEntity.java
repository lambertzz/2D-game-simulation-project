import processing.core.PImage;

import java.util.List;

public abstract class ActivityEntity extends AnimationEntity {
    private double actionPeriod;
    int health;

    public ActivityEntity(String id, Point position, double animationPeriod, double actionPeriod, List<PImage> images) {
        super(id, position, animationPeriod, images);
        this.actionPeriod = actionPeriod;
        this.health = health;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Factory.createAnimationAction(this, 0), getAnimationPeriod());
        scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), actionPeriod);
    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public double getActionPeriod() {
        return actionPeriod;
    }


    protected int getHealth() {
        return health;
    }
}
