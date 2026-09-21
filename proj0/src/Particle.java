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
        lifespan = -1;
    }

    public Color color() {
        return switch (flavor) {
            case ParticleFlavor.EMPTY    -> Color.BLACK;
            case ParticleFlavor.SAND     -> Color.YELLOW;
            case ParticleFlavor.BARRIER  -> Color.GRAY;
            case ParticleFlavor.WATER    -> Color.BLUE;
            case ParticleFlavor.FOUNTAIN -> Color.CYAN;
            case ParticleFlavor.PLANT    -> new Color(0, 255, 0);
            case ParticleFlavor.FIRE     -> new Color(255, 0, 0);
            case ParticleFlavor.FLOWER   -> new Color(255, 141, 161);
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
                    upNeighbor.lifespan = lifespan;
                }
            }
            case 1 -> {
                Particle leftNeighbor = neighbors.get(Direction.LEFT);
                if (leftNeighbor.flavor == ParticleFlavor.EMPTY) {
                    leftNeighbor.flavor = flavor;
                    leftNeighbor.lifespan = lifespan;
                }
            }
            case 2 -> {
                Particle rightNeighbor = neighbors.get(Direction.RIGHT);
                if (rightNeighbor.flavor == ParticleFlavor.EMPTY) {
                    rightNeighbor.flavor = flavor;
                    rightNeighbor.lifespan = lifespan;
                }
            }
        }
    }

    public void burn(Map<Direction, Particle> neighbors) {
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