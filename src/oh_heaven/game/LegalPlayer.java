package oh_heaven.game;

import ch.aplu.jcardgame.Card;

import java.util.ArrayList;
import java.util.List;

public class LegalPlayer extends Player{
    public LegalPlayer(int playerId) {
        super(playerId);
    }
    @Override
    public Card chooseACard(){
        List<Card> legalCards = new ArrayList<>();
        if(curlead==null){
            legalCards = getHand().getCardList();
        }else {
            for(Card c : getHand().getCardList()){
                if(c.getSuit() == curlead){
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
