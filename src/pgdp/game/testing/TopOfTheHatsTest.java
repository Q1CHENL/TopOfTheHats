package pgdp.game.testing;

import org.junit.jupiter.api.Test;
import pgdp.game.*;
import static org.junit.jupiter.api.Assertions.*;

public class TopOfTheHatsTest {


    private final WolfPingu wolfPingu1 = new WolfPingu(new Position(5, 5));
    private final WolfPinguAI wolfPinguAI1 = new WolfPinguAI();

    private final WolfPingu wolfPingu2 = new WolfPingu(new Position(1, 5));
    private final WolfPinguAI wolfPinguAI2 = new WolfPinguAI();

    private final Hamster hamster1 = new Hamster(new Position(8, 5));
    private final HamsterAI hamsterAI1 = new HamsterAI();

    private final Hamster hamster2 = new Hamster(new Position(8, 8));
    private final HamsterAI hamsterAI2 = new HamsterAI();

    private Hat hat1;
    private Hat hat2;

    void setup(){
        wolfPingu1.getHitBox().get().setPosition(new Position(5, 5));
        wolfPingu2.getHitBox().get().setPosition(new Position(1, 5));
        hamster1.getHitBox().get().setPosition(new Position(8, 5));
        hamster2.getHitBox().get().setPosition(new Position(8, 8));
        hat1 = new Hat(new Position(5, 8));
        hat2 = new Hat(new Position(1, 9));

        wolfPingu1.setControls(wolfPinguAI1);
        wolfPingu2.setControls(wolfPinguAI2);
        hamster1.setControls(hamsterAI1);
        hamster2.setControls(hamsterAI2);
    }

    //zur Bewertung
    @Test
    void testWolfPinguAIAttack(){
        setup();
        Game game = new Game(new CollisionBoard(80, 45, 5, 5));
        wolfPingu1.setHasHat(false);
        wolfPingu1.setAttackCooldown(0);
        wolfPingu1.setMoveCooldown(0);
        wolfPingu1.setLastDirection(Direction.RIGHT);
        hamster1.setHasHat(false);
        hamster2.setHasHat(true);

        game.getEntityAdd().add(hat1);
        game.getEntityAdd().add(hat2);
        game.getEntityAdd().add(hamster1);
        game.getEntityAdd().add(hamster2);
        game.getEntityAdd().add(wolfPingu1);
        game.getEntityAdd().add(wolfPingu2);

        game.runTick();

        //test case 1: check attack hamster1(without hat) on the right side: a direction parallel to axis
        assertTrue(wolfPinguAI1.attack(game, wolfPingu1));

        wolfPingu1.setLastDirection(Direction.DOWN_RIGHT);
        //test case 2: check attack hamster2(with hat) in the right bottom direction
        assertTrue(wolfPinguAI1.attack(game, wolfPingu1));

        wolfPingu1.setLastDirection(Direction.LEFT);
        //test case 3: check won't attack because no collision with lastDirection
        assertFalse(wolfPinguAI1.attack(game, wolfPingu1));
    }

