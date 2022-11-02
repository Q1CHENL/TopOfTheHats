package pgdp.game;

import java.util.ArrayList;
import java.util.List;
import pgdp.game.helper.*;

public abstract class AIControls implements Controls{

    private Entity target;

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    public void move(Game game, Figure figure) {
        Direction direction;

        if (selectTarget(game) != null) {
            if (!figure.isHasHat()) {
                direction = selectDirection(selectTarget(game), figure);
            } else {
                direction = selectRandomDirection();
            }

            figure.moveTo(game, direction);
        }
    }

    public Entity selectTarget(Game game) {
        if (target == null
                || target.getHitBox().isEmpty()
                || target instanceof Figure && !((Figure) target).isHasHat()) {
            List<Entity> entities = game.getEntities();
            List<Entity> targets = new ArrayList<>();
            for (Entity entity : entities) {
                if (entity.asTarget()) {
                    targets.add(entity);
                }
            }
            if (targets.size() >= 1) {
                setTarget(targets.get(pgdp.game.helper.Random.get(0, targets.size())));
            }
        }
        return target;
    }

    public Direction selectRandomDirection() {
        int random = Random.get(1, 9);
        return switch (random) {
            case 1 -> Direction.UP;
            case 2 -> Direction.UP_RIGHT;
            case 3 -> Direction.RIGHT;
            case 4 -> Direction.DOWN_RIGHT;
            case 5 -> Direction.DOWN;
            case 6 -> Direction.DOWN_LEFT;
            case 7 -> Direction.LEFT;
            case 8 -> Direction.UP_LEFT;
            default -> throw new IllegalStateException("Unexpected value: " + random);
        };
    }


    public Direction selectDirection(Entity other, Figure self) {
        if (oneHasNoPosition(other, self) || samePosition(other, self)) {
            return Direction.UP;
        }

        int indicator = parallelToAxis(self.getHitBox().get().getPosition().getX(),
                self.getHitBox().get().getPosition().getY(),
                other.getHitBox().get().getPosition().getX(),
                other.getHitBox().get().getPosition().getY());

        if (indicator != 0) {
            return switch (indicator) {
                case 1 -> Direction.DOWN;
                case 2 -> Direction.RIGHT;
                case 3 -> Direction.UP;
                case 4 -> Direction.LEFT;
                default -> throw new IllegalStateException("Unexpected value: " + indicator);
            };
        } else {
            return diagonalDirection(other, self);
        }
    }


    public Direction diagonalDirection(Entity other, Figure self) {
        int selfX = self.getHitBox().get().getPosition().getX();
        int selfY = self.getHitBox().get().getPosition().getY();
        int otherX = other.getHitBox().get().getPosition().getX();
        int otherY = other.getHitBox().get().getPosition().getY();
        if (otherX - selfX > 0) {
            if (otherY - selfY < 0) {
                return Direction.UP_RIGHT;
            }
            return Direction.DOWN_RIGHT;
        } else {
            if (otherY - selfY < 0) {
                return Direction.UP_LEFT;
            }
            return Direction.DOWN_LEFT;
        }
    }

    public int parallelToAxis(int selfX, int selfY, int otherX, int otherY) {

        if (selfX == otherX) {
            if (selfY < otherY) {
                return 1;
            }
            return 3;
        }

        if (selfY == otherY) {
            if (selfX < otherX) {
                return 2;
            }
            return 4;
        }
        return 0;
    }

    public boolean samePosition(Entity other, Figure self) {
        return self.getHitBox().get().getPosition().getX() ==
                other.getHitBox().get().getPosition().getX() &&
                self.getHitBox().get().getPosition().getY() ==
                        other.getHitBox().get().getPosition().getY();
    }

    public boolean oneHasNoPosition(Entity other, Figure self) {
        return self.getHitBox().isEmpty()
                || other.getHitBox().isEmpty();
    }

}
