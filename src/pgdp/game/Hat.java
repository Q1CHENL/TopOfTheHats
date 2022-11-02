package pgdp.game;

public class Hat extends Entity{

    //true: being worn and moving with a figure
    //false: not being worn
    private boolean dontRemove;

    public Hat(Position position) {
        super(new LocatedBoundingBox(position, new BoundingBox(2, 1)));
        dontRemove = true;
    }

    @Override
    public boolean visit(Game game) {
        return dontRemove;
    }

    public void setDontRemove(boolean dontRemove) {
        this.dontRemove = dontRemove;
    }

    @Override
    public Image getImage() {
        return Image.HAT;
    }

    public boolean isDontRemove() {
        return dontRemove;
    }
}
