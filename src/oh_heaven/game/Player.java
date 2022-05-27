package oh_heaven.game;

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;

public class Player implements MyListener{
    private Hand hand;
    public final int madeBidBonus = 10;
    private final int nbStartCards = 13;
    private int score;
    private int bid;
    private int trick;
    private final Location scoreLocation;
    private final Location handLocation;
    private Actor scoreActor;
    private String playerType;
    private int playerId;

    //add by Qinglin
    private Cards.Suit curtrumps;
    private Cards.Suit curlead;
    private List<Card> curTrickCards;


    Font bigFont = new Font("Serif", Font.BOLD, 36);


    public void setTrick(int trick) {
        this.trick = trick;
    }

    public Player(int playerId, String playerType, Location scoreLocation, Location handLocation) {
        this.scoreLocation = scoreLocation;
        this.playerId = playerId;
        this.playerType = playerType;
        this.handLocation = handLocation;
        trick = 0;
        score = 0;
        bid = 0;
        this.curTrickCards = new ArrayList<>();
    }

    /** Bidding strategy can be changed and report MUST comment on it **/
    public int makeBid() {
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

    public int getTrick() {
        return trick;
    }

    public int getBid() {
        return bid;
    }

    public Location getScoreLocation() {
        return scoreLocation;
    }

    public String getPlayerType() {
        return playerType;
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

    public int getScore() {
        return score;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
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


}
