package oh_heaven.game;

// Oh_Heaven.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import oh_heaven.utility.PropertiesLoader;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class Oh_Heaven extends CardGame implements MyPublisher{

    /** Attributes related to how the game works **/
	private Properties properties;
	private int nbStartCards;
	private int nbRounds;
	private boolean enforceRules;
	static public int seed;
	static public final Random random = new Random(seed);
	private List<Player> players;
    private Card selected;
	private final int nbPlayers = 4;
	public final int madeBidBonus = 10;

	/** Attributes related to displaying graphical interfaces **/
	private final String version = "1.0";
	private final int handWidth = 400;
	private final int trickWidth = 40;
		private final Location[] handLocations = {
			new Location(350, 625),
			new Location(75, 350),
			new Location(350, 75),
			new Location(625, 350)
	};
	private final Location[] scoreLocations = {
			new Location(575, 675),
			new Location(25, 575),
			new Location(575, 25),
			new Location(575, 575)
	};
	private Actor[] scoreActors = {null, null, null, null };
	private final Location trickLocation = new Location(350, 350);
	private final Location textLocation = new Location(350, 450);
	private final int thinkingTime = 2000;
	private Location hideLocation = new Location(-500, - 500);
	private Location trumpsActorLocation = new Location(50, 50);
	public void setStatus(String string) { setStatusText(string); }
	Font bigFont = new Font("Serif", Font.BOLD, 36);

	private List<Player> initGameProperties(Properties properties){
		nbRounds =
				(properties.getProperty("rounds") == null)
						? 2
						: Integer.parseInt(properties.getProperty("rounds"));

		nbStartCards =
				(properties.getProperty("nbStartCards") == null)
						? 13
						: Integer.parseInt(properties.getProperty("nbStartCards"));

		seed =
				(properties.getProperty("seed") == null)
						? 30006
						: Integer.parseInt(properties.getProperty("seed"));

		enforceRules =
				properties.getProperty("enforceRules") != null && Boolean.parseBoolean(properties.getProperty("enforceRules"));

		return PropertiesLoader.loadPlayers(properties, nbPlayers);
	}

	private void initRound() {
		int counter = 0;
		for (Player p : players) {
			p.setHand(new Hand(Cards.getDeck()));
		}
		Cards.dealingOut(players, nbStartCards);
		for (Player p : players) {
			p.getHand().sort(Hand.SortType.SUITPRIORITY, true);
			p.resetClickListener();

			RowLayout layout = new RowLayout(handLocations[counter], handWidth);
			layout.setRotationAngle(90 * counter);
			p.getHand().setView(this, layout);
			p.getHand().setTargetArea(new TargetArea(trickLocation));
			p.getHand().draw();
			counter++;
		}
	}

	private void playRound() {
		final Cards.Suit trumps = Cards.randomEnum(Cards.Suit.class);
		final Actor trumpsActor = new Actor("sprites/"+(Cards.trumpImage[trumps.ordinal()]));
		addActor(trumpsActor, trumpsActorLocation);
		notifyPlayers("add","trumps",trumps);
		Hand trick;
		int winner;
		Card winningCard;
		Cards.Suit lead;
		int nextPlayer = random.nextInt(nbPlayers); // randomly select player to lead for this round

		/** Bidding phase **/
		int total = 0;
		for (int i = nextPlayer; i < nextPlayer + nbPlayers; i++) {
			total += players.get(i % nbPlayers).makeBid(nbStartCards);
		}
		if (total == nbStartCards) {  // Force last bid so not every bid possible
			players.get((nextPlayer + nbPlayers) % nbPlayers).forceBid();
		}
		displayScores();

		/** Lead phase **/
		for (int i = 0; i < nbStartCards; i++) {
			trick = new Hand(Cards.getDeck());
			selected = null;
			if (players.get(nextPlayer).getClass()==HumanPlayer.class) {  // Select lead depending on player type
				setStatus("Player " + nextPlayer + " double-click on card to lead.");
				selected = players.get(nextPlayer).chooseACard();
			} else {
				setStatusText("Player " + nextPlayer + " thinking...");
				delay(thinkingTime);
				selected = players.get(nextPlayer).chooseACard();
			}
			trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
			trick.draw();
			selected.setVerso(false);
			lead = (Cards.Suit) selected.getSuit();

			/** Update players of the card that the leader played **/
			notifyPlayers("add","trickCard",selected);
			notifyPlayers("add","lead",lead);
			selected.transfer(trick, true); // transfer to trick (includes graphic effect)
			winner = nextPlayer;
			winningCard = selected;


			/** Other players playing after lead**/
			for (int j = 1; j < nbPlayers; j++) {
				if (++nextPlayer >= nbPlayers) nextPlayer = 0;
				selected = null;

				/** Human player is taking their turn **/
				if (players.get(nextPlayer).getClass()==HumanPlayer.class) {
					setStatus("Player " + nextPlayer + " double-click on card to lead.");
					selected = players.get(nextPlayer).chooseACard();

				/** NPC player using their determined playing strategy **/
				} else {
					setStatusText("Player " + nextPlayer + " thinking...");
					delay(thinkingTime);
					selected = players.get(nextPlayer).chooseACard();
				}

				/** Graphics rendering **/
				trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
				trick.draw();
				notifyPlayers("add","trickCard",selected);
				selected.setVerso(false);

				/** Checking for rule violations **/
				if (selected.getSuit() != lead && players.get(nextPlayer).getHand().getNumberOfCardsWithSuit(lead) > 0) {
					String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
					System.out.println(violation);
					if (enforceRules)
						try {
							throw(new BrokeRuleException(violation));
						} catch (BrokeRuleException e) {
							e.printStackTrace();
							System.out.println("A cheating player spoiled the game!");
							System.exit(0);
						}
				}


				selected.transfer(trick, true);
				System.out.println("winning: " + winningCard);
				System.out.println(" played: " + selected);

				/** Compare the card values played **/
				if (
						(selected.getSuit() == winningCard.getSuit() && Cards.rankGreater(selected, winningCard)) ||
								(selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
					System.out.println("NEW WINNER");
					winner = nextPlayer;
					winningCard = selected;
				}
			}

			/** Graphics rendering **/
			delay(600);
			trick.setView(this, new RowLayout(hideLocation, 0));
			trick.draw();
			nextPlayer = winner;
			setStatusText("Player " + nextPlayer + " wins trick.");
			players.get(nextPlayer).setTrick(players.get(nextPlayer).getTrick() + 1);
			displayScores();

			/** Update players that turn of a round is finished **/
			notifyPlayers("delete","trickCard",null);
			notifyPlayers("delete","lead",null);
		}
		notifyPlayers("delete","trumps",null);
		removeActor(trumpsActor);
	}

	/**	observer pattern**/
	@Override
	public void notifyPlayers(String mode, String feature, Object arg){
		for(Player p:players){
			p.update(mode, feature, arg);
		}
	}

	/** Graphics rendering of score **/
	private void displayScores() {
		for (int i = 0; i < nbPlayers; i++) {
			if (scoreActors[i] != null) {
				removeActor(scoreActors[i]);
			}
			String text = "[" + String.valueOf(players.get(i).getScore()) + "]" + String.valueOf(players.get(i).getTrick()) + "/" + String.valueOf(players.get(i).getBid());
			scoreActors[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
			addActor(scoreActors[i], scoreLocations[i]);
		}
	}

	/** Calculate the score at the end of each round **/
	private void calculateScore(){
		for (Player p : players) {
			p.setScore(p.getScore() + p.getTrick());
			if (p.getTrick() == p.getBid()) {
				p.setScore(p.getScore() + madeBidBonus);
			}
		}
	}

	public Oh_Heaven(Properties properties)
	{
		super(700, 700, 30);
		setTitle("Oh_Heaven (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
		setStatusText("Initializing...");
		this.properties = properties;
		players = initGameProperties(properties);
		displayScores();

		for (int i=0; i <nbRounds; i++) {
			for (Player p: players) {
				p.setTrick(0);
			}
			initRound();
			playRound();
			calculateScore();
		}

		/** Find the highest scoring winner **/
		int maxScore = 0;
		displayScores();
		for (Player p: players) {
			if (p.getScore() > maxScore){
				maxScore = p.getScore();
			}
		}
		Set <Integer> winners = new HashSet<Integer>();
		for (Player p: players) {
			if (p.getScore() == maxScore) winners.add(p.getPlayerId());
		}
		String winText;
		if (winners.size() == 1) {
			winText = "Game over. Winner is player: " +
					winners.iterator().next();
		}
		else {
			winText = "Game Over. Drawn winners are players: " +
					String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toSet()));
		}
		addActor(new Actor("sprites/gameover.gif"), textLocation);
		setStatusText(winText);
		refresh();
	}

	public static void main(String[] args)
	{
		final Properties properties;
		if (args == null || args.length == 0) {
			properties = PropertiesLoader.loadPropertiesFile(null);
		} else {
			properties = PropertiesLoader.loadPropertiesFile(args[0]);
		}
		new Oh_Heaven(properties);
	}
}