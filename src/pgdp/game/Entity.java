package pgdp.game;

import java.util.Optional;

public abstract class Entity {
    private LocatedBoundingBox hitBox;

    public Entity(LocatedBoundingBox hitBox) {
        this.hitBox = hitBox;
    }

    public Optional<LocatedBoundingBox> getHitBox() {
        return Optional.ofNullable(hitBox);
    }

    public void setHitBox(LocatedBoundingBox hitBox) {
        this.hitBox = hitBox;
    }

    public abstract boolean visit(Game game);

    public boolean asTarget() {
        return this instanceof Hat || this instanceof Figure && this.getHitBox().isPresent() && ((Figure) this).isHasHat();
    }

    public abstract Image getImage();

}
