package blackjack;

import java.util.ArrayList;

public class Player {

	private static final int defaultNameLength = 8;		// for text justification purposes
	private String name;
	private boolean playing;	// currently participating in the round
	private boolean bust;		// busted out or not
	private Hand hand;	
	
	/**
	 * Initializes player with name
	 * 
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
		hand = new Hand();
		playing = true;
	}
	
	///////////////////////////// Methods /////////////////////////////////

	// All Player Methods:
	// Section 1: Essential Player Methods
	// Section 2: Getters & Setters
	
	/////////////////////
	// Player Methods
	/////////////////////
	
	/**
	 * hit() adds another card to the hand
	 * @param card
	 */
	public void hit(Card card) {
		// addToHand handles busting
		hand.addToHand(card);
		bust = getHand().isBust();
	}
	
	/**
	 * stay() ends the turn
	 */
	public void stay() {
		playing = false;
	}
	
	/**
	 * toString prints the current player
	 * 		and his hand
	 */
	public String toString() {
		// justify each print statement
		int shift = Math.max(0, defaultNameLength-name.length());
		String gap = "";
		// could change to StringBuilder for efficiency
		for (int i=0; i<shift; i++) {
			gap = gap + " ";
		}
		return name + "'s Hand: " + gap + hand.toString();
	}
	
	/**
	 * Basic reset function
	 */
	public void reset() {
		 bust = false;
		 playing = true;
	}
	
	/////////////////////
	// Getter & Setters
	/////////////////////
	
	public String getName() {
		return name;
	}
	
	public boolean isPlaying(){
		if (hand.isBust()) playing = false;
		return playing;
	}
	
	public void setPlaying(boolean b) {
		playing = b;
	}
	
	public boolean isBust() {
		return bust;
	}
	
	public void setBust(boolean b) {
		bust = b;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public int getHandValue() {
		return hand.getValue();
	}
}
