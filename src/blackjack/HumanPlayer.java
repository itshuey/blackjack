package blackjack;

public class HumanPlayer extends Player {

	private int balance;
	private int currentBet;
	private HumanPlayer split;
	
	/**
	 * uses super constructor
	 * 
	 * @param name
	 * @param initialBalance
	 */
	public HumanPlayer(String name, int initialBalance) {		
		super(name);
		balance = initialBalance;
		currentBet = 0;
	}	
	
	/**
	 * constructor used for splitting
	 * 
	 * @param original player p
	 */
	public HumanPlayer(HumanPlayer p) {		
		super("Split " + p.getName());
		balance = 0;
		// the bet is equaled
		currentBet = p.getBet();
		// set the split head
		split = p;
	}
	
	////////////////////////
	// Blackjack Operations
	////////////////////////
	
	/**
	 * attempts to bet the input amount
	 * 
	 * @param bet
	 * @return whether or not bet was successful
	 */
	public boolean bet(int bet) {
		if (bet <= balance - currentBet) {
			currentBet += bet;
			return true;
		} else {
			System.out.println("Insufficient funds!");
			System.out.println();
			return false;
		}
	}
	
	/**
	 * doubleDown lets you double your bet 
	 * 		(or bet whatever you have left)
	 * 		to hit once and end your turn
	 * 
	 * @param card, the dealt Card
	 */
	public void doubleDown(Card card) {
		if (split != null) {
			System.out.println("Can't double down! You already split.");
		} else {
			// will double, or get as close as possible
			int bet = Math.min(currentBet, balance-currentBet);
			currentBet += bet;
			hit(card); stay();
		}
	}
	
	/**
	 * split lets you split your first two cards
	 * 		and play them separately
	 * 
	 * @param card, the dealt Card
	 */
	public HumanPlayer split() {
		// initialize split to yourself
		// this is for other methods that check if split is null
		split = this;
		HumanPlayer splitPlayer = new HumanPlayer(this);
		// transfer the second card to the splitPlayer
		splitPlayer.getHand().addToHand(getHand().removeSecondCard());
		return splitPlayer;
	}
	
	/////////////////////
	// Handling Win/Loss 
	/////////////////////

	/**
	 * tie() resets the current bet
	 */
	public void tie() {
		System.out.println(getName() + " got a draw!");
		currentBet = 0; 
		System.out.println("Total balance: $" + balance);
	}
	
	/**
	 * loseBet() subtracts the current bet from your balance
	 */
	public void loseBet() {
		if (isBust()) {
			// information about loss condition!
			System.out.println("Oh no, " + getName() + " busted!");
		} else {
			System.out.println("Oh no, " + getName() + " lost!");
		}
		balance -= currentBet;
		currentBet = 0;
		printBalance();
	}
	
	/**
	 * winBet() adds the current bet to your balance
	 * 
	 * if your hand is a blackjack, multiplies winnings by 1.5!
	 */
	public void winBet() {
		int winnings = currentBet;
		
		// check blackjack condition (can't bj when split)
		if (getHand().isBlackjack() && split == null){
			winnings = (int) (winnings * 1.5);
			System.out.println("Blackjack!!! " + getName() + "'s winnings: $" + winnings);
		} else {
			System.out.println(getName() + " won! Winnings: $" + winnings);
		}
		
		balance += winnings;
		printBalance();
	}
	
	/**
	 * Handles the balance printing
	 * If there's a split, adds the two balances together
	 */
	public void printBalance() {
		if (split != null && !split.equals(this)) {
			// clearing up confusing with split balance
			System.out.println("Total balance: $" + (balance + split.balance));
		} else {
			System.out.println("Total balance: $" + balance);
		}
	}
	
	/**
	 * resets the bets, and hands!
	 */
	public void reset() {
		super.reset();
		getHand().resetHand();
		currentBet = 0;
		split = null;
	}
	
	/////////////////////
	// Getter & Setters
	/////////////////////
	
	/**
	 * Gets current balance
	 * @return balance
	 */
	public int getBalance() {
		return balance;
	}
	
	/**
	 * Gets current bet
	 * @return bet
	 */
	public int getBet() {
		return currentBet;
	}
	
	/**
	 * Gets the split player
	 * @return HumanPlayer split
	 */
	public HumanPlayer getSplitPlayer() {
		return split;
	}
	
	/**
	 * adds input player's balance to own
	 * @param HumanPlayer other
	 */
	public void transferBalance(HumanPlayer other) {
		balance += other.balance;
	}
}
