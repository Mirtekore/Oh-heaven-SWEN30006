package oh_heaven.game;

import java.util.ArrayList;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;
import ch.aplu.jcardgame.Hand;

public class Cards {
	
		// Note: Card class already exists
		// Hence this class has card related functions and variables
	  public enum Suit
	  {
	    SPADES, HEARTS, DIAMONDS, CLUBS
	  }

	  public enum Rank
	  {
	    // Reverse order of rank importance (see rankGreater() below)
		// Order of cards is tied to card images
		ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
	  }
	  
	  final static String trumpImage[] = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};
	  
	  // return random Enum value
	  public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
	      int x = Oh_Heaven.random.nextInt(clazz.getEnumConstants().length);
	      return clazz.getEnumConstants()[x];
	  }
	  
	  // return random Card from Hand
	  public static Card randomCard(Hand hand){
	      int x = Oh_Heaven.random.nextInt(hand.getNumberOfCards());
	      return hand.get(x);
	  }
	 
	  // return random Card from ArrayList
	  public static Card randomCard(ArrayList<Card> list){
	      int x = Oh_Heaven.random.nextInt(list.size());
	      return list.get(x);
	  }
	  
	  public static boolean rankGreater(Card card1, Card card2) {
		  return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
	  }
	  
	  private static final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
	  
	  public static Deck getDeck() {
		  return deck;
	  }
	  
	  public static void dealingOut(Hand[] hands, int nbPlayers, int nbCardsPerPlayer) {
		  Hand pack = deck.toHand(false);
		  // pack.setView(Oh_Heaven.this, new RowLayout(hideLocation, 0));
		  for (int i = 0; i < nbCardsPerPlayer; i++) {
			  for (int j=0; j < nbPlayers; j++) {
				  if (pack.isEmpty()) return;
				  Card dealt = randomCard(pack);
			      // System.out.println("Cards = " + dealt);
			      dealt.removeFromHand(false);
			      hands[j].insert(dealt, false);
				  // dealt.transfer(hands[j], true);
			  }
		  }
	  }

	public static void dealingOut2(Hand hand, int nbCardsPerPlayer) {
		Hand pack = deck.toHand(false);
		// pack.setView(Oh_Heaven.this, new RowLayout(hideLocation, 0));
		for (int i = 0; i < nbCardsPerPlayer; i++) {

				if (pack.isEmpty()) return;
				Card dealt = randomCard(pack);
				// System.out.println("Cards = " + dealt);
				dealt.removeFromHand(false);
				hand.insert(dealt, false);
				// dealt.transfer(hands[j], true);

		}
	}
}