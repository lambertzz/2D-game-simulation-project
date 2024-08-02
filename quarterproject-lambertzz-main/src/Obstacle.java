import processing.core.PImage;

import java.util.List;

public class Obstacle extends AnimationEntity {

    public Obstacle(String id, Point position, double animationPeriod, List<PImage> images) {
        super(id, position, animationPeriod, images);
    }
}
