package oh_heaven.game;

import ch.aplu.jcardgame.Card;

public class RandomPlayer extends Player{
    public RandomPlayer(int playerId) {
        super(playerId);
    }
    @Override
    public Card chooseACard(){
        int x = Oh_Heaven.random.nextInt(getHand().getNumberOfCards());
        return getHand().get(x);


    }

}
