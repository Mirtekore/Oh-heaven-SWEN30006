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

        /** Smart player is the leader **/
        if(curLead==null){
            /** Try to win the trick to get the madeBidBonus **/
            if(tryToWin){
                for(Card c : getHand().getCardList()){
                    if(selected == null || Cards.rankGreater(c,selected)){
                        selected = c;
                    }
                }

            /** Player is at madeBidBonus or has surpassed the threshold,
             *  the player plays the lowest value cards to preserve future chances of winning**/
            }else {
                for(Card c : getHand().getCardList()){
                    if(selected == null || !Cards.rankGreater(c,selected)){
                        selected = c;
                    }
                }
            }
            return selected;

        /** Same logic applies as above, but player must try to follow the lead suit **/
        }else {
            List<Card> legalCards = new ArrayList<>();
            for (Card c : getHand().getCardList()) {
                if (c.getSuit() == curLead) {
                    legalCards.add(c);
                }
            }
            if (legalCards.size() == 0) {
                legalCards = getHand().getCardList();
            }
            Card max = null;
            Card min = null;
            for(Card c : legalCards){
                if(max ==null||Cards.cardGreater(c,max,curTrumps,curLead)){
                    max = c;
                }
                if(min ==null||Cards.cardGreater(min,c,curTrumps,curLead)){
                    min = c;
                }
            }
            if(tryToWin){
                for(Card opponent : curTrickCards){
                    if(Cards.cardGreater(opponent,max,curTrumps,curLead)){
                        return min;
                    }
                }
                return max;
            }else {
                for(Card opponent : curTrickCards){
                    if(Cards.cardGreater(min,opponent,curTrumps,curLead)){
                        return max;
                    }
                }
                return min;
            }
        }
    }
}
