package pgdp.game;


//speichert die Bewegung der Figuren

//Das Steuern der Figuren wird von Implementierungen des Controls Interfaces übernommen.

//methods in Controls: entscheidet , wie gehandelt wird
public interface Controls {

    void move(Game game, Figure figure);
    boolean attack(Game game, Figure figure);

}
