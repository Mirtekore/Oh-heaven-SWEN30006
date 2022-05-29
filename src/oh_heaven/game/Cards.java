package oh_heaven.game;

import java.util.List;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;
import ch.aplu.jcardgame.Hand;

public class Cards {

	private static final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");

	/** Card class already provided in jcardgame **/
	public enum Suit
	{
		SPADES, HEARTS, DIAMONDS, CLUBS
	}

	public enum Rank
	{
		ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
	}
	  
	final static String trumpImage[] = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};
	  
	/** return random Enum value **/
	public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
		int x = Oh_Heaven.random.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}
	  
	/** return random Card from Hand **/
	public static Card randomCard(Hand hand){
		int x = Oh_Heaven.random.nextInt(hand.getNumberOfCards());
		return hand.get(x);
	}

	/** Ranks the card **/
	public static boolean rankGreater(Card card1, Card card2) {
		return card1.getRankId() < card2.getRankId();
	}
	  
	public static Deck getDeck() {
		return deck;
	}

	/** Gives each player a unique set of cards **/
	public static void dealingOut(List<Player> players, int nbCardsPerPlayer) {
		Hand pack = deck.toHand(false);
		for (int i = 0; i < nbCardsPerPlayer; i++) {
			for (Player p: players) {
				if (pack.isEmpty()) return;
				Card dealt = randomCard(pack);
				dealt.removeFromHand(false);
				p.getHand().insert(dealt, false);
			}
		}
	}

	/** Compare value of a card, factoring the lead suit and trump suit**/
	public static boolean cardGreater(Card c1, Card c2, Suit trumps, Suit lead){
		if(c1.getSuit()==trumps&&c2.getSuit()!=trumps){
			return true;
		}else if(c1.getSuit()==trumps&&c2.getSuit()!=trumps){
			return rankGreater(c1,c2);
		}else if(c1.getSuit()!= trumps&& c2.getSuit()==trumps){
			return false;
		}else{
			if(c1.getSuit() == lead&&c2.getSuit()!=lead){
				return true;
			}else if(c1.getSuit()==lead&&c2.getSuit()==lead){
				return rankGreater(c1,c2);
			}else if(c1.getSuit()!=lead&&c2.getSuit()==lead){
				return false;
			}else {
				return rankGreater(c1,c2);
			}
		}
	}
}