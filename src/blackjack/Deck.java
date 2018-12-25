package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	
	private List<Card> cards;
	private int numDecks;
	private int pointer;
	
	/**
	 * Default constructor assumes one deck
	 */
	public Deck() {
		this(1);
	}
	
	/**
	 * Creates our list of cards from numDecks decks
	 * 
	 * @param numDecks
	 */
	public Deck(int numDecks) {
		pointer = 0;
		this.numDecks = numDecks;

		cards = new ArrayList<Card>();
		for (int i=0; i<numDecks; i++) {
			for (Suit s: Suit.values()) {
				for (Rank r : Rank.values()) {
					cards.add(new Card(r, s));
				}
			}
		}
	}
	
	/**
	 * Uses default implementation of shuffle
	 */
	public void shuffle() {
		Collections.shuffle(cards);
		// reset pointer
		pointer = 0;
	}
	
	public Card deal() {
		// if we're getting too far, shuffle
		if (pointer > cards.size()/2) shuffle();
		return cards.get(pointer++);
	}
}
