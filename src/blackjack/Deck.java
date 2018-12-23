package blackjack;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	
	private ArrayList<Card> cards;
	private int numDecks;
	private int pointer;
	
	public Deck() {
		this(1);
	}
	
	public Deck(int numDecks) {
		pointer = 0;
		numDecks = 1;

		cards = new ArrayList<Card>();
		for (int i=0; i<numDecks; i++) {
			for (Suit s: Suit.values()) {
				for (Rank r : Rank.values()) {
					cards.add(new Card(r, s));
				}
			}
		}
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
		pointer = 0;
	}
	
	public Card deal() {
		// shuffle
		if (pointer > cards.size()/2) shuffle();
		return cards.get(pointer++);
	}
}
