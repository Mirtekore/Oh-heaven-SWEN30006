package oh_heaven.game;

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;


public class Player extends CardGame{
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

    Font bigFont = new Font("Serif", Font.BOLD, 36);

    
    public Player(int playerId, String playerType, Location scoreLocation, Location handLocation) {
        this.scoreLocation = scoreLocation;
        this.playerId = playerId;
        this.playerType = playerType;
        this.handLocation = handLocation;
        trick = 0; // Was originally it's own method
    }

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

    private void displayScore(int player) {
        removeActor(scoreActor);
        String text = "[" + String.valueOf(score) + "]" + String.valueOf(trick) + "/" + String.valueOf(bid);
        scoreActor = new TextActor(text, Color.WHITE, bgColor, bigFont);
        addActor(scoreActor, scoreLocation);
    }

    private void updateScore() {
        score += trick;
        if (trick == bid){
            score += madeBidBonus;
        }
    }


    private void initScore() {
        score = 0;
        String text = "[" + String.valueOf(score) + "]" + String.valueOf(trick) + "/" + String.valueOf(bid);
        scoreActor = new TextActor(text, Color.WHITE, bgColor, bigFont);
        addActor(scoreActor, scoreLocation);
    }


    //public abstract void playCard();


}
