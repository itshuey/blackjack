package blackjack;

enum Suit{CLUBS,HEARTS,DIAMONDS,SPADES}
enum Rank{ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING}

public class Card {
	private final Rank rank;
	private final Suit suit;

	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public String toString() {
		return rank + " OF " + suit;
	}
}
