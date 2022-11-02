package pgdp.game;

import java.util.List;

public class WolfPingu extends Figure {

    public WolfPingu(Position position) {
        super(new LocatedBoundingBox(position, new BoundingBox(3, 3)));
    }

    @Override
    public void attack(Game game) {

        if (game.getCollisionBoard().hasCollisions(this, getLastDirection())) {

        List<Entity> entitiesWithCollision = game.getCollisionBoard().getCollisions(this, getLastDirection());
        for (Entity current : entitiesWithCollision) {
            if (current instanceof Figure) {
                ((Figure) current).setDisabledCooldown(60);
                if (((Figure) current).isHasHat() && !isHasHat()) {
                    ((Figure) current).setHasHat(false);
                    setHasHat(true);
                }
            }
        }
    }

}
    @Override
    public int getFullMoveCooldown() {
        return 10;
    }

    @Override
    public int getFullAttackCooldown() {
        return 120;
    }

    @Override
    public Image getImage() {
        if(!isHasHat()){
        return Image.WOLF_PINGU;
        }
        return Image.WOLF_PINGU_HAT;
    }


}
