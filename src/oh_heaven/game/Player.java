package oh_heaven.game;

import ch.aplu.jcardgame.*;
import java.util.*;

public abstract class Player implements MyListener{
    private Hand hand;
    private int score;
    private int bid;
    private int trick;
    private int playerId;

    protected Cards.Suit curTrumps;
    protected Cards.Suit curLead;
    protected List<Card> curTrickCards;
    protected Card clicked;

    public Player(int playerId) {
        trick = 0;
        score = 0;
        bid = 0;
        this.playerId = playerId;
        this.curTrickCards = new ArrayList<>();
    }

    public int makeBid(int nbStartCards) {
        bid = nbStartCards / 4 + Oh_Heaven.random.nextInt(2);
        return bid;
    }

    /** Player must change their bid to follow the bidding rules **/
    public void forceBid(){
        if (bid == 0) {
            bid = 1;
        } else {
            bid += Oh_Heaven.random.nextBoolean() ? -1 : 1;
        }
    }

    /** Track information such as the cards played by other players
     *  smart and legal players will utilize this information **/
    @Override
    public void update(String mode, String feature, Object arg){
        if(mode.equals("add")){
            switch (feature){
                case "trickCard":
                    this.curTrickCards.add((Card) arg);
                    break;
                case "trumps":
                    this.curTrumps = (Cards.Suit) arg;
                    break;
                case "lead":
                    this.curLead = (Cards.Suit) arg;
                    break;
            }
        }else if(mode.equals("delete")){
            switch (feature){
                case "trickCard":
                    this.curTrickCards = new ArrayList<>();
                    break;
                case "trumps":
                    this.curTrumps = null;
                    break;
                case "lead":
                    this.curLead = null;
                    break;
            }
        }
    }

    /** Algorithm for choosing a card depending on player **/
    public abstract Card chooseACard();

    /** GUI interfaces **/
    public void resetClickListener(){
        CardListener cardListener = new CardAdapter()  // Human Player plays card
        {
            public void leftDoubleClicked(Card card) {
                clicked = card;
                hand.setTouchEnabled(false);
            }
        };
        hand.addCardListener(cardListener);

    }

    /** Getters and setters for Player class **/
    public int getScore() {
        return score;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Hand getHand() {
        return hand;
    }

    public int getTrick() {
        return trick;
    }

    public int getBid() {
        return bid;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void setTrick(int trick) {
        this.trick = trick;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
