package pgdp.game;

import java.util.List;


//methods in Figure: setzt Handlung um
public abstract class Figure extends Entity{

    private Direction lastDirection;
    private int attackCooldown;
    private int moveCooldown;
    private int disabledCooldown;
    private boolean hasHat;
    private Controls controls;

    public Figure(LocatedBoundingBox locatedBoundingBox){
        super(locatedBoundingBox);
        setMoveCooldown(getFullMoveCooldown());
        setAttackCooldown(getFullAttackCooldown());
        setHasHat(false);
        setLastDirection(Direction.UP);
        setDisabledCooldown(0);
    }

    @Override
    public boolean visit(Game game) {
        if (disabledCooldown > 0) {
            disabledCooldown--;
        }
        if (disabledCooldown > 0) {
            return true;
        }

        moveCooldown--;
        if (moveCooldown == 0) {
            setMoveCooldown(getFullMoveCooldown());
            controls.move(game, this);
        }

        if (attackCooldown > 0) {
            attackCooldown--;
        }

        if (attackCooldown == 0 && controls.attack(game, this)) {
                setAttackCooldown(getFullAttackCooldown());
                attack(game);
            }

        return true;
    }


    public abstract void attack(Game game);

    public void moveTo(Game game, Direction direction) {
        lastDirection = direction;
        List<Entity> entitiesWithCollision = game.getCollisionBoard().getCollisions(this, direction);

        if (!isHasHat()) {
            for (Entity entity : entitiesWithCollision) {
                if (entity instanceof Hat) {
                    setHasHat(true);
                    game.getCollisionBoard().remove(entity);
                    ((Hat) entity).setDontRemove(false);
                    break;
                }
            }
        }

        game.getCollisionBoard().move(this, direction);
    }

    public abstract int getFullMoveCooldown();

    public abstract int getFullAttackCooldown();

    public Direction getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(Direction lastDirection) {
        this.lastDirection = lastDirection;
    }

    public int getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public int getMoveCooldown() {
        return moveCooldown;
    }

    public void setMoveCooldown(int moveCooldown) {
        this.moveCooldown = moveCooldown;
    }

    public int getDisabledCooldown() {
        return disabledCooldown;
    }

    public void setDisabledCooldown(int disabledCooldown) {
        this.disabledCooldown = disabledCooldown;
    }

    public boolean isHasHat() {
        return hasHat;
    }

    public void setHasHat(boolean hasHat) {
        this.hasHat = hasHat;
    }

    public Controls getControls() {
        return controls;
    }

    public void setControls(Controls controls) {
        this.controls = controls;
    }
}
