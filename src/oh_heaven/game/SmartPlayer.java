package oh_heaven.game;

import ch.aplu.jcardgame.Card;

import java.util.ArrayList;
import java.util.List;

public class SmartPlayer extends Player{
    public SmartPlayer(int playerId) {
        super(playerId);
    }
    @Override
    public Card chooseACard(){
        boolean tryToWin = getTrick()<getBid();
        Card selected = null;
        if(curlead==null){
            if(tryToWin){
                for(Card c : getHand().getCardList()){
                    if(selected == null || Cards.rankGreater(c,selected)){
                        selected = c;
                    }
                }
            }else {
                for(Card c : getHand().getCardList()){
                    if(selected == null || !Cards.rankGreater(c,selected)){
                        selected = c;
                    }
                }

            }
            return selected;
        }else {
            List<Card> legalCards = new ArrayList<>();
            for (Card c : getHand().getCardList()) {
                if (c.getSuit() == curlead) {
                    legalCards.add(c);
                }
            }
            if (legalCards.size() == 0) {
                legalCards = getHand().getCardList();
            }
            Card max = null;
            Card min = null;
            for(Card c : legalCards){
                if(max ==null||Cards.cardGreater(c,max,curtrumps,curlead)){
                    max = c;
                }
                if(min ==null||Cards.cardGreater(min,c,curtrumps,curlead)){
                    min = c;
                }
            }
            if(tryToWin){
                for(Card opponent : curTrickCards){
                    if(Cards.cardGreater(opponent,max,curtrumps,curlead)){
                        return min;
                    }
                }
                return max;
            }else {
                for(Card opponent : curTrickCards){
                    if(Cards.cardGreater(min,opponent,curtrumps,curlead)){
                        return max;
                    }
                }
                return min;

            }
        }
    }
}
