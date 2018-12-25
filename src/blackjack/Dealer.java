package blackjack;

public class Dealer extends Player{
	
	private boolean roundOver;	// Controls card hiding
	
	/**
	 * Create player named "Dealer"
	 */
	public Dealer() {
		super("Dealer");
		roundOver = false;
	}
	
	/**
	 * Uncovers the cards in the toString method
	 */
	public void uncoverCard() {
		roundOver = true;
	}
	
	/**
	 * Reset roundOver and hand
	 */
	public void reset() {
		super.reset();
		roundOver = false;
		getHand().resetHand();
	}
	
	/**
	 * Represent the dealer
	 * 
	 * Print the cards depending on if the round is over
	 */
	public String toString() {
		// if the round is over or we have one card, show everything
		if (roundOver || getHand().getCards().size() < 2) {
			return super.toString();
		} else {
			// Otherwise, hide the second card
			return "Dealer's Hand:   " + 
					getHand().getFirstCard() + " ?-?";
		}	
	}
	
}
