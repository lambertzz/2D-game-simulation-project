import processing.core.PImage;

import java.util.List;

public class Sapling extends Grow {
    private static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    private static final int SAPLING_HEALTH_LIMIT = 5;

    public Sapling(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod, int health, int healthLimit) {
        super(id, position, images, SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, 0, SAPLING_HEALTH_LIMIT);
    }
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            Stump stump = Factory.createStump(WorldLoader.STUMP_KEY + "_" + this.getId(), this.getPosition(), imageStore.getImageList(WorldLoader.STUMP_KEY));

            world.removeEntity(scheduler, (Entity) this);

            world.tryAddEntity(stump);

        } else if (this.getHealth() >= ((Sapling) this).getHealthLimit()) {
                Tree tree = Factory.createTreeWithDefaults(WorldLoader.TREE_KEY + "_" + this.getId(), this.getPosition(), imageStore.getImageList(WorldLoader.TREE_KEY));

                world.removeEntity(scheduler, (Entity) this);

                world.tryAddEntity(tree);
                tree.scheduleActions(scheduler, world, imageStore);

                return true;
            }
            return false;
        }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        super.setHealth(super.getHealth()+1);
        super.executeActivity(world, imageStore, scheduler);

    }
}
