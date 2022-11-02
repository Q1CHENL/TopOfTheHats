package pgdp.game;

public class HamsterAI extends AIControls{

    @Override
    public boolean attack(Game game, Figure figure) {
        return figure.getAttackCooldown() == 0;
    }

}
