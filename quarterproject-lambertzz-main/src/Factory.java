import processing.core.PImage;

import java.util.*;

/**
 * This class contains the creation logic for Actions and Entities
 */

public final class Factory {
    private static final double TREE_ANIMATION_MAX = 0.600;
    private static final double TREE_ANIMATION_MIN = 0.050;
    private static final double TREE_ACTION_MAX = 1.400;
    private static final double TREE_ACTION_MIN = 1.000;
    private static final int TREE_HEALTH_MAX = 3;
    private static final int TREE_HEALTH_MIN = 1;
    private static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    private static final int SAPLING_HEALTH_LIMIT = 5;

    public static Action createAnimationAction(AnimationEntity entity, int repeatCount) {
        return new Animation(entity, repeatCount);
    }

    public static Action createActivityAction(ActivityEntity entity, WorldModel world, ImageStore imageStore) {
        return new Activity(entity, world, imageStore);
    }

    public static House createHouse(String id, Point position, List<PImage> images) {
        return new House(id, position, images);
    }

    public static Obstacle createObstacle(String id, Point position, double animationPeriod, List<PImage> images) {
        return new Obstacle(id, position, animationPeriod, images);
    }

    public static Tree createTree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
        return new Tree(id, position, images, animationPeriod, actionPeriod, health, 0);
    }
    public static Tree createTreeWithDefaults(String id, Point position, List<PImage> images) {
        return new Tree(id, position, images, Factory.getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN), Factory.getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN), Factory.getIntFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN), 0);
    }
    private static int getIntFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(max-min);
    }

    private static double getNumFromRange(double max, double min) {
        Random rand = new Random();
        return min + rand.nextDouble() * (max - min);
    }

    public static Stump createStump(String id, Point position, List<PImage> images) {
        return new Stump(id, position, images);
    }

    // health starts at 0 and builds up until ready to convert to Tree
    public static Sapling createSapling(String id, Point position, List<PImage> images) {
        return new Sapling(id, position, images, SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, 0, SAPLING_HEALTH_LIMIT);
    }

    public static Fairy createFairy(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Fairy(id, position, animationPeriod, actionPeriod, images);
    }

    // need resource count, though it always starts at 0
    public static PersonSearching createPersonSearching(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new PersonSearching(id, position, animationPeriod, actionPeriod, resourceLimit, images);
    }

    // don't technically need resource count ... full
    public static PersonFull createPersonFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new PersonFull(id, position, animationPeriod, actionPeriod, resourceLimit, images);
    }

}