    //zur Bewertung
    @Test
    void testWolfPinguAttack(){
        setup();
        Game game = new Game(new CollisionBoard(80, 45, 5, 5));
        wolfPingu1.setHasHat(false);
        wolfPingu1.setAttackCooldown(0);
        wolfPingu1.setLastDirection(Direction.RIGHT);
        hamster1.setHasHat(true);
        hamster2.setHasHat(false);
        wolfPingu2.setHasHat(true);

        game.getEntityAdd().add(hat1);
        game.getEntityAdd().add(hat2);
        game.getEntityAdd().add(hamster1);
        game.getEntityAdd().add(hamster2);
        game.getEntityAdd().add(wolfPingu1);
        game.getEntityAdd().add(wolfPingu2);

        game.runTick();

        //test case 1: attack hamster1
        wolfPingu1.attack(game);
        //get hat from hamster1 on the right side
        assertTrue(wolfPingu1.isHasHat());
        //hamster1 loses hat
        assertFalse(hamster1.isHasHat());
        //hamster1 get disabledCooldown of 60
        assertEquals(60, hamster1.getDisabledCooldown());

        wolfPingu1.setHasHat(false);
        wolfPingu1.setLastDirection(Direction.DOWN_RIGHT);

        //test case2: attack hamster2 in direction bottom right
        wolfPingu1.attack(game);
        //gets no hat because hamster2 has no hat
        assertFalse(wolfPingu1.isHasHat());
        //hamster2 still get disabledCoolDown of 60
        assertEquals(60, hamster2.getDisabledCooldown());

        wolfPingu1.setLastDirection(Direction.LEFT);

        //test case 3: attacking a wolfPingu: wolfPingu2
        wolfPingu1.moveTo(game, Direction.LEFT);
        wolfPingu1.attack(game);
        assertTrue(wolfPingu1.isHasHat());
        assertFalse(wolfPingu2.isHasHat());

    }

    //zur Bewertung
    @Test void testMoveTo(){
        setup();
        Game game = new Game(new CollisionBoard(80, 45, 5, 5));
        wolfPingu1.setHasHat(false);
        wolfPingu1.setAttackCooldown(0);
        wolfPingu1.setMoveCooldown(0);
        wolfPingu1.setLastDirection(Direction.RIGHT);

        game.getEntityAdd().add(hat1);
        game.getEntityAdd().add(hat2);
        game.getEntityAdd().add(hamster1);
        game.getEntityAdd().add(hamster2);
        game.getEntityAdd().add(wolfPingu1);
        game.getEntityAdd().add(wolfPingu2);

        game.runTick();

        //test case 1: wolfPingu1 moves down to get hat1: a vertical movement
        wolfPingu1.moveTo(game, Direction.DOWN);
        //y-coordinate updated
        assertEquals(6, wolfPingu1.getHitBox().get().getPosition().getY());
        assertFalse(hat1.isDontRemove());
        //gets hat
        assertTrue(wolfPingu1.isHasHat());
        //lastDirection updated
        assertEquals(Direction.DOWN, wolfPingu1.getLastDirection());


        //test case 2: wolfPingu moves bottom left to get hat2: a diagonal movement
        wolfPingu1.setHasHat(false);
        wolfPingu1.moveTo(game, Direction.DOWN_LEFT);
        wolfPingu1.moveTo(game, Direction.DOWN_LEFT);
        wolfPingu1.moveTo(game, Direction.DOWN_LEFT);

        //position updated to (2, 9)
        assertEquals(2, wolfPingu1.getHitBox().get().getPosition().getX());
        assertEquals(9, wolfPingu1.getHitBox().get().getPosition().getY());
        assertFalse(hat2.isDontRemove());
        //gets hat2
        assertTrue(wolfPingu1.isHasHat());
        //lastDirection updated to Left
        assertEquals(Direction.DOWN_LEFT, wolfPingu1.getLastDirection());

        //test case 3: wolfPingu moves left: a horizontal movement
        wolfPingu1.moveTo(game, Direction.LEFT);
        assertEquals(1, wolfPingu1.getHitBox().get().getPosition().getX());
        assertEquals(9, wolfPingu1.getHitBox().get().getPosition().getY());

    }


    //nicht zur Bewertung!
    @Test
    void testHasCollision(){
        setup();
        Game game = new Game(new CollisionBoard(80, 45, 5, 5));
        wolfPingu1.setHasHat(false);
        wolfPingu1.setAttackCooldown(0);
        wolfPingu1.setLastDirection(Direction.RIGHT);
        hamster1.setHasHat(false);
        hamster2.setHasHat(true);

        game.getEntityAdd().add(hat1);

        game.runTick();

        System.out.println(game.getCollisionBoard().hasCollisions(wolfPingu1, Direction.DOWN));
    }

}
