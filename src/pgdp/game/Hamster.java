package pgdp.game;

import java.util.List;

public class Hamster extends Figure {

    public Hamster(Position position) {
        super(new LocatedBoundingBox(position, new BoundingBox(3, 3)));
    }

    @Override
    public void attack(Game game) {
        List<Entity> entitiesWithCollision = game.getCollisionBoard().getCollisions(this, getLastDirection());
        int figureCount = 0;
        for (Entity current : entitiesWithCollision) {
            if (current instanceof Figure) {
                figureCount++;
                ((Figure) current).setDisabledCooldown(60);
                if (((Figure) current).isHasHat() && !isHasHat()) {
                    ((Figure) current).setHasHat(false);
                    setHasHat(true);
                }
            }
        }
        if (figureCount == 0) {
            game.getEntityAdd().add(new Cookie(newCookiePosition(getLastDirection()), getLastDirection()));
        }
    }

    public Position newCookiePosition(Direction direction) {
        int x = super.getHitBox().get().getPosition().getX();
        int y = super.getHitBox().get().getPosition().getY();
        switch (direction) {
            case UP -> {
                x+=1;
                y -= 1;
            }
            case UP_RIGHT -> {
                x += 3;
                y -= 1;
            }
            case RIGHT -> {
                x += 3;
                y += 1;
            }
            case DOWN_RIGHT -> {
                x += 3;
                y += 3;
            }
            case DOWN -> {
                x += 1;
                y += 3;
            }
            case DOWN_LEFT -> {
                x -= 1;
                y += 3;
            }
            case LEFT -> {
                x -= 1;
                y += 1;
            }
            case UP_LEFT -> {
                x -= 1;
                y -= 1;
            }
            default -> {
                throw new RuntimeException();
            }
        }
        return new Position(x, y);
    }

    @Override
    public int getFullAttackCooldown() {
        return 140;
    }

    @Override
    public int getFullMoveCooldown() {
        return 15;
    }

    @Override
    public Image getImage() {
        if (!isHasHat()) {
            return Image.HAMSTER;
        }
        return Image.HAMSTER_HAT;
    }
}
