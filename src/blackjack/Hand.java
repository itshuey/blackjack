package blackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	
	private List<Card> myCards;
	private int runningTotal;
	
	/**
	 * Initialize Hand
	 */
	public Hand() {
		myCards = new ArrayList<Card>();
		runningTotal = 0;
	}
	
	///////////////////
	// Core Operations
	///////////////////
	
	/**
	 * Adds card to our hand
	 * 
	 * @param Card c
	 */
	public void addToHand(Card c) {
		myCards.add(c);
		runningTotal += c.getValue();
		
		// If we're over 21, check ace condition!
		if (runningTotal>21)
			runningTotal = checkScore();
	}

	
	/**
	 * Calculating the score to handle
	 * 		(potentially many) aces in our hand
	 * 
	 * @return total hard score
	 */
	public int checkScore() {
		int checkedScore = 0;
		
		for (Card c : myCards) 
			checkedScore += c.getValue();
		
		// iterate through the deck
		for (Card c : myCards) {
			if (c.getRank().equals(Rank.ACE) && checkedScore > 21) 
				checkedScore -= 10;
		}	
		return checkedScore;
	}
	
	
	/**
	 * Clears our cards and resets total value
	 */
	public void resetHand() {
		myCards.clear();
		runningTotal = 0;
	}
	
	
	/**
	 * Represent the hand
	 * @return Returns cards separated by a space
	 */
	public String toString() {
		String output = "";
		for (int i = 0; i<myCards.size(); i++) {
			output = output + myCards.get(i).toString() + " ";
		}
		return output;
	}
	
	/////////////
	// Checkers
	/////////////
	
	/**
	 * Check if we got a blackjack!
	 * @return boolean
	 */
	public boolean isBlackjack() {
		// no need to iterate through cards!
		return myCards.size() == 2 && runningTotal == 21;
	}
	
	/**
	 * Checks if we're bust
	 * @return boolean
	 */
	public boolean isBust() {
		return runningTotal > 21; 
	}
	
	/////////////////////
	// Getter & Setters
	/////////////////////
	
	/**
	 * Get the cards
	 * @return List<Card> 
	 */
	public List<Card> getCards(){
		return myCards;
	}
	
	/**
	 * Get the current total score
	 * @return running total
	 */
	public int getValue() {
		return runningTotal;
	}
	
	/**
	 * Get the first card, if one exists
	 * @return Card
	 */
	public Card getFirstCard(){
		if (myCards.size() == 0) return null;
		return myCards.get(0);
	}
	
	/**
	 * Removes the second card, if one exists
	 * @return removed Card
	 */
	public Card removeSecondCard(){
		if (myCards.size() != 2) return null;
		return myCards.remove(1);
	}
	
	/**
	 * Checks if you can split,
	 * 		or if the first two cards have the same rank
	 * @return boolean
	 */
	public boolean canSplit(){
		if (myCards.size() < 2) return false;
		return myCards.get(0).getRank().equals(myCards.get(1).getRank());
	}
}
