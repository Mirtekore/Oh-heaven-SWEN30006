package oh_heaven.game;

import ch.aplu.jcardgame.Card;

import java.util.ArrayList;
import java.util.List;

public class LegalPlayer extends Player{
    public LegalPlayer(int playerId) {
        super(playerId);
    }

    /** Chooses cards that will follow the lead suit when possible
     *  chooses random cards when there is no lead suit / does not have cards of the lead suit**/
    @Override
    public Card chooseACard() {
        List<Card> legalCards = new ArrayList<>();
        if(curLead==null) {
            legalCards = getHand().getCardList();
        } else {
            for(Card c : getHand().getCardList()){
                if(c.getSuit() == curLead){
                    legalCards.add(c);
                }
            }
            if(legalCards.size()==0){
                legalCards = getHand().getCardList();
            }
        }
        int x = Oh_Heaven.random.nextInt(legalCards.size());
        return legalCards.get(x);

    }
}
