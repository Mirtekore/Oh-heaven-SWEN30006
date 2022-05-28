package oh_heaven.game;

import ch.aplu.jcardgame.*;
import java.util.*;

public class Player implements MyListener{
    private Hand hand;
    public final int madeBidBonus = 10;
    private int score;
    private int bid;
    private int trick;
    private String playerType;
    private int playerId;

    //add by Qinglin
    private Cards.Suit curtrumps;
    private Cards.Suit curlead;
    private List<Card> curTrickCards;


    public Player(int playerId, String playerType) {
        this.playerId = playerId;
        this.playerType = playerType;
        trick = 0;
        score = 0;
        bid = 0;
        this.curTrickCards = new ArrayList<>();
    }

    /** Bidding strategy can be changed and report MUST comment on it **/
    public int makeBid(int nbStartCards) {
        bid = nbStartCards / 4 + Oh_Heaven.random.nextInt(2);
        return bid;
    }

    public void forceBid(){
        if (bid == 0) {
            bid = 1;
        } else {
            bid += Oh_Heaven.random.nextBoolean() ? -1 : 1;
        }
    }

    public void updateScore() {
        score += trick;
        if (trick == bid){
            score += madeBidBonus;
        }
    }

    public void winTrick() {
        trick++;
    }

    @Override
    public void update(String mode, String feature, Object arg){
//        System.out.println(mode+"  "+feature);
        if(mode.equals("add")){
            switch (feature){
                case "trickCard":
                    this.curTrickCards.add((Card) arg);
                    break;
                case "trumps":
                    this.curtrumps = (Cards.Suit) arg;
                    break;
                case "lead":
                    this.curlead = (Cards.Suit) arg;
                    break;
            }
        }else if(mode.equals("delete")){
            switch (feature){
                case "trickCard":
                    this.curTrickCards = new ArrayList<>();
                    break;
                case "trumps":
                    this.curtrumps = null;
                    break;
                case "lead":
                    this.curlead = null;
                    break;
            }
        }
    }

    //public abstract void playCard();

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

    public String getPlayerType() {
        return playerType;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void setTrick(int trick) {
        this.trick = trick;
    }
}
