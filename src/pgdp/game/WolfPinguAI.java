package pgdp.game;

public class WolfPinguAI extends AIControls {
    @Override
    public boolean attack(Game game, Figure figure) {
        return game.getCollisionBoard().hasCollisions(figure, figure.getLastDirection()) && figure.getAttackCooldown() == 0;
    }
}
