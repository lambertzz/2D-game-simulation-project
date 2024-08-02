import processing.core.PImage;

import java.util.List;

public abstract class AnimationEntity extends Entity {
    private double animationPeriod;

    public AnimationEntity(String id, Point position, double animationPeriod, List<PImage> images) {
        super(id, position, images);
        this.animationPeriod = animationPeriod;
    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Factory.createAnimationAction(this, 0), this.getAnimationPeriod());
    }

    public double getAnimationPeriod() {
        return animationPeriod;
    }
}
