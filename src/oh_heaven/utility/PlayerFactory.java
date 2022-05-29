package oh_heaven.utility;

import oh_heaven.game.*;

public class PlayerFactory {
    private static PlayerFactory instance;

    public static PlayerFactory getInstance(){
        if (instance == null){
            instance = new PlayerFactory();
        }
        return instance;
    }

    /** Creates players based on the NPC type of property file **/
    public Player createNewPlayer(int id, String type){
        if(type.equals("smart")){
            return createSmartPlayer(id);
        }else if(type.equals("legal")){
            return createLegalPlayer(id);
        }else if(type.equals("human")){
            return createHumanPlayer(id);
        }else if(type.equals("random")){
            return createRandomPlayer(id);
        }
        return null;
    }

    private SmartPlayer createSmartPlayer(int id){
        return new SmartPlayer(id);
    }
    private LegalPlayer createLegalPlayer(int id){
        return new LegalPlayer(id);
    }
    private HumanPlayer createHumanPlayer(int id){
        return new HumanPlayer(id);
    }
    private RandomPlayer createRandomPlayer(int id){
        return new RandomPlayer(id);
    }
}
