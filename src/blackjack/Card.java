package blackjack;

enum Suit{CLUBS,HEARTS,DIAMONDS,SPADES}
enum Rank{ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING}

public class Card {
	private final Rank rank;
	private final Suit suit;

	/**
	 * Constructs a card with a suit and rank
	 * 
	 * @param rank
	 * @param suit
	 */
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	/**
	 * get rank of card
	 * @return rank 
	 */
	public Rank getRank() {
		return rank;
	}
	
	/**
	 * get suit of card
	 * @return rank 
	 */
	public Suit getSuit() {
		return suit;
	}
	
	/**
	 * Get the card's full name
	 * 
	 * @return full representation
	 */
	public String fullToString() {
		return rank + " OF " + suit;
	}
	
	/**
	 * Gets the card's abbreviated name
	 * 
	 * @return abbreviated representation
	 */
	public String toString() {
		return abbreviatedRank() + "-" + abbreviatedSuit();
	}
	
	/**
	 * abbreviates the rank of input card
	 * 
	 * @return String abbreviation
	 */
	public String abbreviatedRank() {
		switch (rank){
			case ACE: return "A";
			case TWO: return "2";
			case THREE: return "3";
			case FOUR: return "4";
			case FIVE: return "5";
			case SIX: return "6";
			case SEVEN: return "7";
			case EIGHT: return "8";
			case NINE: return "9";
			case TEN: return "10";
			case JACK: return "J";
			case QUEEN: return "Q";
			case KING: return "K";
			default: return "?";
		}
	}
	
	/**
	 * abbreviates the suit of the card
	 * 
	 * @return String abbreviation
	 */
	public String abbreviatedSuit() {
		switch (suit){
			case CLUBS: return "C";
			case HEARTS: return "H";
			case DIAMONDS: return "D";
			case SPADES: return "S";
			default: return "?";
		}
	}
	
	/**
	 * gets the value of input card
	 * 
	 * @return int value
	 */
	public int getValue() {
		switch (rank){
			case ACE: return 11;
			case TWO: return 2;
			case THREE: return 3;
			case FOUR: return 4;
			case FIVE: return 5;
			case SIX: return 6;
			case SEVEN: return 7;
			case EIGHT: return 8;
			case NINE: return 9;
			default: return 10;
		}
		
	}
}
