package oh_heaven.game;

import ch.aplu.jcardgame.Card;

import static ch.aplu.jgamegrid.GameGrid.delay;

public class HumanPlayer extends Player{
    public HumanPlayer(int playerId) {
        super(playerId);
    }

    /** Card chosen based on what the player clicked on **/
    @Override
    public Card chooseACard(){
        clicked = null;
        getHand().setTouchEnabled(true);
        while(clicked==null){delay(100);}
        return clicked;
    }

}
