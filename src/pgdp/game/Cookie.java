package pgdp.game;

import java.util.List;

public class Cookie extends Entity{

    private Direction direction;

    public Cookie(Position position, Direction direction) {
        super(new LocatedBoundingBox(position, new BoundingBox(1, 1)));
        setDirection(direction);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean visit(Game game) {
        if (super.getHitBox().isEmpty()) {
            return false;
        }
        if (game.getCollisionBoard().hasCollisions(this, direction)) {
            List<Entity> entitiesWithCollisions = game.getCollisionBoard().getCollisions(this, direction);
            Entity currentEntity;
            for (int i = 0; i < entitiesWithCollisions.size(); i++) {
                currentEntity = entitiesWithCollisions.get(i);
                if (currentEntity instanceof Figure) {
                    ((Figure) currentEntity).setDisabledCooldown(60);
                    if (((Figure) currentEntity).isHasHat()) {
                        ((Figure) currentEntity).setHasHat(false);
                        Hat hat = new Hat(currentEntity.getHitBox().get().getPosition());
                        game.getEntityAdd().add(hat);
                    }
                }
            }
            return false;
        }

        game.getCollisionBoard().move(this, direction);
        return true;
    }

    @Override
    public Image getImage() {
        return Image.COOKIE;
    }
}
