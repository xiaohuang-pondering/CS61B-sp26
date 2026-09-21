import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;
import java.util.Map;

public class Particle {
    public ParticleFlavor flavor;
    public int lifespan;

    public static final int PLANT_LIFESPAN = 150;
    public static final int FLOWER_LIFESPAN = 75;
    public static final int FIRE_LIFESPAN = 10;
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                   ParticleFlavor.PLANT, PLANT_LIFESPAN,
                   ParticleFlavor.FIRE, FIRE_LIFESPAN);

    public Particle(ParticleFlavor flavor) {
        this.flavor = flavor;
        if (flavor == ParticleFlavor.FLOWER ||
                flavor == ParticleFlavor.PLANT ||
                flavor == ParticleFlavor.FIRE) {
            lifespan = LIFESPANS.get(flavor);
        } else {
            lifespan = -1;
        }
    }

    public Color color() {
        return switch (flavor) {
            case ParticleFlavor.EMPTY    -> Color.BLACK;
            case ParticleFlavor.SAND     -> Color.YELLOW;
            case ParticleFlavor.BARRIER  -> Color.GRAY;
            case ParticleFlavor.WATER    -> Color.BLUE;
            case ParticleFlavor.FOUNTAIN -> Color.CYAN;
            case ParticleFlavor.PLANT    -> {
                double ratio = (double) Math.max(0, Math.min(lifespan, PLANT_LIFESPAN)) / PLANT_LIFESPAN;
                int g = 120 + (int) Math.round((255 - 120) * ratio);
                yield new Color(0, g, 0);
            }
            case ParticleFlavor.FIRE     -> {
                double ratio = (double) Math.max(0, Math.min(lifespan, FIRE_LIFESPAN)) / FIRE_LIFESPAN;
                int r = (int) Math.round(255 * ratio);
                yield new Color(r, 0, 0);
            }
            case ParticleFlavor.FLOWER   -> {
                double ratio = (double) Math.max(0, Math.min(lifespan, FLOWER_LIFESPAN)) / FLOWER_LIFESPAN;
                int r = 120 + (int) Math.round((255 - 120) * ratio);
                int g = 70 + (int) Math.round((141 - 70) * ratio);
                int b = 80 + (int) Math.round((161 - 80) * ratio);
                yield new Color(r, g, b);
            }
        };
    }

    public void moveInto(Particle other) {
        other.flavor = flavor;
        other.lifespan = lifespan;

        flavor = ParticleFlavor.EMPTY;
        lifespan = -1;
    }

    public void fall(Map<Direction, Particle> neighbors) {
        Particle downNeighbor = neighbors.get(Direction.DOWN);
        if (downNeighbor.flavor == ParticleFlavor.EMPTY) {
            moveInto(downNeighbor);
        }
    }

    public void flow(Map<Direction, Particle> neighbors) {
        int act = StdRandom.uniformInt(3);
        if (act == 1) {
            Particle rightNeighbor = neighbors.get(Direction.RIGHT);
            if (rightNeighbor.flavor == ParticleFlavor.EMPTY) {
                moveInto(rightNeighbor);
            }
        } else if (act == 2) {
            Particle leftNeighbor = neighbors.get(Direction.LEFT);
            if (leftNeighbor.flavor == ParticleFlavor.EMPTY) {
                moveInto(leftNeighbor);
            }
        }
    }

    public void grow(Map<Direction, Particle> neighbors) {
        int act = StdRandom.uniformInt(10);
        switch (act) {
            case 0 -> {
                Particle upNeighbor = neighbors.get(Direction.UP);
                if (upNeighbor.flavor == ParticleFlavor.EMPTY) {
                    upNeighbor.flavor = flavor;
                    upNeighbor.lifespan = LIFESPANS.get(flavor);
                }
            }
            case 1 -> {
                Particle leftNeighbor = neighbors.get(Direction.LEFT);
                if (leftNeighbor.flavor == ParticleFlavor.EMPTY) {
                    leftNeighbor.flavor = flavor;
                    leftNeighbor.lifespan = LIFESPANS.get(flavor);
                }
            }
            case 2 -> {
                Particle rightNeighbor = neighbors.get(Direction.RIGHT);
                if (rightNeighbor.flavor == ParticleFlavor.EMPTY) {
                    rightNeighbor.flavor = flavor;
                    rightNeighbor.lifespan = LIFESPANS.get(flavor);
                }
            }
        }
    }

    public void burn(Map<Direction, Particle> neighbors) {
    }

    public void decrementLifespan() {
        if (lifespan > 0) {
            lifespan--;
        }
        if (lifespan == 0) {
            flavor = ParticleFlavor.EMPTY;
            lifespan = -1;
        }
    }

    public void action(Map<Direction, Particle> neighbors) {
        if (flavor == ParticleFlavor.EMPTY) {
            return;
        }
        if (flavor != ParticleFlavor.BARRIER) {
            fall(neighbors);
        }
        if (flavor == ParticleFlavor.WATER){
            flow(neighbors);
        }
        if (flavor == ParticleFlavor.PLANT || flavor == ParticleFlavor.FLOWER) {
            grow(neighbors);
        }
    }
}