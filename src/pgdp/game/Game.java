package pgdp.game;

import java.util.ArrayList;
import java.util.List;

//enthält die Daten des Spiels
public class Game {

    private CollisionBoard collisionBoard;
    private List<Entity> entities;
    private List<Entity> entityAdd;

    public Game(CollisionBoard collisionBoard){
        setCollisionBoard(collisionBoard);
        setEntities(new ArrayList<>());
        setEntityAdd(new ArrayList<>());
    }

    public void runTick() {
        List<Entity> entitiesToRemove = new ArrayList<>();

        for (Entity entity : entities) {
            if (!entity.visit(this)) {
                entitiesToRemove.add(entity);
                collisionBoard.remove(entity);
            }
        }

        entities.removeAll(entitiesToRemove);

        List<Entity> entitiesToRemoveFromEntityAdd = new ArrayList<>();

        for (Entity entity : entityAdd) {
            if (!collisionBoard.set(entity)) {
                entitiesToRemoveFromEntityAdd.add(entity);
            }
        }

        entityAdd.removeAll(entitiesToRemoveFromEntityAdd);
        entities.addAll(entityAdd);

        entityAdd.clear();
    }


    public CollisionBoard getCollisionBoard() {
        return collisionBoard;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Entity> getEntityAdd() {
        return entityAdd;
    }

    public void setEntityAdd(List<Entity> entityAdd) {
        this.entityAdd = entityAdd;
    }

    public void setCollisionBoard(CollisionBoard collisionBoard) {
        this.collisionBoard = collisionBoard;
    }

}
