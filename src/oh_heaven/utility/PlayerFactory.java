package oh_heaven.utility;

import oh_heaven.game.Player;

public class PlayerFactory {
    private static PlayerFactory instance;

    public static PlayerFactory getInstance(){
        if (instance == null){
            instance = new PlayerFactory();
        }
        return instance;
    }

//    public Player createSmartPlayer(){
//
//    }
//    public Player createLegalPlayer(){
//
//    }
//    public Player createHumanPlayer(){
//
//    }
}
