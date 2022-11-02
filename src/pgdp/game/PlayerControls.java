package pgdp.game;
import  pgdp.game.helper.*;

public class PlayerControls implements Controls{

    @Override
    public void move(Game game, Figure figure) {
        if (!movementNotAllowed()) {
            figure.moveTo(game, resultDirection());
        }
    }

    public boolean movementNotAllowed(){
        return !PlayerCtl.isLeft() && !PlayerCtl.isUp() && !PlayerCtl.isRight() && !PlayerCtl.isDown();
    }

    @Override
    public boolean attack(Game game, Figure figure) {
        return PlayerCtl.isAttack();
    }

    private Direction resultDirection() {
        //eliminate opposite directions
        if (PlayerCtl.isUp() && PlayerCtl.isDown()) {
            PlayerCtl.setUp(false);
            PlayerCtl.setDown(false);
        }
        if (PlayerCtl.isLeft() && PlayerCtl.isRight()) {
            PlayerCtl.setLeft(false);
            PlayerCtl.setRight(false);
        }

        if (PlayerCtl.isUp()) {
            if (PlayerCtl.isLeft()) {
                return Direction.UP_LEFT;
            } else if (PlayerCtl.isRight()) {
                return Direction.UP_RIGHT;
            } else {
                return Direction.UP;
            }
        }

        if (PlayerCtl.isDown()) {
            if (PlayerCtl.isLeft()) {
                return Direction.DOWN_LEFT;
            } else if (PlayerCtl.isRight()) {
                return Direction.DOWN_RIGHT;
            } else {
                return Direction.DOWN;
            }
        }

        if (PlayerCtl.isRight()) {
            return Direction.RIGHT;
        }

        if(PlayerCtl.isLeft()){
            return Direction.LEFT;
        }
        return Direction.UP;
    }


}
